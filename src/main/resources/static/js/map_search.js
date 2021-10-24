
$(function() {


    $('#btsearch').click(function (e) {

        e.preventDefault();


        let enteraddress = $('#searchAddress').val();

        //創建物件
        let article = {};

        article['enterAddress'] = enteraddress;

        $.ajax({
            url: '/findByAddress',
            type: 'GET',
            data: article,
            success: function (response) {
                alert(JSON.parse(response));
                }

        });

    });
});


