class ListResponse
  attr_reader :id, :name, :creationDate, :accountId
  attr_accessor :tasks

  def self.generate(account_id, page = nil)
    lists = []
    sql_results(account_id, page.to_i - 1).each do |sql_result|
      list = lists.detect { |l| l.id == sql_result["id"] }
      if list.nil?
        list = new(sql_result)
        lists << list
      end
      list.tasks << TaskResponse.new(sql_result)
    end
    lists
  end

  def initialize(sql_result)
    @id = sql_result["id"]
    @name = sql_result["name"]
    @creationDate = sql_result["creation_date"]
    @accountId = sql_result["account_id"]
    @tasks = []
  end

  private

  def self.sql_results(account_id, page)
    ActiveRecord::Base.connection.execute("
      SELECT l.id, l.name, l.creation_date, l.account_id, t.id AS task_id, t.name AS task_name, t.description, t.creation_date AS task_creation_date
      FROM list l
      LEFT JOIN task t ON l.id = t.list_id
      WHERE l.account_id = '#{account_id}' AND l.id IN (
        SELECT id
        FROM list
        WHERE account_id = '#{account_id}'
        LIMIT 10
        OFFSET #{10 * (page < 0 ? 0 : page)}
      )
    ")
  end
end