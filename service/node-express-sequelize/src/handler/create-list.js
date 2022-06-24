const Joi = require('joi');
const models = require('../model');
const { toBinaryUUID } = require ("@hypercharlie/sequelize-binary-uuid");

const schema = Joi.object().keys({
  name: Joi.string().required(),
}).required();

module.exports = (req, res, next) => {
  const body = schema.validate(req.body, {stripUnknown: true});
  if (body.error) return next(body.error);
  return models.list
    .create({
      ...body.value,
      accountId: toBinaryUUID(req.params.account),
    })
    .then((list) => res.send({
            id: list.id,
            name: list.name,
            creation_date: list.creationDate
        })
        )
    .catch(next);
}