#!/bin/bash

/home/ror/bin/rails db:migrate RAILS_ENV=development

/home/ror/bin/rails server -b 0.0.0.0