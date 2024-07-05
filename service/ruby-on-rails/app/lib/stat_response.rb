class StatResponse
  attr_reader :accountId, :accountLogin, :listCount, :taskAvg

  def self.generate
    stats = []
    sql_results.each do |sql_result|
      stats << new(sql_result)
    end
    stats
  end

  def initialize(sql_result)
    @accountId = sql_result["id"]
    @accountLogin = sql_result["login"]
    @listCount = sql_result["list_count"].to_i
    @taskAvg = sql_result["task_avg"].to_f
  end

  private

  def self.sql_results
    ActiveRecord::Base.connection.execute("
      SELECT id, login, COUNT(list_id) AS list_count, ROUND(AVG(nb_tasks), 2) AS task_avg
      FROM (
        SELECT account.id, account.login, list.id list_id, COUNT(task.id) nb_tasks
        FROM account
        INNER JOIN list ON (list.account_id = account.id)
        LEFT JOIN task ON (task.list_id = list.id)
        GROUP BY account.id, account.login, list.id
      ) t
      GROUP BY id, login
    ")
  end
end