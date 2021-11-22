
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

    let accountCheck = false;
    let passwordtCheck = false;

    //前端帳號格式判斷
    $('#username').blur(function (e) {
        e.preventDefault();

        let username = $('#username').val();
        let pass = /^(?=.*\d)(?=.*[a-zA-Z]).{4,20}$/;

        if (username.trim().length < 1 || !pass.test(username)) {
            $('#login').addClass('disabled');
            $('#message').text('帳號格式錯誤');
            $('.inUsername').text('帳號格式錯誤');
            accountCheck = false;
        } else {
            $('#login').removeClass('disabled');
            $('#message').empty();
            $('.inUsername').empty();
            accountCheck = true;
        }

    });


    //前端密碼格式判斷
    $('#password').blur(function (e) {
        e.preventDefault();

        let password = $('#password').val();
        let pass = /^(?=.*\d)(?=.*[a-zA-Z]).{6,36}$/;

        if (password.trim().length < 1 || !pass.test(password)) {
            $('#login').addClass('disabled');
            $('#message').text('密碼格式錯誤');
            $('.inPassword').text('密碼格式錯誤');
            passwordtCheck = false;
        } else {
            $('#login').removeClass('disabled');
            $('#message').empty();
            $('.inPassword').empty();
            passwordtCheck = true;
        }
    });

    //登入按鈕註冊Ajax請求
    $('#login').click(function (e) {
        //取消原按鈕功能
        e.preventDefault();

        //獲取帳號密碼
        let username = $('#username').val();
        let password = $('#password').val();
        let imageCode = $('#code').val();
        let remember = $('#remember-me').is(":checked");


        if (!accountCheck) {
            $('#login').addClass('disabled');
            $('#message').text('請輸入帳號');
            return;
        }

        if (!passwordtCheck) {
            $('#login').addClass('disabled');
            $('#message').text('請輸入密碼');
            return;
        }

        //創建物件
        let data = {};
        data['username'] = username;
        data['password'] = password;
        data['imageCode'] = imageCode;
        data['remember-me'] = remember;

        $.ajax({
            url: '/user/login',
            type: 'POST',
            async: false,
            data: data,
            statusCode: {
                200: function () {
                    document.location.href = "/user/afterLogin";
                },
                202: function () {
                    $('#message').text('驗證碼錯誤');
                    $('#changeCode').trigger('click');
                },
                401: function () {
                    $('#message').text('帳號或密碼錯誤');
                    $('#changeCode').trigger('click');
                }
            }
        });
    });

});

