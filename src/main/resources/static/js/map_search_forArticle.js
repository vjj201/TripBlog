alert("有抓到這個js!")
$(function () {

    $("#btsearch").click(function (e) {
        e.preventDefault();

        let enteraddress = $("#searchAddress").val();
        let subject = $('#subject option:selected').val();
        let timeDirect = $('#timeDirect option:selected').val();

//創建物件
        let article = {};
        article["enterAddressName"] = enteraddress;
        article["subject"] = subject;
        article["timeDirect"]=timeDirect;
       console.log("timeDirect="+timeDirect);
       console.log(jQuery.type(timeDirect)); 

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

                    // 從資料庫取出文章資訊
                    html += `
            <div class="card mx-2 my-2 rounded-3" style="max-width: 540px;">
              <div class="row g-0">
                <div class="col-md-3 py-3 px-2 overflow-hidden rounded-3">
                  <img class="w-100 h-100" src="https://picsum.photos/300/300?random=1" alt="一張圖">
                </div>
                <div class="col-md-9">
                  <div class="card-body">
                    <h5><a href="#" class="post-headline text-bl04 mapArticleTitle">${articleTitle}</a></h5>
                    <p class="card-text m-0">
                        <small class="text-bl04 mapArticleAdress">${enterAddress}</small>
                    </p>
                    <p class="card-text"><small class="text-bl04">Last updated 3 mins ago</small></p>
                    <a href="#" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">推薦</a>
                    <a href="#" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">收藏</a>
                    <a href="#" class="btn btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">檢舉</a>
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

// ---------------------------------------------------
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

            let pagevalue = $(this).text()


            console.log("pagevalue=" + pagevalue);

            let page = pagevalue - 1;
            let article = {}
            article["page"] = page;
            article["enterAddressName"] = enteraddress;
            article["subject"] = subject;
            article["timeDirect"]=timeDirect;
    
            $.ajax({
                url: "/changeSearchOfPage",
                type: "GET",
                data: article,
                success: function (response) {
                    let html = "";
                    console.log("文章-for迴圈開始")
                    // (開始)文章換頁生成
                    for (let articleAll of response) {
                        let articleTitle = articleAll.articleTitle;
                        let enterAddress = articleAll.enterAddressName;
                        // 從資料庫取出文章資訊
                        html += `
                  <div class="card mx-2 my-2 rounded-3" style="max-width: 540px;">
                    <div class="row g-0">
                      <div class="col-md-3 py-3 px-2 overflow-hidden rounded-3">
                        <img class="w-100 h-100" src="https://picsum.photos/300/300?random=1" alt="一張圖">
                      </div>
                      <div class="col-md-9">
                        <div class="card-body">
                          <h5><a href="#" class="post-headline text-bl04 mapArticleTitle">${articleTitle}</a></h5>
                          <p class="card-text m-0">
                              <small class="text-bl04 mapArticleAdress">${enterAddress}</small>
                          </p>
                          <p class="card-text"><small class="text-bl04">Last updated 3 mins ago</small></p>
                          <a href="#" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">推薦</a>
                          <a href="#" class="btn btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">收藏</a>
                          <a href="#" class="btn btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold">檢舉</a>
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






});
