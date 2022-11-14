# Ruby on Rails Benchmark

## Contexte

https://github.com/Boavizta/ecobenchmark-applicationweb-backend

## Application RoR

```bash
rails new ecobenchmark -d postgresql
cd ecobenchmark
rails g migration EnableUUID
rails g scaffold Account login:text
rails g scaffold List account:references name:text
rails g scaffold Task list:references name:text description:text
```