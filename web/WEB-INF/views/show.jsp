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
    <title>查看任务信息</title>
</head>
<body>

<table class="joy-form-table">
    <tr>
        <td class="joy-form-table-title" colspan="2">
            预览定时任务信息
        </td>
    </tr>
    <tr>
        <td class="joy-form-td-title">任务名称</td>
        <td class="joy-form-td-value"><s:property value="%{model.taskName}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">任务开始时间</td>
        <td class="joy-form-td-value"><s:property value="%{model.startTimeString}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">任务结束时间</td>
        <td class="joy-form-td-value"><s:property value="%{model.endTimeString}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">任务间隔时间</td>
        <td class="joy-form-td-value"><s:property value="%{model.intervalTime}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">任务间隔单位</td>
        <td class="joy-form-td-value"><s:property value="%{model.intervalUnit}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">执行类全类名</td>
        <td class="joy-form-td-value"><s:property value="%{model.operateClass}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">最后一次执行时间</td>
        <td class="joy-form-td-value"><s:property value="%{model.lastExecTimeString}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">任务状态</td>
        <td class="joy-form-td-value"><s:property value="%{model.status}"/></td>
    </tr>
    <tr>
        <td class="joy-form-td-title">任务备注</td>
        <td class="joy-form-td-value"><s:property value="%{model.remark}"/></td>
    </tr>
</table>
</body>
</html>