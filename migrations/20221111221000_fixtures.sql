insert into account (login)
select 'account_'||idx
from generate_series(0, 1000000) idx;

insert into list (account_id, name)
select
    account.id as account_id,
    md5('list_'||account.id||'_'||idx) as name
from account join generate_series(0, 1000) idx on true;

insert into task (list_id, name, description)
select
    list.id as list_id,
    md5('list_'||list.id||'_'||idx||'_name') as name,
    md5('list_'||list.id||'_'||idx||'_description') as description
from list join generate_series(0, 1000) idx on true;
