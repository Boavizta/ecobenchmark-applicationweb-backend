const { Model, QueryTypes } = require('sequelize');

const taskFromResult = (item) => ({
  id: item.task_id,
  listId: item.listId,
  name: item.task_name,
  description: item.description,
  creationDate: item.taskCreationDate,
});

const aggregateLists = (result) => {
  const lists = result.reduce((res, item) => {
    if (!res.has(item.id)) {
      res.set(item.id, {
        id: item.id,
        accountId: item.accountId,
        name: item.name,
        creationDate: item.creationDate,
        tasks: [taskFromResult(item)],
      });
    } else {
      res.get(item.id).tasks.push(taskFromResult(item));
    }
    return res;
  }, new Map());
  return [...lists.values()];
};

module.exports = (sequelize, DataTypes) => {
  class List extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      List.belongsTo(models.account, {
        foreignKey: 'accountId',
      });
      List.hasMany(models.task, {
        foreignKey: 'listId',
      });
    }

    static findByAccount(accountId, page, size = 10) {
      return sequelize.query(`
          SELECT
              l.id,
              l.account_id,
              l.name,
              l.creation_date,
              t.id AS task_id,
              t.name AS task_name,
              t.description,
              t.creation_date AS task_creation_date
          FROM list l
              LEFT JOIN task t ON l.id = t.list_id
          WHERE
              l.account_id = :account_id
              AND l.id IN (
                  SELECT id
                  FROM list
                  WHERE account_id = :account_id
                  LIMIT :limit OFFSET :offset
              )
        `, {
          replacements: {
            account_id: accountId,
            limit: size,
            offset: page * size,
          },
          type: QueryTypes.SELECT,
        }).then(aggregateLists);
    }
  }

  List.init({
    id: {
      primaryKey: true,
      allowNull: false,
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV4,
    },
    accountId: {
      type: DataTypes.UUID,
      allowNull: false,
    },
    name: {
      allowNull: false,
      type: DataTypes.TEXT,
      unique: true,
    },
  }, {
    sequelize,
    tableName: 'list',
    createdAt: 'creationDate',
    updatedAt: false,
    underscored: true,
    modelName: 'list',
  });

  return List;
};