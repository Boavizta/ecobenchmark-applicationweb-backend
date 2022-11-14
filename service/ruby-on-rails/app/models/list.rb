class List < ApplicationRecord
  self.table_name = 'list'

  belongs_to :account
  has_many :tasks

  def to_s
    "#{name}"
  end
end
