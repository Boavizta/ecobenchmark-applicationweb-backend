class StatsController < ApplicationController
  def index
    render json: StatResponse.generate
  end
end
