/**
 * 将表单提交值放入对象obj
 * @param from 表单jQuery对象
 * @param obj 输入对象
 */
function setFormDataInObject(form,obj) {
    var formObj = form.serializeArray();
    for(var i=0;i<formObj.length;i++) {
        obj[formObj[i].name] = formObj[i].value;
    }
}

/**
 * 将obj中key-value填入表单 class带DataInput
 * @param from 表单jQuery对象
 * @param obj 输入对象
 */
function setFormDataFromObj(form,obj) {
    form.find(".DataInput").each(function(index,element){
        for(var key in obj) {
            if($(element).attr("name") == key) {
                var tagName = $(element).get(0).tagName;
                if(tagName == 'SPAN') {
                    $(element).html(obj[key]);
                } else {
                    $(element).val(obj[key]);
                }
            }
        }
    });
}

/**
 * 清空表单数值
 */
function setFormDataEmpty(form) {
    form.find(".DataInput").each(function(index,element){
        var tagName = $(element).get(0).tagName;
        if(tagName == 'SPAN') {
            $(element).html("");
        } else {
            $(element).val("");
        }
    });
}

/**
 * 获取数组对象
 * @param array 数组
 * @param field 字段名
 * @param value 字段值
 */
function getDataObjFromArray(array,field,value) {
    for(var i = 0;i<array.length;i++) {
        if(array[i][field] == value) {
            return array[i];
        }
    }
    return {};
}

/**
 * 页面跳转
 * @param url
 */
function goPage(url){
    self.location=url;
}


String.prototype.toDate = function (fmt) {
    if (fmt == null) fmt = 'yyyy-MM-dd hh:mm:ss';
    var str = this;
    //fmt为日期格式,str为日期字符串
    var reg = /([yMdhmsS]+)/g;//日期格式中的字符
    var key = {};
    var tmpkeys = fmt.match(reg);
    for (var i = 0 ; i < tmpkeys.length; i++) {
        key[tmpkeys[i].substr(0, 1)] = i + 1;
    }
    var r = str.match(fmt.replace(reg, "(\\d+)"));
    return new Date(r[key["y"]], r[key["M"]] - 1, r[key["d"]], r[key["h"]], r[key["m"]], r[key["s"]], r[key["S"]]);
}
