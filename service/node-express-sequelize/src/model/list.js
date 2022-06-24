const { Model, QueryTypes } = require('sequelize');
const { BINARYUUID, VIRTUALBINARYUUID,toBinaryUUID,fromBinaryUUID } = require("@hypercharlie/sequelize-binary-uuid");

const taskFromResult = (item) => (item.task_id?{
  id: fromBinaryUUID(item.task_id),
  name: item.task_name,
  description: item.description,
  creationDate: item.taskCreationDate,
}:null);

const aggregateLists = (result) => {
  const lists = result.reduce((res, item) => {
    if (!res.has(item.id)) {
      res.set(item.id, {
        id: fromBinaryUUID(item.id),
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

      console.log(accountId);
      return sequelize.query(`

        SELECT
          l.id AS id,
          l.name,
          l.creation_date,
          l.account_id,
          t.id AS task_id,
          t.name AS task_name,
          t.description,
          t.creation_date AS task_creation_date
        FROM list l
               LEFT JOIN task t ON l.id = t.list_id
        WHERE
          l.account_id = :account_id
          AND l.id IN (select id from (SELECT id FROM list WHERE account_id = :account_id LIMIT :offset,:limit) tmp)
        `, {
          replacements: {
            account_id: toBinaryUUID(accountId),
            limit: size,
            offset: page * size,
          },
          type: QueryTypes.SELECT,
        }).then(aggregateLists);
    }
  }

  List.init({
    bid: BINARYUUID({
      field: 'id',
      primaryKey: true,
      allowNull: false
    }),
    id: VIRTUALBINARYUUID("bid", "id"),
    login: {
      allowNull: true,
      type: DataTypes.TEXT,
      unique: true,
    },
    accountId : BINARYUUID({
      allowNull: false
    }),
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