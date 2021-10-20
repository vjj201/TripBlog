
$(function() {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //-------------------------顯示資料庫裏面的標籤------------------
    $.ajax({
        url: "/user/findtags",
        type: "GET",
        // async: false,
        contentType: 'application/json;charset=utf-8',
        data: "json",
        success: function (response) {
            for (let tag of response) {
         //       let id = tag.id;
          //      let name = tag.name;
           //      console.log(tag);
                $("#articleTag").append(
                    $("<option></option>").text(tag)
               );
            }
        },
    });

//提交按鈕發表AJAX請求
    $('#pusharticle').click(function (e) {

        e.preventDefault();

        let subjectcategory = $('#subjectcategory').val();

        let selectregion = $('#selectregion').val();

        let enteraddress = $('#enteraddress').val();

        let articletitle = $('#articletitle').val();

        let texteditor = editor.getData();
//-------------------------------------
        let freeTags =$("#articleTag").val();
//---------------------------------------------
        alert("已寫入資料庫" + texteditor);
        //創建物件
        let article = {};
        article['subjectCategory'] = subjectcategory;
        article['selectRegion'] = selectregion;
        article['enterAddress'] = enteraddress;
        article['articleTitle'] = articletitle;
        article['textEditor'] = texteditor;
        article['free_Tags'] = freeTags;


        $.ajax({
            url: '/user/newarticle',
            type: 'POST',
            async: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(article),
            success: null
        });
    });
});


