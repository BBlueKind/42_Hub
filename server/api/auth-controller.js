const intraConfig = require('../config/intra.conf')

const auth_payload = {
	grant_type: 'authorization_code',
    client_id: process.env.INTRA_KEY,
    client_secret: process.env.INTRA_SECRET,
	scope: process.env.INTRA_SCOPE,
	redirect_uri: process.env.INTRA_REDIRECT_URI
};

async function authenticate(payload) {
    let token = await intraConfig.auth(payload);
    if (token === undefined || token['error']) {
		console.log('token', token);
        return {
            status: 500,
            message: "Intranet is down, please try again later."
        };
    }
    console.log('token', token);
    return {
        access_token: token.access_token
    };
}

module.exports = authController = {
    authToken: async (req, res) => {
        // code is used to authenticate user to make api calls
        let code = req.body.code;
        if (code !== undefined) {
            let payload = auth_payload;
            payload.code = code;
            let response = await authenticate(payload);
            res.status(200).send(response);
            return;
        }
        res.status(400).send({
            status: 400,
            message: "Code invalid: your request body must provide code param."
        });
    },
    authTokenValidator: async (req, res, next) => {
        // reauthenticating token for security
        let authValue = req.headers.authorization;
        if (authValue === undefined) {
            res.status(400).send({
                status: 400,
                message: "Header invalid: your header must provide access token."
            });
            return;
        }
        intraConfig.authHeader = {
            'authorization': authValue
        };
        // TODO: can add user role with '/me' using auth instead of bearer token
        // for example, only staff members are allowed to make calls to this api
        let response = await intraConfig.get(`/me`);
        if (response.status === 200)
            return next();
        
        res.status(400).send({
            status: 400,
            message: "Access token expired, please authenticate again."
        });
    }
}