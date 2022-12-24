
var existingAddedAmount = 0;
var lastDataTitle;
var lastDataPrice;

$(document).ready(function() {
	refresh();
});


function refresh() {
	
	
	var existingEntries = JSON.parse(localStorage.getItem("allEntries"));
	var addedAmount = (Object.keys(existingEntries).length);
	
	
	console.log(addedAmount);
	console.log(existingAddedAmount);
	
	
		for(var a = 0; a < addedAmount - existingAddedAmount; a++)
	{
		console.log(addedAmount - existingAddedAmount);
		var div = document.createElement('div');
		document.getElementById('thirdrow').appendChild(div);

		div.className='col-lg-4 col-sm-6 d-flex flex-column align-items-center justify-content-center product-item my-3';
		div.innerHTML = '<div class="product" OnClick="movetoDetail('+a+')"><img src="https://picsum.photos/200/300?random=' +(a+existingAddedAmount+addedAmount+1)+ '" alt=""><ul class="d-flex align-items-center justify-content-center list-unstyled icons"><li class="icon"><span class="fas fa-expand-arrows-alt"></span></li><li class="icon mx-3"><span class="far fa-heart"></span></li><li class="icon"><span class="fas fa-shopping-bag"></span></li></ul></div><div class="tag bg-green">new</div><div class="title pt-4 pb-1">Givenchy</div><div class="d-flex align-content-center justify-content-center"> <span class="fas fa-star"></span> <span class="fas fa-star"></span> <span class="fas fa-star"></span> <span class="fas fa-star"></span> <span class="fas fa-star"></span> </div><div class="price">$ 70.0</div>';
		
		var storedTitle = existingEntries[a+existingAddedAmount].title;
		var storedPrice = existingEntries[a+existingAddedAmount].price;
		
			console.log(storedTitle);
	console.log(storedPrice);
		
		
		  document.getElementsByClassName("title pt-4 pb-1")[6+existingAddedAmount+a].innerHTML = storedTitle;
			document.getElementsByClassName("price")[6+existingAddedAmount+a].innerHTML = '$ ' + storedPrice;
	}
	
	
	if(existingAddedAmount < addedAmount)	existingAddedAmount = existingAddedAmount + addedAmount;
	
	
}

function movetoDetail(a) {
	window.location.href='./product_detail_new.html?index=' + a;
}
	



var existingAddedAmount = 0;
var lastDataTitle;
var lastDataPrice;

$(document).ready(function() {
	refresh();
});


/*
function movetoDetailDefault(product) {
	
	var jquerythis = $(product);
	
	var title = $(product).siblings(".title").text();
	var price = $(product).siblings(".price").text();
	window.location.href='./product_detail.html?title=' + title + '&price=' + price;
}
*/


function movetoDetailDefault(product) {
	
	var jquerythis = $(product);
	
	var title = $(product).siblings(".title").text();
	var price = $(product).siblings(".price").text();
	var productId = $(product).siblings(".productid").text();

           

	//window.location.href=`./product/detail/${productId}`;
	window.location.href = `http://localhost:3000/product/detail/${productId}`;
}




function movetoBrandDefault(brand) {
	

	var brandName=$(brand).context.innerHTML;
	console.log(brandName);

	//window.location.href=`./product/${brandName}`;
	window.location.replace(`./product/${brandName}`);
}


/*
function addInterestList(this){
	console.log(this);


	var jquerythis = $(product);
	

	var productId = $(product).children(".productid").text();
	console.log(productId);

           

}
*/

function search(){
	var searchtext=document.getElementById("searchtext").value;
	console.log(searchtext);

	var sendData = {'searchtext' : searchtext};

	sendData = JSON.stringify(sendData);    //ajax로 서버에 보내기 위해 문자열을 json으로 저장. 
	var xhr = new XMLHttpRequest();     //ajax로 브라우저와 서버가 상호작용하기 위한 객체 
	xhr.open('POST', 'http://localhost:3000/product/searches');     //post 형식으로 join.js 호출
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send(sendData);

}

