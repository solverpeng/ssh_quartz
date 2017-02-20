<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/themes/default/easyui.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/css/themes/icon.css">
    <link type="text/css" rel="stylesheet" href="${ctx}/css/joy.css">
    <script type="text/javascript" src="${ctx}/scripts/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/locale/easyui-lang-zh_CN.js"></script>
    <title>新增任务</title>
</head>
<body>
<s:debug/>
<form id="form1" action="<s:url action="save"/>" method="post">
    <table class="joy-form-table">
        <tr>
            <td class="joy-form-table-title" colspan="2">
                新增定时任务
            </td>
        </tr>
        <input type="hidden" id="id" name="id" value="${id}"/>

        <tr>
            <td class="joy-form-td-title">
                任务名称:
            </td>
            <td class="joy-form-td-value">
                <input id="taskName" name="taskName" value="${taskName}" type="text" class="easyui-validatebox" required="true"/>
                <span class="required">*</span>
            </td>
        </tr>

        <tr>
            <td class="joy-form-td-title">
                任务开始时间:
            </td>
            <td class="joy-form-td-value">
                <input value="${startTimeString}" id="startTimeString" name="startTimeString" type="hidden"/>
                <select id="syear" style='width:55px'></select>年
                <select id="smonth" style='width:40px' onchange="dateLimit('s')"></select>月
                <select id="sdate" style='width:40px'></select>日
                <select id="shour" style='width:40px'></select>时
                <select id="sminute" style='width:40px'></select>分
                <select id="ssecond"></select>秒
                <span class="required">*</span>
            </td>
        </tr>

        <tr>
            <td class="joy-form-td-title">
                任务结束时间:
            </td>
            <td class="joy-form-td-value">
                <input value="${endTimeString}" id="endTimeString" name="endTimeString" type="hidden"/>
                <select id="eyear" onchange="etsIsShow()"></select>
                <span id='ets'>年
                    <select id="emonth" onchange="dateLimit('e')"></select>月
                    <select id="edate"></select>日
                    <select id="ehour"></select>时
                    <select id="eminute"></select>分
                    <select id="esecond"></select>秒
                </span>
            </td>
        </tr>

        <tr>
            <td class="joy-form-td-title">
                任务间隔时间:
            </td>
            <td class="joy-form-td-value">
                <input id="intervalTime" name="intervalTime" value="${intervalTime}" type="text"
                       class="easyui-numberbox" required="true"/>
                <span class="required">*</span>
            </td>
        </tr>
        <tr>
            <td class="joy-form-td-title">
                任务间隔单位:
            </td>
            <td class="joy-form-td-value">
                <select id="intervalUnit" name="intervalUnit">
                    <option value="S">秒</option>
                    <option value="m">分</option>
                    <option value="H">时</option>
                    <option value="D">天</option>
                    <option value="M">月</option>
                    <option value="Y">年</option>
                    <option value="W">周</option>
                </select>
                <span class="required">*</span>
            </td>
        </tr>

        <tr>
            <td class="joy-form-td-title">
                执行类全类名:
            </td>
            <td class="joy-form-td-value">
                <input id="operateClass" name="operateClass" value="${operateClass}" type="text"
                       class="easyui-validatebox" required="true"/>
                <span class="required">*</span>(类全名,即包名+类名)
            </td>
        </tr>

        <tr>
            <td class="joy-form-td-title">
                任务状态:
            </td>
            <td class="joy-form-td-value">
                <select id="status" name="status">
                    <option value="1">启用</option>
                    <option value="0">停用</option>
                </select>
                <span class="required">*</span>
            </td>
        </tr>

        <tr>
            <td class="joy-form-td-title">
                任务备注:
            </td>
            <td class="joy-form-td-value">
                <input id="remark" name="remark" value="${remark}" type="text" class="easyui-validatebox"/>
            </td>
        </tr>
        <tr>
            <td class="tdLabel" align="center" colspan="2">
                <input type="button" class="joy-button" value="提 交" onclick="$('#form1').submit();"/>
            </td>
        </tr>
    </table>
