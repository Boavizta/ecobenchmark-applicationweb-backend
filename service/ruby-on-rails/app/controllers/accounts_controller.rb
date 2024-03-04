class AccountsController < ApplicationController
  def create
    @account = Account.new(login: params[:login])
    if @account.save
      render json: @account, status: :created
    else
      render json: @account.errors, status: :bad_request
    end
  end
end
