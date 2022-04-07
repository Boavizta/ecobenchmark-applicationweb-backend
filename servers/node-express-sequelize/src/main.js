const express = require('express');

const PORT = process.env.PORT || 3000;

const app = express();

app.get('/_healthcheck', (_, res) => res.send('ok'));

app.listen(PORT, (err) => {
  if (err) {
    console.error(err);
    process.exit(1);
  }
  console.log(`server running on port ${PORT}`);
});