
class TasksController < ApplicationController
  before_action :load_list

  def create
    @task = @list.tasks.new(name: params[:name], description: params[:description])
    if @task.save
      render json: @task, status: :created
    else
      render json: @task.errors, status: :unprocessable_entity
    end
  end

  private

  def load_list
    @list = List.find(params[:list_id])
  end
end
