<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
    <title>定时任务</title>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/joy.css"/>
    <script type="text/javascript" src="${ctx}/scripts/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/joy.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#btnclear').click(function () {
                $('#taskName').val('');
                $('#operateClass').val('');
                $('#status').val('');
            });

            $('#btnstart').click(function () {
                $.post("${ctx}/startTask.action",
                    function (data) {
                        if (data === '1') {
                            alert('启动成功！');
                            $('#queryForm').submit();
                        } else {
                            alert('启动失败！');
                        }
                    }
                );
            });

            $('#btnend').click(function () {
                $.post("${ctx}/endTask.action",
                    function (data) {
                        if (data === '1') {
                            alert('停止成功！');
                            $('#queryForm').submit();
                        } else {
                            alert('停止失败！');
                        }
                    }
                );
            });

            $('#btnadd').click(function () {
                window.open('${ctx}/create.action', '', 'height=600,width=600,,location=no,scrollbars');
            });

            $('#btnedit').click(function () {
                var $id = $('[name=ids]:checkbox:checked');

                if ($id.length === 0) {
                    alert("请选择要修改的信息！");
                    return false;
                }
                window.open('${ctx}/edit.action?id=' + $id.val(), '', 'height=600,width=500,,location=no,scrollbars');
            });

            $('#btndel').click(function () {
                var $id = $('[name=ids]:checkbox:checked');

                if ($id.length === 0) {
                    alert("请选择要删除的信息！");
                    return false;
                }
                var flag = window.confirm("确定要删除任务 " + $id.val() + " 吗？");

                if(flag) {
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/remove.action",
                        data: {
                            id: $id.val()
                        },
                        success: function(result){
                            if(result === '1') {
                                $id.parents('tr').remove();
                            }
                        }
                    });
                }
            });

            $('#btnshow').click(function () {
                var $id = $('[name=ids]:checkbox:checked');

                if ($id.length === 0) {
                    alert("请选择要查看的信息！");
                    return false;
                }
                window.open('${ctx}/show.action?id=' + $id.val(), '', 'height=600,width=500,,location=no,scrollbars');
            });
        });
        function changeStatus(id, obj) {
            $.post("${ctx}/changeTask.action", {"id": id},
                function (data) {
                    if (data === '1') {
                        alert('启用成功！');
                        $('#' + id).text("启用");
                        obj.innerHTML = "停用";
                    } else if (data === '2') {
                        alert('停用成功！');
                        $('#' + id).text("停用");
                        obj.innerHTML = "启用";
                    } else {
                        alert('操作失败！');
                    }
                }
            );
        }
    </script>
</head>
<body>
<div class="joy-panel">
    <div class="joy-panel-head">&nbsp;查询条件</div>
    <form id="queryForm" name="queryForm" action="<c:url value="list.action"/>" method="post" style="display: inline;">
        <div class="joy-panel-body">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">任务名称：</td>
                    <td align="left">
                        <input value="${taskName}" id="taskName" name="taskName" maxlength="100"/>
                    </td>
                    <td align="right">执行类全类名：</td>
                    <td align="left">
                        <input value="${operateClass}" id="operateClass" name="operateClass" maxlength="200"/>
                    </td>
                    <td align="right">状态：</td>
                    <td align="left">
                        <select id="status" name="status">
                            <option value="">请选择...</option>
                            <option value="0" <c:if test="${status=='0' }">selected="selected"</c:if>>启用</option>
                            <option value="1" <c:if test="${status=='1' }">selected="selected"</c:if>>停用</option>
                        </select>
                    </td>
                </tr>
            </table>
            <br/>
            <div align="center">
                <input type="submit" class="joy-button" value="模糊查询"/>
                <input type="button" class="joy-button" value="清 空" id="btnclear"/>
            </div>
        </div>
    </form>
    <br>
    <div class="joy-panel-head">&nbsp;任务列表</div>
    <div class="datagrid-toolbar">
        <a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="btnstart">
			<span class="l-btn-left">
				<span class="l-btn-text icon-view" style="padding-left: 20px;">启动定时器</span>
			</span>
        </a>
        <a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="btnend">
			<span class="l-btn-left">
				<span class="l-btn-text icon-view" style="padding-left: 20px;">停止定时器</span>
			</span>
        </a>
        <a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="btnshow">
			<span class="l-btn-left">
				<span class="l-btn-text icon-view" style="padding-left: 20px;">查看</span>
			</span>
        </a>
        <a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="btnadd">
			<span class="l-btn-left">
				<span class="l-btn-text icon-add" style="padding-left: 20px;">新增</span>
			</span>
        </a>
        <a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="btnedit">
			<span class="l-btn-left">
				<span class="l-btn-text icon-edit" style="padding-left: 20px;">修改</span>
			</span>
        </a>
        <a href="javascript:void(0)" style="float: left;" class="l-btn l-btn-plain" id="btndel">
			<span class="l-btn-left">
				<span class="l-btn-text icon-remove" style="padding-left: 20px;">删除</span>
			</span>
        </a>
    </div>
    <div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="tb">
            <tr height="30px">
                <td class="joy-list-title">
                    <label for="allChoose">
                        <input id="allChoose" type="checkbox"/>
                    </label>
                </td>
                <td class="joy-list-title">序号</td>
                <td class="joy-list-title">任务名称</td>
                <td class="joy-list-title">执行类全类名</td>
                <td class="joy-list-title">任务开始时间</td>
                <td class="joy-list-title">任务结束时间</td>
                <td class="joy-list-title">任务间隔时间</td>
                <td class="joy-list-title">任务间隔单位</td>
                <td class="joy-list-title">Cron表达式</td>
                <td class="joy-list-title">任务状态</td>
            </tr>
            <c:if test="${requestScope.page.content.size() eq '0'}">
                <tr>
                    <td colspan="10" align="center" class="joy-list-td">暂无数据</td>
                </tr>
            </c:if>
            <c:if test="${requestScope.taskList.size() > 0}">
                <c:forEach items="${requestScope.taskList}" var="task">
                    <tr height="30px">
                        <td align="center" class="joy-list-td">
                            <input name="ids" type="checkbox" value="${task.id}"/>
                        </td>
                        <td align="center" class="joy-list-td">${task.id}</td>
                        <td align="center" class="joy-list-td">${task.taskName}</td>
                        <td align="center" class="joy-list-td">${task.operateClass}</td>
                        <td align="center" class="joy-list-td">${task.startTime}</td>
                        <td align="center" class="joy-list-td">${task.endTime}</td>
                        <td align="center" class="joy-list-td">${task.intervalTime}</td>
                        <td align="center" class="joy-list-td">${task.intervalUnit}</td>
                        <td align="center" class="joy-list-td">${task.cronExpression}</td>
                        <td align="center" class="joy-list-td">${task.status=='0'?'启用':'停用'}</td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>
    </div>
</div>
</body>
</html>
