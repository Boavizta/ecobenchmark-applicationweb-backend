class StatResponse
  attr_reader :account_id, :account_login, :nb_list, :avg_tasks

  def self.generate
    stats = []
    sql_results.each do |sql_result|
      stats << new(sql_result)
    end
    stats
  end

  def initialize(sql_result)
    @account_id = sql_result["id"]
    @account_login = sql_result["login"]
    @nb_list = sql_result["nb_list"].to_i
    @avg_tasks = sql_result["avg_tasks"].to_f
  end

  private

  def self.sql_results
    ActiveRecord::Base.connection.execute("
      SELECT id, login, COUNT(list_id) AS nb_list, ROUND(AVG(nb_tasks), 2) AS avg_tasks
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