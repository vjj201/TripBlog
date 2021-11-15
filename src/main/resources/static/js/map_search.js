//----------------------------------------------------
// The location of Uluru
let map;
let marker;
let markers = [];
let controllerForMarkers = 0;
let enterAddressLat;
let enterAddressLng;
let contentString = "你好呀"

function initMap() {
    console.log("執行init");
    let uluru = {lat: 23.4598797, lng: 120.1445705};

// The map, centered at Uluru
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 8,
        center: uluru,
    });
}


$(function () {
    let markers = [];
    firstinitmap();
    $('#btsearch').click(function (e) {
        e.preventDefault();
        firstinitmap();
    });
    $("#changePageAll").on("click", "#pageSearch", function () {
        changlePageMap();
    });
    function firstinitmap(){
        let enteraddress = $('#searchAddress').val();
        let subject = $('#subject option:selected').val();
        let timeDirect = $('#timeDirect option:selected').val();
        //創建物件
        let article = {};
        let points = [];
        let inpoint;

        var bounds = new google.maps.LatLngBounds();
        article['enterAddressName'] = enteraddress;
        article["subject"] = subject;
        article["timeDirect"] = timeDirect;

        $.ajax({
            url: '/firstSearchOfPageEatTravel',
            type: 'GET',
            data: article,

            success: function (response) {
                let a = 0;
                for (let article of response) {
//------------------依照經緯度產生標籤----------------------------------------------------
                    enterAddressLat = article.enterAddressLat;
                    enterAddressLng = article.enterAddressLng;
                    Marker();

//------------------依照地標調整放大倍數----------------------------------------------------
                    inpoint = new google.maps.LatLng(enterAddressLat, enterAddressLng);
                    console.log("迴圈裡面inpoint " + inpoint);
                    points[a] = inpoint;
                    console.log("迴圈裡面points " + points[a]);
                    a++;
                }
                console.log("迴圈外面 " + points);
                console.log("markers"+markers[1].position)



//---------------執行自動調整視窗方法-------------------------
                for (var i = 0; i < points.length; i++) {
                    console.log("extend裡面 " + points[i]);
                    bounds.extend(points[i]);
                }
                map.fitBounds(bounds);
//---------------執行自動調整視窗方法結束-------------------------
            }
        });
    }
    function changlePageMap(){
        let enteraddress = $('#searchAddress').val();
        let subject = $('#subject option:selected').val();
        let timeDirect = $('#timeDirect option:selected').val();
        let pageValue = $("#changePageBox option:selected").val();
        console.log("pageValue=" + pageValue);
        let page = pageValue - 1;
        //創建物件
        let article = {};
        let points = [];
        let inpoint;
        var bounds = new google.maps.LatLngBounds();
        article['enterAddressName'] = enteraddress;
        article["subject"] = subject;
        article["timeDirect"] = timeDirect;
        article["page"] = page;

        $.ajax({
            url: '/changeSearchOfPageEatTravel',
            type: 'GET',
            data: article,
            success: function (response) {
                let a = 0;
                for (let article of response) {
//------------------依照經緯度產生標籤----------------------------------------------------
                    enterAddressLat = article.enterAddressLat;
                    enterAddressLng = article.enterAddressLng;
                    Marker();

//------------------依照地標調整放大倍數----------------------------------------------------
                    inpoint = new google.maps.LatLng(enterAddressLat, enterAddressLng);
                    console.log("迴圈裡面inpoint " + inpoint);
                    points[a] = inpoint;
                    console.log("迴圈裡面points " + points[a]);
                    a++;
                }
                console.log("迴圈外面 " + points);
                console.log("markers"+markers[1].position)
//---------------執行自動調整視窗方法-------------------------
                for (var i = 0; i < points.length; i++) {
                    console.log("extend裡面 " + points[i]);
                    bounds.extend(points[i]);
                }
                map.fitBounds(bounds);
//---------------執行自動調整視窗方法結束-------------------------
            }
        });
    }
    function Marker() {
        console.log("有執行MAKER");
        console.log("裡面lat: " + enterAddressLng)
        marker = new google.maps.Marker({
            position: {
                lat: enterAddressLat,
                lng: enterAddressLng
            },
            map: map,
        });
        // $(marker).click(function(){
        //     console.log("有執行產生資訊視窗")
        // });
        markers[controllerForMarkers] = marker;
        console.log("markers" + markers[controllerForMarkers].position);
        console.log("執行完marker");
        controllerForMarkers ++;
        console.log("controllerForMarkers" + controllerForMarkers)

    }
    //-----------------------------------------------------------------------
//-----------------------------------------------------------------------
    function mapsInfoWindos() {
        var infowindow = new google.maps.InfoWindow({
            content: contentString,
            position: marker.position,
            maxWidth: 200,
            pixelOffset: new google.maps.Size(100, -20)
        });
        infowindow.open(map, marker);
        console.log("mapsInfoWindos外面")
    }

//----------------------------------------------------
});
//-----------------------------------------------------------------------





