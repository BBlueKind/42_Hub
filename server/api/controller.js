const properties = require('../package.json');
const intraConfig = require('../config/intra.conf')

module.exports = controllers = {
    getAbout(req, res) {
        let aboutInfo = {
            name:properties.name,
            version: properties.version
        }
        res.json(aboutInfo);
    },
    getProfile(req, res) {
		intraConfig.getAll(`/me`).then((info) => {
			res.send(info);
		});
    }
}