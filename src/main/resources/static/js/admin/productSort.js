'use strict';
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    loadData();

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

    //刪除標籤
    $(document).on('click', 'button[name="removeTag"]', function () {
        $(this).parent().remove();
    });

    //創建分類
    $('#create').on('click', function () {
        let sortName = $('#productSortName').val();

        if (sortName === '') {
            $('#productSortName').attr('placeholder', '麻煩輸入分類名稱');
            return;
        }
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
                            '<li class="list-group-item"><small>' +
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
                        '<button type="button" class="btn btn-primary me-2">編輯</button>' +
                        '<button type="button" class="btn btn-danger me-2"> 刪除</button>' +
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