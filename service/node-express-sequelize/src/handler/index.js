const {Router} = require('express');

const router = new Router();

router.head('/healthcheck', require('./healthcheck'));
router.post('/api/accounts', require('./create-account'));
router.post('/api/accounts/:account/lists', require('./create-list'));
router.get('/api/accounts/:account/lists', require('./list-list'));
router.post('/api/lists/:list/tasks', require('./create-task'));
router.get('/api/stats', require('./stats'));

module.exports = router;
