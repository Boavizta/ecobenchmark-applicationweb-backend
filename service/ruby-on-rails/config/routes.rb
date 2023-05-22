Rails.application.routes.draw do
  match "/healthcheck" => "health#check", via: :head

  post "/api/accounts" => "accounts#create"
  post "/api/accounts/:account_id/lists" => "lists#create"
  post "/api/lists/:list_id/tasks" => "tasks#create"

  get "/api/accounts/:account_id/:lists" => "lists#index"
  get "/api/stats" => "stats#index"
end
