class CreateSchema < ActiveRecord::Migration[7.0]
  def change
    # Required for UUIDs
    enable_extension "pgcrypto"

    # CREATE TABLE account
    # (
    #     id            uuid primary key default gen_random_uuid(),
    #     login         text,
    #     creation_date timestamptz default now()
    # );
    #
    # CREATE INDEX idx_account_creation_date
    #     ON account(creation_date);
    create_table :account, id: :uuid, if_not_exists: true do |t|
      t.text :login
      t.timestamptz :creation_date, null: false, index: { name: :idx_account_creation_date }
    end

    # CREATE TABLE list
    # (
    #     id            uuid primary key default gen_random_uuid(),
    #     account_id    uuid references account(id),
    #     name          text,
    #     creation_date timestamptz default now()
    # );
    #
    # CREATE INDEX idx_list_account_id
    #     ON list(account_id);
    #
    # CREATE INDEX idx_list_creation_date
    #     ON list(creation_date);
    create_table :list, id: :uuid, if_not_exists: true do |t|
      t.references :account, type: :uuid, null: false, foreign_key: { to_table: :account }, index: { name: :idx_list_account_id }
      t.text :name
      t.timestamptz :creation_date, null: false, index: { name: :idx_list_creation_date }
    end

    # CREATE TABLE task
    # (
    #     id            uuid primary key default gen_random_uuid(),
    #     list_id       uuid references list(id),
    #     name          text,
    #     description   text,
    #     creation_date timestamptz default now()
    # );
    #
    # CREATE INDEX idx_task_list_id
    #     ON task(list_id);
    #
    # CREATE INDEX idx_task_creation_date
    #     ON task(creation_date);
    create_table :task, id: :uuid, if_not_exists: true do |t|
      t.references :list, type: :uuid, null: false, foreign_key: { to_table: :list }, index: { name: :idx_task_list_id }
      t.text :name
      t.text :description
      t.timestamptz :creation_date, null: false, index: { name: :idx_task_creation_date }
    end

  end
end
