$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //提交
    $('#submit').click(function (e) {
       e.preventDefault();

        let data = {};
        data['payment'] = $("input[name='payment']:checked").val();
        data['cardOwner'] = $('#cardOwner').val();
        data['cardNumber'] = $('#cardNumber').val();

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