<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>


<bean:define id="messageId" name="messageReplyForm" property="messageId"/>
<bean:define id="pMessageId" name="messageReplyForm" property="parentMessage.messageId"/>
<bean:define id="action" name="messageReplyForm" property="action"/>


<logic:messagesNotPresent>
    <logic:empty name="errors">
        <logic:notEqual name="action" value="delete">
          帖子保存成功
            <script>
                window.top.location.href = '<%=request.getContextPath()%>/forum/messageNavList2.shtml?pMessage=<bean:write name="pMessageId"/>&message=<bean:write name="messageId"/>#<bean:write name="messageId"/>';
            </script>
        <