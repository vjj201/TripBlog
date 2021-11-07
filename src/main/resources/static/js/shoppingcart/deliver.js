$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    function objToString (obj) {
        let str = '';
        for (let p in obj) {
            if (Object.prototype.hasOwnProperty.call(obj, p)) {
                str += obj[p] + '<br>';
            }
        }
        return str;
    }

    //動態選項
    $(document).on('change', '#location', function (e) {
        e.preventDefault();

        let location =  $('#location').val();

        if (location != null) {

            let data = {};
            data['location'] = location;

            $.ajax({
                url: '/shop/deliver/city',
                type: 'GET',
                data: data,
                success: function (response) {
                    $("#city").replaceWith(response);
                }
            });
        }
    });

    $(document).on('change', '#city', function (e) {
        e.preventDefault();

        let city =  $('#city').val();

        if (city != null) {

            let data = {};
            data['cityName'] = city;

            $.ajax({
                url: '/shop/deliver/district',
                type: 'GET',
                data: data,
                success: function (response) {
                    $("#district").replaceWith(response);
                }
            });
        }
    });

    //提交
    $('#submit').click(function (e) {
       e.preventDefault();

        let receiver = $('#receiver').val();
        let location =  $('#location').val();
        let city =  $('#city').val();
        let district =  $('#district').val();
        let address =  $('#address').val();
        let deliver =  $("input[name='deliver']:checked").val();

        let error = {};
        if('' === receiver) {
            error['receiver'] = '請填寫收件人名';
        }

        if('鄉鎮市區' === district) {
            error['district'] = '請選擇收件地區';
        }

        if('' === address) {
            error['address'] = '請填寫收件地址';
        }

        if(deliver === undefined) {
            error['deliver'] = '請選擇貨運公司';
        }

        if(!$.isEmptyObject(error)) {
            $('#message').html(objToString(error));
            return;
        }

        $('#message').empty();

        let data = {};
        data['receiver'] = receiver;
        data['location'] = location;
        data['city'] = city;
        data['district'] = district;
        data['address'] = address;
        data['deliver'] = deliver;

        $.ajax({
            url: '/shop/deliver/done',
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            success : function () {
                document.location.href="/shop/payment";
            }
        });

    });


});