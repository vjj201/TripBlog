let storage = localStorage;
function doFirst(){
	let itemString = storage.getItem('addItemList');
    items = itemString.substr(0, itemString.length - 2).split(', ');
    console.log(items);
    
    itemsCount = items.length;
    total = 0;
    deliverFee = 0;
    sum = 0;

    //動態新增開始
    newDiv = document.createElement('div');
    newTable = document.createElement('table');
    // 將 table 放進 div, 再將 div 放進 carList
    newDiv.appendChild(newTable);
    document.getElementById('cartList').appendChild(newDiv);
    
    for(let i = 0; i < items.length; i++){
        let itemInfo = storage.getItem(items[i]);
        createCartList(items[i],itemInfo);

        let itemPrice = parseInt(itemInfo.split('|')[2]);
        total += itemPrice;
    }
    
    //總金額計算
    if(itemString.length == 0) {
        document.getElementById('items').innerText = 0;
    } else {
        document.getElementById('items').innerText = itemsCount;
    }
    document.getElementById('total').innerText = total;
}
function createCartList(itemId, itemValue){
    // alert(`${itemId} : ${itemValue}`)
    let itemTitle = itemValue.split('|')[0];
    let itemImage = '../../images/shop/product/' + itemValue.split('|')[1] + '.jpg';
    let itemPrice = parseInt(itemValue.split('|')[2]);

    // 建立每個品項的清單區域 -- tr
    let trItemList = document.createElement('tr');
    trItemList.className = 'item';

    let nt = newTable.appendChild(trItemList);
    nt.className = 'table m-3 align-middle';

    // 建立商品圖片-- 第一個 td
    let tdImage = document.createElement('td');
    tdImage.style.width = '200px';
    tdImage.style.height = '150px';

    let image = document.createElement('img');
    image.src = itemImage;
    image.width = 150;

    tdImage.appendChild(image);
    trItemList.appendChild(tdImage);

    // 建立商品商品名稱-- 第二個 td
    let tdTitle = document.createElement('td');
    let aProductInfo = document.createElement('a');
    
    tdTitle.style.width = '180px';
    tdTitle.id = itemId;
    
    aProductInfo.href = '/shop/product/' + itemId;
    aProductInfo.style.textDecoration = 'none';
    aProductInfo.style.color = 'black';
    aProductInfo.style.fontWeight = 'bold';
    aProductInfo.innerText = itemTitle;

    tdTitle.appendChild(aProductInfo);
    trItemList.appendChild(tdTitle);

    // 建立商品價格-- 第三個 td
    let tdPrice = document.createElement('td');
    tdPrice.style.width = '100px';
    tdPrice.innerText = itemPrice;

    trItemList.appendChild(tdPrice);

    // 建立商品數量-- 第四個 td
    let tdItemCount = document.createElement('td');
    tdItemCount.style.width = '120px';

    inputItemCount = document.createElement('input');
    inputItemCount.type = 'number';
    inputItemCount.value = 1;
    inputItemCount.min = 1;
    inputItemCount.max = 50;
    inputItemCount.style.width = '50px'
    inputItemCount.id = itemId + 'num';
    inputItemCount.addEventListener('input', changeItemCount);

    tdItemCount.appendChild(inputItemCount);
    trItemList.appendChild(tdItemCount);

    // 建立移除按鈕-- 第五個 td
    let tdRemove = document.createElement('td');
    let delButton = document.createElement('button');
    delButton.innerText = '移除';
    delButton.className = 'btn btn-dark';
    delButton.addEventListener('click', deleteItem);

    tdRemove.appendChild(delButton);
    trItemList.appendChild(tdRemove);
}
function deleteItem(){
    let itemId = this.parentNode.previousSibling.previousSibling.previousSibling.id;
    let itemValue = storage.getItem(itemId);
    console.log(itemId);
    console.log(itemValue);

    // 1. 先扣除總金額、商品數量
    let itemPrice = parseInt(itemValue.split('|')[2]);
    let itemCount = parseInt(this.parentNode.previousSibling.previousSibling.innerText) / itemPrice;
    
    total -= itemPrice * itemCount;
    itemsCount = itemsCount - itemCount;

    document.getElementById('total').innerText = total;
    document.getElementById('items').innerText = itemsCount;

    // 2. 清除 storage
    storage.removeItem(itemId);

    // 字串.replace(子字串,欲取代的字串)
    storage['addItemList'] = storage['addItemList'].replace(`${itemId}, `,'');
    
    // 3. 刪除該筆 <tr>
    // this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode);
    newTable.removeChild(this.parentNode.parentNode);
}
function changeItemCount(){
    let itemOldPrice = this.parentNode.previousSibling.innerText;
    let itemId = this.parentNode.previousSibling.previousSibling.id;
    let itemValue = storage.getItem(itemId);
    let itemPrice = parseInt(itemValue.split('|')[2]);
    let subtotal = parseInt(this.value) * itemPrice;
    let itemOldCount = parseInt(itemOldPrice) / itemPrice;

    //計算商品總數
    itemNum = parseInt(this.value);
    itemsCount = parseInt(itemsCount) - itemOldCount + parseInt(this.value);
    document.getElementById('items').innerText = itemsCount;

    //計算商品金額、總金額
    this.parentNode.previousSibling.innerText = subtotal;
    total = total - itemOldPrice + subtotal;
    document.getElementById('total').innerText = total;
}
window.addEventListener('load', doFirst);

//-------------------------------------------------------------

$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

     //結帳按鈕
    $(document).on('click', '#checkPayment', function (e) {

        let itemString = storage.getItem('addItemList');
        items = itemString.substr(0, itemString.length - 2).split(', ');
        console.log(items);
        
        JData = [];

        for(let i = 0; i < items.length; i++){
            let itemInfo = storage.getItem(items[i]);

            let title = itemInfo.split('|')[0];
            let itemId = itemInfo.split('|')[1];
            let price = itemInfo.split('|')[2];
            let productNum = itemId + "num";
            let count = document.getElementById(productNum).value;
            let infoStr = title + "|" + itemId + "|" + price + "|" + count;
            console.log(infoStr);
            storage.setItem(itemId, infoStr);
            // JData[i] = {title, itemId, price, count}
        }

    //     console.log(JData);
    //     // storage.clear();
    //     $.ajax({
    //         url: '/shop/checkShopCart',
    //         type: 'POST',
    //         async: false,
    //         contentType: 'application/json;charset=utf-8',
    //         data: JSON.stringify(JData),
    //         success: function (response) {
    //             console.log("傳送成功");  
    //         }
    //     });
        
    });

});