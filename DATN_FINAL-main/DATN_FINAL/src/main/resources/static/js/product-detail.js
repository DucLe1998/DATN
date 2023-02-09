var color = document.getElementById("color");
var bigImg = document.getElementById("bigImg");
var pro = document.getElementById("proId");
var quantity = document.getElementById("quan");
var Parameter = {
    url: 'http://localhost:8080/product/getList',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
	params: { "id": pro.value },
    method: 'GET', //do backend cung cấp 
    responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
}
//gọi ajax = axios => nó trả về cho chúng ta là một promise
/*var promise = axios(Parameter);*/

var promise = axios(Parameter);
promise.then(function (result) {
    console.log(result.data);
    renderCity(result.data);
});
function renderCity(data) {
	
		bigImg.src = "/customerAssets/images/"+ data[0].thumnail;
		bigImg.alt =  data[0].thumnail;
		var smallImg1 = document.getElementById("lining1.jpg");
		console.log(smallImg1);

		for (var i =0; i < data.length ; i++) {
	        var smallImg = document.getElementById(data[i].thumnail);
			smallImg.onclick = function () {
				bigImg.src = "/customerAssets/images/"+smallImg.alt;
				bigImg.alt =  smallImg.alt;
				
			}	
	    }
		
};
function prevImg() {
	var oldImg = bigImg.alt;
	var Parameter = {
	    url: 'http://localhost:8080/product/getList',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
		params: { "id": pro.value },
	    method: 'GET', //do backend cung cấp 
	    responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
	}
	
	var promise = axios(Parameter);
	promise.then(function (result) {
	    for (var i =1; i < result.data.length ; i++) {
			if(oldImg == result.data[i].thumnail){
				
				bigImg.src = "/customerAssets/images/"+result.data[i-1].thumnail;
				bigImg.alt =  result.data[i-1].thumnail;
			}
	    }
	});
	
};
function nextImg() {
	var oldImg = bigImg.alt;
	var Parameter = {
	    url: 'http://localhost:8080/product/getList',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
		params: { "id": pro.value },
	    method: 'GET', //do backend cung cấp 
	    responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
	}
	
	var promise = axios(Parameter);
	promise.then(function (result) {
	    for (var i =0; i < result.data.length -1 ; i++) {
			if(oldImg == result.data[i].thumnail){
				
				bigImg.src = "/customerAssets/images/"+result.data[i+1].thumnail;
				bigImg.alt =  result.data[i+1].thumnail;
			}
	    }
	});
	
}

function chooseColor(x) {
	document.getElementById('S').setAttribute("disabled","disabled");
	document.getElementById('M').setAttribute("disabled","disabled");
	document.getElementById('L').setAttribute("disabled","disabled");
	document.getElementById('XL').setAttribute("disabled","disabled");
	document.getElementById('XXL').setAttribute("disabled","disabled");
	color.innerHTML=x;
	console.log(x);
	var Parameter5 = {
	    url: 'http://localhost:8080/product/getSize',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
		params: { "color": x,
					"proId" : pro.value },
	    method: 'GET', //do backend cung cấp 
	    responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
	}
	var promise1 = axios(Parameter5);
	promise1.then(function (result) {
		console.log(result);
		for (const s of result.data) {
             var dor = document.getElementById(s.size);
				console.log(dor);
				dor.removeAttribute("disabled");
        }
	});
};

function augure1() {
	quantity.value++;
}

function reduce1() {
	quantity.value--;
}