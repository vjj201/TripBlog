console.log("有抓到這個js");
$(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });
    //不需判斷是否登入（我的空間已有filter攔截）

    let subject = "";
    let timeDirect;
    let myArticle = {};

    loadPage();
    function loadPage() {

        timeDirect = "000";
        subject = "";
        myArticle["subject"] = subject;
        myArticle["timeDirect"] = timeDirect;

        firstPage();
        newButton();
        clickPage();
        $("#timeDirect").change(function (e1){
            e1.preventDefault();
            subject = $("#subject option:selected").val();
            timeDirect = $("#timeDirect option:selected").val();
            console.log("subject" + subject);
            console.log("timeDirect" + timeDirect);
            console.log("ajax前-輸入搜尋吧查詢並送出第一頁");
            myArticle["subject"] = subject;
            myArticle["timeDirect"] = timeDirect;

            firstPage();
            newButton();
            // clickPage();
        })
        $("#subject").change(function (e1){
            e1.preventDefault();
            subject = $("#subject option:selected").val();
            timeDirect = $("#timeDirect option:selected").val();
            console.log("subject" + subject);
            console.log("timeDirect" + timeDirect);
            console.log("ajax前-輸入搜尋吧查詢並送出第一頁");
            myArticle["subject"] = subject;
            myArticle["timeDirect"] = timeDirect;

            firstPage();
            newButton();
            clickPage();
        })

    }

    function newButton(){
        $.ajax({
            url: "/user/newPageButtonForCollect",
            type: "GET",
            data: myArticle,
            success: function(response){
                console.log("按鈕換頁response=" + response);
                let html = "";
                // 取出頁數總數(頁數總數，於後端檔案，已計算完成)

                console.log("有取出頁數總數pageMount=" + response);

                //迴圈放入數字(例如:總共5頁；分別放入5,4,3,2,1)
                for(let p = 1 ; p <= response ; p++ ){
                    console.log("p迴圈" + p);
                    //自動生成的html格式
                    html += `
                  <option value="${p}">${p}</option>
                    `;
                    // 裝入[自動生成]的位置
                    $("#changePageBox").html(html);
                    console.log("跑完--裝入[自動生成]的位置");
                }
            },
        })
    };

    function firstPage(){

        $.ajax({
            url: "/user/myFirstSearchOfPageEatTravelForCollect",
            type: "GET",
            data: myArticle,
            success: function (response){  //response from 後端
                console.log("第一頁文章response" + response);
                console.log("建立空的html");
                let html = "";
                console.log("文章-for迴圈開始");
                // (開始)文章換頁生成
                for(let myArticleAll of response){
                    // 從資料庫取出文章資訊
                    html += getHtmlArticle(myArticleAll);

                    console.log("文章-for迴圈結束");
                    $("#articleBox").html(html);
                    console.log("跑完--輸入搜尋吧查詢並送出第一頁");

                    // (結束)文章換頁生成
                }
            }
        })
    };

    function getHtmlArticle(myArticleAll){
        let articleTitle = myArticleAll.articlesCollectId.articleTitle;
        let textEditor = myArticleAll.articlesCollectId.textEditor;
        let createDate = myArticleAll.articlesCollectId.createDate;
        let createTime = myArticleAll.articlesCollectId.createTime;
        let saveImgPath = myArticleAll.articlesCollectId.saveImgPath;
        let articleId = myArticleAll.articlesCollectId.articleId;
        const imgPath = "https://localhost:8080/";

        return `
        <!-- 文章圖片  -->
                     <div class="single-blog-area bg-gr0200 blog-style-2 mb-5 wow fadeInUp " data-wow-delay="0.2s"
                    data-wow-duration="1000ms">
                    <div class="row align-items-center">
                        <div class="col-12 col-md-6">
                            <div class="single-blog-thumbnail">
                             <img src="${ imgPath + saveImgPath}">
                            </div>
                        </div>
                        <div class="col-12 col-md-6 text-bl04">
                            <!-- 文章內容 -->
                            <div class="single-blog-content">
                                <h4><a href="https://localhost:8080/user/article/${articleTitle}" class="post-headline  btn-outline-bl01 text-bl04 fw-bold">
                                    ${articleTitle}   
                                    </a></h4>

                                <p class="text-bl04">${textEditor.substring(0,45)}...</p>
                                <div class="post-meta">
                                   <p class="text-bl04">發表於:&nbsp${createDate}&nbsp${createTime}</p>
                                    <button
                                         class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                        type="submit" name="${articleTitle}" onclick="javascript:location.href='/user/deleteCollect/${articleId}'">取消收藏</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        `;}

    function clickPage(){
        $("#changePageAll").on("click", "#pageSearch", function (event) {
            let pageValue = $("#changePageBox option:selected").val();
            console.log("pageValue=" + pageValue);
            let page = pageValue - 1;
            let myArticle = {};
            myArticle["page"] = page;
            myArticle["subject"] = subject;
            myArticle["timeDirect"] = timeDirect;

            $.ajax({
                url:"/user/myChangeSearchOfPageEatTravelForCollect",
                type: "GET",
                data: myArticle,
                success: function (response){
                    let html = "";
                    console.log("文章-for迴圈開始");
                    //(開始)文章換頁生成
                    for(let myArticleAll of response){
                        //從資料庫取出文章資訊
                        html += getHtmlArticle(myArticleAll);
                        console.log("文章-for迴圈結束");
                        $("#articleBox").html(html);
                        console.log("跑完 -- 輸入搜尋吧查詢並送出第一頁")
                    }
                }
            })
        })
    }

})


