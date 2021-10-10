$(function(){
    
    //自我介紹更新按鈕
    $('#updateIntro').click(function(e) {
    
        e.preventDefault();
        
        //抓取彈跳式表單中輸入的值
        let editIntroduceTitle = $('#editIntroduceTitle').val();
        let editIntroduceContent = $('#editIntroduceContent').val();
    
        //將值放入自我介紹頁面
        $('#introduceTitle').text(editIntroduceTitle);
        $('#introduceContent').text(editIntroduceContent)
        
        //創建物件
        let intro = {};
        intro['introTitle'] = editIntroduceTitle;
        intro['introContent'] = editIntroduceContent;
        
    });
});

