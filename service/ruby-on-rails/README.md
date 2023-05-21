# README

This README would normally document whatever steps are necessary to get the
application up and running.

Things you may want to cover:

* Ruby version

* System dependencies

* Configuration

* Database creation

* Database initialization

* How to run the test suite

* Services (job queues, cache servers, search engines, etc.)

* Deployment instructions

* ...


# Ruby on Rails - Eco Benchmark

## Context

https://github.com/Boavizta/ecobenchmark-applicationweb-backend

## Application

### Generation

```
rails new ecobenchmark \
  --api \
  --database=postgresql \
  --skip-action-mailer \
  --skip-action-mailbox \
  --skip-action-text \
  --skip-active-job \
  --skip-active-storage \
  --skip-action-cable \
  --skip-asset-pipeline \
  --skip-javascript \
  --skip-hotwire \
  --skip-test \
  --skip-system-test
```

### Specifities

#### Creation timestamp

To use `creation_date` as the creation timestamp instead of the Rails-default `created_at`, we set an alias in `app/models/application_record.rb`

```rb
class ApplicationRecord < ActiveRecord::Base
  # ...

  alias_attribute :created_at, :creation_date
end
```

#### Initial schema

Because database tables are already defined, we create the database schema with a database migration file present in the `db/migrations` folder.

The `if_not_exists` prevent the application to override the existing database.