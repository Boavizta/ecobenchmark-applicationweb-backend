FROM ruby:3.2.2-slim

ENV PORT=8080

USER root

RUN apt-get update -qq && apt-get install -y build-essential git pkg-config libpq-dev postgresql-client

RUN groupadd ror
RUN useradd ror -g ror

COPY . /home/ror
RUN chown -R ror:ror /home/ror

USER ror

WORKDIR /home/ror

RUN bundle install
RUN mkdir -p /home/ror/lib/tasks /home/ror/log /home/ror/tmp/pids /home/ror/vendor
RUN chmod u+x /home/ror/docker-entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/home/ror/docker-entrypoint.sh"]