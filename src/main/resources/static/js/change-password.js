
$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    let oldPasswordtCheck = false;
    let passwordtCheck = false;

    //前端舊密碼格式判斷
    $('#oldPassword').blur(function (e) {
        e.preventDefault();

        let password = $('#oldPassword').val();
        let pass = /^(?=.*\d)(?=.*[a-zA-Z]).{6,36}$/;

        if (password.trim().length < 1 || !pass.test(password)) {
            $('#message').text('密碼格式錯誤');
            oldPasswordtCheck = false;
        } else {
            $('#message').empty();
            oldPasswordtCheck = true;
        }
    });

    //前端新密碼格式判斷
    $('#password').blur(function (e) {
        e.preventDefault();

        let password = $('#password').val();
        let pass = /^(?=.*\d)(?=.*[a-zA-Z]).{6,36}$/;

        if (password.trim().length < 1 || !pass.test(password)) {
            $('#submit').addClass('disabled');
            $('#message').text('密碼格式錯誤');
            passwordtCheck = false;
        } else {
            $('#submit').removeClass('disabled');
            $('#message').empty();
            passwordtCheck = true;
        }
    });

    //前端新密碼確認格式判斷
    $('#confirmPassword').blur(function (e) {
        e.preventDefault();

        let password = $('#password').val();
        let confirmPassword = $('#confirmPassword').val();

        if (password != confirmPassword) {
            $('#submit').addClass('disabled');
            $('#message').text('密碼不吻合');
        } else {
            $('#submit').removeClass('disabled');
            $('#message').empty();
        }
    });

    //取消按鈕註冊
    $('#cancel').click(function (e) {
        window.location.href = "profile"
    });

    //送出按鈕註冊Ajax請求
    $('#submit').click(function (e) {
        //取消原按鈕功能
        e.preventDefault();

        //獲取帳號密碼
        let oldPassword = $('#oldPassword').val();
        let password = $('#password').val();
        let confirmPassword = $('#confirmPassword').val();

        if (!oldPasswordtCheck) {
            $('#submit').addClass('disabled');
            $('#message').text('請輸入舊密碼');
            return;
        }

        if (!passwordtCheck) {
            $('#submit').addClass('disabled');
            $('#message').text('請輸入新密碼');
            return;
        }

        if (password != confirmPassword) {
            $('#submit').addClass('disabled');
            $('#message').text('請確認新密碼');
            return;
        }


        //創建物件
        let data = {};
        data['oldPassword'] = oldPassword;
        data['password'] = password;
        
        $.ajax({
            url: '/user/changePassword',
            type: 'POST',
            async: false,
            data: data,
            success: function (response) {
                if (response){
                    alert("變更密碼成功 返回會員資料頁面");
                } else {
                    alert("舊密碼輸入錯誤 返回會員資料頁面");
                }
                window.location.href = "profile"
            }
        });
    });
});

