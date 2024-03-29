FROM ruby:3.2.2-slim

ENV PORT=8080

RUN apt-get update -qq && apt-get install -y build-essential git pkg-config libpq-dev postgresql-client

WORKDIR /workdir
COPY . /workdir
RUN bundle install
RUN mkdir -p /workdir/lib/tasks /workdir/log /workdir/tmp/pids /workdir/vendor
RUN chmod u+x /workdir/docker-entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/workdir/docker-entrypoint.sh"]