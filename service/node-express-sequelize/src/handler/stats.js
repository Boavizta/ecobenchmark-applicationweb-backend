const models = require('../model');

module.exports = (req, res, next) => {
  return models.account.statistics()
    .then((lists) => res.send(lists))
    .catch(next);
}
