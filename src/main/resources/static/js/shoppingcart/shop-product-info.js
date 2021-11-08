let storage = localStorage;
function doFirst(){
    if(storage['addItemList'] == null){
        storage['addItemList'] = ''; //storage.setItem('addItemList','');
    }
    //幫每個Add cart建立事件聆聽功能
    let list = document.querySelectorAll('.addButton'); //list是陣列
    for(let i = 0; i < list.length; i++){
        list[i].addEventListener('click',function(e){
            console.log("點擊加入購物車");
            let productId = document.getElementById(this.id);
            let productInfo = productId.querySelector(`.info`).value;
            addItem(this.id, productInfo);
        });
    }
    let itemString = storage.getItem('addItemList');

    let items = itemString.substr(0, itemString.length - 2).split(', ')
    if(itemString.length == 0) {
        document.getElementById('itemCount').innerText = 0;
    } else {
        document.getElementById('itemCount').innerText = items.length;
    }
}
function addItem(itemId,itemValue){
    
    // 存入 storage
    if(storage[itemId]){
        alert('已加入購物車')
    }else{
        storage['addItemList'] += `${itemId}, `;
        storage.setItem(itemId, itemValue);
        alert('成功加入購物車')
    }

    // 計算購買數量和小計
    let itemString = storage.getItem('addItemList');
    let items = itemString.substr(0, itemString.length - 2).split(', ')
    console.log(items);

    subtotal = 0;
    for(let i = 0; i < items.length; i++){
        let itemInfo = storage.getItem(items[i]);
        let itemPrice = parseInt(itemInfo.split('|')[2]);
        subtotal += itemPrice;
    }
    document.getElementById('itemCount').innerText = items.length;
    // document.getElementById('subtotal').innerText = subtotal;
}
window.addEventListener('load', doFirst);