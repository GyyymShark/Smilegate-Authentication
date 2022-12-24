// 모든 라우터 js 파일을 컨트롤하는 컨트롤러 

var express = require('express');   //node_modules에 있는 express에 관련된 함수 모듈을 가져옴(객체는 아님. 반환값 함수)
var app = express();    
var router = express.Router();      //라우터
var path = require('path');         //상대경로로 편리하게 이동할 수 있는 객체

var main = require('./main/main');
var join = require('./join/join');
var login = require('./login/login');
var mypage = require('./mypage/mypage');



router.use('/main', main);
router.use('/join', join);
router.use('/login', login);
router.use('/mypage', mypage);


router.get('/', function(req, res){
  res.render('index.ejs');
})

module.exports = router;





