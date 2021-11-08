console.log("有抓到這個js");
$(function () {
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //開啟頁面即啟動-抓入所有文章
    let timeDirect = "000";

    console.log("ajax前-輸入搜尋吧查詢並送出第一頁");

    $.ajax({
        url: "/user/findByUserId",
        type: "GET",
        success: function (response) {
            console.log("第一頁文章response" + response);
            console.log("建立空的html");
            let html = "";
            console.log("文章-for迴圈開始");
            // (開始)文章換頁生成
            for (let articleAll of response) {
                let articleTitle = articleAll.articleTitle;
                let textEditor = articleAll.textEditor;
                let createDate = articleAll.createDate;

                // 從資料庫取出文章資訊
                html += `

                <!-- 文章圖片  -->
                     <div class="single-blog-area bg-gr0200 blog-style-2 mb-5 wow fadeInUp " data-wow-delay="0.2s"
                    data-wow-duration="1000ms">
                    <div class="row align-items-center">
                        <div class="col-12 col-md-6">
                            <div class="single-blog-thumbnail">
                                <img src="https://localhost:8080/user/articlePhoto">
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
                                    <p class="text-bl04">By <a href="#" class="text-bl04">作者</a></p>
                                   <p class="text-bl04">發表於:&nbsp${createDate}</p>
                                    <input
                                        class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                        type="submit" value="推薦">
                                    <input
                                        class="btn btn-sm btn-bl03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                        type="submit" value="收藏">
                                    <input
                                        class="btn btn-sm btn-pk03 border-2 border-gr0200 rounded-pill text-gr0200 fw-bold"
                                        type="submit" value="檢舉">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
          `;

                console.log("文章-for迴圈結束");
                $("#articleBox").html(html);
                console.log("跑完--輸入搜尋吧查詢並送出第一頁");

                // (結束)文章換頁生成
            }
        },
    });


});
