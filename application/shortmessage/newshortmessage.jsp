<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-store");
response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@page import="com.jdon.jivejdon.presentation.listener.UserCounterListener,java.util.List,java.util.Iterator"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<bean:defin