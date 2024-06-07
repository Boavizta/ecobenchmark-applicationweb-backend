class StatsController < ApplicationController
  def index
    response.headers['Content-Type'] = 'application/json'
    render json: StatResponse.generate
  end
end