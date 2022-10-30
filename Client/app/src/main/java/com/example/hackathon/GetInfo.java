package com.example.hackathon;


import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class GetInfo{

    private Context context;

    public GetInfo(Context context){
        this.context = context;
    }
    public static String userName;
    private static String token;

    public static void setUserName(String myString) {
        userName = myString;
    }

    public void reqUserInfo(TextView textViewName, TextView textViewId, ImageView imageView, ImageView qrCode,String code) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.19.246.91:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequest service = retrofit.create(ApiRequest.class);
        SendData sendData = new SendData(code);
        Call<Auth> sendCode = service.sendCode(sendData);
        sendCode.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(@NonNull Call<Auth> call, Response<Auth> response)
            {

                if (!response.isSuccessful()) {
                    textViewName.setText("Error" + response.code());
                    textViewId.setText("Error" + response.code());
                    imageView.setImageURI(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Error.svg/1200px-Error.svg.png"));
                    return ;
                }

                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + response.body().access_token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).build();
                Retrofit retrofit1 = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("http://10.19.246.91:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiRequest service1 = retrofit1.create(ApiRequest.class);
                Call<Repo> repos = service1.listRepos();
                repos.enqueue(new Callback<Repo>() {
                    @Override
                    public void onResponse(@NonNull Call<Repo> call, Response<Repo> response)
                    {
                        if (!response.isSuccessful()) {
                            textViewName.setText("Error" + response.code());
                            textViewId.setText("Error" + response.code());
                            imageView.setImageURI(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Error.svg/1200px-Error.svg.png"));
                            return ;
                        }
                        textViewName.setText(response.body().displayname);
                        textViewId.setText(response.body().email);
                        Picasso.get().load(response.body().image_url).into(imageView);

                        generateQR(response.body().displayname,response.body().email,qrCode);
                    }
                    @Override
                    public void onFailure(Call<Repo> call, Throwable t)
                    {
                        textViewName.setText("Error" + t.getMessage());
                        textViewId.setText("Error" + t.getMessage());
                        imageView.setImageURI(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Error.svg/1200px-Error.svg.png"));
                    }
                });
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t)
            {
                textViewName.setText("Error" + t.getMessage());
                textViewId.setText("Error" + t.getMessage());
                imageView.setImageURI(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Error.svg/1200px-Error.svg.png"));
            }
        });
    }

    private void generateQR(String name, String Email, ImageView qrCode){

        // below line is for getting
        // the windowmanager service.
        QRGEncoder qrgEncoder;
        Bitmap bitmap;
        WindowManager manager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 2 / 2;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(name, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
        }

    }
}
