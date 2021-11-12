$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });


    $("#btsearch").click(function (e) {
        e.preventDefault();

        let enteraddress = $("#searchAddress").val();
        let subject = $('#subject option:selected').val();
        let timeDirect = $('#timeDirect option:selected').val();
        console.log("時間排序" + timeDirect)

//創建物件
        let article = {};
        article["enterAddressName"] = enteraddress;
        article["subject"] = subject;
        article["timeDirect"] = timeDirect;
        console.log("article時間排序" + article.timeDirect)
//輸入搜尋吧查詢並送出第一頁
        $.ajax({
            url: "/firstSearchOfPage",
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
                    let enterAddress = articleAll.enterAddressName;
                    let subjectCategory = articleAll.subjectCategory;
                    let createDate = articleAll.createDate;
                    let createTime = articleAll.createTime;
                    let saveImgPath =articleAll.saveImgPath;
                    // 從資料庫取出文章資訊
                    html += `
           <div class="card mx-2 my-2 rounded-3" style="max-width: 540px;">
              <div class="row g-0">
                <div class="col-md-3 py-3 px-2 overflow-hidden rounded-3">
                  <img class="w-100 h-100" src="${saveImgPath}" alt="一張圖">
                </div>
                <div class="col-md-9">
                  <div class="card-body">
                    <h5><a href="https://localhost:8080/article/${articleTitle}" class="post-headline text-bl04 mapArticleTitle">${articleTitle}</a></h5>
                    <p class="card-text">
                        <small class="text-bl04 mapArticleAdress">地址:&nbsp${enterAddress}</small>
                    </p>
                    <p class="card-text">
                    <small class="text-bl04">分類:&nbsp${subjectCategory}</small></p>
                    <p class="card-text">
                    <small class="text-bl04">發表於:&nbsp${createDate}&nbsp${createTime}</small></p>
                    <button name = "${articleTitle}" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">推薦</button>
                    <button name = "${articleTitle}" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">收藏</button>
                    <button name = "${articleTitle}" class="btn btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">檢舉</button>
                  </div>
                </div>
              </div>
              </div>
              `;

                    console.log("文章-for迴圈結束")
                    $("#articleBox").html(html);
                    console.log("跑完--輸入搜尋吧查詢並送出第一頁");

// (結束)文章換頁生成
                }
            }
        });


// 自動生成換頁按鈕
        $.ajax({
            url: "/newPageButton",
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

            let page = pageValue - 1;
            let article = {}
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
                    console.log("文章-for迴圈開始")
                    // (開始)文章換頁生成
                    for (let articleAll of response) {
                        let articleTitle = articleAll.articleTitle;
                        let enterAddress = articleAll.enterAddressName;
                        let subjectCategory = articleAll.subjectCategory;
                        let createDate = articleAll.createDate;
                        let saveImgPath =articleAll.saveImgPath;
                        // 從資料庫取出文章資訊
                        html += `
                  <div class="card mx-2 my-2 rounded-3" style="max-width: 540px;">
              <div class="row g-0">
                <div class="col-md-3 py-3 px-2 overflow-hidden rounded-3">
                  <img class="w-100 h-100" src="${saveImgPath}" alt="一張圖">
                </div>
                <div class="col-md-9">
                  <div class="card-body">
                    <h5><a href="https://localhost:8080/article/${articleTitle}" class="post-headline text-bl04 mapArticleTitle">${articleTitle}</a></h5>
                    <p class="card-text">
                        <small class="text-bl04 mapArticleAdress">地址:&nbsp${enterAddress}</small>
                    </p>
                    <p class="card-text">
                    <small class="text-bl04">分類:&nbsp${subjectCategory}</small></p>
                    <p class="card-text">
                    <small class="text-bl04">發表於:&nbsp${createDate}</small></p>
                      <button name = "${articleTitle}" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">推薦</button>
                      <button name = "${articleTitle}" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">收藏</a>
                      <button name = "${articleTitle}" class="btn btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">檢舉</button>
                  </div>
                </div>
              </div>
              </div>
                    `;

                        console.log("文章-for迴圈結束")
                        $("#articleBox").html(html);
                        console.log("跑完--輸入搜尋吧查詢並送出第一頁");

                    }
                }
            });


        });


    });
//-----------------------------------------------------------------------------
    $("#articleBox").on('click', 'a', function (event) {
        //    alert("a標籤被點了");
        let articleTitle = $(this).text();
        //    alert(articleTitle);
        let article = {};
        article["articleTitle"] = articleTitle;

        // $.ajax({
        //     url: "articleForone",
        //     type:"GET",
        //     success: function (){
        //         alert("跳轉搂")
        //     }
        // })


        $.ajax({
            url: "/findByArticleTitle",
            type: "GET",
            data: article,
            success: function (response) {
                //      alert("林北成功用標題找到文章了")

                // $(location).attr("href","https://localhost:63342/TripBlog/templates/article.html");
//---------------------------------------------------------------------------------------------------
                //for秉豐
//---------------------------------------------------------------------------------------------------

            }


        });

    });

    $("#articleBox").on('click', 'button', function (event) {
        let choose = $(this).text();
        let articleTitle = $(this).attr('name');

        let article = {};
        article["articleTitle"] = articleTitle;
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
                alert("林北按鈕回來瞜")

            }


        });


    })


});
