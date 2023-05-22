
class ListsController < ApplicationController
  before_action :load_account

  def index
    render json: ListResponse.generate(@account, params[:page])
  end

  def create
    @list = @account.lists.new(name: params[:name])
    if @list.save
      render json: @list, status: :created
    else
      render json: @list.errors, status: :unprocessable_entity
    end
  end

  private

  def load_account
    @account = Account.find(params[:account_id])
  end
end
