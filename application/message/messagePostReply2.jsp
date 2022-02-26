<%@ page session="false"  %>
<%@page isELIgnored="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:parameter id="forumId" name="forum.forumId" value="0"/>
<bean:parameter id="pmessageId" name="pmessageId" value="0"/>
<bean:parameter id="parentMessageSubject" name="parentMessageSubject" value="0"/>

<div class="box">
<div class="comment">
<iframe id='target_reply' name='target_reply' src='' style='display: none'></iframe>
<html:form action="/message/replySaveAction.sthml" method="post" target="target_reply" styleId="messageReply"  onsubmit="return checkPost(this);" >

<div class="row">
	<div class="col-md-12">
       
        <div class="form-group">
            
<input type="hidden" name="jdonActionType" value="create">
<input type="hidden" name="forum.forumId" value="<bean:write name='forumId' />" />
<input type="hidden" name="parentMessage.messageId" id="parentMessageId" value="<bean:write name="pmessageId" />"/>
<input type="hidden" name="" id="parentMessageSubject" value="<bean:write name="parentMessageSubject"  />"/>
<input type="hidden" name="messageId" value="">
<input type="hidden" name="reblog" id="reblog" value="" />
<input type="hidden" name="onlyreblog" id="onlyreblog" value="" />

		
        <inpu