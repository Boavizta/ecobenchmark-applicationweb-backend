const Joi = require('joi');
const models = require('../model');

const schema = Joi.object().keys({ 
  page: Joi.number(),
});

module.exports = (req, res, next) => {
  const query = schema.validate(req.query, {stripUnknown: true});
  if (query.error) return next(query.error);
  return models.list.findByAccount(req.params.account)
    .then((lists) => res.send(lists))
    .catch(next);
}