</form>

</body>
<script>
    $(function () {
        $('#form1').form({
            onSubmit: function () {
                $('#startTimeString').val($("#syear").val() + "-" + $("#smonth").val() + "-" + $("#sdate").val() + " " + $("#shour").val() + ":" + $("#sminute").val() + ":" + $("#ssecond").val());
                if ($("#eyear").val() != '') {
                    $('#endTimeString').val($("#eyear").val() + "-" + $("#emonth").val() + "-" + $("#edate").val() + " " + $("#ehour").val() + ":" + $("#eminute").val() + ":" + $("#esecond").val());
                    if ($('#endTimeString').val() < $('#startTimeString').val()) {
                        alert("结束时间不能小于开始时间!");
                        return false;
                    }
                }
                return $('#form1').form('validate');
            },
            success: function (data) {
                if (data === 1) {
                    alert("保存成功!");
                    window.close();
                } else {
                    alert(data);
                }
            }
        })
    });
    $(document).ready(function () {
        $("#eyear").append("<option value=''>-无-</option>");
        $("#ets").hide();
        var date = new Date();
        for (var i = 0; i < 60; i++) {
            var str = (i < 10 ? '0' : '') + i;
            if (i < 5) {
                var year = date.getFullYear() + i;
                $("#syear").append("<option value='" + year + "'>" + year + "</option>");
                $("#eyear").append("<option value='" + year + "'>" + year + "</option>");
            }
            if (i > 0 && i < 13) {
                $("#smonth").append("<option value='" + str + "' " + (date.getMonth() === i - 1 ? "selected" : "") + ">" + str + "</option>");
                $("#emonth").append("<option value='" + str + "'>" + str + "</option>");
            }
            if (i > 0 && i < 32) {
                $("#sdate").append("<option value='" + str + "' " + (date.getDate() === i ? "selected" : "") + ">" + str + "</option>");
                $("#edate").append("<option value='" + str + "'>" + str + "</option>");
            }
            if (i < 24) {
                $("#shour").append("<option value='" + str + "' " + (date.getHours() === i ? "selected" : "") + ">" + str + "</option>");
                $("#ehour").append("<option value='" + str + "'>" + str + "</option>");
            }
            $("#sminute").append("<option value='" + str + "' " + (date.getMinutes() === i ? "selected" : "") + ">" + str + "</option>");
            $("#ssecond").append("<option value='" + str + "' " + (date.getSeconds() === i ? "selected" : "") + ">" + str + "</option>");
            $("#eminute").append("<option value='" + str + "'>" + str + "</option>");
            $("#esecond").append("<option value='" + str + "'>" + str + "</option>");
        }
        dateLimit('s');
    });
    function etsIsShow() {
        if ($("#eyear").val() !== '') {
            $("#ets").show();
        } else {
            $("#ets").hide();
        }
    }
    function dateLimit(se) {
        var array = {
            "01": 31,
            "02": 28,
            "03": 31,
            "04": 30,
            "05": 31,
            "06": 30,
            "07": 31,
            "08": 31,
            "09": 30,
            "10": 31,
            "11": 30,
            "12": 31
        };
        var len = $("#" + se + "date option").length;
        if (len < array[$("#" + se + "month").val()]) {
            for (var i = len + 1; i <= array[$("#" + se + "month").val()]; i++) {
                $("#" + se + "date").append("<option value='" + i + "'>" + i + "</option>");
            }
        } else {
            for (var i = len; i > array[$("#" + se + "month").val()]; i--) {
                $("#" + se + "date option:last").remove();
            }
        }
        if ($("#" + se + "year").val() % 4 === 0 && $("#" + se + "month").val() === '02') {
            $("#" + se + "date").append("<option value='29'>29</option>");
        }
    }
    window.onbeforeunload = function () {
        window.opener.document.queryForm.submit();
    };
</script>
</html>

