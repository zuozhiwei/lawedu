/**
 * Datatables 真分页初始化
 */
function dataTablesServerSideInit() {
    $.extend(true, $.fn.dataTable.defaults,{
        "processing" : true,
        "serverSide" : true,
        "ordering" : false,
        "searching" : false,
        "language" : {
            "decimal" : "",
            "emptyTable" : "No data available in table",
            "info" : "第_START_条到第_END_条,共 _TOTAL_条",
            "infoEmpty" : "Showing 0 to 0 of 0 entries",
            "infoFiltered" : "(filtered from _MAX_ total entries)",
            "infoPostFix" : "",
            "thousands" : ",",
            "lengthMenu" : "每页显示_MENU_条",
            "loadingRecords" : "加载中...",
            "processing" : "数据处理中...",
            "search" : "Search:",
            "zeroRecords" : "没有查询到数据，请重新设置查询条件",
            "paginate" : {
                "first" : "第一页",
                "last" : "最后一页",
                "next" : "下一页",
                "previous" : "上一页"
            },
            "aria" : {
                "sortAscending" : ": activate to sort column ascending",
                "sortDescending" : ": activate to sort column descending"
            }
        }
    });
}

/**
 * Datatables 假分页初始化
 */
function dataTablesPageSideInit() {
    $.extend(true, $.fn.dataTable.defaults,{
        "processing" : true,
        "serverSide" : false,
        "ordering" : false,
        "searching" : false,
        "language" : {
            "decimal" : "",
            "emptyTable" : "No data available in table",
            "info" : "第_START_条到第_END_条,共 _TOTAL_条",
            "infoEmpty" : "Showing 0 to 0 of 0 entries",
            "infoFiltered" : "(filtered from _MAX_ total entries)",
            "infoPostFix" : "",
            "thousands" : ",",
            "lengthMenu" : "每页显示_MENU_条",
            "loadingRecords" : "加载中...",
            "processing" : "数据处理中...",
            "search" : "Search:",
            "zeroRecords" : "没有查询到数据，请重新设置查询条件",
            "paginate" : {
                "first" : "第一页",
                "last" : "最后一页",
                "next" : "下一页",
                "previous" : "上一页"
            },
            "aria" : {
                "sortAscending" : ": activate to sort column ascending",
                "sortDescending" : ": activate to sort column descending"
            }
        }
    });
}
