console.log("有抓到這個js");
$(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //------
    // ~~~開啟及啟用－文章用~~~

    let subject = "";
    let timeDirect;
    let myArticle = {};

    //-------
    //不需判斷是否登入（我的空間已有filter攔截）
    loadPage();

    function loadPage() {

        timeDirect = "000";
        subject = "";
        myArticle["subject"] = subject;
        myArticle["timeDirect"] = timeDirect;

        firstPage();
        newButton();
        clickPage();

        //點擊主題
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
        //點擊時間排序
        $("#timeDirect").change(function (e2){
                e2.preventDefault();
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

    //文章內容
    function getHtmlArticle(myArticleAll){
        let articleTitle = myArticleAll.articleTitle;
        let textEditor = myArticleAll.textEditor;
        let createDate = myArticleAll.createDate;
        let createTime = myArticleAll.createTime;
        let saveImgPath = myArticleAll.saveImgPath;
        let articleId = myArticleAll.articleId;
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
                                        type="submit" name="${articleTitle}" onclick="javascript:location.href='/user/edit/${articleTitle}'">編輯</button>
                                            <button
                                         class="btn btn-sm btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                        type="submit" name="${articleTitle}" onclick="javascript:location.href='/user/delete/${articleTitle}/${articleId}'">刪除</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        `;}
    //~~第一頁~~
    function firstPage(){

        $.ajax({
            url: "/user/myFirstSearchOfPageEatTravel",
            type: "GET",
            data: myArticle,
            success: function (response){  //response from 後端
                console.log("第一頁文章response" + response);
                console.log("建立空的html");
                let html = "";
                console.log("文章-for迴圈開始");
                // (開始)文章換頁生成
                for(let myArticleAll of response){
                    //從資料庫取出文章資訊

                    html += getHtmlArticle(myArticleAll);

                    console.log("文章-for迴圈結束");
                    $("#articleBox").html(html);
                    console.log("跑完--輸入搜尋吧查詢並送出第一頁");

                    // (結束)文章換頁生成
                }
            }
        })
    };

    // 自動生成換頁按鈕
    function newButton(){
        $.ajax({
            url: "/user/newPageButtonForUser",
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

    //點擊換頁按鈕
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
                url:"/myChangeSearchOfPageEatTravel",
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
    //大方 end

    //大方  刪除文章
    // $("#btArticleDelete").on("click", "#btArticleDelete", function (e2) {
    //     e2.preventDefault();
    //     console.log("刪除 step1")
    //
    //     let articleTitle;
    //     let myArticle = {};
    //
    //     articleTitle = $("#articleTitle").text();
    //     myArticle["articleTitle"] = articleTitle;
    //
    //     $.ajax({
    //         url:"/myArticleDelete",
    //         type: "DELETE",
    //         data: myArticle,
    //         success: function (response) {
    //             console.log("刪除文章： " + myArticle);
    //             alert("文章刪除成功" + myArticle)
    //         }
    //     })
    // })

    //點擊主題
    // $("#subject").change(function (e1){
    //     e1.preventDefault();
    //     subject = $("#subject option:selected").val();
    //     timeDirect = $("#timeDirect option:selected").val();
    //     console.log("subject" + subject);
    //     console.log("timeDirect" + timeDirect);
    //     console.log("ajax前-輸入搜尋吧查詢並送出第一頁");
    //     myArticle["subject"] = subject;
    //     myArticle["timeDirect"] = timeDirect;







    //trytry第一階段 start
    //開啟頁面即啟動-抓入所有文章
    // let subject = $("#subject option:selected").val();
    // let timeDirect = $("#timeDirect option:selected").val();
    //
    // let article = {};
    // article["subject"] = subject;
    // article["timeDirect"] = timeDirect;
    // console.log("subject" + subject);
    // console.log("timeDirect" + timeDirect)
    // console.log("ajax前-輸入搜尋吧查詢並送出第一頁");
    //
    // $.ajax({
    //     url: "/user/findByUserId",
    //     type: "GET",
    //     // data: article,
    //     success: function (response) {
    //         console.log("第一頁文章response" + response);
    //         console.log("建立空的html");
    //         let html = "";
    //         console.log("文章-for迴圈開始");
    //         // (開始)文章換頁生成
    //         for (let articleAll of response) {
    //             let articleTitle = articleAll.articleTitle;
    //             let textEditor = articleAll.textEditor;
    //             let createDate = articleAll.createDate;
    //             let createTime = articleAll.createTime;
    //             let saveImgPath =articleAll.saveImgPath;
    //
    //             // 從資料庫取出文章資訊
    //             html += `
    //
    //             <!-- 文章圖片  -->
    //                  <div class="single-blog-area bg-gr0200 blog-style-2 mb-5 wow fadeInUp " data-wow-delay="0.2s"
    //                 data-wow-duration="1000ms">
    //                 <div class="row align-items-center">
    //                     <div class="col-12 col-md-6">
    //                         <div class="single-blog-thumbnail">
    //                         <img src="${'https://localhost:8080/' + saveImgPath}">
    //                         </div>
    //                     </div>
    //                     <div class="col-12 col-md-6 text-bl04">
    //                         <!-- 文章內容 -->
    //                         <div class="single-blog-content">
    //                             <h4><a href="https://localhost:8080/user/article/${articleTitle}" class="post-headline  btn-outline-bl01 text-bl04 fw-bold">
    //                                 ${articleTitle}
    //                                 </a></h4>
    //
    //                             <p class="text-bl04">${textEditor.substring(0,45)}...</p>
    //                             <div class="post-meta">
    //                                <p class="text-bl04">發表於:&nbsp${createDate}&nbsp${createTime}</p>
    //                                 <button
    //                                      class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
    //                                     type="submit" name="${articleTitle}" onclick="javascript:location.href='/user/edit/${articleTitle}'">編輯</button>
    //                                         <button
    //                                      class="btn btn-sm btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
    //                                     type="submit" name="${articleTitle}">刪除</button>
    //                             </div>
    //                         </div>
    //                     </div>
    //                 </div>
    //             </div>
    //       `;
    //
    //             console.log("文章-for迴圈結束");
    //             $("#articleBox").html(html);
    //             console.log("跑完--輸入搜尋吧查詢並送出第一頁");
    //
    //             // (結束)文章換頁生成
    //         }
    //     }
    // });

    //trytry第一階段 end

    //trytry第二階段 start
    // 自動生成換頁按鈕
            //迴圈放入數字(例如:總共5頁；分別放入5,4,3,2,1)
    //         for (p = 1; p <= response; p++) {
    //
    //             console.log("p迴圈=" + p);
    //
    //             // 自動生成的html格式
    //             html += `
    //       <li class="page-item "><button type="submit" id="${p}" class="page-link text-gr03 changePageButton">${p}</button></li>
    //       `;
    //             // 裝入[自動生成]的位置
    //             $("#changePageBox").html(html);
    //             console.log("跑完--裝入[自動生成]的位置");
    //         }
    //     }
    // });

    //trytry第二階段 end

    // 點按鈕
   //  $("#articleBox").on('click', 'button', function (event) {
   //      let choose = $(this).text();
   //      let articleTitle = $(this).attr('name');
   //      let article = {};
   //      article["articleTitle"] = articleTitle;
   //      let TUrl;
   //      console.log("choose" + choose);
   //      if (choose == "編輯") {
   //          // article["recommend"] = choose;
   //          TUrl = "/user/changleArticle";
   //          console.log("recommend" + article.articleTitle)
   //      } else {
   //          // article["collect"] = choose;
   //          TUrl = "/forCollect";
   //          console.log("collect" + article.articleTitle)
   //      }
   //      $.ajax({
   //          url: TUrl,
   //          type: "GET",
   //          data: article,
   //          success: function (response) {
   //              alert("林北按鈕回來瞜")
   //
   //          }
   //
   //
   //      });
   //
   //
   //  });


})


