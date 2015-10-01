var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
    res.render('game', {
        title : 'Игра',
        body : 'Game',
        script : 'src = \'js/game.js\''
    })
});

module.exports = router;