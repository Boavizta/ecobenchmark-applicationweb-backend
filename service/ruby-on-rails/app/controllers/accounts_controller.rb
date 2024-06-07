class AccountsController < ApplicationController
  def create
    @account = Account.new(login: params[:login])
    response.headers['Content-Type'] = 'application/json'
    if @account.save
      @account_json = @account.to_json.to_str.gsub('creation_date', 'creationDate')
      render json: @account_json, status: :created
    else
      render json: @account.errors, status: :bad_request
    end
  end
end