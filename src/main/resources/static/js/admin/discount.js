'use strict';
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    // 載入頁面
    loadDiscountList();

    //優惠卷詳細按鈕
    $(document).on('click', 'button[name="getDiscountDetail"]', function () {
        let id = this.id;
        $.ajax({
            type: 'GET',
            url: "/admin/discount/" + id,
            dataType: 'json',
            statusCode: {
                200: function (response) {

                    let trHTML = '';

                    trHTML +=
                        '<table class="table table-striped table-sm">' +
                        '<div>' +
                        response.detail +
                        '</div></table>';
                    $('#discountDetailBody').html(trHTML);
                },
                204: function () {
                    let trHTML = '<tr class="my-3 h-100">' +
                        '<td class="pt-2"></td>' +
                        '<td class="pt-2">查無此會員</td>' +
                        '<td class="pt-2"></td>' +
                        '</tr>';
                    $('#discountDetailBody').html(trHTML);
                }
            }
        })
    });

    //新增優惠按鈕
    $('#addNewDiscountCheck').click(function (e) {
        e.preventDefault();

        //抓取彈跳式表單中輸入的值
        let addDiscountTitle = $('#addDiscountTitle').val();
        let addDiscountDetail = $('#addDiscountDetail').val();
        let addDiscountRequirement = $('#addDiscountRequirement').val();
        let addDiscountNumber = $('#addDiscountNumber').val();
        let addDiscountExpiredTime = $('#addDiscountExpiredTime').val();

        //非空判斷
        if (!addDiscountTitle || !addDiscountDetail || !addDiscountRequirement ||
            !addDiscountNumber || !addDiscountExpiredTime ) {
            if (!addDiscountTitle) {
                $('#addNewDiscountCheck').addClass('disabled');
                $('#messageDiscountTitle').text('請輸入優惠代碼');
            } else {
                $('#addNewDiscountCheck').removeClass('disabled');
                $('#messageDiscountTitle').empty();
            }
            if (!addDiscountDetail) {
                $('#addNewDiscountCheck').addClass('disabled');
                $('#messageDiscountDetail').text('請輸入優惠詳細內容');
            } else {
                $('#addNewDiscountCheck').removeClass('disabled');
                $('#messageDiscountDetail').empty();
            }
            if (!addDiscountRequirement) {
                $('#addNewDiscountCheck').addClass('disabled');
                $('#messageDiscountRequirement').text('請輸入優惠低消');
            } else {
                $('#addNewDiscountCheck').removeClass('disabled');
                $('#messageDiscountRequirement').empty();
            }
            if (!addDiscountNumber) {
                $('#addNewDiscountCheck').addClass('disabled');
                $('#messageDiscountNumber').text('請輸入優惠折數');
            } else {
                $('#addNewDiscountCheck').removeClass('disabled');
                $('#messageDiscountNumber').empty();
            }
            if (!addDiscountExpiredTime) {
                $('#addNewDiscountCheck').addClass('disabled');
                $('#messageDiscountExpiredTime').text('請選擇使用期限');
            } else {
                $('#addNewDiscountCheck').removeClass('disabled');
                $('#messageDiscountExpiredTime').empty();
            }
        } else { //確認所有欄位都有值才執行

            //創建物件
            let discount = {};
            discount['title'] = addDiscountTitle;
            discount['detail'] = addDiscountDetail;
            discount['requirement'] = addDiscountRequirement;
            discount['discountNumber'] = addDiscountNumber;
            discount['expiredTime'] = addDiscountExpiredTime;
            console.log(discount);

            $.ajax({
                url: 'discount',
                type: 'POST',
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(discount),
                success: function (response) {
                    console.log(response);
                    window.location.href = "discountPage"
                }
            });
        }
    });

    //刪除優惠按鈕
    $(document).on('click', 'button[name="delete"]', function () {
        let id = this.id;
        $.ajax({
            type: 'DELETE',
            url: "/admin/discount/" + id,
            dataType: 'json',
            statusCode: {
                200: function () {
                    console.log("成功刪除產品Id：" + id);
                    window.location.href = "discountPage"
                }
            }
        })
    });
});

//載入所有優惠卷
function loadDiscountList() {
    $.ajax({
        type: 'GET',
        url: "/admin/discount",
        contentType: 'application/json;charset=utf-8',
        statusCode: {
            200: function (discounttList) {
                let trHTML = '';

                $.each(discounttList, function (i, discount) {
                    trHTML +=
                    '<tr class="my-3 h-100">' +
                    '<td class="pt-2">' +
                    discount.id +
                    '</td><td>' +
                    '<button id="' + 
                    discount.id + 
                    '" name="getDiscountDetail" type="button" class="btn btn-gr0201"' +
                    'data-bs-toggle="modal" data-bs-target="#Modal">' +
                    discount.title +
                    '</button></td><td class="pt-2">' +
                    discount.requirement +
                    'TWD</td><td class="pt-2">' +
                    discount.discountNumber +
                    '%</td><td class="pt-2">' +
                    discount.createDate +
                    '</td><td class="pt-2">' +
                    discount.expiredTime +
                    '</td><td>' +
                    '<button id="' +
                     discount.id + 
                     '"value="true" name="delete" type="button" class="btn btn-danger me-2">刪除</button>' +
                    '</td></tr>';
                })
                $('#tbody').html(trHTML);

            },
            204: function () {
                $('#tbody').html('<tr><td colspan="12" class="text-center p-5 m-5"><h3>查無資料</h3></td></tr>');
            }
        }
    })
}