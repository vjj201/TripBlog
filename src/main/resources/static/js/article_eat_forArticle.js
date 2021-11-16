
$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    let subject = "美食";


//創建物件
    let article = {};
    article["subject"] = subject;
    console.log("第14行");


//輸入搜尋吧查詢並送出第一頁
    console.log("ajax前-輸入搜尋吧查詢並送出第一頁");

    $.ajax({
        url: "/firstSearchOfPageEatTravel",
        type: "GET",
        data: article,
        success: function (response) {
            console.log("第一頁文章response" + response);
            //
            console.log("建立空的html")
            let html = "";
            console.log("文章-for迴圈開始")
// (開始)文章換頁生成
            for (let articleAll of response) {
                let articleTitle = articleAll.articleTitle;
                let textEditor = articleAll.textEditor;


                // 從資料庫取出文章資訊
                html += `

                    <!-- 文章圖片  -->
                    <div class="single-blog-area bg-gr0200 blog-style-2 mb-5 wow fadeInUp " data-wow-delay="0.2s"
                        data-wow-duration="1000ms">
                        <div class="row align-items-center">
                            <div class="col-12 col-md-6">
                                <div class="single-blog-thumbnail">
                                    <img src="../static/images/3.jpg" alt="" th:src="@{/images/3.jpg}">
                                    <div class="post-date">
                                        <a href="#" class="text-bl04">12 <span class="text-bl04">march</span></a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12 col-md-6 text-bl04">
                                <!-- 文章內容 -->
                                <div class="single-blog-content">
                                    <div class="line"></div>
                                    <a href="#" class="post-tag text-bl04 ">Lifestyle</a>
                                    <h4><a href="#" class="post-headline  btn-outline-bl01 text-bl04 fw-bold">
                                        ${articleTitle}   
                                        </a></h4>

                                    <p class="text-bl04"> ${textEditor}</p>
                                    <div class="post-meta">
                                        <p class="text-bl04">By <a href="#" class="text-bl04">james smith</a></p>
                                        <p>3 comments</p>
                                        <button
                                            class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                            type="submit" value="推薦">推薦</button>
                                        <button
                                            class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                            type="submit" value="收藏">收藏</button>
                                        <button
                                            class="btn btn-sm btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                            type="submit" value="檢舉">檢舉</button>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>


              `;

                console.log("文章-for迴圈結束")
                $("#eatArticleBox").html(html);
                console.log("跑完--輸入搜尋吧查詢並送出第一頁");

// (結束)文章換頁生成
            }
            let firstPageAnswer = response;
        }
    });


// 自動生成換頁按鈕
    $.ajax({
        url: "/newPageButtonEatTravel",
        type: "GET",
        data: article,
        success: function (response) {
            console.log("按鈕換頁response=" + response);

            let html = "";

            // 取出頁數總數(頁數總數，於後端檔案，已計算完成)

            console.log("有取出頁數總數pageMount=" + response);

            //迴圈放入數字(例如:總共5頁；分別放入5,4,3,2,1)
            for (p = 1; p <= response; p++) {

                console.log("p迴圈=" + p);

                // 自動生成的html格式
                html += `
          <li class="page-item "><button type="submit" id="${p}" class="page-link text-gr03 changePageButton">${p}</button></li>
          `;
                // 裝入[自動生成]的位置
                $("#changePageBox").html(html);
                console.log("跑完--裝入[自動生成]的位置");
            }
        }
    });
