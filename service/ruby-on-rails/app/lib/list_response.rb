class ListResponse
  attr_reader :lists

  def self.generate(account_id, page = nil)
    new(account_id, page).lists
  end

  def initialize(account_id, page = nil)
    @account_id = account_id
    @page = page.to_i - 1
    @lists = []
    sql_results.each do |result|
      existing_index = @lists.index { |list| list["id"] == result["id"] }
      if existing_index.nil?
        @lists << {
          "id" => result["id"],
          "name" => result["name"],
          "creation_date" => result["creation_date"],
          "account_id" => result["account_id"],
          "tasks" => [
            {
              "id" => result["task_id"],
              "name" => result["task_name"],
              "description" => result["description"],
              "creation_date" => result["task_creation_date"],
            }
          ]
        }
      else
        @lists[existing_index]["tasks"] << {
          "id" => result["task_id"],
          "name" => result["task_name"],
          "description" => result["description"],
          "creation_date" => result["task_creation_date"],
        }
      end
    end
  end

  private

  def sql_results
    ActiveRecord::Base.connection.execute("
      SELECT l.id, l.name, l.creation_date, l.account_id, t.id AS task_id, t.name AS task_name, t.description, t.creation_date AS task_creation_date
      FROM list l
      LEFT JOIN task t ON l.id = t.list_id
      WHERE l.account_id = '#{@account_id}' AND l.id IN (
        SELECT id
        FROM list
        WHERE account_id = '#{@account_id}'
        LIMIT 10
        OFFSET #{10 * (@page < 0 ? 0 : @page)}
      )
    ")
  end
end