$(function(){
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    // MySpace背景圖上傳按鈕
    // MySpace背景圖 croppie------
    (function($) {
        var width_crop = 1200, // 圖片裁切寬度 px 值
            height_crop = 400, // 圖片裁切高度 px 值
            type_crop = "square", // 裁切形狀: square 為方形, circle 為圓形
            width_preview = 1200, // 預覽區塊寬度 px 值
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

            // html += "<p>原始圖片尺寸 " + width + "x" + height + "</p>";
            // html += "<p>檔案大小約 " + fileSize + "k</p>";
            $("#main-cropper").before(html);
        };

        $("#uploadIntroBanner").on("click", function() {
            myCrop.croppie("result", {
                type: "canvas",
                format: type_img,
                quality: compress_ratio
            }).then(function(src) {
                displayCropImg(src);
                // MySpace背景圖 croppie end------
                //將值放入自我介紹頁面
                $('#mySpaceBannerImg').attr('src',src)


                let formData = new FormData();
                formData.append('file', file);

                $.ajax({
                    type: "POST",
                    url: "/user/updateIntroBanner",
                    data: formData,
                    async: false,
                    mimeType: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    dataType: "json",

                    success: function (src) {
                        return src;
                    }
                });
            });
        });
    })(jQuery);
    // MySpace背景圖 更新 end------



    //自我介紹更新按鈕
    $('#updateIntro').click(function(e) {

        e.preventDefault();

        //抓取彈跳式表單中輸入的值
        let editIntroduceTitle = $('#editIntroduceTitle').val();
        let editIntroduceContent = $('#editIntroduceContent').val();
        //處理自我介紹內容的空白及換行
        let textarea = editIntroduceContent.replace(/\n/g,"<br/>").replace(/\s/g,"&nbsp;")

        console.log(editIntroduceContent);
        console.log(textarea);
        //創建物件
        let intro = {};
        intro['introTitle'] = editIntroduceTitle;
        intro['introContent'] = editIntroduceContent;

        //將值放入自我介紹頁面
        if(editIntroduceTitle != ''){
            $('#introduceTitle').text(editIntroduceTitle);
        }
        if(editIntroduceContent != ''){
            $('#introduceContent').html(textarea);
        }

        $.ajax({
            url: '/user/updateIntro',
            type: 'POST',
            async: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(intro),
            success: function (response) {
                if (response){
                    e.preventDefault();
                    return true;
                } else {
                    return false;
                }
            }
        });
    });

    //Link更新按鈕
    $('#updateLink').click(function(e) {

        e.preventDefault();

        //抓取彈跳式表單中輸入的值
        let fbLink = $('#fbLink').val();
        let igLink = $('#igLink').val();
        let ytLink = $('#ytLink').val();
        let mailLink = $('#mailLink').val();

        //修改a標籤的href連結
        if(fbLink != ''){
            $('#fbAddr').attr('href', fbLink);
        }
        if(igLink != ''){
            $('#igAddr').attr('href', igLink);
        }
        if(ytLink != ''){
            $('#ytAddr').attr('href', ytLink);
        }
        if(mailLink != ''){
            $('#mailAddr').attr('href', 'mailto:' + mailLink);
        }

        //創建物件
        let intro = {};
        intro['fbLink'] = fbLink;
        intro['igLink'] = igLink;
        intro['ytLink'] = ytLink;
        intro['emailLink'] = mailLink;

        $.ajax({
            url: '/user/updateIntroLink',
            type: 'POST',
            async: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(intro),
            success: function (response) {
                if(response){
                    e.preventDefault();
                    return true;
                } else {
                    return false;
                }
            }
        });
    });

    // 會員照片更新
    // 會員照片 croppie------

    (function($) {
        let width_crop = 150, // 圖片裁切寬度 px 值
            height_crop = 150, // 圖片裁切高度 px 值
            type_crop = "circle", // 裁切形狀: square 為方形, circle 為圓形
            width_preview = 350, // 預覽區塊寬度 px 值
            height_preview = 350, // 預覽區塊高度 px 值
            compress_ratio = 0.85, // 圖片壓縮比例 0~1
            type_img = "jpeg", // 圖檔格式 jpeg png webp
            oldImg = new Image(),
            myCrop, file, oldImgDataUrl;

        // 裁切初始參數設定
        myCrop = $("#oldImg").croppie({
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
                const reader = new FileReader();

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
            const html = "<img src='" + src + "' />";
            $("#newImg").html(html);
        }

        $("#upload_img").on("change", function() {
            $("#oldImg").show();
            readFile(this);
        });

        oldImg.onload = function() {
            let width = this.width,
                height = this.height,
                fileSize = Math.round(file.size / 1000),
                html = "";

            // html += "<p>原始圖片尺寸 " + width + "x" + height + "</p>";
            // html += "<p>檔案大小約 " + fileSize + "k</p>";
            $("#oldImg").before(html);
        };

        function displayNewImgInfo(src) {
            let html = "",
                filesize = src.length * 0.75;

            // html += "<p>裁切圖片尺寸 " + width_crop + "x" + height_crop + "</p>";
            // html += "<p>檔案大小約 " + Math.round(filesize / 1000) + "k</p>";
            $("#newImgInfo").html(html);
        }

        $("#crop_img").on("click", function() {
            myCrop.croppie("result", {
                type: "canvas",
                format: type_img,
                quality: compress_ratio
            }).then(function(src) {
                displayCropImg(src);
                displayNewImgInfo(src);

                // console.log(src);

                // 會員照片 croppie end------

                //將值放入自我介紹頁面
                if(memberPic !== ""){
                    $('#memberPic').attr('src',src)
                }
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
                    url: "/user/updateMemberPic",
                    data: formData,
                    async: false,
                    mimeType: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    dataType: "json",

                    success: function (src) {
                        return src;
                    }
                });

            });
        });
    })(jQuery);
});
