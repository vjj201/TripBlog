//----------------------------------------------------
// The location of Uluru
let map;
let marker;
let markers = [];
let controllerForMarkers;
let enterAddressLat;
let enterAddressLng;
// let contentString = "幹你娘";

//-----------------------------------------------------------------------
function initMap() {
    console.log("執行init");
    let uluru = {lat: 23.4598797, lng: 120.1445705};

// The map, centered at Uluru
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 8,
        center: uluru,
    });

    let points = [];
    let inpoint;
    let bounds = new google.maps.LatLngBounds();
    // article['enterAddressName'] = enteraddress;


    $.ajax({
        url: '/randomArticle',
        type: 'GET',
        // data: article,
        success: function (response) {
            let a = 0;
            for (let article of response) {
//------------------依照經緯度產生標籤----------------------------------------------------
                enterAddressLat = article.enterAddressLat;
                enterAddressLng = article.enterAddressLng;
                Marker();
                // for (let markerFor of markers) {
                //     let x = 0;
                //     $(markerFor).click(mapsInfoWindos);
                //     markers[x] = markerFor;
                //     console.log("click"+markers[x]);
                //     x++;
                // }
//------------------依照地標調整放大倍數----------------------------------------------------
                inpoint = new google.maps.LatLng(enterAddressLat, enterAddressLng);
                console.log("迴圈裡面inpoint " + inpoint);
                points[a] = inpoint;
                console.log("迴圈裡面points " + points[a]);
                a++;
            }
            console.log("迴圈外面 " + points);
//---------------執行自動調整視窗方法-------------------------
            for (var i = 0; i < points.length; i++) {
                console.log("extend裡面 " + points[i]);
                bounds.extend(points[i]);
            }
            map.fitBounds(bounds);
//---------------執行自動調整視窗方法結束-------------------------
        }
    });
    // 載入最新商品
    getNewestProducts();
}

//-----------------------------------------------------------------------
// function initChangeMap() {
//     console.log("執行init");
//     let uluru = {lat: enterAddressLat, lng: enterAddressLng};
//
// // The map, centered at Uluru
//     map = new google.maps.Map(document.getElementById("map"), {
//         zoom: 15,
//         center: uluru,
//     });
// }
//-----------------------------------------------------------------------
//-----------------------------------------------------------------------
function mapsInfoWindos(){
    var infowindow = new google.maps.InfoWindow({
        content: contentString,
        position: marker.position,
        maxWidth:200,
        pixelOffset: new google.maps.Size(100, -20)
    });
    infowindow.open(map,marker);
    console.log("mapsInfoWindos外面")
}

//-----------------------------------------------------------------------
function Marker() {
    controllerForMarkers = 0;
    console.log("有執行MAKER");
    console.log("裡面lat: " + enterAddressLng)
    marker = new google.maps.Marker({
        position: {
            lat: enterAddressLat,
            lng: enterAddressLng
        },
        map: map,
    });
    console.log("執行完marker");
    // markers[controllerForMarkers] = marker;
    // controllerForMarkers++;
    // console.log( markers[controllerForMarkers])
}

//----------------------------------------------------
$(function () {

        // e.preventDefault();
        //  initMap();
        // let enteraddress = $('#searchAddress').val();
        //創建物件
        // let article = {};

//    $(markers[0]).click(mapsInfoWindos);
});
//-----------------------------------------------------------------------

function getNewestProducts() {
    $.ajax({
        url: '/newestProduct',
        type: 'GET',
        statusCode: {
            200: function (response) {
                let products = response.content;
                let trHTML = '';
                $.each(products, function (i, product) {
                    trHTML +=
                        '<div class="col-12 col-md-6 col-xl-3 mb-4">' +
                        '<div class="card mr-3" style="height: 500px; overflow: hidden">' +
                        '<img src="/shop/productPic/' +
                        product.id +
                        '" class="card-img-top object-cover" alt="..." style="height: 400px; overflow: hidden">' +
                        '<div class="card-body">' +
                        '<h4 class="card-title">' +
                        product.productName +
                        '</h4>' +
                        '<p class="card-text" style="height: 100px; overflow: hidden; object-fit: cover">' +
                        product.productDetail +
                        '</p>' +
                        '<a href="/shop/product/' +
                        product.id +
                        '" class="btn btn-primary">看看商品詳細介紹</a>' +
                        '</div></div></div>';
                })
                $('#newestProductBody').html(trHTML);
            }
        }
    })
}



