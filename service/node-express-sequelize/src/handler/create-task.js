const Joi = require('joi');
const models = require('../model');
const { toBinaryUUID,fromBinaryUUID } = require ("@hypercharlie/sequelize-binary-uuid");

const schema = Joi.object().keys({
  name: Joi.string().required(),
  description: Joi.string().required(),
}).required();

module.exports = (req, res, next) => {
  const body = schema.validate(req.body, {stripUnknown: true});
  if (body.error) return next(body.error);
  return models.task
    .create({
      ...body.value,
      listId: toBinaryUUID(req.params.list),
    })
    .then((task) => res.send({
        id: task.id,
        name: task.name,
        description: task.description,
        creation_date: task.creationDate,
        list_id: fromBinaryUUID(task.listId),
    }))
    .catch(next);
}