class CreateTasks < ActiveRecord::Migration[7.0]
  def change
    create_table :tasks, id: :uuid do |t|
      t.references :list, null: false, foreign_key: true, type: :uuid
      t.text :name
      t.text :description

      t.timestamps
    end
  end
end
