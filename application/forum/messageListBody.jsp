<%@ page session="false" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>

<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">

<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
<%-- <logic:equal name="i" value="0">
  <logic:notEmpty name="principal">
    <logic:equal name="loginAccount" property="roleName" value="Admin">
      <p> <a
        href="javascript: