class Task < ApplicationRecord
  self.table_name = "task"

  belongs_to :list
end