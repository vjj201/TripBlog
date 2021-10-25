
//----------------------------------------------------
// The location of Uluru
let map;
let markers = [];
function initMap() {
    console.log("執行init");
    let uluru = {lat: -25.344, lng: 131.036};

// The map, centered at Uluru
        map = new google.maps.Map(document.getElementById("map"), {
        zoom: 4,
        center: uluru,
    });
}


//----------------------------------------------------
$(function() {


    $('#btsearch').click(function (e) {

        e.preventDefault();


        let enteraddress = $('#searchAddress').val();

        //創建物件
        let article = {};

        article['enterAddress'] = enteraddress;

        $.ajax({
            url: '/findByAddress',
            type: 'GET',
            data: article,
            success: function (response) {
         //       geocode(response);
         //       article['enterAddress'] = response[0].address;
                for(let article of response){
                    let enteradress = article.enterAddress;
                    geocode(enteradress);


   //                 console.log("外面"+changeposition.lat);

                }

                }

        });


    });



//將輸入的地址轉換成經緯度
    function geocode(enteradress){
        let location = enteradress;
        axios.get('https://maps.googleapis.com/maps/api/geocode/json',{
            params:{
                address:location,
                key:'AIzaSyDHO6WziMRpUayXSQnX8Xth566rnsZdQeY'
            }
        })
            .then(function (response){

                let changeposition = {};
                let A;
                let B;
                let position;
             console.log(response);
             changeposition = response.data.results[0].geometry.location;
             console.log("changeposition"+ changeposition);
             console.log("裡面"+ changeposition.lat);
                 A = changeposition.lat;
                 B = changeposition.lng;
                position = {lat: A, lng: B};
                console.log(position.lat);
                console.log("A"+A);
                console.log("B"+B);
//--------------------------------------------------------------
                function initMap() {
                    console.log("執行init");
                    let uluru = {lat: position.lat, lng: position.lng};
// The map, centered at Uluru
                    map = new google.maps.Map(document.getElementById("map"), {
                        zoom: 15,
                        center: uluru,
                    });
                }

 //----------------------------------------------------------------------
                initMap();
                Marker();

                function Marker() {
                    console.log("有執行MAKER");
                    console.log("裡面lat: "+ position.lat)
                    markers = new google.maps.Marker({
                        position: {
                            lat:position.lat,
                            lng:position.lng
                        },
                        map: map,
                    });
                    console.log("執行完marker");

                }

            })
            .catch(function (error){
                console.log(error);
            });
    }
});


