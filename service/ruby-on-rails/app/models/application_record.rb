class ApplicationRecord < ActiveRecord::Base
  primary_abstract_class

  before_validation :set_creation_date

  protected

  def set_creation_date
    self.creation_date = Time.now
  end
end
