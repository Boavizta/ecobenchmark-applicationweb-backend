class Account < ApplicationRecord
  self.table_name = "account"

  has_many :lists
end