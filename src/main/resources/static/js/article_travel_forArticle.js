console.log("有抓到這個js");
// --------------------------------------
$(function () {
        //csrf防護
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function (e, xhr) {
            xhr.setRequestHeader(header, token);
        });
        //-----------------------------------------------
        // 全域變數
        //~~~登入判定用~~~
        let loginCheck = $("#dropdownMenuLink").text().split(" ").join("");
        let cus = loginCheck.search("訪客");
        console.log("這是" + loginCheck);
        if (cus == true) {
            console.log("判斷為訪客");
        } else {
            console.log("判斷是會員或其他");
        }

        // })

        // ~~~開啟即起用-文章用~~~
        let timeDirect;
        let enteraddress;
        let subject;

        //...創建物件...

        let article = {};
        //-----------------------------------------------
        // if登入或未登入
        console.log("這是" + loginCheck);
        if (cus == true) {
            console.log("判斷為訪客");
            //~~~未登入~~~
            unlogin();
        } else {
            console.log("判斷是會員或其他");
            //~~~登入~~~
            login();
        }
        //-----------------------------------------------

        // 未登入
        function unlogin() {
            // 開啟頁面即生成
            timeDirect = 000;
            enteraddress = "";
            subject = "";
            article["enterAddressName"] = enteraddress;
            article["subject"] = subject;
            article["timeDirect"] = timeDirect;
            console.log("typeof_enteraddress-->" + typeof enteraddress);
            unloginFirstPage();
            newButton();
            unloginClickPage();
            //按下搜尋吧按鈕
            $("#btsearch").click(function (e) {
                e.preventDefault();
                enteraddress = $("#searchAddress").val();
                subject = $("#subject option:selected").val();
                timeDirect = $("#timeDirect option:selected").val();
                article["enterAddressName"] = enteraddress;
                article["subject"] = subject;
                article["timeDirect"] = timeDirect;

                unloginFirstPage();
                newButton();
                unloginClickPage();
            });
            $("#travelArticleBox").on('click', 'input', function (event) {
                window.location.href = '/user/loginPage';
            })
        }

        //-----------------------------------------------
        // 登入
        function login() {
            console.log("gggggggggg");

            alreadyButtoned().then(recommend => {
                console.log("login的recommend" + recommend)
                timeDirect = 000;
                enteraddress = "";
                subject = "";
                article["enterAddressName"] = enteraddress;
                article["subject"] = subject;
                article["timeDirect"] = timeDirect;
                loginFirstPage(recommend);
                newButton();
                loginClickPage(recommend);
                $("#btsearch").click(function (e) {
                    e.preventDefault();
                    enteraddress = $("#searchAddress").val();
                    subject = $("#subject option:selected").val();
                    timeDirect = $("#timeDirect option:selected").val();
                    article["enterAddressName"] = enteraddress;
                    article["subject"] = subject;
                    article["timeDirect"] = timeDirect;
                    loginFirstPage(recommend);
                    newButton();
                    loginClickPage(recommend);
                });
            })
        }

        //--------------------------------------------

        //-----------------------------------------------
        // 原-機動生成文章

        //~~~未登入-html自動生成文章~~~
        function getHtmlArticleUnlog(articleAll) {
            let articleTitle = articleAll.articleTitle;
            let textEditor = articleAll.textEditor;
            let createDate = articleAll.createDate;
            let createTime = articleAll.createTime;
            let saveImgPath = articleAll.saveImgPath;
            let user = articleAll.userId.nickname;


            return `

<!-- 文章圖片  -->
     <div class="single-blog-area bg-gr0200 blog-style-2 mb-5 wow fadeInUp " data-wow-delay="0.2s"
    data-wow-duration="1000ms">
    <div class="row align-items-center">
        <div class="col-12 col-md-6">
            <div class="single-blog-thumbnail">
                <img src="${saveImgPath}">
            </div>
        </div>
        <div class="col-12 col-md-6 text-bl04">
            <!-- 文章內容 -->
            <div class="single-blog-content">
                <h4><a href="https://localhost:8080/article/${articleTitle}" class="post-headline  btn-outline-bl01 text-bl04 fw-bold">
                    ${articleTitle}   
                    </a></h4>

                <p class="text-bl04">${textEditor}</p>
                <div class="post-meta">
                    <p class="text-bl04">By <a href="#" class="text-bl04">${user}</a></p>
                   <p class="text-bl04">發表於:&nbsp${createDate}&nbsp${createTime}</p>
                    <input
                    name="${articleTitle}"
                        class="btn-recommend btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                        type="submit" value="推薦">
                    <input
                    name="${articleTitle}"
                        class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                        type="submit" value="收藏">
                    <input
                    name="${articleTitle}"
                        class="btn btn-sm btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                        type="submit" value="檢舉">
                </div>
            </div>
        </div>
    </div>
</div>
`;
        }


        // ~~~第一頁~~~
        function unloginFirstPage() {

            $.ajax({
                url: "/firstSearchOfPageEatTravel",
                type: "GET",
                data: article,
                success: function (response) {
                    console.log("第一頁文章response" + response);
                    console.log("建立空的html");
                    let html = "";
                    console.log("文章-for迴圈開始");
                    // (開始)文章換頁生成
                    for (let articleAll of response) {

                        // 從資料庫取出文章資訊
                        html += getHtmlArticleUnlog(articleAll);

                        console.log("文章-for迴圈結束");
                        $("#travelArticleBox").html(html);
                        console.log("跑完--輸入搜尋吧查詢並送出第一頁");

                        // (結束)文章換頁生成
                    }
                },
            });
        }

        //~~~點擊換頁按鈕~~~
        function unloginClickPage() {
            $("#changePageAll").on("click", "#pageSearch", function (event) {
                // let a = $(this).attr("name",true)
                // console.log(a);

                let pageValue = $("#changePageBox option:selected").val();

                console.log("pageValue=" + pageValue);

                let page = pageValue - 1;
                let article = {};
                article["page"] = page;
                article["enterAddressName"] = enteraddress;
                article["subject"] = subject;
                article["timeDirect"] = timeDirect;

                $.ajax({
                    url: "/changeSearchOfPageEatTravel",
                    type: "GET",
                    data: article,
                    success: function (response) {
                        let html = "";
                        console.log("文章-for迴圈開始");
                        // (開始)文章換頁生成
                        for (let articleAll of response) {

                            // 從資料庫取出文章資訊

                            html += getHtmlArticleUnlog(articleAll);

                            console.log("文章-for迴圈結束");
                            $("#travelArticleBox").html(html);
                            console.log("跑完--輸入搜尋吧查詢並送出第一頁");
                        }
                    },
                });
            });
        }


        //-----------------------------------------------
        // 判斷會員-機動生成文章(已推薦收藏檢舉)
        // ~~~get已經收藏&推薦&檢舉並存入function外的全域變數~~~



        function alreadyButtoned() {
            return fetch('/alreadyTravelEatButtoned')
                .then(res => res.json())
                .then(function (data) {
                    let recommendresult = [];
                    for (let recommend of data) {
                        recommendresult.push(recommend.articlesRecommendId.articleId);
                    }
                    return recommendresult;
                });

            // let recommendresult = [];
            // $.ajax({
            //     url: "/alreadyTravelEatButtoned",
            //     type: "GET",
            //     success: function (response) {
            //         for (let recommend of response) {
            //             recommendresult.push(recommend.articlesRecommendId.articleId);
            //         }
            //         console.log("所有的recommend" + recommendresult);
            //     },
            //     error:function (response){
            //         console.log(response);
            //     }
            // });

            // return recommendresult;
        }

        //~~~登入版-html自動生成文章~~~
        function getHtmlArticle(articleAll, recommend) {
            let articleTitle = articleAll.articleTitle;
            let textEditor = articleAll.textEditor;
            let createDate = articleAll.createDate;
            let createTime = articleAll.createTime;
            let saveImgPath = articleAll.saveImgPath;
            let user = articleAll.userId.nickname;
            let articleId = articleAll.articleId;
            console.log("articleId=  " + articleId);
            console.log("recommend   " + recommend)

            return `

<!-- 文章圖片  -->
     <div class="single-blog-area bg-gr0200 blog-style-2 mb-5 wow fadeInUp " data-wow-delay="0.2s"
    data-wow-duration="1000ms">
    <div class="row align-items-center">
        <div class="col-12 col-md-6">
            <div class="single-blog-thumbnail">
                <img src="${saveImgPath}">
            </div>
        </div>
        <div class="col-12 col-md-6 text-bl04">
            <!-- 文章內容 -->
            <div class="single-blog-content">
                <h4><a href="https://localhost:8080/article/${articleTitle}" class="post-headline  btn-outline-bl01 text-bl04 fw-bold">
                    ${articleTitle}   
                    </a></h4>

                <p class="text-bl04">${textEditor}</p>
                <div class="post-meta">
                    <p class="text-bl04">By <a href="#" class="text-bl04">${user}</a></p>
                   <p class="text-bl04">發表於:&nbsp${createDate}&nbsp${createTime}</p>
                    <input
                    name="${articleTitle}"
                        class="btn-recommend btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                        type="submit" value="${getRecommendStatus(
                articleId,
                recommend
            )}">
                    <input
                    name="${articleTitle}"
                        class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                        type="submit" value="收藏">
                    <input
                    name="${articleTitle}"
                        class="btn btn-sm btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                        type="submit" value="檢舉">
                </div>
            </div>
        </div>
    </div>
</div>
`;
        }

        //~~~[已推薦]放進html方法~~~
        function getRecommendStatus(articleId, recommend) {
            if (recommend.includes(articleId)) {
                return "已推薦";
            }
            return "推薦";
        }

        // ~~~第一頁~~~
        function loginFirstPage(recommend) {
            //for迴圈機動生成文章&判斷已收藏
            console.log("loginFirstPage裡面的recommend" + recommend)
            alreadyButtoned().then(recommend => {
                $.ajax({
                    url: "/firstSearchOfPageEatTravel",
                    type: "GET",
                    data: article,
                    success: function (response) {
                        console.log("第一頁文章responselogin" + response);
                        console.log("建立空的html");
                        let html = "";
                        console.log("文章-for迴圈開始");
                        // (開始)文章換頁生成
                        for (let articleAll of response) {
                            // 從資料庫取出文章資訊
                            console.log("---------------------------------------");
                            console.log(articleAll, recommend);
                            html += getHtmlArticle(articleAll, recommend);

                            console.log("文章-for迴圈結束");
                            $("#travelArticleBox").html(html);
                            console.log("跑完--輸入搜尋吧查詢並送出第一頁");

                            // (結束)文章換頁生成
                        }
                    },
                });
            })
        }

        //~~~點擊換頁按鈕~~~
        function loginClickPage(recommend) {
            console.log("典籍換頁按鈕外" + recommend);

                $("#changePageAll").on("click", "#pageSearch", function (event) {
                    // let a = $(this).attr("name",true)
                    // console.log(a);
                    alreadyButtoned().then(recommend => {
                        console.log("典籍換頁按鈕" + recommend);
                        let pageValue = $("#changePageBox option:selected").val();

                        console.log("pageValue=" + pageValue);

                        let page = pageValue - 1;
                        let article = {};
                        article["page"] = page;
                        article["enterAddressName"] = enteraddress;
                        article["subject"] = subject;
                        article["timeDirect"] = timeDirect;

                        $.ajax({
                            url: "/changeSearchOfPageEatTravel",
                            type: "GET",
                            data: article,
                            success: function (response) {
                                let html = "";
                                console.log("文章-for迴圈開始");
                                // (開始)文章換頁生成
                                for (let articleAll of response) {
                                    // 從資料庫取出文章資訊
                                    html += getHtmlArticle(articleAll, recommend);
                                    console.log("文章-for迴圈結束");
                                    $("#travelArticleBox").html(html);
                                    console.log("跑完--輸入搜尋吧查詢並送出第一頁");
                                }
                            },
                        });
                    });
                })
        }

        //-----------------------------------------------
        // 自動生成按鈕
        function newButton() {
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
  
                  <option value="${p}">${p}</option>
  
        `;
                        // 裝入[自動生成]的位置
                        $("#changePageBox").html(html);
                        console.log("跑完--裝入[自動生成]的位置");
                    }
                }
            })
        }
        //-------------------------------------------------------
    }
)

