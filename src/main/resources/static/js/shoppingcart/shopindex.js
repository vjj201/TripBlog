let storage = localStorage;

function doFirst() {
    if (storage['addItemList'] == null) {
        storage['addItemList'] = ''; //storage.setItem('addItemList','');
    }
    //幫每個Add cart建立事件聆聽功能
    let list = document.querySelectorAll('.addButton'); //list是陣列
    for (let i = 0; i < list.length; i++) {
        list[i].addEventListener('click', function (e) {
            console.log("點擊加入購物車");
            let productName = document.getElementById(this.id);
            let productInfo = productName.querySelector(`.info`).value;
            console.log(productInfo);
            let productId = productInfo.split('|')[1];
            addItem(productId, productInfo);
        });
    }
    let itemString = storage.getItem('addItemList');

    let items = itemString.substr(0, itemString.length - 2).split(', ')
    if (itemString.length == 0) {
        document.getElementById('itemCount').innerText = 0;
    } else {
        document.getElementById('itemCount').innerText = items.length;
    }
}

function addItem(itemId, itemValue) {
    // 存入 storage
    if (storage[itemId]) {
        alert('已加入購物車')
    } else {
        storage['addItemList'] += `${itemId}, `;
        storage.setItem(itemId, itemValue);
        alert('成功加入購物車')
    }

    // 計算購買數量和小計
    let itemString = storage.getItem('addItemList');
    let items = itemString.substr(0, itemString.length - 2).split(', ')
    console.log(items);

    subtotal = 0;
    for (let i = 0; i < items.length; i++) {
        let itemInfo = storage.getItem(items[i]);
        let itemPrice = parseInt(itemInfo.split('|')[2]);
        subtotal += itemPrice;
    }
    document.getElementById('itemCount').innerText = items.length;
    // document.getElementById('subtotal').innerText = subtotal;
}

window.addEventListener('load', doFirst);

// ------------------------------------------------------

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('.sidebar .nav-link').forEach(function (element) {

        element.addEventListener('click', function (e) {

            let nextEl = element.nextElementSibling;
            let parentEl = element.parentElement;

            if (nextEl) {
                e.preventDefault();
                let mycollapse = new bootstrap.Collapse(nextEl);

                if (nextEl.classList.contains('show')) {
                    mycollapse.hide();
                } else {
                    mycollapse.show();
                    var opened_submenu = parentEl.parentElement.querySelector('.submenu.show');
                    if (opened_submenu) {
                        new bootstrap.Collapse(opened_submenu);
                    }
                }
            }
        });
    })
});

$(function () {

    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });


    //上一頁
    $(document).on('click', '#pre', function (e) {
        e.preventDefault();
        let uri = $('#pre').attr('href');
        changePage(uri);
    });
    //下一頁
    $(document).on('click', '#next', function (e) {
        e.preventDefault();
        let uri =  $('#next').attr('href');
        changePage(uri);
    });
    //搜尋
    $(document).on('click', '#button-addon5', function (e) {
        e.preventDefault();
        let uri = '/shop/' + $('#button-addon5').val();
        changePage(uri);
    });

    function changePage (uri) {

        let tag = $("[name='options-outlined']:checked").val();
        let brand = $("[name='brand']").val();
        let sort = $("[name='sort']").val();
        let productName = $("[name='productName']").val();

        let productQuery = {};
        productQuery['tagId'] = tag;
        productQuery['brandId'] = brand;
        productQuery['sort'] = sort;
        productQuery['productName'] = productName;

        $.ajax({
            url: uri,
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(productQuery),
            success: function (response) {
                $("#main").replaceWith(response);
                doFirst();
            }
        });
    }
});