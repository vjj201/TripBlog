//----------------------------------------------------
// The location of Uluru
let map;
let marker;
let markers = [];
let controllerForMarkers;
let enterAddressLat;
let enterAddressLng;
photoChange();

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
//幻燈片變換
//~~get幻燈片資料
function photoChange() {
    $.ajax({
        url: "/photoImgChange",
        type: "GET",
        success: function (response) {
            console.log("幻燈片-success方法有進來!");
            let articleTitleArray = [];
            let articlePathArray = [];

            for (let changeImg of response) {
                console.log("幻燈片-for迴圈有進來");
                articleTitleArray.push(changeImg.articleTitle);
                articlePathArray.push(changeImg.saveImgPath);
            }

            let articleTitleArray1 = articleTitleArray[0];
            let articleTitleArray2 = articleTitleArray[1];
            let articleTitleArray3 = articleTitleArray[2];

            let articlePathArray1 = articlePathArray[0];
            let articlePathArray2 = articlePathArray[1];
            let articlePathArray3 = articlePathArray[2];

            html = `
        

        <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1"
                    aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2"
                    aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="${articlePathArray1}" class="d-block mx-auto" width="1000px" height="500px" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h1 class="fw-bold"><a href="https://localhost:8080/article/${articleTitleArray1}" style="text-decoration:none;color: white" target="_blank">${articleTitleArray1}</a></h1>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="${articlePathArray2}" class="d-block mx-auto" width="1000px" height="500px" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h1 class="fw-bold"><a href="https://localhost:8080/article/${articleTitleArray2}" style="text-decoration:none;color: white" target="_blank">${articleTitleArray2}</a></h1>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="${articlePathArray3}" class="d-block mx-auto" width="1000px" height="500px" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h1 class="fw-bold"><a href="https://localhost:8080/article/${articleTitleArray3}" style="text-decoration:none;color: white" target="_blank">${articleTitleArray3}</a></h1>
                    </div>
                </div>
            </div>

            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions"
                data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>

        
        `;
            $("#photoChangeBigBox").html(html);
        },
    });
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





