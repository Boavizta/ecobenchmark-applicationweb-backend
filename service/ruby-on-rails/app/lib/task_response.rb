class TaskResponse
  attr_reader :id, :name, :description, :creationDate

  def initialize(sql_result)
    @id = sql_result["task_id"]
    @name = sql_result["task_name"]
    @description = sql_result["description"]
    @creationDate = sql_result["task_creation_date"]
  end
end