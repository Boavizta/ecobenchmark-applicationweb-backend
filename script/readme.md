# Eco benchmark script

This folder is actually the core of the system. It will orchestrate the database, server and watchers on the remote servers.
Once everything is started, it will run the runner on your local machine.
If you want to run it at home, you'll need to update the `host-*` files in order to make them point to your servers.
Then, you'll need to run the following command from within this folder.

```bash
ansible-playbook -u THE_USER_TO_CONNECT_YOUR_SERVERS -i hosts-default site.yml --extra-vars "service=THE_NAME_OF_THE_SERVICE output_directory=WHERE_THE_RESULT_WILL_BE_WRITEN run_id=A_RANDOM_RUN_ID"
```

- `service` is the name of the service you want to test (can be `go-pgx`, `php-symfony`, etc).
- `output_directory` is where the result of the tests will be written at the end. You can put `$PWD` as a value.
- `run_id` is a unique id amongs all the runs. This allows to save several results for the same test.

To switch use cases, just use a different inventory (in this case `hosts-default`).

