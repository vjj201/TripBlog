$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });


    //發送信件
    $('#send').click(function (e) {

        e.preventDefault();

        $.ajax({
            url: '/sendSignupMail',
            type: 'GET',
            async: false
        });

        alert("驗證信已發送");
    });


    $('#send').trigger('click');

    //提交驗證Ajax請求
    $('#submit').click(function (e) {

        e.preventDefault();

        let code = $('#code1').val() + $('#code2').val() + $('#code3').val() + $('#code4').val() + $('#code5').val() +$('#code6').val();

        let data = {};
        data['code'] = code;

        $.ajax({
            url: '/checkSignupMail',
            type: 'POST',
            async: false,
            data: data,
            success: function (response) {

                if (response) {
                    $('#message').text('驗證成功，即將返回首頁，請重新登入');
                    setTimeout(function() { document.location.href = "/user/logout";},2000);
                } else {
                    $('#message').text('驗證失敗');
                }
            }
        });
    });

});

