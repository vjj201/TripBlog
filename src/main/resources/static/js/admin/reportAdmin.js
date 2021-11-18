"use strict";
$(document).ready(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    loadData();

    //文章點擊列表
    $(document).on("click", 'button[name="getArticle"]', function () {
        let articleId = $(this).val();
        console.log("articleId外面==>"+articleId);
        // let article = {};
        // article["articleId"] = articleId;
        // console.log("article{}外面==>"+article);

        $.ajax({
            type: "GET",
            url: "/admin/report/" + articleId + "/product",
            // data: article,
            statusCode: {
                200: function (response) {
                    console.log("----------------------------------------");
                    console.log(response);

                    for (let article of response) {
                        let articleId = article.articleId;
                        let articleTitle = article.articleTitle;
                        let textEditor = article.textEditor;

                        let html = "";
                        html += `
          <tr class="my-3 h-100">
          <td class="pt-2">${articleId}</td>
          <td class="pt-2">${articleTitle}</td>
          <td class="pt-2">${textEditor}</td>
          </tr>
          `;
                        $('#tbody2').html(html);
                    }
                },
                404: function () {
                    console.log("文章標題連結抓不到");
                    let html = '<tr class="my-3 h-100">' +
                        '<td class="pt-2"></td>' +
                        '<td class="pt-2">查無此文章</td>' +
                        '<td class="pt-2"></td>' +
                        '</tr>';
                    $('#tbody2').html(html);
                },
            },
        });
    });

    //刪除分類
    $(document).on('click', 'button[name="delete"]', function () {
        let name = $(this).parent().parent().children().eq(1).text();
        let confrim = confirm("刪除文章:" + name);
        if (confrim) {

            let id = $(this).parent().parent().children().first().text();

            $.ajax({
                url: '/admin/report/' + id,
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



    // 修改狀態
$('.btn-update').on('click', function () {

let id =$(this).val();
let status = $("#statusSelect option:selected").text();
console.log('-----------------------------------');
console.log("id="+id,"status="+status);

let data = {};
data['id'] =id ;
data['status'] =status ;

$.ajax({
    url: '/admin/report/'+id,
    type: 'PUT',
    async: false,
    contentType: 'application/json;charset=utf-8',
    data: JSON.stringify(data),
    statusCode: {
        200: function (response) {      
            alert(response);},
        500: function () {
                alert('編輯失敗');
        }
        }
});
});

function loadData() {
    $.ajax({
        type: "GET",
        url: "/admin/report",
        statusCode: {
            200: function (response) {
                console.log("檢舉列表response" + response);
                let html = "";
                console.log("檢舉列表-for迴圈開始");
                // (開始)檢舉列表迴圈生成
                for (let reports of response) {
                    // 文章ID
                    let ArticleId = reports.articlesReportId.articleId;
                    // 文章標題
                    let articleTitle = reports.articlesReportId.articleTitle;
                    // 檢舉次數
                    let Report = reports.articlesReportId.report;
                    //文章顯示狀態
                    let isShow = reports.articlesReportId.show;
                    // 從資料庫取出文章資訊
                    html += `
      <tr class="my-3 h-100">
      <td class="pt-2">${ArticleId}</td>
      <td class="pt-2">
          <button name="getArticle" type="button" class="btn btn-gr0201"
              data-bs-toggle="modal" data-bs-target="#Modal2" value="${ArticleId}">${articleTitle}
          </button>
      </td>
      <td class="pt-2">${Report}</td>
      <td class="pt-2">${isShow}</td>

      <td>
          <button name="update" type="button" class="btn-update btn btn-primary me-2"
              data-bs-toggle="modal" data-bs-target="#Modal" value="${ArticleId}">編輯</button>
          <button name="delete" type="button" class="btn btn-danger me-2">刪除</button>
      </td>
      </tr>

      `;

                    console.log("檢舉列表-for迴圈結束");
                    $("#tbody").html(html);
                    // (結束)檢舉列表迴圈生成
                }
            },

            204: function () {
                alert("204");
            },
        },
    });
}

});
