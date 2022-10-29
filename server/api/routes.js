const controller = require('./controller');

module.exports = (app) => {
    // Info about the seat selector microservice
    app.route("/").get(controller.getAbout);
    app.route("/about").get(controller.getAbout);
    
    // Getting all exam seats
    app.route("/profile").get(controller.getProfile);
};