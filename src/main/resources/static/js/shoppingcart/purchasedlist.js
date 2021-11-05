    //设置需要显示的列
    var columns = [{
        field: 'Id',
        title: '商品編號',
        sortable:true //设置ID列可以排序
    }, {
        field: 'ProductName',
        title: '商品名稱',
        sortable:true //设置ID列可以排序
    }, {
        field: 'ShippingStatus',
        title: '出貨狀態',
        sortable:true //设置ID列可以排序
    }, {
        field: 'Qty',
        title: '數量',
        sortable:true //设置ID列可以排序
    }, {
        field: 'UnitPrice',
        title: '單價',
        sortable:true //设置ID列可以排序
    }, {
        field: 'total',
        title: '小計',
        sortable:true //设置ID列可以排序
    }];

    //需要显示的数据
    var data = [{
        Id: 1,
        ProductName: '滷蛋',
        ShippingStatus: '待出貨',
        Qty: '1',
        UnitPrice: '$10',
        total: '$10'
    }, {
        Id: 2,
        ProductName: '滷蛋',
        ShippingStatus: '待出貨',
        Qty: '1',
        UnitPrice: '$10',
        total: '$10'
    }];
   


    
    // //bootstrap table初始化数据
    // $('#table').bootstrapTable({
    //     columns: columns,
    //     data: getData(),   //请求后台的URL（*）
    //     method: 'GET', //请求方式（*）
    //     height: 400,
    //     customSort: customSort,
    //     onSort: function (name, order) {
    //     alert(order)
    //     }
    // });

    // $('#table').on('sort.bs.table', function (e,name,order) {
    //     alert(name);
    //});

    // function customSort(sortName, sortOrder, data) {
    //     var order = sortOrder === 'desc' ? -1 : 1
    //     data.sort(function (a, b) {
    //         var v1 = a[sortName]
    //         var v2 = b[sortName]
    //         if (v1 == 3) {
    //             return -1;
    //         }
    //         if (v1 > v2) {
    //             return 1
    //         }
    //         return 0
    //     })
    // }

    // function getData() {
    //     var data = [];

    //     for (var i = 0; i < 2; i++) {
    //         var item = {
    //             Id: i,
    //             ProductName: '香蕉' + i,
    //             StockNum: i
    //         };

    //         data.push(item);
    //     };
    //     for (var i = 0; i < 5; i++) {
    //         var item = {
    //             Id: i,
    //             ProductName: '苹果' + i,
    //             StockNum: i
    //         };

    //         data.push(item);
    //     };
    //     for (var i = 0; i < 5; i++) {
    //         var item = {
    //             Id: i,
    //             ProductName: '橘子' + i,
    //             StockNum: i
    //         };

    //         data.push(item);
    //     };
    //     return data;
    // }