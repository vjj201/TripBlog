alert("有進來嗎")
$(function () {
    //--------------------全域變數的家---------------------------------------
    let article = {};
    //----------------------------------------------------------
    $("#travelArticleBox").on('click', 'input', function (e) {
        let choose = $(this).val();
        let articleTitle = $(this).attr('name');
        $(this).val("已推薦");
        article["articleTitle"] = articleTitle;
        clickButton(choose,articleTitle);

    });

    function clickButton(choose,articleTitle) {
        $("#searchAddress").val()
        console.log("choose" + choose);
        let TUrl;
        console.log("choose" + choose);
        if (choose == "推薦") {
            // article["recommend"] = choose;
            TUrl = "/forRecommend";
            console.log("recommend" + article.articleTitle)
        } else if (choose == "檢舉") {
            // article["Report"] = choose;
            TUrl = "/forReport";
            console.log("Report" + article.articleTitle)
        } else {
            // article["collect"] = choose;
            TUrl = "/forCollect";
            console.log("collect" + article.articleTitle)
        }
        $.ajax({
            url: TUrl,
            type: "POST",
            data: article,
            success: function (response) {
                console.log("按鈕裡面的" + articleTitle)


            }
        });

    }

})