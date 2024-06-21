class HealthController < ApplicationController
  def check
    head :no_content
  end
end