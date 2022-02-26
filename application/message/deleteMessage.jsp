<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="messageList" name="messageListForm" property="list" />

<bean:define id="parentMessage" name="messageListForm" property="oneModel" />
<bean:define id="forum" name="parentMessage" property="forum" />
<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>



<div class="col-md-offset-2 col-md-8">

    <p>
    在
    <html:link page="/forum.jsp"  paramId="forumId" paramName="forum" paramProperty="forumId">
            <bean:write name="