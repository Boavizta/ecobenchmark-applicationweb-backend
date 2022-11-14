class Account < ApplicationRecord
  self.table_name = 'account'

  has_many :lists

  def to_s
    "#{login}"
  end
end
