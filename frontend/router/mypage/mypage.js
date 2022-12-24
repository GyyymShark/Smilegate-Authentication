var express = require('express');   //node_modules에 있는 express에 관련된 함수 모듈을 가져옴(객체는 아님. 반환값 함수)
var app = express();    
var router = express.Router();      //라우터
var path = require('path');         //상대경로로 편리하게 이동할 수 있는 객체
var mysql = require('mysql');


/* 데이터베이스 세팅 */
var connection = mysql.createConnection({     //mysql connection 생성 
  host : 'localhost',
  port : 3306,
  user : 'root',
  password : 'root',
  database : 'middlenote',        //데이터베이스 이름
  multipleStatements: true
});
connection.connect();       //mysql 연동


router.get('/',function(req,res){   //임시로 마이페이지 조회
    //판매목록, 구매목록, 약속목록 데이터 전달해줌
    
 res.render('mypage');

})




router.post('/create', function(req, res){ //약속 생성

var promiseData=req.body;
var productId="'"+promiseData.productId +"'";
var sellerId="'"+promiseData.sellerId +"'";
var buyerId="'"+promiseData.buyerId +"'";

 var sql1=`SELECT * from promise WHERE productId=${productId}`;    //약속 존재하는지 조회하는 쿼리
 var sql2=`INSERT INTO promise(productId, sellerId, buyerId) VALUES(${productId}, ${sellerId}, ${buyerId})`;    //약속 생성하는 쿼리

 connection.query(sql1, function(err, data){
    if(err) throw err;
    else{
        if(data.length){    //이미 약속이 존재할때
            res.write("<script>alert('You already have a promise')</script>");
            res.write("<script>window.location=\"../product\"</script>");
        }

        else{   
            connection.query(sql2, function(err2, data2){
                if(err2) throw err2;
                else{
                    return res.redirect(`/product/detail/${promiseData.productId}`);
                }
            });
        }
    }
 });

})


router.post('/cancel',function(req, res){     //약속 취소
    var productId="'"+req.body.productId+"'";
    var statusId=req.body.statusId;


    var sql1=`SELECT COUNT(*) AS promiseCount FROM promise WHERE productId=${productId}`;   //삭제할 약속이 존재하는지 조회하는 쿼리
    var sql3=`DELETE FROM promise WHERE productId=${productId}`;

    connection.query(sql1, function(err1,data1){
        if(err1) throw err1;
        else{
            if(data1[0].promiseCount>0){    //약속이 존재할때
                if(statusId==1){    //약속이 종료되지 않았을때
                    connection.query(sql3, function(err2, data2){   //약속을 지운다
                        if(err2) throw err2;
                        else{
                            return res.redirect(`/product/detail/${req.body.productId}`);
                        }
                    })
                }
                else{   //약속이 종료됐을때
                    return res.redirect(`/product`);
                }
             }
            else{   //약속이 존재하지 않을때
                res.write("<script>alert('promise does not exist')</script>");
                res.write("<script>window.location=\`../product\`</script>");
            }
        }
    })

})



router.post('/confirm' ,function(req,res){  //약속 확정
    var productId="'"+req.body.productId+"'";
    var sql1=`SELECT COUNT(*) AS promiseCount FROM promise WHERE productId=${productId}`;   //약속이 존재하는지 조회하는 쿼리
    var sql2=`UPDATE product SET statusId=2 
    WHERE productId=${productId}`;  //글의 거래 상태를 바꾸는 쿼리

    connection.query(sql1, function(err1, data1){
        if(err1) throw err1;
        else{
            if(data1[0].promiseCount>0){    //약속이 존재할 때
                connection.query(sql2, function(err2, data2){   //글의 거래 상태를 바꾼다
                    if(err2) throw err2;
                    else{
                        return res.redirect(`/product`);
                    }
                })
            }
            else{   //약속이 존재하지 않을때
                res.write("<script>alert('promise does not exist')</script>");
                res.write("<script>window.location=\`../product\`</script>");
            }
            
        }
    })
})


router.post('/mypage', function(req,res){  //마이페이지 이동
    var userId = req.user;
    var password = req.body.password;
    
    var sql=`SELECT nickname FROM user WHERE id=${userId} and password='${password}'`;

    connection.query(sql, function(err,data){
        if(err) throw err;
        if(data[0]){
            res.render('mypage_edit', {user: data[0].nickname});
        }
        else{
            res.write("<script>alert('Wrong Password')</script>");
            res.write("<script>window.location=\"../mypage\"</script>");
        }
        
        
})
})

router.post('/edit', function(req,res){
    var user=req.user;
    var id="'"+user+"'";
    var nickname="'"+userData.nickname+"'";
    var password="'"+userData.password+"'";
    var city="'"+userData.city+"'";
    var gu="'"+userData.gu+"'";
    var dong="'"+userData.dong+"'";
    var detailAddress="'"+userData.detailAddress+"'";


    var sql=`UPDATE user SET nickname=${nickname},
    password=${password}, city=${city},
    gu=${gu}, dong=${dong}, detailAddress=${detailAddress}
    WHERE id=${id}`;


    connection.query(sql, function(err,data){
        if(err) throw err;
        else{
            res.redirect('/mypage');
        }
    })

})

router.post('/passwordCheck', function(req, res){
    var password = req.body.password;
    console.log(password);
})


//약속진행중
//약속완료 -> 구매, 판매





module.exports = router;