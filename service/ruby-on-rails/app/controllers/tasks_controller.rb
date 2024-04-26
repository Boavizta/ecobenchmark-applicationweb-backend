
class TasksController < ApplicationController
  before_action :load_list

  def create
    @task = @list.tasks.new(params.permit(:name, :description))
    if @task.save
      @task_json = @task.to_json.to_str.gsub('creation_date', 'creationDate').gsub('list_id', 'listId')
      response.headers['Content-Type'] = 'application/json'
      response.headers['Content-Length'] = @task_json.length
      render json: @task_json, status: :created
    else
      response.headers['Content-Type'] = 'application/json'
      response.headers['Content-Length'] = @task.errors.to_json.to_str.length
      render json: @task.errors, status: :bad_request
    end
  end

  private

  def load_list
    @list = List.find(params[:list_id])
  end
end