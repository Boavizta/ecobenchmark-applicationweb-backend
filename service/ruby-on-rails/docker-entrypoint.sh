#!/bin/bash

/workdir/bin/rails db:migrate RAILS_ENV=development

/workdir/bin/rails server -b 0.0.0.0