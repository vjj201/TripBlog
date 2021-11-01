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
            let productInfo = document.querySelector(`#${this.id} input`).value
            addItem(this.id, productInfo)
        });
    }
    let itemString = storage.getItem('addItemList');
    let items = itemString.substr(0, itemString.length - 2).split(', ')
    document.getElementById('itemCount').innerText = items.length;
}
function addItem(itemId,itemValue){
    let image = document.createElement('img');
    image.src = 'imgs/' + itemValue.split('|')[1];

    let title = document.createElement('span');
    title.innerText = itemValue.split('|')[0];

    let price = document.createElement('span');
    price.innerText = itemValue.split('|')[2];

    // 存入 storage
    if(storage[itemId]){
        alert('已加入購物車')
    }else{
        storage['addItemList'] += `${itemId}, `;
        storage.setItem(itemId, itemValue);
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