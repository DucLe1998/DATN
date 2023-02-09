var temp = document.getElementById("temp");
var total = document.getElementById("total");
// option city
var citis = document.getElementById("city");
// option quận huyện
var districts = document.getElementById("district");
// option phường xã
var wards = document.getElementById("ward");
// option phương thức giao hàng
var services = document.getElementById("service");
// get moneys
var moneys = document.getElementById("money");
// get soluongs
var soluongs = document.getElementById("soluong");
// get phivanchuyen
var phivanchuyens = document.getElementById("phivanchuyen");

var Parameter = {
    url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
    headers: {
        "token": "f1e25a8c-6ec0-11ed-b62e-2a5743127145"
    },
    method: 'GET', //do backend cung cấp 
    responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
}
//gọi ajax = axios => nó trả về cho chúng ta là một promise
var promise = axios(Parameter);
// const [ provinceID, provinceName ] = data;
//Xử lý khi request thành công
promise.then(function (result) {
    //console.log(result.data.data)
    renderCity(result.data.data);
});
function renderCity(data) {
    for (const c of data) {
        citis.options[citis.options.length] = new Option(c.ProvinceName, c.ProvinceID);
    }
    citis.onchange = function () {
        district.length = 1;
        ward.length = 1;
        service.length = 1;
        if (this.value != "") {
            var Parameter1 = {
                url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
                params: { "province_id": this.value },
                headers: {
                    "token": "f1e25a8c-6ec0-11ed-b62e-2a5743127145"
                },
                method: 'GET', //do backend cung cấp 
                responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
            }
            var promise = axios(Parameter1);
            promise.then(function (result) {
                //console.log(result)
                for (const d of result.data.data) {
                    district.options[district.options.length] = new Option(d.DistrictName, d.DistrictID);
                }
            })
        }
    };
    district.onchange = function () {
        ward.length = 1;
        service.length = 1;
        //const dataCity = data.filter((n) => n.Id === citis.value);
        //console.log(dataCity);
        //console.log(this.value);
        if (this.value != "") {

            // option phường xã
            var Parameter2 = {
                url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
                params: { "district_id": this.value },
                headers: {
                    "token": "f1e25a8c-6ec0-11ed-b62e-2a5743127145"
                },
                method: 'GET', //do backend cung cấp 
                responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
            }
            var promise = axios(Parameter2);
            promise.then(function (result) {
                //console.log(result)
                for (const w of result.data.data) {
                    ward.options[ward.options.length] = new Option(w.WardName, w.WardCode);
                }
            })

            // option phương thức giao hàng
            var Parameter3 = {
                url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
                params: {
                    "shop_id": 120837,     // id client shop the boys
                    "from_district": 3440,  // quận Nam Từ Liêm - địa chỉ cửa hàng
                    "to_district": this.value
                },
                headers: {
                    "token": "f1e25a8c-6ec0-11ed-b62e-2a5743127145"
                },
                method: 'GET', //do backend cung cấp 
                responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
            }
            var promise = axios(Parameter3);
            promise.then(function (result) {
                //console.log(result)
                for (const s of result.data.data) {
                    service.options[service.options.length] = new Option(s.short_name, s.service_id);
                }
            })
        }
    };
    service.onchange = function () {
        submitForm();
    };    
};


function submitForm() {
    /*event.preventDefault();*/
    //console.log(services.value);
    //console.log(moneys.value);
    //console.log(districts.value);
    //console.log(wards.value);
    var height = soluongs.value * 1;  //cm
    var weight = soluongs.value * 400; //gram

    var Parameter4 = {
        url: ' https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee',//Đường dẫn đến file chứa dữ liệu hoặc api do backend cung cấp
        params: {
            "service_id": services.value,     //phương thức thanh toán
            "insurance_value": moneys.value,  //giá trị sản phẩm
            "from_district_id": 3440,         // quận Nam Từ Liêm - địa chỉ cửa hàng
            "to_district_id": districts.value,
            "to_ward_code": wards.value,
            "height": height,
            "length": 15,
            "weight": weight,
            "width": 15
        },
        headers: {
            "shop_id": 120837,     // id client shop the boys
            "token": "f1e25a8c-6ec0-11ed-b62e-2a5743127145"
        },
        method: 'GET', //do backend cung cấp 
        responseType: 'json', //kiểu Dữ liệu trả về do backend cung cấp
    }
    var promise = axios(Parameter4);
    promise.then(function (result) {
        console.log(result.data.data.total);
        const p = result.data.data.total;     
	const temp2 = Number(temp.value);
	const tong = p+temp2;
    document.getElementById('phivanchuyen').innerHTML = ` ${p} ₫`
	document.getElementById('total').innerHTML = `${tong}`
})
}
