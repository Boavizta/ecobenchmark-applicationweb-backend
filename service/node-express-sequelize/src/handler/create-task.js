const Joi = require('joi');
const models = require('../model');

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
      listId: req.params.list,
    })
    .then((task) => res.send(task))
    .catch(next);
}