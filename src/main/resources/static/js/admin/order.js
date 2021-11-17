'use strict';
let page = 1;
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
        alert('收到訊息:' + JSON.parse(event.data).message);
        loadData();
    }

    websocket.onerror = function () {
        alert('websocket通訊發生錯誤！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }

    loadData();

    //搜尋按鈕
    $('#button-addon5').on('click', function () {
        page = 1;
        loadData();
    });

    //商家產品列表
    $(document).on('click', 'button[name="getOrders"]', function () {
        let uuid = $(this).text();
        let orderStatus = $(this).parent().parent().children().eq(3).children().find(":selected").text();
        $('#orderId').text(uuid);
        $('#orderStatus').text(orderStatus);
        $.ajax({
            type: 'GET',
            url: "/admin/order/detail/" + uuid,
            statusCode: {
                200: function (order) {
                    $('#payment').val(order.payment);
                    $('#owner').val(order.cardOwner);
                    $('#cardNumber').val(order.cardNumber);
                    $('#deliver').val(order.deliver);
                    $('#sendFor').val(order.receiver);
                    $('#address').val(order.address);

                    $('#freight').text(order.freight);
                    $('#totalAmount').text(order.amounts);
                    loadItem(uuid);
                    if (!order.adminCheck) {
                        $.ajax({
                            type: 'PUT',
                            url: "/admin/order/checked/" + uuid,
                            statusCode: {
                                200: function () {
                                    $('button:contains(' + uuid + ')').removeClass('btn-info');
                                    $('button:contains(' + uuid + ')').addClass('btn-gr0201');
                                },
                                404: function () {
                                    alert('查看狀態，更新失敗');
                                }
                            }
                        });
                    }
                },
                404: function () {
                    let trHTML = '<tr class="my-3 h-100">' +
                        '<td class="pt-2"></td>' +
                        '<td class="pt-2">查無資訊</td>' +
                        '<td class="pt-2"></td>' +
                        '</tr>';
                    $('#Modal').html(trHTML);
                }
            }
        });
    });

    //狀態更新
    $('#updateBtn').on('click', function () {

        let result = confirm('確定更新');

        if (result) {
            let updateList = new Array();
            $("select[name='orderStatus']").find(":selected").each(function () {
                let uuid = $(this).parent().parent().parent().children().children().eq(0).text();
                let data = { 'uuid': uuid, 'orderStatus': $(this).val() };
                updateList.push(data);
            });

            $.ajax({
                url: '/admin/order',
                type: 'PUT',
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(updateList),
                statusCode: {
                    200: function () {
                        alert('編輯成功');
                        location.reload(true);
                    },
                    500: function () {
                        alert('編輯失敗');
                    }
                }
            });
        }

    })

    //下一頁
    $('#next').on('click', function () {
        page = ++page;
        loadData();
    });

    //上一頁
    $('#pre').on('click', function () {
        page = --page;
        loadData();
    });

    //頁數
    $('#pageZone').on('change', function () {
        page = $(this).val();
        loadData();
    });


});

//載入頁面
function loadData() {
    let status = $('select[name="status"]').val();
    let username = $('input[name="username"]').val();
    let uuid = $('input[name="uuid"]').val();

    let data = {};
    data['orderStatus'] = status;
    data['username'] = username;
    data['uuid'] = uuid;

    $.ajax({
        type: 'POST',
        url: "/admin/order/page/" + page,
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(data),
        statusCode: {
            200: function (response) {
                let totalPage = response.totalPages;
                let orderList = response.content;
                let first = response.first;
                let last = response.last;

                let trHTML = '';
                $.each(orderList, function (i, order) {

                    let option = '';
                    let btnColor = 'btn-info';
                    let orderStatus = order.orderStatus;

                    let username = order.username;
                    if (username === null) {
                        username = '訪客';
                    }

                    if (order.adminCheck) {
                        btnColor = 'btn-gr0201';
                    }

                    if (orderStatus === -1) {
                        option += '<option selected value="-1">待出貨</option>';
                    } else {
                        option += '<option value="-1">待出貨</option>';
                    }

                    if (orderStatus === 0) {
                        option += '<option selected value="0">運送中</option>';
                    } else {
                        option += '<option value="0">運送中</option>';
                    }

                    if (orderStatus === 1) {
                        option += '<option selected value="1">已收件</option>';
                    } else {
                        option += '<option value="1">已收件</option>';
                    }


                    trHTML +=
                        '<tr class="my-3 h-100">' +
                        '<td>' +
                        '<button name="getOrders" type="button" class="btn ' + btnColor + '" data-bs-toggle="modal"' +
                        'data-bs-target="#Modal">' +
                        order.uuid +
                        '</button></td>' +
                        '<td class="pt-2">' +
                        username +
                        '</td>' +
                        '<td class="pt-2">' +
                        order.orderTime +
                        '</td> ' +
                        '<td><select name="orderStatus" class="form-select me-2" style = "width: 9rem"><li>' +
                        option +
                        '</li></select></td> ' +
                        '<td class="pt-2">' +
                        order.amounts +
                        'TWD</td></tr>';

                });

                let pageZone = '';
                for (let i = 1; i <= totalPage; i++) {
                    if (page == i) {
                        pageZone += '<option selected>' + i + '</option>';
                    } else {
                        pageZone += '<option>' + i + '</option>';
                    }

                }

                if (first) {
                    $('#pre').attr("disabled", true);
                } else {
                    $('#pre').removeAttr("disabled");
                }

                if (last) {
                    $('#next').attr("disabled", true);
                } else {
                    $('#next').removeAttr("disabled");
                }

                $('#pageZone').html(pageZone);
                $('#totalPage').text(totalPage);
                $('#tbody').html(trHTML);
            },
            204: function () {
                $('#tbody').html('<tr><td colspan="5" class="text-center p-5 m-5"><h3>查無資料</h3></td></tr>');
            }
        }
    });
}

function loadItem(uuid) {
    $.ajax({
        type: 'GET',
        url: "/admin/order/detail/" + uuid + "/item",
        statusCode: {
            200: function (response) {

                let trHTML = '';
                $.each(response, function (i, item) {

                    trHTML +=
                        '<tr class="my-3 border-bottom">' +
                        '<td class="pt-3"><h6>' +
                        item.title +
                        '</h6></td>' +
                        '<td class="pt-3">' +
                        item.quantity +
                        '</td> ' +
                        '<td class="pt-3">' +
                        item.price +
                        'TWD</td></tr>';
                });

                $('#itemZone').html(trHTML);

            },
            404: function () {
                let trHTML = '<tr class="my-3 h-100">' +
                    '<td class="pt-2"></td>' +
                    '<td class="pt-2">查無資訊</td>' +
                    '<td class="pt-2"></td>' +
                    '</tr>';
                $('#Modal').html(trHTML);
            }
        }
    });
}

