'use strict';
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    loadData();


    //刪除分類
    $(document).on('click', 'button[name="delete"]', function () {
        let name = $(this).parent().parent().children().eq(1).text();
        let confrim = confirm("刪除店家:" + name);
        if (confrim) {

            let id = $(this).parent().parent().children().first().text();

            $.ajax({
                url: '/admin/brand/' + id,
                type: 'DELETE',
                async: false,
                statusCode: {
                    200: function () {
                        alert('刪除成功');
                        loadData();
                    },
                    500: function () {
                        alert('刪除失敗');
                    }
                }
            });
        }

    });

    //判斷更新或新增
    let updateId = null;

    //創建或更新分類
    $('#create').on('click', function () {
        let brandName = $('#brandName').val();
        let brandLocation = $('#brandLocation').val();
        let brandAbout = $('#brandAbout').val();

        let flag = false;
        if (brandName === '') {
            flag = true;
            $('#brandName').attr('placeholder', '麻煩輸入商家名稱');
        }

        if (brandLocation === '') {
            flag = true;
            $('#brandLocation').attr('placeholder', '麻煩輸入店家地址');
        }

        if (brandAbout === '') {
            flag = true;
            $('#brandAbout').attr('placeholder', '麻煩輸入店家簡介');
        }

        if (flag) {
            return;
        }

        let data = {};
        data['brandName'] = brandName;
        data['aboutBrand'] = brandAbout;
        data['location'] = brandLocation;

        if (updateId != null) {

            $.ajax({
                url: '/admin/brand/' + updateId,
                type: 'PUT',
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(data),
                statusCode: {
                    200: function () {
                        updateId = null;
                        alert('編輯成功');

                        $('#brandName').val('');
                        $('#brandLocation').val('');
                        $('#brandAbout').val('');

                        loadData();
                        $('#createBack').trigger('click');
                    },
                    404: function () {
                        alert('查無資料');
                    },
                    500: function () {
                        alert('編輯失敗');
                    }
                }
            });
        } else {

            $.ajax({
                url: '/admin/brand',
                type: 'POST',
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(data),
                statusCode: {
                    201: function () {
                        alert('新增成功');

                        $('#brandName').val('');
                        $('#brandLocation').val('');
                        $('#brandAbout').val('');

                        loadData();
                        $('#createBack').trigger('click');
                    },
                    500: function () {
                        alert('新增失敗');
                    }
                }
            });
        }

    });

    //新增按鈕
    $(document).on('click', '#createBtn', function () {
        updateId = null;
        $('#modelName').text('新增分類');
        $('#create').text('創建');
        $('#brandName').val('');
        $('#brandLocation').val('');
        $('#brandAbout').val('');
    });

    //編輯按鈕
    $(document).on('click', 'button[name="update"]', function () {
        $('#modelName').text('編輯分類');
        $('#create').text('編輯')

        updateId = $(this).parent().parent().children().first().text();
        let name = $(this).parent().parent().children().eq(1).text();
        let location = $(this).parent().parent().children().eq(2).text();
        let about = $(this).parent().parent().children().eq(3).text();

        $('#brandName').val(name);
        $('#brandLocation').val(location);
        $('#brandAbout').val(about);
    });

    //商家產品列表
    $(document).on('click', 'button[name="getProducts"]', function () {
        let id = $(this).parent().parent().children().eq(0).text();

        $.ajax({
            type: 'GET',
            url: "/admin/brand/" + id + "/product",
            statusCode: {
                200: function (response) {

                    let trHTML = '';
                    $.each(response, function (i, product) {

                        trHTML +=
                            '<tr class="my-3 h-100">' +
                            '<td class="pt-3">' +
                            product.id +
                            '</td>' +
                            '<td class="pt-3">' +
                            product.productName +
                            '</td> ' +
                            '<td class="pt-3">' +
                            product.price +
                            'TWD</td></tr>';
                    });

                    $('#tbody2').html(trHTML);
                },
                404: function () {
                    let trHTML = '<tr class="my-3 h-100">' +
                        '<td class="pt-2"></td>' +
                        '<td class="pt-2">查無商品</td>' +
                        '<td class="pt-2"></td>' +
                        '</tr>';
                    $('#tbody2').html(trHTML);
                }
            }
        });
    });

});

//載入頁面
function loadData() {
    $.ajax({
        type: 'GET',
        url: "/admin/brand",
        statusCode: {
            200: function (response) {

                let trHTML = '';
                $.each(response, function (i, brand) {

                    trHTML +=
                        '<tr class="my-3 h-100">' +
                        '<td class="pt-3">' +
                        brand.id +
                        '</td>' +
                        '<td>' +
                        '<button name="getProducts" type="button" class="btn btn-gr0201" data-bs-toggle="modal"' +
                        'data-bs-target="#Modal2">' +
                        brand.brandName +
                        '</button></td>' +
                        '<td class="pt-3">' +
                        brand.location +
                        '</td> ' +
                        '<td class="pt-3">' +
                        brand.aboutBrand +
                        '</td> ' +
                        '<td>' +
                        '<button name="update" type="button" class="btn btn-primary me-2" data-bs-toggle="modal" data-bs-target="#Modal">編輯</button>' +
                        '<button name="delete" type="button" class="btn btn-danger me-2"> 刪除</button>' +
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

