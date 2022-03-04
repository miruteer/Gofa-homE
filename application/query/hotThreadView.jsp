<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title" value=" 道场查询"/>
<%@ include file="../common/IncludeTop.jsp" %>

<bean:parameter name="queryType" id="queryType" value=""/>


<logic:present name="threadListForm">
    <logic:greaterThan name="threadListForm" property="allCount" value="0">

        <div class="tres">
                <%-- request.setAttribute("paramMaps", qForm.getParamMaps());  in ThreadQueryAction --%>
            符合查询主题贴共有<b><bean:write name="threadListForm" property="allCount"/></b>贴
          <MultiPages:pager actionFormName="threadListForm" page="/message/threadViewQuery.shtml"
                            name="paramMaps">
                <MultiPages:prev name=" 上一页 "/>
                <MultiPages:index displayCount="3"/>
                <MultiPages:next name=" 下一页 "/>
            </MultiPages:pager>

        </div>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>主题</th>
                <th>作者</th>
                <t