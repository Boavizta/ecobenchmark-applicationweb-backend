class CreateLists < ActiveRecord::Migration[7.0]
  def change
    create_table :lists, id: :uuid do |t|
      t.references :account, null: false, foreign_key: true, type: :uuid
      t.text :name

      t.timestamps
    end
  end
end
