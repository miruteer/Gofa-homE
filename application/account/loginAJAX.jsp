<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<script>
    var isLogin = false;
</script>
<%
  if (request.getUserPrincipal() != null) {
    request.setAttribute("principal", request.getUserPrincipal());
    request.getSession().setAttribute("online",request.getUserPrincipal().toString());
%>
<script>   isLogin = true;</script>
<%
}else{
  request.removeAttribute("principal");
  if (request.getSession(false) != null)
    request.getSession().removeAttribute("online");
%>
<script>   isLogin = false;</script>
<%
  }
%>


<%@page import="com.jdon.controller.WebAppUtil,
              com.jdon.jivejdon.domain.model.account.Account,
              com.jdon.jivejdon.api.account.AccountService" %>
<%
  //need at first include IncludeTop.jsp
//this jsp can save logined account all datas into request, if you inlude this jsp, you can get them. 
  if (request.getAttribute("principal") != null) {
    AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
    Account account = accountService.getloginAccount();
    if (account != null) {
      request.setAttribute("loginAccount", account);
    }
  }
%>
<script>
    var log