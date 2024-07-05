class StatsController < ApplicationController
  def index
    @stats_json = StatResponse.generate.to_json.to_str.gsub('accountId', 'account_id').gsub('accountLogin', 'account_login').gsub('listCount', 'list_count').gsub('taskAvg', 'task_avg')
    response.headers['Content-Type'] = 'application/json'
    render json: @stats_json
  end
end