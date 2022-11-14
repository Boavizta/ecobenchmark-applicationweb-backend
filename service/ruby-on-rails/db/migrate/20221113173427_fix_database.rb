class FixDatabase < ActiveRecord::Migration[7.0]
  def change
    rename_table :accounts, :account
    remove_column :account, :updated_at
    rename_column :account, :created_at, :creation_date
    rename_table :lists, :list
    remove_column :list, :updated_at
    rename_column :list, :created_at, :creation_date
    rename_table :tasks, :task
    remove_column :task, :updated_at
    rename_column :task, :created_at, :creation_date
  end
end
