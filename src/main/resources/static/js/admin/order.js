'use strict';
let page = 1;
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    loadData();

    //搜尋按鈕
    $('#button-addon5').on('click', function () {
        page = 1;
        loadData();
    });

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
                console.log(response);
                let totalPage = response.totalPages;
                let orderList = response.content;
                let first = response.first;
                let last = response.last;

                let trHTML = '';
                $.each(orderList, function (i, order) {

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

