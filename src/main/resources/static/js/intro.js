$(function(){
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //MySpace背景圖上傳按鈕
    $('#uploadIntroBanner').click(function(e) {

        e.preventDefault();

        //抓取彈跳式表單中輸入的值
        // 取得檔案
        const uploadBannerFile = $('#uploadBannerFile').prop('files')[0];

        // 宣告 FileReader
        const reader = new FileReader();
        // 轉換成 DataURL
        reader.readAsDataURL(uploadBannerFile);

        //檔案讀取完成執行
        reader.onload = function(){

            //將值放入自我介紹頁面
            if(uploadBannerFile != ''){
                $('#mySpaceBannerImg').attr("src", reader.result);
            }

            //通過base64來轉化圖片，去掉圖片頭（data:image/png;base64,）
            let fileB64 = (reader.result);//.split(":")[1];

            $.ajax({
                url: '/user/updateIntroBanner',
                type: 'POST',
                async: false,
                processData: false,
                contentType:'application/json;charset=utf-8',
                dataType: 'json',
                data: fileB64,
                success: function (response) {
                    if (response){
                        e.preventDefault();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    });

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
});

