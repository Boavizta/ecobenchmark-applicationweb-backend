const { Sequelize } = require('sequelize');

module.exports = new Sequelize(process.env.DATABASE_URL, {
  pool: {
    max: process.env.DATABASE_MAX_POOL ? +process.env.DATABASE_MAX_POOL : 10,
  },
});