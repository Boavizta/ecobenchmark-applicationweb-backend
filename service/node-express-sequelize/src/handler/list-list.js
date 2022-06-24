const Joi = require('joi');
const models = require('../model');

const PAGE_SIZE = 20;

const schema = Joi.object().keys({ 
  page: Joi.number(),
});

module.exports = (req, res, next) => {
  const query = schema.validate(req.query, {stripUnknown: true});
  if (query.error) return next(query.error);
  return models.list
    .findAll({
      where: {
        accountId: req.params.account,
      },
      include: {
        all: true,
        nested: true,
      },
      limit: PAGE_SIZE,
      offset: PAGE_SIZE * query.value.page,
    })
    .then((lists) => res.send(lists))
    .catch(next);
}
