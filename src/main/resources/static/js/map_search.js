
$(function() {
    let position = [];

//----------------------------------------------------
        // The location of Uluru
        const uluru = { lat: -25.344, lng: 131.036 };
        const entertheadress = {lat: position.lat , lng: position.lng}
        // The map, centered at Uluru
        const map = new google.maps.Map(document.getElementById("map"), {
            zoom: 4,
            center: uluru,
        });
        // The marker, positioned at Uluru

    function Marker(enterPosition){
        const marker = new google.maps.Marker({
            position: enterPosition,
            map: map,
        });
    }


//----------------------------------------------------
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

                    console.log(geocode(enteradress));
                    console.log("外面"+position);
                    Marker(entertheadress);
                    console.log("有成功嗎?"+entertheadress.lat);
                }


                }

        });


    });



//將輸入的地址轉換成經緯度
    function geocode(enteradress){
        var location = enteradress;
        axios.get('https://maps.googleapis.com/maps/api/geocode/json',{
            params:{
                address:location,
                key:'AIzaSyDHO6WziMRpUayXSQnX8Xth566rnsZdQeY'
            }
        })
            .then(function (response){
             console.log(response);
             position = response.data.results[0].geometry.location;
             console.log("裡面"+ position.lat);

    //        var formattedAddress = response.data.result[0].formatted_address;
    //        var formattedAddressOutPut = `
     //          <ul class="list-group">
  ////          `;

            })
            .catch(function (error){
                console.log(error);
            });
    }
});


