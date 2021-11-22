//------------------------
//之後要修改 經緯度轉換方法呼叫
//------------------------

$(function() {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    $("#enteraddress").mouseleave(function(){
        if($("#enteraddress").val() == ""){
            $("iframe").attr("src","https://www.google.com/maps/embed/v1/place?key=AIzaSyDHO6WziMRpUayXSQnX8Xth566rnsZdQeY&q="+ "台灣");
        }
        else{
            $("iframe").attr("src","https://www.google.com/maps/embed/v1/place?key=AIzaSyDHO6WziMRpUayXSQnX8Xth566rnsZdQeY&q="+ $("#enteraddress").val());
        }
    });
// -------上傳圖片----------
    (function($) {
        var width_crop = 500, // 圖片裁切寬度 px 值
            height_crop = 400, // 圖片裁切高度 px 值
            type_crop = "square", // 裁切形狀: square 為方形, circle 為圓形
            width_preview = 1500, // 預覽區塊寬度 px 值
            height_preview = 500, // 預覽區塊高度 px 值
            compress_ratio = 0.85, // 圖片壓縮比例 0~1
            type_img = "jpeg", // 圖檔格式 jpeg png webp
            oldImg = new Image(),
            myCrop, file, oldImgDataUrl;

        // 裁切初始參數設定
        myCrop = $("#main-cropper").croppie({
            viewport: { // 裁切區塊
                width: width_crop,
                height: height_crop,
                type: type_crop
            },
            boundary: { // 預覽區塊
                width: width_preview,
                height: height_preview
            }
        });

        function readFile(input) {
            if (input.files && input.files[0]){
                file = input.files[0];
            } else {
                alert("瀏覽器不支援此功能！建議使用最新版本 Chrome");
                return;
            }

            if (file.type.indexOf("image") == 0) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    oldImgDataUrl = e.target.result;
                    oldImg.src = oldImgDataUrl; // 載入 oldImg 取得圖片資訊
                    myCrop.croppie("bind", {
                        url: oldImgDataUrl
                    });
                };
                reader.readAsDataURL(file);
            } else {
                alert("您上傳的不是圖檔！");
            }
        }

        function displayCropImg(src) {
            var html = "<img src='" + src + "' />";
            $("#mySpaceBannerImg").html(html);
        }

        $("#uploadBannerFile").on("change", function() {
            $("#main-cropper").show();
            readFile(this);
        });

        oldImg.onload = function() {
            var width = this.width,
                height = this.height,
                fileSize = Math.round(file.size / 1000),
                html = "";

            html += "<p>原始圖片尺寸 " + width + "x" + height + "</p>";
            html += "<p>檔案大小約 " + fileSize + "k</p>";
            $("#main-cropper").before(html);
        };

        $("#uploadIntroBanner").on("click", function() {
            myCrop.croppie("result", {
                type: "canvas",
                format: type_img,
                quality: compress_ratio
            }).then(function(src) {
                displayCropImg(src);
                // 背景圖 croppie end------

                //將值放入文章編輯頁面
                $('#mySpaceBannerImg').attr('src',src)

                //將裁切過的圖片轉成FormData
                const b64data = src.split(',')[1];
                const contentType = src.split(',')[0].split(';')[0].split(':')[1];

                const b64toBlob = (b64Data, contentType='', sliceSize=512) => {
                    const byteCharacters = atob(b64Data);
                    const byteArrays = [];

                    for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                        const slice = byteCharacters.slice(offset, offset + sliceSize);

                        const byteNumbers = new Array(slice.length);
                        for (let i = 0; i < slice.length; i++) {
                            byteNumbers[i] = slice.charCodeAt(i);
                        }
                        const byteArray = new Uint8Array(byteNumbers);
                        byteArrays.push(byteArray);
                    }
                    const blob = new Blob(byteArrays, {type: contentType});
                    return blob;
                }
                let formData = new FormData();
                formData.append('file', b64toBlob(b64data, contentType, sliceSize=512));

                $.ajax({
                    type: "POST",
                    url: "/user/updateArticleImg",
                    data: formData,
                    async: false,
                    mimeType: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    dataType: "json",

                    success: function (response) {
                        if(response) {
                            alert('圖片新增成功');
                        } else {
                            alert('上傳失敗');
                        }
                    }
                });
            });
        });
    })
    (jQuery);
