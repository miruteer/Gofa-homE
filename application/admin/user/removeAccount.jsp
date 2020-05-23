<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 用户管理" />
<%@ include file="../header.jsp" %>

<html:form action="/admin/user/editSaveAccount.shtml" method="post">

<ht