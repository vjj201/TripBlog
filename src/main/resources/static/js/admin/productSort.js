'use strict';
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    loadData();
    $('#changeTag').hide();

    //動態新增標籤
    $('#createTag').on('click', function () {
        let productTag = $('#productTag').val();
        if (productTag === '') {
            $('#productTag').attr('placeholder', '麻煩輸入標籤名稱');
            return;
        }
        let div =
            '<div class="badge bg-light text-dark me-2">' +
            '<span name="tag" class="me-1">' +
            productTag +
            '</span>' +
            '<button type="button" name="removeTag" class="btn-close position-absolute" style="width: 0.1px; height:0.1px;" aria-label="Close"></button>' +
            '</div>';

        $('#tagZone').append(div);
        $('#productTag').val('');
    });

    //刪除動態新增標籤
    $(document).on('click', 'button[name="removeTag"]', function () {
        $(this).parent().remove();
    });

    //編輯標籤文字
    $(document).on('click', 'span[name="tag"]', function () {
        $('#changeTag').toggle();
        $('#changeTag').children().eq(0).children().eq(1).text($(this).attr('data-tag-id'));
        $('#changeTag').children().eq(1).val($(this).text());
        // $('#changeTag').children().eq(0).focus();
    });

    //標籤框按鈕
    $('#changeTag').children().eq(2).click(function () {
        let id = $('#changeTag').children().eq(0).children().eq(1).text();
        let name = $('#changeTag').children().eq(1).val();
        $('[data-tag-id=' + id + ']').text(name);

        $('#changeTag').toggle();
    });

    //刪除分類
    $(document).on('click', 'button[name="delete"]', function () {
        let name = $(this).parent().parent().children().eq(1).text();
        let confrim = confirm("刪除分類:" + name);
        if (confrim) {

            let id = $(this).parent().parent().children().first().text();

            $.ajax({
                url: '/admin/productSort/' + id,
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
        let sortName = $('#productSortName').val();

        if (sortName === '') {
            $('#productSortName').attr('placeholder', '麻煩輸入分類名稱');
            return;
        }

        if (updateId != null) {

            let tagList = new Array();
            $('span[name="tag"]').each(function () {
                let data = { 'id': $(this).attr('data-tag-id'), 'tagName': $(this).text() };
                tagList.push(data);
            });

            let data = {};
            data['sortName'] = sortName;
            data['tagList'] = tagList;


            $.ajax({
                url: '/admin/productSort/' + updateId,
                type: 'PUT',
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(data),
                statusCode: {
                    200: function () {
                        updateId = null;
                        alert('編輯成功');

                        $('button[name="removeTag"]').trigger('click');
                        $('#productTag').val('');
                        $('#productSortName').val('');

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

            let tagList = new Array();
            $('span[name="tag"]').each(function () {
                let data = { 'tagName': $(this).text() };
                tagList.push(data);
            });

            let data = {};
            data['sortName'] = sortName;
            data['tagList'] = tagList;

            $.ajax({
                url: '/admin/productSort',
                type: 'POST',
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(data),
                statusCode: {
                    201: function () {
                        alert('新增成功');

                        $('button[name="removeTag"]').trigger('click');
                        $('#productTag').val('');
                        $('#productSortName').val('');

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
        $('button[name="removeTag"]').trigger('click');
        $('#productTag').val('');
        $('#productSortName').val('');
    });

    //編輯按鈕
    $(document).on('click', 'button[name="update"]', function () {
        $('button[name="removeTag"]').trigger('click');
        $('#modelName').text('編輯分類');
        $('#create').text('編輯')

        updateId = $(this).parent().parent().children().first().text();
        let name = $(this).parent().parent().children().eq(1).text();
        let tagList = $(this).parent().parent().children().eq(2).children().children().children();

        $('#productSortName').val(name);
        $(tagList).each(function () {
            let productTag = ($(this).text());
            let id = $(this).attr('data-id');
            let div =
                '<div class="badge bg-light text-dark me-2">' +
                '<span name="tag" class="me-1" data-tag-id="' +
                id +
                '">' +
                productTag +
                '</span>' +
                '<button type="button" name="removeTag" class="btn-close position-absolute" style="width: 0.1px; height:0.1px;" aria-label="Close"></button>' +
                '</div>';

            $('#tagZone').append(div);
        });
    });




});

//載入頁面
function loadData() {
    $.ajax({
        type: 'GET',
        url: "/admin/productSort",
        statusCode: {
            200: function (response) {

                let trHTML = '';
                $.each(response, function (i, productSort) {
                    let li = '';
                    $.each(productSort.tagList, function (i, tag) {

                        li +=
                            '<li class="list-group-item"><small name="tagName" data-id="' +
                            tag.id +
                            '">' +
                            tag.tagName +
                            '</small></li>';

                    });

                    trHTML +=
                        '<tr class="my-3 h-100">' +
                        '<td class="pt-3">' +
                        productSort.id +
                        '</td>' +
                        '<td class="pt-3">' +
                        productSort.sortName +
                        '</td>' +
                        '<td><ul class="list-group list-group-horizontal">' +
                        li +
                        '</ul></td> ' +
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

