class AccountsController < ApplicationController
  def create
    @account = Account.new(login: params[:login])
    if @account.save
      @account_json = @account.to_json.to_str.gsub('creation_date', 'creationDate')
      response.headers['Content-Type'] = 'application/json'
      response.headers['Content-Length'] = @account_json.length
      render json: @account_json, status: :created
    else
      response.headers['Content-Type'] = 'application/json'
      response.headers['Content-Length'] = @account.errors.to_json.to_str.length
      render json: @account.errors, status: :bad_request
    end
  end
end