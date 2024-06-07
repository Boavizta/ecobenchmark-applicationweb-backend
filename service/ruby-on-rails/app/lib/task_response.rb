class TaskResponse
  attr_reader :id, :name, :description, :creation_date

  def initialize(sql_result)
    @id = sql_result["task_id"]
    @name = sql_result["task_name"]
    @description = sql_result["description"]
    @creation_date = sql_result["task_creation_date"]
  end
end