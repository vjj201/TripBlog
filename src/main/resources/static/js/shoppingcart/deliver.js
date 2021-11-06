$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    $(document).on('change', '#location', function (e) {
        e.preventDefault();

        let location =  $('#location').val();

        if (location != null) {

            let data = {};
            data['location'] = location;

            $.ajax({
                url: '/shop/deliver/city',
                type: 'Get',
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
            data['id'] = city;

            $.ajax({
                url: '/shop/deliver/district',
                type: 'Get',
                data: data,
                success: function (response) {
                    $("#district").replaceWith(response);
                }
            });
        }
    });




});