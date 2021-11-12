$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });


    //前端姓名格式判斷 erName
    $('#name').blur(function (e) {
        e.preventDefault();

        let name = $('#name').val();

        // if (name.trim().length < 2) {
        //     $('#name').addClass('border border-1 border border-danger');
        //     $('#submitButton').addClass('disabled');
        //     $('#message').text('姓名格式錯誤');
        // } else {
        //     $('#name').removeClass('border border-1 border border-danger');
        //     $('#submitButton').removeClass('disabled');
        //     $('#message').empty();
        // }

        if (name == "") {
            $('#name').addClass('border border-1 border border-danger');
            $('#submitButton').addClass('disabled');
            $('#message').text('姓名格式錯誤');
            $('.erName').text('姓名欄不可為空');

        } else if (name.trim().length < 2) {

            $('#name').addClass('border border-1 border border-danger');
            $('#submitButton').addClass('disabled');
            $('#message').text('姓名格式錯誤');
            $('.erName').text('姓名格式須大於等於2個字');

        }else{
            $('#name').removeClass('border border-1 border border-danger');
            $('#submitButton').removeClass('disabled');
            $('#message').empty();
            $('.erName').empty();
        }
    });

    //前端暱稱格式判斷 erNickName
    $('#nickname').blur(function (e) {
        e.preventDefault();

        let nickname = $('#nickname').val();

        if (nickname.trim().length < 1) {
            $('#nickname').addClass('border border-1 border border-danger');
            $('#submitButton').addClass('disabled');
            $('#message').text('暱稱格式錯誤');
            $('.erNickName').text('暱稱格式錯誤');
        } else {
            $('#nickname').removeClass('border border-1 border border-danger');
            $('#submitButton').removeClass('disabled');
            $('#message').empty();
            $('.erNickName').empty();
        }

    });

    //前端信箱格式判斷 erEmail
    $('#email').blur(function (e) {
        e.preventDefault();

        let email = $('#email').val();
        let pass = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;

        if (email.trim().length < 1 || !pass.test(email)) {
            $('#email').addClass('border border-1 border border-danger');
            $('#submitButton').addClass('disabled');
            $('#message').text('信箱格式錯誤');
            $('.erEmail').text('信箱格式錯誤');
        } else {
            $('#email').removeClass('border border-1 border border-danger');
            $('#submitButton').removeClass('disabled');
            $('#message').empty();
            $('.erEmail').empty();
        }

    });

    //帳號確認按鈕註冊Ajax請求 erUsername
    $('#username').blur(function (e) {
        //取消原按鈕功能
        e.preventDefault();
        //獲取帳號密碼
        let username = $('#username').val();
        //判斷條件
        let pass = /^(?=.*\d)(?=.*[a-zA-Z]).{4,20}$/;

        if (username.trim().length < 1 || !pass.test(username)) {
            $('#username').addClass('border border-1 border border-danger');
            $('#submitButton').addClass('disabled');
            $('#message').text('帳號格式錯誤');
            $('.erUsername').text('帳號格式錯誤');
            return;
        } else {
            $('#username').removeClass('border border-1 border border-danger');
            $('#submitButton').removeClass('disabled');
            $('#message').empty();
            $('.erUsername').empty();
        }
        //創建物件
        let data = {};
        data['username'] = username;

        $.ajax({
            url: '/user/accountCheck',
            type: 'GET',
            data: data,
            success: function (response) {
                if (response) {
                    $('#username').addClass('border border-1 border border-danger');
                    $('#submitButton').addClass('disabled');
                    $('#message').text('帳號已存在，請重新輸入');
                    $('.erUsername').text('帳號已存在，請重新輸入');
                    
                } else {
                    $('#username').removeClass('border border-1 border border-danger');
                    $('#submitButton').removeClass('disabled');
                    $('#message').text('帳號可以使用');
                    $('.erUsername').text('帳號可以使用');
                }
            }
        });
    });

    //前端密碼格式判斷 erPassword
    $('#password').blur(function (e) {
        e.preventDefault();

        let password = $('#password').val();
        let pass = /^(?=.*\d)(?=.*[a-zA-Z]).{6,36}$/;

        if (password.trim().length < 1 || !pass.test(password)) {
            $('#submitButton').addClass('disabled');
            $('#password').addClass('border border-1 border border-danger');
            $('#message').text('密碼格式錯誤');
            $('.erPassword').text('密碼格式錯誤');
            return;
        } else {
            $('#submitButton').removeClass('disabled');
            $('#password').removeClass('border border-1 border border-danger');
            $('#message').empty();
            $('#confirmPassword').focus();
            $('.erPassword').empty();
        }
    });

    //前端確認密碼格式判斷  erConfirmPassword
    $('#confirmPassword').blur(function (e) {
        e.preventDefault();

        let password = $('#password').val();
        let confirmPassword = $('#confirmPassword').val();

        if (password != confirmPassword) {
            $('#submitButton').addClass('disabled');
            $('#confirmPassword').addClass('border border-1 border border-danger');
            $('#message').text('密碼不吻合');
            $('.erConfirmPassword').text('密碼不吻合');
        } else {
            $('#submitButton').removeClass('disabled');
            $('#confirmPassword').removeClass('border border-1 border border-danger');
            $('#message').empty();
            $('.erConfirmPassword').empty();
        }
    });

    
    //提交按鈕註冊Ajax請求  erGender
    $('#submitButton').click(function (e) {

        e.preventDefault();

        let name = $('#name').val();
        let nickname = $('#nickname').val();
        let gender = $("input[name='gender']:checked").val();
        let email = $('#email').val();
        let username = $('#username').val();
        let password = $('#password').val();

        if (name.trim() < 1 || nickname.trim() < 1 || gender == null || email.trim() < 1 || username.trim() < 1 || password.trim() < 1) {
            $('#message').text('麻煩請先填寫完成');
            return;
        } else {
            $('#message').empty();
        }
        
        let sex = $("[name='gender']:checked").val();
        // console.log(gender);
        if(sex == null){
            $('.erGender').text('請選擇性別');
            alert("請選擇性別");
        }else{
            $('.erGender').empty();
        }



        //創建物件
        let user = {};
        user['name'] = name;
        user['nickname'] = nickname;
        user['gender'] = gender;
        user['email'] = email;
        user['username'] = username;
        user['password'] = password;


        $.ajax({
            url: '/user/signup',
            type: 'POST',
            async: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(user),
            success: function (response) {

                if (response) {
                    document.location.href = "/user/signup-success";
                } else {
                    $('#message').text('註冊失敗，請重新輸入');
                }
            }
        });
    });

});

