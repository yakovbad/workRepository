var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index',
      { title: 'Морской бой',
        body : '<h1> Это главаня страница</h1>'

      });
});

module.exports = router;
