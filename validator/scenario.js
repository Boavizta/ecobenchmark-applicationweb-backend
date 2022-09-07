import http from 'k6/http';
import { check, group } from 'k6';

const serverHost = __ENV.SERVER_HOST || 'localhost:8080';

const httpParams = {
  headers: {
    'Content-Type': 'application/json',
  },
};

function expect_status(res, status) {
  if (res.status !== status) {
    console.error(`expected status ${status}, got ${res.status}`);
    return false;
  }

  return true;
}

function expect_header(res, name, value) {
  if (!(name in res.headers)) {
    console.error(`expected header ${name} to present`);
    return false;
  }

  if (res.headers[name] !== value) {
    console.error(`expected header ${name} to be ${value}, got ${res.headers[name]}`);
    return false;
  }

  return true;
}

function expect_keys(obj, keys) {
  if (Array.isArray(obj)) {
    return obj.reduce((res, item) => res && expect_keys(item, keys), true);
  }

  if (Object.keys(obj).length !== keys.length) {
    console.error(`invalid key length, got ${Object.keys(obj)} expected ${keys}`);
    return false;
  }

  for (const key of keys) {
    if (!(key in obj)) {
      console.error(`key ${key} not found in ${JSON.stringify(obj)}`);
      return false;
    }
  }

  return true;
}

function healthcheck() {
  const url = `http://${serverHost}/healthcheck`;
  // invalid behaviors
  group('invalid method', function() {
    check(http.post(url), {
      'post status code is 404': (r) => expect_status(r, 404),
    });
    check(http.put(url), {
      'put status code is 404': (r) => expect_status(r, 404),
    });
    check(http.get(url), {
      'get status code is 404': (r) => expect_status(r, 404),
    });
  });
  // valid behavior
  check(http.head(url), {
    'head status code is 204': (r) => expect_status(r, 204),
  });
}

function login() {
  const url = `http://${serverHost}/api/accounts`;
  // invalid behavior
  group('missing content type', function() {
    const req = http.post(url, JSON.stringify({ login: 'user' }));
    check(req, {
      'status code is 400': (r) => expect_status(r, 400),
    });
  });
  group('invalid payload fields', function() {
    [
      { username: 'user' },
      { login: 'user', role: 'admin' },
    ].forEach((payload) => {
      check(http.post(url, JSON.stringify(payload), httpParams), {
        [`status code is 400 for ${JSON.stringify(payload)}`]: (r) => r.status === 400,
      });
    });
  });
  // valid behavior
  const req = http.post(url, JSON.stringify({ login: 'user' }), httpParams);
  check(req, {
    'status code is 201': (r) => expect_status(r, 201),
    'content length is 105': (r) => expect_header(r, 'Content-Length', '105'),
    'content type is json': (r) => expect_header(r, 'Content-Type', 'application/json'),
    'body only contains everything "id", "login" and "creationDate"': (r) =>
      expect_keys(r.json(), ['id', 'login', 'creationDate']),
  });
  return req.json();
}

function create_list(userId) {
  const url = `http://${serverHost}/api/accounts/${userId}/lists`;
  // invalid behavior
  group('unknown user id', () => {
    const req = http.post(`http://${serverHost}/api/accounts/fe98fe36-47a8-4945-8c52-d6922578cef8/lists`, JSON.stringify({ name: 'foo' }), httpParams);
    check(req, {
      'status code is 404': (r) => expect_status(r, 404),
    });
  });
  group('invalid payload', () => {
    const req = http.post(url, JSON.stringify({ title: 'foo' }), httpParams);
    check(req, {
      'status code is 400': (r) => expect_status(r, 400),
    });
  });
  group('unknown attributes', () => {
    const req = http.post(url, JSON.stringify({ name: 'foo', role: 'admin' }), httpParams);
    check(req, {
      'status code is 400': (r) => expect_status(r, 400),
    });
  });
  // valid behavior
  const req = http.post(url, JSON.stringify({ name: 'foo' }), httpParams);
  check(req, {
    'status code is 201': (r) => expect_status(r, 201),
    'content length is 154': (r) => expect_header(r, 'Content-Length', '154'),
    'content type is json': (r) => expect_header(r, 'Content-Type', 'application/json'),
    'body only contains everything "id", "accountId", "name" and "creationDate"': (r) =>
      expect_keys(r.json(), ['id', 'accountId', 'name', 'creationDate']),
  });
  return req.json();
}

function create_task(listId) {
  const url = `http://${serverHost}/api/lists/${listId}/tasks`;
  // invalid behavior
  group('unknown list id', () => {
    const req = http.post(`http://${serverHost}/api/lists/fe98fe36-47a8-4945-8c52-d6922578cef8/tasks`, JSON.stringify({ name: 'foo', description: 'bar' }), httpParams);
    check(req, {
      'status code is 404': (r) => expect_status(r, 404),
    });
  });
  group('invalid payload', () => {
    const req = http.post(url, JSON.stringify({ title: 'foo' }), httpParams);
    check(req, {
      'status code is 400': (r) => expect_status(r, 400),
    });
  });
  group('unknown attributes', () => {
    const req = http.post(url, JSON.stringify({ name: 'foo', description: 'bar', role: 'admin' }), httpParams);
    check(req, {
      'status code is 400': (r) => expect_status(r, 400),
    });
  });
  
  // valid behavior
  const req = http.post(url, JSON.stringify({ name: 'bar', description: 'hello world' }), httpParams);
  check(req, {
    'status code is 201': (r) => expect_status(r, 201),
    'content length is 179': (r) => expect_header(r, 'Content-Length', '179'),
    'content type is json': (r) => expect_header(r, 'Content-Type', 'application/json'),
    'body only contains everything "id", "listId", "name", "description" and "creationDate"': (r) =>
      expect_keys(r.json(), ['id', 'listId', 'name', 'description', 'creationDate']),
  });
  return req.json();
}

function get_lists(accountId) {
  const url = `http://${serverHost}/api/accounts/${accountId}/lists`;
  // valid behavior
  const req = http.get(url);
  check(req, {
    'status code is 200': (r) => expect_status(r, 200),
    'content length is 346': (r) => expect_header(r, 'Content-Length', '346'),
    'content type is json': (r) => expect_header(r, 'Content-Type', 'application/json'),
    'body only contains everything "id", "accountId", "tasks", "name" and "creationDate"': (r) =>
      expect_keys(r.json(), ['id', 'accountId', 'tasks', 'name', 'creationDate']),
  });
  return req.json();
}

function get_stats() {
  const url = `http://${serverHost}/api/stats`;
  check(http.get(url), {
    'status code is 200': (r) => expect_status(r, 200),
    'content type is json': (r) => expect_header(r, 'Content-Type', 'application/json'),
    'body only contains everything "accountId", "accountLogin", "listCount" and "taskAvg"': (r) =>
      expect_keys(r.json(), ['accountId', 'accountLogin', 'listCount', 'taskAvg']),
  });
}

export default function() {
  group('HEAD /healthcheck', healthcheck);
  const account = group('POST /api/accounts', login);
  const list = group('POST /api/accounts/:accountId/lists', () => create_list(account.id))
  const _task = group('POST /api/lists/:listId/tasks', () => create_task(list.id));
  group('GET /api/accounts/:accountId/lists', () => get_lists(account.id));
  group('GET /api/stats', get_stats);
}