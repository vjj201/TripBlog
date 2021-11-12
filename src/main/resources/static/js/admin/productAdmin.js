

$(function(){
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    $.ajax({
        url: 'product/manage',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);            
            createProductList(data);
        }
    });

    function createProductList(data){
        //動態新增商品開始
        newTbody = document.createElement('tbody');
        // 將 tbody 放進 productAdmin
        document.getElementById('productAdmin').appendChild(newTbody);
        
        for(let i = 0; i < data.length; i++){
        // 建立每個品項的清單區域 -- tr
            let trItemList = document.createElement('tr');
            newTbody.appendChild(trItemList);
        // 建立商品ID -- th
            let thItemId = document.createElement('th');
            thItemId.scope = "row";
            thItemId.innerText = data[i].productID;
            trItemList.appendChild(thItemId);

        // 建立商品圖片-- 第一個 td
            let tdImage = document.createElement('td');
            let image = document.createElement('img');
            image.src = "/shop/productPic/" + data[i].productID;
            image.style.width = '70px';
            tdImage.appendChild(image);
            trItemList.appendChild(tdImage);
        // 建立商品名稱-- 第二個 td
            let tdTitle = document.createElement('td');
            tdTitle.innerText = data[i].productName;
            trItemList.appendChild(tdTitle);
        // 建立商品簡介-- 第三個 td
            let tdAbout = document.createElement('td');
            tdAbout.innerText = data[i].aboutProduct;
            trItemList.appendChild(tdAbout);
        // 建立商品詳細-- 第四個 td
            let tdDetail = document.createElement('td');
            tdDetail.innerText = data[i].productDetail;
            trItemList.appendChild(tdDetail);
        // 建立商品價格-- 第五個 td
            let tdPrice = document.createElement('td');
            tdPrice.innerText = data[i].price + "TWD";
            trItemList.appendChild(tdPrice);
        // 建立商品庫存-- 第六個 td
            let tdStock = document.createElement('td');
            tdStock.innerText = data[i].inStock;
            trItemList.appendChild(tdStock);
        // 建立商品已售出-- 第七個 td
            let tdSold = document.createElement('td');
            tdSold.innerText = data[i].alreadySold;
            trItemList.appendChild(tdSold);
        // 建立商品店家-- 第八個 td
            let tdBrand = document.createElement('td');
            tdBrand.innerText = data[i].brand;
            trItemList.appendChild(tdBrand);
        // 建立商品標籤-- 第九個 td
            let tdTag = document.createElement('td');
            tdTag.innerText = data[i].productTag;
            trItemList.appendChild(tdTag);
        // 建立商品編輯、移除按鈕-- 第十個 td
            let tdButton = document.createElement('td');
            let btEdit = document.createElement('button');
            let btRemove = document.createElement('button');
            // btEdit.data-bs-toggle = "modal";
            // btEdit.data-bs-target = "#editModal"; 
            btEdit.innerHTML = `data-bs-toggle="modal" data-bs-target = "#editModal"`;
            btEdit.type = "button"; 
            btEdit.className = "btn btn-primary m-1";
            btEdit.innerText = "編輯";

            btRemove.type = "button";
            btRemove.className = "btn btn-danger m-1";
            btRemove.innerText = "移除";

            trItemList.appendChild(tdButton);
            tdButton.appendChild(btEdit);
            tdButton.appendChild(btRemove);
        }

    }

    //自我介紹更新按鈕
    // $('#updateIntro').click(function(e) {

    //     e.preventDefault();

    //     //抓取彈跳式表單中輸入的值
    //     let editIntroduceTitle = $('#editIntroduceTitle').val();
    //     let editIntroduceContent = $('#editIntroduceContent').val();
    //     //處理自我介紹內容的空白及換行
    //     let textarea = editIntroduceContent.replace(/\n/g,"<br/>").replace(/\s/g,"&nbsp;")

    //     console.log(editIntroduceContent);
    //     console.log(textarea);
    //     //創建物件
    //     let intro = {};
    //     intro['introTitle'] = editIntroduceTitle;
    //     intro['introContent'] = editIntroduceContent;

    //     //將值放入自我介紹頁面
    //     if(editIntroduceTitle != ''){
    //         $('#introduceTitle').text(editIntroduceTitle);
    //     }
    //     if(editIntroduceContent != ''){
    //         $('#introduceContent').html(textarea);
    //     }

    //     $.ajax({
    //         url: '/user/updateIntro',
    //         type: 'POST',
    //         async: false,
    //         contentType: 'application/json;charset=utf-8',
    //         data: JSON.stringify(intro),
    //         success: function (response) {
    //             if (response){
    //                 e.preventDefault();
    //                 return true;
    //             } else {
    //                 return false;
    //             }
    //         }
    //     });
    // });

    // MySpace背景圖上傳按鈕
    // MySpace背景圖 croppie------
    // (function($) {
    //     var width_crop = 1500, // 圖片裁切寬度 px 值
    //         height_crop = 400, // 圖片裁切高度 px 值
    //         type_crop = "square", // 裁切形狀: square 為方形, circle 為圓形
    //         width_preview = 1500, // 預覽區塊寬度 px 值
    //         height_preview = 500, // 預覽區塊高度 px 值
    //         compress_ratio = 0.85, // 圖片壓縮比例 0~1
    //         type_img = "jpeg", // 圖檔格式 jpeg png webp
    //         oldImg = new Image(),
    //         myCrop, file, oldImgDataUrl;

    //     // 裁切初始參數設定
    //     myCrop = $("#main-cropper").croppie({
    //         viewport: { // 裁切區塊
    //             width: width_crop,
    //             height: height_crop,
    //             type: type_crop
    //         },
    //         boundary: { // 預覽區塊
    //             width: width_preview,
    //             height: height_preview
    //         }
    //     });

    //     function readFile(input) {
    //         if (input.files && input.files[0]){
    //             file = input.files[0];
    //         } else {
    //             alert("瀏覽器不支援此功能！建議使用最新版本 Chrome");
    //             return;
    //         }

    //         if (file.type.indexOf("image") == 0) {
    //             var reader = new FileReader();

    //             reader.onload = function(e) {
    //                 oldImgDataUrl = e.target.result;
    //                 oldImg.src = oldImgDataUrl; // 載入 oldImg 取得圖片資訊
    //                 myCrop.croppie("bind", {
    //                     url: oldImgDataUrl
    //                 });
    //             };
    //             reader.readAsDataURL(file);
    //         } else {
    //             alert("您上傳的不是圖檔！");
    //         }
    //     }

    //     function displayCropImg(src) {
    //         var html = "<img src='" + src + "' />";
    //         $("#mySpaceBannerImg").html(html);
    //     }

    //     $("#uploadBannerFile").on("change", function() {
    //         $("#main-cropper").show();
    //         readFile(this);
    //     });

    //     $("#uploadIntroBanner").on("click", function() {
    //         myCrop.croppie("result", {
    //             type: "canvas",
    //             format: type_img,
    //             quality: compress_ratio
    //         }).then(function(src) {
    //             displayCropImg(src);


    //             // MySpace背景圖 croppie end------
    //             //將值放入自我介紹頁面
    //             $('#mySpaceBannerImg').attr('src',src)

    //             //將裁切過的圖片轉成FormData
    //             const b64data = src.split(',')[1];
    //             const contentType = src.split(',')[0].split(';')[0].split(':')[1];

    //             const b64toBlob = (b64Data, contentType='', sliceSize=512) => {
    //                 const byteCharacters = atob(b64Data);
    //                 const byteArrays = [];

    //                 for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
    //                     const slice = byteCharacters.slice(offset, offset + sliceSize);

    //                     const byteNumbers = new Array(slice.length);
    //                     for (let i = 0; i < slice.length; i++) {
    //                         byteNumbers[i] = slice.charCodeAt(i);
    //                     }
    //                     const byteArray = new Uint8Array(byteNumbers);
    //                     byteArrays.push(byteArray);
    //                 }
    //                 const blob = new Blob(byteArrays, {type: contentType});
    //                 return blob;
    //             }



    //             let formData = new FormData();
    //             formData.append('file', b64toBlob(b64data, contentType, sliceSize=512));

    //             $.ajax({
    //                 type: "POST",
    //                 url: "/user/updateIntroBanner",
    //                 data: formData,
    //                 async: false,
    //                 mimeType: "multipart/form-data",
    //                 processData: false,
    //                 contentType: false,
    //                 dataType: "json",

    //                 success: function (response) {
    //                     if(response) {
    //                         alert('圖片新增成功');
    //                     } else {
    //                         alert('上傳失敗');
    //                     }
    //                 }
    //             });
    //         });
    //     });
    // })(jQuery);
    // MySpace背景圖 更新 end------


    

});
