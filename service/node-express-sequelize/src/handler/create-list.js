const Joi = require('joi');
const models = require('../model');

const schema = Joi.object().keys({
  name: Joi.string().required(),
}).required();

module.exports = (req, res, next) => {
  const body = schema.validate(req.body, {stripUnknown: true});
  if (body.error) return next(body.error);
  return models.list
    .create({
      ...body.value,
      accountId: req.params.account,
    })
    .then((list) => res.send(list))
    .catch(next);
}