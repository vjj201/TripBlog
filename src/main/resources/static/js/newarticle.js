
$(function() {

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

        //創建物件
        let article = {};
        article['subjectcategory'] = subjectcategory;
        article['selectregion'] = selectregion;
        article['enteraddress'] = enteraddress;
        article['articletitle'] = articletitle;
        article['texteditor'] = texteditor;
        article['free_Tags'] = freeTags;


        $.ajax({
            url: '/user/newarticle',
            type: 'POST',
            async: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(article),
            success: function () {
                let name_1 = "美食";

                if (subjectcategory == name_1) {
                    window.location.href='eat';
                } else {
                    window.location.href='travel';
                }
            }
        });
    });

    //保存草稿按鈕功能
    $('#savearticle').click(function (e) {

        e.preventDefault();

        let subjectcategory = $('#subjectcategory').val();

        let selectregion = $('#selectregion').val();

        let enteraddress = $('#enteraddress').val();

        let articletitle = $('#articletitle').val();

        let texteditor = editor.getData();

        let freeTags =$("#articleTag").val();
        //創建物件
        let article = {};
        article['subjectcategory'] = subjectcategory;
        article['selectregion'] = selectregion;
        article['enteraddress'] = enteraddress;
        article['articletitle'] = articletitle;
        article['texteditor'] = texteditor;
        article['free_Tags'] = freeTags;

        $.ajax({
            url: '/user/newarticle',
            type: 'POST',
            async: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(article),
            success:
                alert("已經為您儲存草稿")
        });
    });
});


