let storage = localStorage;
function doFirst(){
	let itemString = storage.getItem('addItemList');
    items = itemString.substr(0, itemString.length - 2).split(', ');
    console.log(items);
    itemsCount = items.length;
    total = 0;

    //動態新增開始購物車
    newUl = document.createElement('ul');
    document.getElementById('cartList').appendChild(newUl);
    newUl.className = "list-group mb-3"
    
    for(let i = 0; i < items.length; i++){
        let itemInfo = storage.getItem(items[i]);
        createCartList(items[i],itemInfo);

        let itemPrice = parseInt(itemInfo.split('|')[2]);
        let itemNum = parseInt(itemInfo.split('|')[3]);
        total += itemPrice * itemNum;
    }

    //建立運費li
    let liDeliverFee = document.createElement('li');
    liDeliverFee.className = 'list-group-item d-flex justify-content-between';
 
    newUl.appendChild(liDeliverFee);

    //建立運費span
    let spanDeliver = document.createElement('span');
    let spanDeliverFee = document.createElement('span');

    spanDeliver.innerText = "運費";
    spanDeliverFee.className = "text-muted";
    

    liDeliverFee.appendChild(spanDeliver);
    liDeliverFee.appendChild(spanDeliverFee);
    
    //運費內層span
    let spanFee = document.createElement('span');
    spanFee.id = "freight";

    if (storage['totalPrice'] != null) {
        deliverFee = storage.getItem('totalPrice').split(', ')[1];
        spanFee.innerText = deliverFee;
    } else {spanFee.innerText = 0;}
    
    spanDeliverFee.appendChild(spanFee);
    let spanCurrency = document.createElement('span');
    spanCurrency.innerText = "TWD"
    spanDeliverFee.appendChild(spanCurrency);

    // 建立總金額li
    let liITotal = document.createElement('li');
    liITotal.className = 'list-group-item d-flex justify-content-between';
    
    newUl.appendChild(liITotal);
    
    // 建立總金額span
    let spanTotal = document.createElement('span');
    let h6Total = document.createElement('h6');
    
    spanTotal.innerText = "總金額";
    h6Total.id = "totalPrice";

    if (storage['totalPrice'] != null) {
        sum = storage.getItem('totalPrice').split(', ')[2];
        h6Total.innerText = sum + "TWD";
    } else {h6Total.innerText = total + "TWD";}
    
    liITotal.appendChild(spanTotal);
    liITotal.appendChild(h6Total);
    refreshTotal(total, parseInt(document.getElementById("freight").innerText));
    
    if (storage['totalPrice'] != null) {
        total = storage.getItem('totalPrice').split(', ')[0];
        deliverFee = storage.getItem('totalPrice').split(', ')[1];
        sum = storage.getItem('totalPrice').split(', ')[2];        
    }
}

//Reset總金額
function refreshTotal(total, deliverFee){
    sum = parseInt(total) + deliverFee;
    document.getElementById("totalPrice").innerText = sum + "TWD";
}
//動態新增購物車商品方法
function createCartList(itemId, itemValue){
    // alert(`${itemId} : ${itemValue}`)
    let itemTitle = itemValue.split('|')[0];
    let item = itemValue.split('|')[1];
    let itemPrice = parseInt(itemValue.split('|')[2]);
    let itemNum = parseInt(itemValue.split('|')[3]);

    // 建立每個品項的清單區域 -- li
    let liItemList = document.createElement('li');
    liItemList.className = 'list-group-item d-flex justify-content-between';

    newUl.appendChild(liItemList);

    // 建立div
    let div = document.createElement('div');
    liItemList.appendChild(div);

    // 建立h6 small
    let h6Title = document.createElement('h6');
    let smallProductNum = document.createElement('small');
    
    h6Title.className = "my-0";
    h6Title.innerText = itemTitle;

    smallProductNum.className = "text-muted";
    smallProductNum.innerText = "數量：" +  itemNum;

    div.appendChild(h6Title);
    div.appendChild(smallProductNum);

    // 建立span價錢
    let spanPrice = document.createElement('span');

    spanPrice.className = "text-muted";
    spanPrice.innerText = parseInt(itemPrice * itemNum) + "TWD";

    liItemList.appendChild(spanPrice);

}
window.addEventListener('load', doFirst);

// ---------------------------------------------------------------

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

        if(location === '外島') {
            $('#freight').text(180);
        }

        if(location === '南部') {
            $('#freight').text(100);
        }

        if(location === '中部') {
            $('#freight').text(80);
        }

        if(location === '北部') {
            $('#freight').text(60);
        }

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
        refreshTotal(total, parseInt(document.getElementById("freight").innerText));
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

        // 將運費和總金額存入localStorage
        let totalPriceStr = total + ", " + parseInt(document.getElementById("freight").innerText) + ", " + sum;
        storage['totalPrice'] = totalPriceStr; //storage.setItem('totalPrice','');

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