// -------------結束

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

//提交按鈕發表AJAX請求
    $('#pusharticle').click(function (e) {

        e.preventDefault();
        let enteraddress = $('#enteraddress').val();
        geocode(enteraddress);
//------------------------------------------------------------------------------------
    });
//-----------經緯度轉換------------------------------------------------------------
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
                // console.log(response);
                changeposition = response.data.results[0].geometry.location;
                // console.log("changeposition" + changeposition);
                // console.log("裡面" + changeposition.lat);
                A = changeposition.lat;
                B = changeposition.lng;
                position = {lat: A, lng: B};
                // console.log(position.lat);
                // console.log("A" + A);
                // console.log("B" + B);
                // console.log("方法裡面的chposition" + chposition.lat);
                let subjectcategory = $('#subjectcategory').val();
                let enterAddressName = $('#enteraddress').val();
                let enterAddressLat = position.lat;
                let enterAddressLng = position.lng;
                let selectregion = $('#selectregion').val();
                let articletitle = $('#articletitle').val();
                let texteditor = editor.getData();
                let freeTags =$("#articleTag").val();

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
                article['show'] = true;

                $.ajax({
                    url: '/user/newArticle',
                    type: 'POST',
                    async: false,
                    contentType: 'application/json;charset=utf-8',
                    data: JSON.stringify(article),
                    success: function () {
                        window.location.href='eat';
                        console.log("跳轉EAT");
                    }
                });
            })
    }
//-------------------------------------------------------------------------------------------------------------
    //提交儲存AJAX請求
    $('#savearticle').click(function (e) {

        e.preventDefault();
        let enteraddress = $('#enteraddress').val();
        saveGeocode(enteraddress);
    });
//-----------經緯度轉換------------------------------------------------------------
    function saveGeocode(enteradress){
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
                // console.log(response);
                changeposition = response.data.results[0].geometry.location;
                // console.log("changeposition" + changeposition);
                // console.log("裡面" + changeposition.lat);
                A = changeposition.lat;
                B = changeposition.lng;
                position = {lat: A, lng: B};
                // console.log(position.lat);
                // console.log("A" + A);
                // console.log("B" + B);
                // console.log("方法裡面的chposition" + chposition.lat);
                let subjectcategory = $('#subjectcategory').val();
                let enterAddressName = $('#enteraddress').val();
                let enterAddressLat = position.lat;
                let enterAddressLng = position.lng;
                let selectregion = $('#selectregion').val();
                let articletitle = $('#articletitle').val()+"(草稿)";
                let texteditor = editor.getData();
                let freeTags =$("#articleTag").val();
                let saveArticle = $('#savearticle').html();
                console.log("saveArticle" + saveArticle)
                //創建物件
                let articleForSave = {};
                articleForSave['subjectCategory'] = subjectcategory;
                articleForSave['selectRegion'] = selectregion;
                articleForSave['enterAddressName'] = enterAddressName;
                articleForSave['enterAddressLng'] = enterAddressLng;
                articleForSave['enterAddressLat'] = enterAddressLat;
                articleForSave['articleTitle'] = articletitle;
                articleForSave['textEditor'] = texteditor;
                articleForSave['free_Tags'] = freeTags;
                articleForSave['show'] = false;
                $.ajax({
                    url: '/user/newArticle',
                    type: 'POST',
                    async: false,
                    contentType: 'application/json;charset=utf-8',
                    data: JSON.stringify(articleForSave),
                    success:
                        function () {
                            alert("已經為您儲存草稿")
                            window.location.href='eat';
                            console.log("跳轉EAT");
                        }
                });
            })
    }

});


