Rails.application.routes.draw do
  resources :tasks
  resources :lists
  resources :accounts
  root "accounts#index"
end
