const models = require('../model');

module.exports = (req, res, next) => {
  return models.account.statistics()
    .then(
      (lists) =>{

        let preparedDataMap = new Map();

        lists.forEach( (list) => {
        

        if (!preparedDataMap.get(list.id)) {
          let prepData = {
            AccountId: list.id,
            AccountLogin: list.login,
            TaskIdMap: new Map(),
          }
          if (list.task_id) {
            prepData.TaskIdMap.set(list.list_id,1);
            prepData.TasksCount=1;
          }
          preparedDataMap.set(list.id, prepData);
        } else {
          if (!preparedDataMap.get(list.id).TaskIdMap.get(list.list_id)) {
            preparedDataMap.get(list.id).TaskIdMap.set(list.list_id,0);
          }
          if (list.task_id) {
            preparedDataMap.get(list.id).TaskIdMap.set(list.list_id,preparedDataMap.get(list.id).TaskIdMap.get(list.list_id)+1);
            preparedDataMap.get(list.id).TasksCount +=1;
            
          }
        }
        
      });

      let stats= [];

      preparedDataMap.forEach((item) => {
        let avgTask = item.TasksCount / item.TaskIdMap.size;
        let stat = {
          id:    item.AccountId,
          login: item.AccountLogin,
          nb_list:    item.TaskIdMap.size,
          avg_tasks:   (Math.round(avgTask*100) / 100),
        };
        stats.push(stat);

      });
        
      res.send(stats)
    })
    .catch(next);
}
