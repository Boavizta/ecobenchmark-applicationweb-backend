
class ListsController < ApplicationController
  before_action :load_account

  def index
    @list = ListResponse.generate(@account.id, params[:page])
    @list_json = @list.to_json.to_str.gsub('creation_date', 'creationDate').gsub('account_id', 'accountId')
    response.headers['Content-Type'] = 'application/json'
    response.headers['Content-Length'] = @list_json.length
    render json: @list_json
  end

  def create
    @list = @account.lists.new(params.permit(:name))
    if @list.save
      @list_json = @list.to_json.to_str.gsub('creation_date', 'creationDate').gsub('account_id', 'accountId')
      response.headers['Content-Type'] = 'application/json'
      response.headers['Content-Length'] = @list_json.length
      render json: @list_json, status: :created
    else
      response.headers['Content-Type'] = 'application/json'
      response.headers['Content-Length'] = @list.errors.to_json.to_str.length
      render json: @list.errors, status: :bad_request
    end
  end

  private

  def load_account
    @account = Account.find(params[:account_id])
  end
end