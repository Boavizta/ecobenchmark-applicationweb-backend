
class ListsController < ApplicationController
  before_action :load_account

  def index
    render json: ListResponse.generate(@account.id, params[:page])
  end

  def create
    @list = @account.lists.new(params.permit(:name))
    if @list.save
      render json: @list, status: :created
    else
      render json: @list.errors, status: :bad_request
    end
  end

  private

  def load_account
    @account = Account.find(params[:account_id])
  end
end