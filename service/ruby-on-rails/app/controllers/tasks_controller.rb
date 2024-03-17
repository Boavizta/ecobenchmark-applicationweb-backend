
class TasksController < ApplicationController
  before_action :load_list

  def create
    @task = @list.tasks.new(params.permit(:name, :description))
    if @task.save
      render json: @task, status: :created
    else
      render json: @task.errors, status: :bad_request
    end
  end

  private

  def load_list
    @list = List.find(params[:list_id])
  end
end