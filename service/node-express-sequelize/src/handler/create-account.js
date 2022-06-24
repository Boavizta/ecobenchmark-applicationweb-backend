const Joi = require('joi');
const models = require('../model');

const schema = Joi.object().keys({
  login: Joi.string().required(),
}).required();

module.exports = (req, res, next) => {
  const body = schema.validate(req.body, {stripUnknown: true});
  if (body.error) return next(body.error);
  return models.account
    .create(body.value,{ plain: true })
    .then((item) =>
        // remap to avoid useless field in API
        res.send({
              id: item.id,
              login: item.login,
              creation_date: item.creationDate
      }))
    .catch(next);
}