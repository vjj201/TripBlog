'use strict';
let websocket = null;
$(document).ready(function () {
    if('WebSocket' in window) {
        websocket = new WebSocket('wss://localhost:8080/user/socket');
    }else {
        alert('該瀏覽器不支援訂單提醒!');
    }

    websocket.onopen = function () {
        console.log('建立連線');
    }

    websocket.onclose = function () {
        console.log('連線關閉');
    }

    websocket.onerror = function () {
        console.log('websocket通訊發生錯誤！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }

});
