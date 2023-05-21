Rails.application.routes.draw do
  post "/api/accounts" => "accounts#create"
end
