# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema[7.0].define(version: 2022_11_13_173427) do
  # These are extensions that must be enabled in order to support this database
  enable_extension "pgcrypto"
  enable_extension "plpgsql"

  create_table "account", id: :uuid, default: -> { "gen_random_uuid()" }, force: :cascade do |t|
    t.text "login"
    t.datetime "creation_date", null: false
  end

  create_table "list", id: :uuid, default: -> { "gen_random_uuid()" }, force: :cascade do |t|
    t.uuid "account_id", null: false
    t.text "name"
    t.datetime "creation_date", null: false
    t.index ["account_id"], name: "index_list_on_account_id"
  end

  create_table "task", id: :uuid, default: -> { "gen_random_uuid()" }, force: :cascade do |t|
    t.uuid "list_id", null: false
    t.text "name"
    t.text "description"
    t.datetime "creation_date", null: false
    t.index ["list_id"], name: "index_task_on_list_id"
  end

  add_foreign_key "list", "account"
  add_foreign_key "task", "list"
end
