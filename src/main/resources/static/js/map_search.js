
//----------------------------------------------------
// The location of Uluru
let map;
let markers = [];
let enterAddressLat;
let enterAddressLng;
//-----------------------------------------------------------------------
function initMap() {
    console.log("執行init");
    let uluru = {lat: 23.4598797, lng: 120.1445705};

// The map, centered at Uluru
        map = new google.maps.Map(document.getElementById("map"), {
        zoom: 8,
        center: uluru,
    });
}
//-----------------------------------------------------------------------
function initChangeMap() {
    console.log("執行init");
    let uluru = {lat: enterAddressLat, lng: enterAddressLng};

// The map, centered at Uluru
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 15,
        center: uluru,
    });
}
//-----------------------------------------------------------------------
function Marker() {
    console.log("有執行MAKER");
    console.log("裡面lat: "+ enterAddressLng)
    markers = new google.maps.Marker({
        position: {
            lat:enterAddressLat,
            lng:enterAddressLng
        },
        map: map,
    });
    console.log("執行完marker");

}

//----------------------------------------------------
$(function() {
    $('#btsearch').click(function (e) {
        e.preventDefault();

        let enteraddress = $('#searchAddress').val();
        //創建物件
        let article = {};

        article['enterAddressName'] = enteraddress;

        $.ajax({
            url: '/findByAddress',
            type: 'GET',
            data: article,

            success: function (response) {
                for (let article of response) {
                     enterAddressLat = article.enterAddressLat;
                     enterAddressLng = article.enterAddressLng;
                    Marker();
                }
            }
        });
    });
});
//-----------------------------------------------------------------------






