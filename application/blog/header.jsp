<%@ page import="com.jdon.jivejdon.domain.model.account.Account" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%@ include file="../../common/security.jsp" %>
<%@ include file="../../common/loginAccount.jsp" %>

<logic:present name="loginAccount" >   
  <% 
//urlrewrite.xml:
//	  <rule>
//	<from>^/blog/(.*)$</from>
//	<!-- note: here must be userId, actually userId'value is username'value, need a model key  -->
//	<to>/blog/index.shtml?userId=$1&amp;username=$1</to>
//</rule>
  Account account = (Account)request.getAttribute("loginAccount");
  String userId = request.getParameter("userId");
