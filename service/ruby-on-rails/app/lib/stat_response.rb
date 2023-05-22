class StatResponse
  attr_reader :stats

  def self.generate
    new.stats
  end

  def initialize
    @stats = []
    sql_results.each do |result|
      @stats << {
        "account_id" => result["id"],
        "account_login" => result["login"],
        "nb_list" => result["nb_list"].to_i,
        "avg_tasks" => result["avg_tasks"].to_f,
      }
    end
  end

  private

  def sql_results
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