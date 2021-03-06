'use strict';

$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    // 執行載入頁面函數
    loadData();

    //會員資料表
    $(document).on('click', 'button[name="getMemberInfo"]', function () {
        let id = $(this).parent().parent().children().eq(0).text();

        $.ajax({
            type: 'GET',
            url: "/admin/member/" + id,
            dataType: 'json',
            statusCode: {
                200: function (response) {

                    let trHTML = '';

                    trHTML +=
                        '<tr class="my-3 h-100">' +
                        '<th scope="row">暱稱</th>' +
                        '<td class="pt-2">' +
                        response.nickname +
                        '</td></tr>' +
                        '<tr class="my-3 h-100">' +
                        '<th scope="row">性別</th>' +
                        '<td class="pt-2">' +
                        response.gender +
                        '</td></tr>' +
                        '<tr class="my-3 h-100">' +
                        '<th scope="row">生日</th>' +
                        '<td class="pt-2">' +
                        response.birthday +
                        '</td></tr>' +
                        '<tr class="my-3 h-100">' +
                        '<th scope="row">信箱</th>' +
                        '<td class="pt-2">' +
                        response.email +
                        '</td></tr>' +
                        '<tr class="my-3 h-100">' +
                        '<th scope="row">手機</th>' +
                        '<td class="pt-2">' +
                        response.phone +
                        '</td></tr>' +
                        '<tr class="my-3 h-100">' +
                        '<th scope="row">註冊時間</th>' +
                        '<td class="pt-2">' +
                        response.signDate +
                        '</td></tr>';

                    $('#tbody2').html(trHTML);
                },
                204: function () {
                    let trHTML = '<tr class="my-3 h-100">' +
                        '<td class="pt-2"></td>' +
                        '<td class="pt-2">查無此會員</td>' +
                        '<td class="pt-2"></td>' +
                        '</tr>';
                    $('#tbody2').html(trHTML);
                }
            }
        });
    });

    //會員訂單表
    $(document).on('click', 'button[name="orderList"]', function () {
        let id = $(this).parent().parent().children().eq(0).text();

        $.ajax({
            type: 'GET',
            url: "/admin/member/" + id + "/order",
            statusCode: {
                200: function (response) {
                    console.log(response);
                    let trHTML = '';
                    $.each(response, function (i, userOrder) {

                        trHTML +=
                            '<tr class="my-3 h-100">' +
                            '<td class="pt-2">' +
                            '<button name="getProductOrder" type="button" class="btn btn-gr0201 align-content-center"' +
                            'data-bs-toggle="modal" data-bs-target="#Modal4">' +
                            userOrder.uuid +
                            '</span></button>' +
                            '</td>' +
                            '<td class="pt-2">' +
                            userOrder.orderTime +
                            '</td>' +
                            '<td class="pt-2"><span>' +
                            userOrder.amounts +
                            '</span>TWD</td>';
                    });
                    $('#tbody3').html(trHTML);
                },
                204: function () {
                    let trHTML = '<tr class="my-3 h-100">' +
                        '<td class="pt-2"></td>' +
                        '<td class="pt-2">查無訂單</td>' +
                        '<td class="pt-2"></td>' +
                        '</tr>';
                    $('#tbody3').html(trHTML);
                }
            }
        });
    });

    //會員訂單詳細列表按鈕
    $(document).on('click', 'button[name="getProductOrder"]', function () {
        let uuid = $(this).text();
        let orderStatus;
        $('#orderId').text(uuid);
        $.ajax({
            type: 'GET',
            url: "/admin/order/detail/" + uuid,
            statusCode: {
                200: function (order) {
                    if (order.orderStatus === -1) {
                        $('#orderStatus').text("待出貨");
                    }
                    if (order.orderStatus === 0) {
                        $('#orderStatus').text("運送中");
                    }
                    if (order.orderStatus === 1) {
                        $('#orderStatus').text("已收件");
                    }
                    $('#payment').val(order.payment);
                    $('#owner').val(order.cardOwner);
                    $('#cardNumber').val(order.cardNumber);
                    $('#deliver').val(order.deliver);
                    $('#sendFor').val(order.receiver);
                    $('#address').val(order.address);

                    $('#freight').text(order.freight);
                    if(order.discountNumber == 0) {
                        $('#discount').text("無");
                    } else {
                        $('#discount').text(order.discountNumber);
                    }                    $('#totalAmount').text(order.amounts);
                    loadItem(uuid);
                },
                404: function () {
                    let trHTML = '<tr class="my-3 h-100">' +
                        '<td class="pt-2"></td>' +
                        '<td class="pt-2">查無資訊</td>' +
                        '<td class="pt-2"></td>' +
                        '</tr>';
                    $('#Modal4').html(trHTML);
                }
            }
        });
    });

    //封鎖按鈕
    $(document).on('click', 'button[name="delete"]', function () {
        let id = $(this).parent().parent().children().eq(0).text();
        let lock;
       if($(this).val() === "true") {
           lock = false;
       }else {
           lock = true;
        }

        let data = {};
        data['locked'] = lock;

        $.ajax({
            type: 'PUT',
            url: "/admin/member/" + id + "/locked",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            statusCode: {
                200: function () {
                    alert('修改成功');
                    loadData();
                },
                404: function () {
                    alert('找不到用戶');
                },
                500: function () {
                    alert('修改錯誤');
                }
            }
        });
    });
});

//載入頁面
function loadData() {
    $.ajax({
        type: 'GET',
        url: "/admin/member",
        statusCode: {
            200: function (response) {

                let trHTML = '';
                $.each(response, function (i, user) {

                    let locked = user.locked;
                    if (locked === undefined) {
                        locked = false;
                    }
                    let lockOrUnlock;
                    let btnColor;
                    if (locked) {
                        btnColor = "btn-warning";
                        lockOrUnlock = "解除鎖定";
                    } else {
                        btnColor = "btn-danger";
                        lockOrUnlock = "封鎖用戶";
                    }
                    trHTML +=
                        '<tr class="my-3 h-100">' +
                        '<td class="pt-2">' +
                        user.id +
                        '</td>' +
                        '<td>' +
                        '<button name="getMemberInfo" type="button" class="btn btn-gr0201" data-bs-toggle="modal"' +
                        'data-bs-target="#Modal2">' +
                        user.username +
                        '</button></td>' +
                        '<td class="pt-2">' +
                        user.name +
                        '</td> ' +
                        '<td>' +
                        '<button name="orderList" type="button" class="btn btn-info me-2" data-bs-toggle="modal"' +
                        'data-bs-target="#Modal3">訂單查詢</button>' +
                        '</td>' +
                        '<td>' +
                        '<button name="delete" type="button" class="btn ' + btnColor + '  me-2" value="' + locked + '">' + lockOrUnlock + '</button>' +
                        '</td></tr>';
                });
                $('#tbody').html(trHTML);
            },
            204: function () {
                alert("204");
            }
        }
    });
}

//載入訂單商品列表
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
