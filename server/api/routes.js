const controller = require('./controller');

module.exports = (app) => {
    // Info about the seat selector microservice
    app.route("/").get(controller.getAbout);
    app.route("/about").get(controller.getAbout);
    
	// Profile
    app.route("/profile").get(controller.getProfile);
};