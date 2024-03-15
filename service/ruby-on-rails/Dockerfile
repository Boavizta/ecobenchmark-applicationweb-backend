FROM ruby:3.2.2-slim

ENV PORT=8080

RUN apt-get update -qq && apt-get install -y build-essential git pkg-config libpq-dev postgresql-client

WORKDIR /workdir
COPY Gemfile Gemfile.lock /workdir/
RUN bundle install
COPY . /workdir
RUN mkdir -p /workdir/lib/tasks /workdir/log /workdir/tmp/pids /workdir/vendor

CMD ["/workdir/bin/rails", "server", "-b", "0.0.0.0"]