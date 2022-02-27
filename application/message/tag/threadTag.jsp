<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="forumThread" name="threadForm"/>

<bean:define id="title" value=" 添加标签"/>
<%@ include file="../messageHeader.jsp" %>


<bean:define id="ForumMessage" name="threadForm" property="rootMessage"/>


<br><br>
<div align="center">

	
	<logic:equal name="threadForm" property="authenticated"
             value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<l