$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    loadData();

    $(document).on('click', 'div[name="delete"]', function () {
        let id = $(this).children().attr('id');
        $.ajax({
            type: 'DELETE',
            url: "/user/notify/" + id,
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
    });

    $(document).on('click', 'button[name="read"]', function () {
        let text = $(this).text();

        if (text === "已讀") {
            return;
        }

        let id = $(this).attr('data-value');
        $.ajax({
            type: 'PUT',
            url: "/user/notify/" + id,
            statusCode: {
                200: function () {
                    loadData();
                },
                404: function () {
                   alert('更新失敗')
                }
            }
        });
    });
});

//載入頁面
function loadData() {
    $.ajax({
        type: 'GET',
        url: "/user/notifyList",
        statusCode: {
            200: function (response) {
                let hTML = '';
                $.each(response, function (i, notify) {
                    let read;
                    let color;
                    if (notify.alreadyRead) {
                        read = '已讀';
                        color = 'btn-pk03';
                    } else {
                        read = '未讀';
                        color = 'btn-info';
                    }

                    hTML +=
                        '<div class="col-8 m-2">' +
                        '<div class="card border border-2 border-pk01 shadow-sm rounded-3">' +
                        '<div class="card-body my-2 ">' +
                        '<div class="row justify-content-center">' +
                        '<div class="col-10">' +
                        '<h5 class="card-title fs-3 text-bl04 fw-bold"><button name="read" data-value="' + notify.id + '" ' +
                        'class="btn rounded-circle p-3 me-3 ' + color + ' border-2 border-gr0200 rounded-pill text-gr0200 fw-bold shadow-sm ">' +
                        read +
                        '</button>' +
                        '<span>' +
                        notify.title +
                        '</span>' +
                        '</h5>' +
                        '<p class="card-text mt-3">' +
                        notify.content +
                        ' </p>' +
                        '</div>' +
                        '<div name="delete" class="col-2 d-flex justify-content-end align-items-center">' +
                        '<a id="' + notify.id + '" class="btn border-2 border-pk03 rounded-pill text-pk03 fw-bold shadow-sm">刪除</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="card-footer bg-pk01">' +
                        '<small class="text-bl04 fw-bold d-flex justify-content-between mt-3">' +
                        '<p>時間 : <span>' + notify.sendTime + '</span></p>' +
                        '</small></div></div></div>';
                });
                $('#cardList').html(hTML);

                $.ajax({
                    type: 'GET',
                    url: "/user/notifyCount",
                    statusCode: {
                        200: function (count) {
                            $('#count').text(count);
                        }
                    }
                });

            },
            204: function () {
                $('#cardList').html('<tr><td colspan="5" class="text-center p-5 m-5"><h3>查無資料</h3></td></tr>');
            }
        }
    });
}

