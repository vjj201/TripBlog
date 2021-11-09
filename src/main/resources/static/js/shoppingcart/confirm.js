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
    newTbody = document.createElement('tbody');
    document.getElementById('cartList').appendChild(newTbody);
    
    for(let i = 0; i < items.length; i++){
        let itemInfo = storage.getItem(items[i]);
        createCartList(items[i],itemInfo);
    }

    // 讀入運費與總金額
    document.getElementById('freight').innerText = storage.getItem('totalPrice').split(', ')[1];
    document.getElementById('totalAmount').innerText = storage.getItem('totalPrice').split(', ')[2];
}

//動態新增購物車商品方法
function createCartList(itemId, itemValue){
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

    //確認按鈕
    document.getElementById('confirmPayment').addEventListener('click', function (e) {
        storage.clear();
    });
}
window.addEventListener('load', doFirst);