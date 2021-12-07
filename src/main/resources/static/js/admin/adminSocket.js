'use strict';
let websocket = null;
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    if('WebSocket' in window) {
        websocket = new WebSocket('wss://localhost:8080/admin/socket');
    }else {
        alert('該瀏覽器不支援訂單提醒!');
    }

    websocket.onopen = function () {
        console.log('建立連線');
    }

    websocket.onclose = function () {
        console.log('連線關閉');
    }

    websocket.onmessage = function (event) {
        let message = JSON.parse(event.data).message;
        if("有新的訂單" === message){
            alert('提醒:' + message);
            return;
        }
        $('#online').text(message);
    }

    websocket.onerror = function () {
        alert('websocket通訊發生錯誤！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }

    $('#getOnline').click(function () {
        $.ajax({
            url: '/admin/onlineUserSet/',
            type: 'GET',
            statusCode: {
                200: function (response) {
                    let userSet = '商城在線會員\n';
                    $.each(response, function (i, username) {
                        userSet += '帳號:' + username + '\n';
                    });
                    alert(userSet);
                },
                204: function () {
                    alert('查無上線會員');
                }
            }
        });
    });

});


