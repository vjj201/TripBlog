let storage = localStorage;

function doFirst() {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });



    let itemString = storage.getItem('addItemList');
    items = itemString.substr(0, itemString.length - 2).split(', ');
    console.log(items);
    itemsCount = items.length;

    // 讀取storage內totalPrice的值 (商品總金額, 運費, 加上運費總金額)
    total = storage.getItem('totalPrice').split(', ')[0];
    deliverFee = storage.getItem('totalPrice').split(', ')[1];
    sum = storage.getItem('totalPrice').split(', ')[2];

    //動態新增開始購物車
    newTbody = document.createElement('tbody');
    document.getElementById('cartList').appendChild(newTbody);

    for (let i = 0; i < items.length; i++) {
        let itemInfo = storage.getItem(items[i]);
        createCartList(items[i], itemInfo);
    }

    //確認按鈕
    $('#confirmPayment').one('click', function (e) {

        e.preventDefault();
        e.stopPropagation();

        let itemString = storage.getItem('addItemList');
        let items = itemString.substr(0, itemString.length - 2).split(', ');
        console.log(items);

        let JData = [];

        for (let i = 0; i < items.length; i++) {
            let itemInfo = storage.getItem(items[i]);

            let productId = itemInfo.split('|')[1];
            let title = itemInfo.split('|')[0];
            let quantity = itemInfo.split('|')[3];
            let price = itemInfo.split('|')[2];

            JData[i] = {productId, title, quantity, price};
        }

        if (storage['totalPrice'] != null) {
            storage.removeItem('totalPrice'); //storage.setItem('addItemList','');
        }

        $.ajax({
            url: '/shop/done',
            type: 'POST',
            async: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(JData),
            statusCode: {
                200: function (response) {
                    storage.clear();
                    alert(response);
                    document.location.href = "/shop/orderList";
                },
                202: function (response) {
                    storage.clear();
                    alert(response);
                    document.location.href = "/shop";
                },
                503: function (response) {
                    alert(JSON.stringify(response.responseText));
                    document.location.href = "/shop";
                }
            }
        });

    });

    // 讀入運費與總金額
    document.getElementById('freight').innerText = storage.getItem('totalPrice').split(', ')[1];
    document.getElementById('totalAmount').innerText = storage.getItem('totalPrice').split(', ')[2];
}

//動態新增購物車商品方法
function createCartList(itemId, itemValue) {
    // alert(`${itemId} : ${itemValue}`)
    let itemTitle = itemValue.split('|')[0];
    let item = itemValue.split('|')[1];
    let itemPrice = parseInt(itemValue.split('|')[2]);
    let itemNum = parseInt(itemValue.split('|')[3]);

    // 建立每個品項的清單區域 -- tr
    let trItemList = document.createElement('tr');
    trItemList.className = "border-bottom my-2";
    newTbody.appendChild(trItemList);

    // 建立商品名稱td
    let tdItemName = document.createElement('td');
    let h6ItemName = document.createElement('h6');
    // thItemName.style.width = "50%";
    h6ItemName.innerText = itemTitle;
    trItemList.appendChild(tdItemName);
    tdItemName.appendChild(h6ItemName);

    // 建立數量th
    let tdItemNum = document.createElement('td');
    // thItemNum.style.width = "25%";
    tdItemNum.innerText = itemNum;
    trItemList.appendChild(tdItemNum);

    // 建立價格th
    let tdItemPrice = document.createElement('td');
    tdItemPrice.innerText = parseInt(itemNum) * itemPrice;
    trItemList.appendChild(tdItemPrice);

}



window.addEventListener('load', doFirst);