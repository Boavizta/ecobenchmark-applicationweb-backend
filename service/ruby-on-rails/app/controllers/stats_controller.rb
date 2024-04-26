class StatsController < ApplicationController
  def index
    @stats = StatResponse.generate
    response.headers['Content-Type'] = 'application/json'
    response.headers['Content-Length'] = @stats.to_json.to_str.length
    render json: @stats
  end
end