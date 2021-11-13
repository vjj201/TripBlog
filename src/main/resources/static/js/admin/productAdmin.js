'use strict'
$(function(){
    //csrf防護
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr) {
        xhr.setRequestHeader(header, token);
    });

    //Get後端資料庫資料
    $.ajax({
        url: 'product/manage',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);            
            createProductList(data);
        }
    });
    //將獲取的資料動態新增欄位
    function createProductList(data){
        //動態新增商品開始
        let newTbody = document.createElement('tbody');
        // 將 tbody 放進 productAdmin
        document.getElementById('productAdmin').appendChild(newTbody);
        
        for(let i = 0; i < data.length; i++){
        // 建立每個品項的清單區域 -- tr
            let trItemList = document.createElement('tr');
            newTbody.appendChild(trItemList);
        // 建立商品ID -- th
            let thItemId = document.createElement('th');
            thItemId.scope = "row";
            thItemId.innerText = data[i].productID;
            trItemList.appendChild(thItemId);

        // 建立商品圖片-- 第一個 td
            let tdImage = document.createElement('td');
            let image = document.createElement('img');
            image.src = "/shop/productPic/" + data[i].productID;
            image.style.width = '70px';
            tdImage.appendChild(image);
            trItemList.appendChild(tdImage);
        // 建立商品名稱-- 第二個 td
            let tdTitle = document.createElement('td');
            tdTitle.innerText = data[i].productName;
            trItemList.appendChild(tdTitle);
        // 建立商品簡介-- 第三個 td
            let tdAbout = document.createElement('td');
            tdAbout.innerText = data[i].aboutProduct;
            trItemList.appendChild(tdAbout);
        // 建立商品詳細-- 第四個 td
            let tdDetail = document.createElement('td');
            tdDetail.innerText = data[i].productDetail;
            trItemList.appendChild(tdDetail);
        // 建立商品價格-- 第五個 td
            let tdPrice = document.createElement('td');
            tdPrice.innerText = data[i].price + "TWD";
            trItemList.appendChild(tdPrice);
        // 建立商品庫存-- 第六個 td
            let tdStock = document.createElement('td');
            tdStock.innerText = data[i].inStock;
            trItemList.appendChild(tdStock);
        // 建立商品已售出-- 第七個 td
            let tdSold = document.createElement('td');
            tdSold.innerText = data[i].alreadySold;
            trItemList.appendChild(tdSold);
        // 建立商品店家-- 第八個 td
            let tdBrand = document.createElement('td');
            tdBrand.innerText = data[i].brand;
            trItemList.appendChild(tdBrand);
        // 建立商品標籤-- 第九個 td
            let tdTag = document.createElement('td');
            tdTag.innerText = data[i].productTag;
            trItemList.appendChild(tdTag);
        // 建立商品編輯、移除按鈕-- 第十個 td
            let tdButton = document.createElement('td');
            let btEdit = document.createElement('button');
            let btRemove = document.createElement('button');
            // btEdit.data-bs-toggle = "modal";
            // btEdit.data-bs-target = "#editModal"; 
            btEdit.innerHTML = `data-bs-toggle="modal" data-bs-target = "#editModal"`;
            btEdit.type = "button"; 
            btEdit.className = "btn btn-primary m-1";
            btEdit.innerText = "編輯";

            btRemove.id = data[i].productID;
            btRemove.type = "button";
            btRemove.className = "removeProduct btn btn-danger m-1";
            btRemove.innerText = "移除";
            btRemove.addEventListener('click', removeProduct);

            trItemList.appendChild(tdButton);
            tdButton.appendChild(btEdit);
            tdButton.appendChild(btRemove);
        }

    }

    //上架新商品按鈕
    $('#addNewProduct').click(function(e) {
        e.preventDefault();
        // 取得商家資訊
        $.ajax({
            url: 'product/showBrands',
            type: 'GET',
            dataType: 'json',
            success: function (brands) {
                console.log(brands);
                createBrandsList(brands);            
            }
        });
        // 取得標籤資訊
        $.ajax({
            url: 'product/showProductTags',
            type: 'GET',
            dataType: 'json',
            success: function (pTags) {
                console.log(pTags);  
                createTagsList(pTags);         
            }
        });
        // 動態新增店家下拉式選單部分
        function createBrandsList(brands){
            let opBrands = document.createElement('option');
            opBrands.selected;
            opBrands.value = "0";
            opBrands.innerText = '選擇店家'
            document.getElementById('addProductBrand').appendChild(opBrands);
            for(let i = 0; i < brands.length; i++){
                let opBrand = document.createElement('option');
                opBrand.value = brands[i].id;
                opBrand.innerText = brands[i].brandName;
                document.getElementById('addProductBrand').appendChild(opBrand);
            }
        }
        // 動態新增標籤下拉式選單部分
        function createTagsList(pTags){
            let opTags = document.createElement('option');
            opTags.selected;
            opTags.value = "0";
            opTags.innerText = '選擇商品標籤'
            document.getElementById('addProductTag').appendChild(opTags);
            for(let i = 0; i < pTags.length; i++){
                let opTag = document.createElement('option');
                opTag.value = pTags[i].id;
                opTag.innerText = pTags[i].tagName;
                document.getElementById('addProductTag').appendChild(opTag);
            }
        }
    });

    //檢查表單非空&格式
    $('#addProductTitle').blur(function (e) {
        e.preventDefault();
        let addNewProductName = $('#addProductTitle').val();
        if (!addNewProductName) {
            $('#addNewProductNext').addClass('disabled');
            $('#messageProductTitle').text('請輸入商品名稱');
        } else {
            $('#addNewProductNext').removeClass('disabled');
            $('#messageProductTitle').empty();
        }
    });
    $('#addProductAbout').blur(function (e) {
        e.preventDefault();
        let addNewAboutProduct = $('#addProductAbout').val();
        if (!addNewAboutProduct) {
            $('#addNewProductNext').addClass('disabled');
            $('#messageProductAbout').text('請輸入商品簡介');
        } else {
            $('#addNewProductNext').removeClass('disabled');
            $('#messageProductAbout').empty();
        }
    });
    $('#addProductInfo').blur(function (e) {
        e.preventDefault();
        let addNewProductDetail = $('#addProductInfo').val();
        if (!addNewProductDetail) {
            $('#addNewProductNext').addClass('disabled');
            $('#messageProductInfo').text('請輸入商品詳細介紹');
        } else {
            $('#addNewProductNext').removeClass('disabled');
            $('#messageProductInfo').empty();
        }
    });
    $('#addProductPrice').blur(function (e) {
        e.preventDefault();
        let addNewProductPrice = $('#addProductPrice').val();
        if (!addNewProductPrice) {
            $('#addNewProductNext').addClass('disabled');
            $('#messageProductPrice').text('請輸入商品價格');
        } else {
            $('#addNewProductNext').removeClass('disabled');
            $('#messageProductPrice').empty();
        }
    });
    $('#addProductStock').blur(function (e) {
        e.preventDefault();
        let addNewProductStock = $('#addProductStock').val();
        if (!addNewProductStock) {
            $('#addNewProductNext').addClass('disabled');
            $('#messageProductStock').text('請輸入商品庫存');
        } else {
            $('#addNewProductNext').removeClass('disabled');
            $('#messageProductStock').empty();
        }
    });
    $('#addProductBrand').blur(function (e) {
        e.preventDefault();
        let addNewProductBrand = $('#addProductBrand').val();
        if (addNewProductBrand == '0') {
            $('#addNewProductNext').addClass('disabled');
            $('#messageProductBrand').text('請選擇上架店家');
        } else {
            $('#addNewProductNext').removeClass('disabled');
            $('#messageProductBrand').empty();
        }
    });
    $('#addProductTag').blur(function (e) {
        e.preventDefault();
        let addNewProductTag = $('#addProductTag').val();
        if (addNewProductTag == '0') {
            $('#addNewProductNext').addClass('disabled');
            $('#messageProductTag').text('請選擇商品標籤');
        } else {
            $('#addNewProductNext').removeClass('disabled');
            $('#messageProductTag').empty();
        }
    });

    //上架新商品的所有Modal頁面(兩頁)
    // 第一頁完成 下一步按鈕
    $('#addNewProductNext').click(function(e) {
        e.preventDefault();

        //抓取彈跳式表單中輸入的值
        let addNewProductName = $('#addProductTitle').val();
        let addNewAboutProduct = $('#addProductAbout').val();
        let addNewProductDetail = $('#addProductInfo').val();
        let addNewProductPrice = $('#addProductPrice').val();
        let addNewProductStock = $('#addProductStock').val();
        let addNewProductBrand = $('#addProductBrand').val();
        let addNewProductTag = $('#addProductTag').val();

        //非空判斷
        if(!addNewProductName || !addNewAboutProduct || !addNewAboutProduct ||
            !addNewProductPrice || !addNewProductStock ||
            addNewProductBrand == '0' || addNewProductTag == '0') {
            if (!addNewProductName) {
                $('#addNewProductNext').addClass('disabled');
                $('#messageProductTitle').text('請輸入商品名稱');
            } else {
                $('#addNewProductNext').removeClass('disabled');
                $('#messageProductTitle').empty();
            }
            if (!addNewAboutProduct) {
                $('#addNewProductNext').addClass('disabled');
                $('#messageProductAbout').text('請輸入商品簡介');
            } else {
                $('#addNewProductNext').removeClass('disabled');
                $('#messageProductAbout').empty();
            }
            if (!addNewAboutProduct) {
                $('#addNewProductNext').addClass('disabled');
                $('#messageProductInfo').text('請輸入商品詳細介紹');
            } else {
                $('#addNewProductNext').removeClass('disabled');
                $('#messageProductInfo').empty();
            }
            if (!addNewProductPrice) {
                $('#addNewProductNext').addClass('disabled');
                $('#messageProductPrice').text('請輸入商品價格');
            } else {
                $('#addNewProductNext').removeClass('disabled');
                $('#messageProductPrice').empty();
            }
            if (!addNewProductStock) {
                $('#addNewProductNext').addClass('disabled');
                $('#messageProductStock').text('請輸入商品庫存');
            } else {
                $('#addNewProductNext').removeClass('disabled');
                $('#messageProductStock').empty();
            }
            if (addNewProductBrand == '0') {
                $('#addNewProductNext').addClass('disabled');
                $('#messageProductBrand').text('請選擇上架店家');
            } else {
                $('#addNewProductNext').removeClass('disabled');
                $('#messageProductBrand').empty();
            }
            if (addNewProductTag == '0') {
                $('#addNewProductNext').addClass('disabled');
                $('#messageProductTag').text('請選擇商品標籤');
            } else {
                $('#addNewProductNext').removeClass('disabled');
                $('#messageProductTag').empty();
            }
        } else { //確認所有欄位都有值才執行

            //創建物件
            let product = {};
            product['productName'] = addNewProductName;
            product['aboutProduct'] = addNewAboutProduct;
            product['productDetail'] = addNewProductDetail;
            product['price'] = addNewProductPrice;
            product['inStock'] = addNewProductStock;
            console.log(product);

            $.ajax({
                url: 'product/manage',
                type: 'POST',
                async: false,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(product),
                success: function (response) {
                    let productId = response;//以下都是利用回傳的id值做剩下的動作
                    $.ajax({
                        url: 'product/manage/' + productId + '/brand',
                        type: 'POST',
                        async: false,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(addNewProductBrand),
                        success: function () {
                            console.log("成功讀入商品店家");
                        }
                    });
                    $.ajax({
                        url: 'product/manage/' + productId + '/productTag',
                        type: 'POST',
                        async: false,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(addNewProductTag),
                        success: function () {
                            console.log("成功讀入商品標籤");
                        }
                    });
                    // 關閉第一頁modal
                    const addModal = document.getElementById('addModal');
                    const myAddModal = bootstrap.Modal.getInstance(addModal); // 建構式
                    myAddModal.hide();
                    // 跳轉第二頁modal上傳圖片
                    const addProductImgModal = document.getElementById('addProductImgModal');
                    const myModal = new bootstrap.Modal(addProductImgModal); // 建構式
                    myModal.show();

                    // 上架新商品圖片完成 上架按鈕
                    $('#submitProductImg').click(function (e) {
                        e.preventDefault();

                        // 取得檔案
                        const productImg = $('#productImg').prop('files')[0];

                        // 宣告 FileReader
                        const reader = new FileReader();

                        // 轉換成 DataURL
                        reader.readAsDataURL(productImg);

                        //檔案讀取完成執行
                        reader.onload = function () {

                            //通過base64來轉化圖片，去掉圖片頭（data:image/png;base64,）
                            let fileB64 = (reader.result);
                            const b64data = fileB64.split(',')[1];
                            const contentType = fileB64.split(',')[0].split(';')[0].split(':')[1];
                            const sliceSize = 512;
                            // Base64轉byteArray方法
                            const b64toBlob = (b64Data, contentType = '', sliceSize = '') => {
                                const byteCharacters = atob(b64Data);
                                const byteArrays = [];

                                for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                                    const slice = byteCharacters.slice(offset, offset + sliceSize);

                                    const byteNumbers = new Array(slice.length);
                                    for (let i = 0; i < slice.length; i++) {
                                        byteNumbers[i] = slice.charCodeAt(i);
                                    }
                                    const byteArray = new Uint8Array(byteNumbers);
                                    byteArrays.push(byteArray);
                                }
                                const blob = new Blob(byteArrays, {type: contentType});
                                return blob;
                            }
                            // 創建formData
                            let formData = new FormData();
                            formData.append('file', b64toBlob(b64data, contentType, sliceSize));

                            $.ajax({
                                type: "POST",
                                url: "product/manage/" + productId + "/productImg",
                                data: formData,
                                async: false,
                                mimeType: "multipart/form-data",
                                processData: false,
                                contentType: false,
                                dataType: "json",

                                success: function (response) {
                                    if (response) {
                                        alert('商品成功上架');
                                        window.location.href = "product";
                                    } else {
                                        alert('商品上架失敗');
                                        window.location.href = "product";
                                    }
                                }
                            });
                        }
                    });

                    // 離開上傳圖片頁面 把整筆product刪除
                    $('#closeAddProduct').click(function (e) {
                        e.preventDefault();
                        $.ajax({
                            type: "DELETE",
                            url: "product/manage/" + productId,
                            data: productId,
                            async: false,
                            contentType: 'application/json;charset=utf-8',
                            dataType: 'json',
                            success: function (response) {
                                if (response) {
                                    console.log("成功刪除產品Id：" + productId);
                                }
                            }
                        });
                    });
                }
            });
        }
    });
    //上架新商品的所有Modal頁面(兩頁) 結束

    //移除商品按鈕函式
    function removeProduct(){
        console.log("移除商品：" + this.id);
        $.ajax({
            type: "DELETE",
            url: "product/manage/" + this.id,
            data: this.id,
            // async: false,
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: function () {
                console.log("成功刪除產品Id：" + this.id);
            }
        });
        window.location.href = "product"
    }

});

