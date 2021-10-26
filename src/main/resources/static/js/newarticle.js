

$(function() {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    $("#enteraddress").mouseleave(function(){
    $("iframe").attr("src","https://www.google.com/maps/embed/v1/place?key=AIzaSyDHO6WziMRpUayXSQnX8Xth566rnsZdQeY&q="+ $("#enteraddress").val());

});

    //-------------------------顯示資料庫裏面的標籤------------------
    $.ajax({
        url: "/user/findtags",
        type: "GET",
        // async: false,
        contentType: 'application/json;charset=utf-8',
        data: "json",
        success: function (response) {
            for (let tag of response) {
         //       let id = tag.id;
          //      let name = tag.name;
           //      console.log(tag);
                $("#articleTag").append(
                    $("<option></option>").text(tag)
               );
            }
        },
    });

    let chposition = {};
//提交按鈕發表AJAX請求
    $('#pusharticle').click(function (e) {

        e.preventDefault();
        let enteraddress = $('#enteraddress').val();
        geocode(enteraddress);
//------------------------------------------------------------------------------------

    });

    function geocode(enteradress){
        let location = enteradress;
        axios.get('https://maps.googleapis.com/maps/api/geocode/json',{
            params:{
                address:location,
                key:'AIzaSyDHO6WziMRpUayXSQnX8Xth566rnsZdQeY'
            }
        })
            .then(function (response) {

                let changeposition = {};
                let A;
                let B;
                let position;
                console.log(response);
                changeposition = response.data.results[0].geometry.location;
                console.log("changeposition" + changeposition);
                console.log("裡面" + changeposition.lat);
                A = changeposition.lat;
                B = changeposition.lng;
                position = {lat: A, lng: B};
                console.log(position.lat);
                console.log("A" + A);
                console.log("B" + B);
                console.log("方法裡面的chposition" + chposition.lat);
                let subjectcategory = $('#subjectcategory').val();
                let enterAddressName = $('#enteraddress').val();
                let enterAddressLat = position.lat;
                let enterAddressLng = position.lng;
                let selectregion = $('#selectregion').val();
                let articletitle = $('#articletitle').val();
                let texteditor = editor.getData();
                let freeTags =$("#articleTag").val();

                alert("已寫入資料庫" + texteditor);
                //創建物件
                let article = {};
                article['subjectCategory'] = subjectcategory;
                article['selectRegion'] = selectregion;
                article['enterAddressName'] = enterAddressName;
                article['enterAddressLng'] = enterAddressLng;
                article['enterAddressLat'] = enterAddressLat;
                article['articleTitle'] = articletitle;
                article['textEditor'] = texteditor;
                article['free_Tags'] = freeTags;


                $.ajax({
                    url: '/user/newArticle',
                    type: 'POST',
                    async: false,
                    contentType: 'application/json;charset=utf-8',
                    data: JSON.stringify(article),
                    success: null
                });
            })
    }
});


