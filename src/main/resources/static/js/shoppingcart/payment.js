let storage = localStorage;
function doFirst(){
	let itemString = storage.getItem('addItemList');
    items = itemString.substr(0, itemString.length - 2).split(', ');
    console.log(items);
    itemsCount = items.length;

    // 讀取storage內totalPrice的值 (商品總金額, 運費, 加上運費總金額)
    total = storage.getItem('totalPrice').split(', ')[0];
    deliverFee = storage.getItem('totalPrice').split(', ')[1];
    sum = storage.getItem('totalPrice').split(', ')[2];

    //動態新增開始購物車
    newUl = document.createElement('ul');
    document.getElementById('cartList').appendChild(newUl);
    newUl.className = "list-group mb-3"
    
    for(let i = 0; i < items.length; i++){
        let itemInfo = storage.getItem(items[i]);
        createCartList(items[i],itemInfo);
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
    spanFee.innerText = deliverFee;
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
    h6Total.innerText = sum + "TWD";
    
    liITotal.appendChild(spanTotal);
    liITotal.appendChild(h6Total);
    // refreshTotal(total, parseInt(document.getElementById("freight").innerText));
    
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

// ------------------------------------------------------------------

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