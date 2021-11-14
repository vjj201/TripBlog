'use strict';
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    loadData();

    $('#button-addon5').click(function () {
        $.ajax({
            type: 'POST',
            url: "/admin/order/page/1",
            statusCode: {
                200: function (response) {

                    let trHTML = '';
                    $.each(response, function (i, order) {

                        let option = '';
                        let orderStatus = order.orderStatus;


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
                            '<button name="getProducts" type="button" class="btn btn-gr0201" data-bs-toggle="modal"' +
                            'data-bs-target="#Modal2">' +
                            order.uuid +
                            '</button></td>' +
                            '<td class="pt-2">' +
                            order.username +
                            '</td>' +
                            '<td class="pt-2">' +
                            order.orderTime +
                            '</td> ' +
                            '<td><select name="status" class="form-select me-2" style = "width: 9rem"><li>' +
                            option +
                            '</li></select></td> ' +
                            '<td class="pt-2">' +
                            order.amounts +
                            'TWD</td></tr>';

                    });

                    $('#tbody').html(trHTML);
                },
                204: function () {
                    alert("204");
                }
            }
        });
    });


});

//載入頁面
function loadData() {
    $.ajax({
        type: 'GET',
        url: "/admin/order",
        statusCode: {
            200: function (response) {

                let trHTML = '';
                $.each(response, function (i, order) {

                    let option = '';
                    let orderStatus = order.orderStatus;


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
                        '<button name="getProducts" type="button" class="btn btn-gr0201" data-bs-toggle="modal"' +
                        'data-bs-target="#Modal2">' +
                        order.uuid +
                        '</button></td>' +
                        '<td class="pt-2">' +
                        order.username +
                        '</td>' +
                        '<td class="pt-2">' +
                        order.orderTime +
                        '</td> ' +
                        '<td><select name="status" class="form-select me-2" style = "width: 9rem"><li>' +
                        option +
                        '</li></select></td> ' +
                        '<td class="pt-2">' +
                        order.amounts +
                        'TWD</td></tr>';

                });

                $('#tbody').html(trHTML);
            },
            204: function () {
                alert("204");
            }
        }
    });
}

