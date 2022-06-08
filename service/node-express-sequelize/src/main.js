const express = require('express');
const bodyParser = require('body-parser');
const morgan = require('morgan');

const PORT = process.env.PORT || 3000;

const app = express();

app.use(morgan('tiny'));
app.use(bodyParser.json());
app.use(require('./handler'));
app.use(require('./middleware/error-handler'));

app.listen(PORT, (err) => {
  if (err) {
    console.error(err);
    process.exit(1);
  }
  console.log(`server running on port ${PORT}`);
});