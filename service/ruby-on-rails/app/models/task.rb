class Task < ApplicationRecord
  self.table_name = 'task'

  belongs_to :list

  def to_s
    "#{name}"
  end
end
