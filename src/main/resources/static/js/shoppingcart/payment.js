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


    //提交
    $('#submit').click(function (e) {
       e.preventDefault();

        let payment = $("input[name='payment']:checked").val();
        let cardOwner = $('#cardOwner').val();
        let cardNumber = $('#cardNumber').val();
        let expiration = $('#expiration').val();
        let cvv = $('#cvv').val();

        let error = {};
        if(undefined === payment) {
            error['payment'] = '請選擇付款方式';
        }

        if('' === cardOwner) {
            error['cardOwner'] = '請填寫持卡人名';
        }

        if('' === cardNumber) {
            error['cardNumber'] = '請填寫卡片號碼';
        }

        let cardNumber_is_numeric = /^(?=.*\d).{12,19}$/.test(cardNumber);
        if(! cardNumber_is_numeric) {
            error['cardNumber'] = '請填寫有效的卡片號碼';
        }

        if('' === expiration) {
            error['expiration'] = '請選擇卡片到期日';
        }

        if('' === cvv) {
            error['cvv'] = '請填寫CVV碼';
        }

        let cvv_is_numeric = /^(?=.*\d).{3}$/.test(cvv);
        if(! cvv_is_numeric) {
            error['cvv'] = '請填寫有效的CVV碼';
        }

        if(!$.isEmptyObject(error)) {
            $('#message').html(objToString(error));
            return;
        }

        $('#message').empty();


        let data = {};
        data['payment'] = payment;
        data['cardOwner'] = cardOwner;
        data['cardNumber'] = cardNumber;


        $.ajax({
            url: '/shop/payment/done',
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            success : function () {
                document.location.href="/shop/confirm";
            }
        });

    });


});