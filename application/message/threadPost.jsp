<%--  called bu ThreadList http://127.0.0.1:8080/jivejdon/forum.jsp --%>
<%@ page session="false"  %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:parameter id="forumId" name="forumId" value="" />
    
<div class="box">	
<div class="comment">

<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>
    
<html:form action="/message/postSaveAction.sthml" 