
$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //更換圖形驗證
    $('#changeCode').click(function (e) {
        e.preventDefault();
        let url = "/captcha/code?" + new Date();
        $('#imageCode').attr("src", url);
    });


    //登入按鈕註冊Ajax請求
    $('#submit').click(function (e) {
        //取消原按鈕功能
        e.preventDefault();

        //獲取帳號密碼
        let email = $('#email').val();
        let imageCode = $('#code').val();


        //創建物件
        let data = {};
        data['email'] = email;
        data['imageCode'] = imageCode;

        $.ajax({
            url: '/sendPasswordResetMail',
            type: 'POST',
            async: false,
            data: data,
            success: function (respond) {
                    alert(JSON.parse(respond).result);
                    $('#changeCode').trigger('click');
            }
        });
    });

});

