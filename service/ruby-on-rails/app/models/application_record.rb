class ApplicationRecord < ActiveRecord::Base
  primary_abstract_class

  alias_attribute :created_at, :creation_date
end
