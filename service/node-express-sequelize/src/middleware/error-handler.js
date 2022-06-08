module.exports = (err, req, res, next) => {
  if (err.isJoi) {
    return res.status(422).json(err);
  }
  if (err.status) {
    res.status(err.status);
  } else {
    console.log(err);
    res.status(500);
  }
  return res.send(err);
};
