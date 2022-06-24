const { Model } = require('sequelize');
const { BINARYUUID,VIRTUALBINARYUUID } = require("@hypercharlie/sequelize-binary-uuid");

module.exports = (sequelize, DataTypes) => {
  class Task extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Task.belongsTo(models.list, {
        foreignKey: 'listId',
      });
    }
  }

  Task.init({
    bid: BINARYUUID({
      field: 'id',
      primaryKey: true,
      allowNull: false
    }),
    id: VIRTUALBINARYUUID("bid", "id"),
    listId: BINARYUUID({
      allowNull: false
    }),
    name: {
      type: DataTypes.TEXT,
    },
    description: {
      type: DataTypes.TEXT,
    },
  }, {
    sequelize,
    tableName: 'task',
    createdAt: 'creationDate',
    updatedAt: false,
    underscored: true,
    modelName: 'task',
  });

  return Task;
};