// ---------------------------------------------------
// 點擊換頁按鈕
    $("#changePageBox").on('click', 'button', function (event) {
        alert('有效');

        // let a = $(this).attr("name",true)
        // console.log(a);

        let pageValue = $(this).text()

        alert(pageValue);

        console.log("pageValue=" + pageValue);

        let page = pageValue - 1;

        let article = {}
        article["page"] = page;
        article["subject"] = subject;

        $.ajax({
            url: "/changeSearchOfPageEatTravel",
            type: "GET",
            data: article,
            success: function (response) {
                let html = "";
                console.log("文章-for迴圈開始")
                // (開始)文章換頁生成
                for (let articleAll of response) {
                    let articleTitle = articleAll.articleTitle;
                    let textEditor = articleAll.textEditor;

                    // 從資料庫取出文章資訊

                    html += `

                        <!-- 文章圖片  -->
                        <div class="single-blog-area bg-gr0200 blog-style-2 mb-5 wow fadeInUp " data-wow-delay="0.2s"
                            data-wow-duration="1000ms">
                            <div class="row align-items-center">
                                <div class="col-12 col-md-6">
                                    <div class="single-blog-thumbnail">
                                        <img src="../images/3.jpg" alt="" th:src="@{/images/3.jpg}" >
                                        <div class="post-date">
                                            <a href="#" class="text-bl04">12 <span class="text-bl04">march</span></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12 col-md-6 text-bl04">
                                    <!-- 文章內容 -->
                                    <div class="single-blog-content">
                                        <div class="line"></div>
                                        <a href="#" class="post-tag text-bl04 ">Lifestyle</a>
                                        <h4><a href="#" class="post-headline  btn-outline-bl01 text-bl04 fw-bold">
                                            ${articleTitle}   
                                            </a></h4>
    
                                        <p class="text-bl04"> ${textEditor}</p>
                                        <div class="post-meta">
                                            <p class="text-bl04">By <a href="#" class="text-bl04">james smith</a></p>
                                            <p>3 comments</p>
                                            <button
                                                class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                                type="submit" value="推薦">
                                            <button
                                                class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                                type="submit" value="收藏">
                                            <button
                                                class="btn btn-sm btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                                type="submit" value="檢舉">
    
                                        </div>
                                    </div>
                                </div>
    
                            </div>
                        </div>
    
    
                  `;

                    console.log("文章-for迴圈結束")
                    $("#eatArticleBox").html(html);
                    console.log("跑完--輸入搜尋吧查詢並送出第一頁");

                }
            }
        });


    });



//-----------------------------------------------------------------------------
//     $("#articleBox").on('click', 'a', function (event){
//     //    alert("a標籤被點了");
//         let articleTitle = $(this).text();
//     //    alert(articleTitle);
//         let article = {};
//         article["articleTitle"] = articleTitle;
//         $.ajax({
//             url: "/findByArticleTitle",
//             type: "GET",
//             data: article,
//             success: function (response) {
//           //      alert("林北成功用標題找到文章了")
//                 $(location).attr("href","https://localhost:63342/TripBlog/templates/article.html");
// //---------------------------------------------------------------------------------------------------
//             //for秉豐
// //---------------------------------------------------------------------------------------------------

//             }
//         });

//     });

    // $("#eatArticleBox").on('click', 'a', function (event) {
    //     alert("a標籤被點了");
    //     let eatArticleTitle = $(this).text();
    //     //    alert(articleTitle);
    //     let eatArticle = {};
    //     eatArticle["eatArticleTitle"] = eatArticleTitle;
    //     $.ajax({
    //         url: "/findByArticleTitle",
    //         type: "GET",
    //         data: eatArticle,
    //         success: function (response) {
    //                     }
    //                 });
    //             });


        $("#eatArticleBox").on('click','button',function(event){

            let choose = $(this).text();
            let eatArticleTitle = $(this).attr('name');

            let eatArticle = {};
            eatArticle["eatArticleTitle"] = eatArticleTitle;
            let TUrl;
            console.log("choose" + choose);
            if(choose == "推薦"){
                // alert('aaaa');
                TUrl="/forRecommend";
                console.log("recommend" + eatArticle.eatArticleTitle)
            }else if (choose == '檢舉') {
                // alert('bbbbb');
                TUrl = "/forReport";
                console.log("Report" + eatArticle.articleTitle)
            } else {
                // alert('ccc');
                TUrl = "/forCollect";
                console.log("collect" + eatArticle.articleTitle)
            }

            $.ajax({
                url: TUrl,
                type: "POST",
                data: eatArticleTitle,
                success: function (response){
                    alert("來來來！");
                }
            })
        })
    });
