const { Model, QueryTypes } = require('sequelize');

module.exports = (sequelize, DataTypes) => {
  class Account extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Account.hasMany(models.list, {
        foreignKey: 'accountId',
      });
    }
    
    static statistics() {
      return sequelize.query(`
          select id, login, count(list_id) as nb_list, round(avg(nb_tasks), 2) as avg_tasks
          from (
              select account.id, account.login, list.id list_id, count(task.id) nb_tasks
              from account
              inner join list on (list.account_id=account.id)
              left join task on (task.list_id=list.id)
              group by account.id, account.login, list.id
          ) t
          group by id, login
      `, {
        type: QueryTypes.SELECT,
      });
    }
  }

  Account.init({
    id: {
      primaryKey: true,
      allowNull: false,
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV4,
    },
    login: {
      allowNull: false,
      type: DataTypes.TEXT,
      unique: true,
    },
  }, {
    sequelize,
    tableName: 'account',
    createdAt: 'creationDate',
    updatedAt: false,
    underscored: true,
    modelName: 'account',
  });

  return Account;
};