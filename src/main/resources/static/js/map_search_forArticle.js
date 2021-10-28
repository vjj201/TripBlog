
$(function() {
    $('#btsearch').click(function (e) {

        e.preventDefault();
        let enteraddress = $('#searchAddress').val();
        //創建物件
        let article = {};
        article['enterAddressName'] = enteraddress;
        $.ajax({
            url: '/findByAddress',
            type: 'GET',
            data: article,
            success: function (response) {
                alert('產生文章0')
                for (let articleAll of response) {
                    let id =articleAll.id;
                    let articleTitle = articleAll.articleTitle;
                    let enterAddressName = articleAll.enterAddressName;
                    let textEditor = articleAll.textEditor;
                    alert('產生文章1')
                    $("#joTest").text("測試成功啦!");
                    $("#joTest2").text(articleTitle);
                    alert('產生文章2')
                    $("#articleBox").append(
                    $("<div></div>").attr("value", id).text(articleTitle));
                    alert('產生文章3')
                    $("#articleBox").append(
                        $("<div></div>").attr("value", id).html("<div class=\"card mx-2 my-2 rounded-3\" style=\"max-width: 540px;\">\n" +
                            "<div class=\"row g-0\">\n" +
                            "  <div class=\"col-md-3 py-3 px-2 overflow-hidden rounded-3\">\n" +
                            "    <img class=\"w-100 h-100\" src=\"https://picsum.photos/300/300?random=1\" alt=\"一張圖\">\n" +
                            "  </div>\n" +
                            "  <div class=\"col-md-9\">\n" +
                            "    <div class=\"card-body\">\n" +
                            "      <h5><a href=\"#\" class=\"post-headline text-bl04 mapArticleTitle\">Party people in the house</a></h5>\n" +
                            "      <p class=\"card-text m-0\"><small class=\"text-bl04 mapArticleAdress\">106台北市大安區忠孝東路三段1號</small></p>\n" +
                            "      <p class=\"card-text\"><small class=\"text-bl04\">Last updated 3 mins ago</small></p>\n" +
                            "      <a href=\"#\" class=\"btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold\">推薦</a>\n" +
                            "      <a href=\"#\" class=\"btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold\">收藏</a>\n" +
                            "      <a href=\"#\" class=\"btn btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold\">檢舉</a>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "</div>\n" +
                            "</div>\n"));
                        $(".mapArticleTitle").attr("value", id).text(articleTitle);
                        $(".mapArticleAdress").attr("value", id).text(enterAddressName);
                    // $("#articleBox").append(
                    //     $("<div></div>").attr("value", id).load("article_new.txt")
                    // );
                  }
                }
        });

    });
});


