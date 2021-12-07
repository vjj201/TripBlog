'use strict';

$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //發送
    $(document).on('click', '#send', function () {
        let username = $('#username').val();
        let title = $('#title').val();
        let content = $('#content').val();

        let data = {};
        data['username'] = username;
        data['title'] = title;
        data['content'] = content;


        $.ajax({
            type: 'POST',
            url: "/admin/notify/",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            dataType: 'json',
            statusCode: {
                201: function () {
                    alert('發送成功');
                    $('#cancel').trigger('click');
                },
                500: function () {
                    alert('發送失敗');
                }
            }
        });
    });

    //清除
    $(document).on('click', '#cancel', function () {
        $('#username').val('');
        $('#title').val('');
        $('#content').val('');
    });


});

