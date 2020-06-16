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
  String username = request.getParameter("username");
  if (userId != null || username != null ){
	  //userId vaule maybe username or userId.
	  if ( account.getUsername().equals(username) ||  account.getUserId().equals(userId)){
	      request.setAttribute("isOwner", "true");
		  com.jdon.jivejdon.presentation.form.AccountProfileForm accountProfileForm = new com.jdon.jivejdon.presentation.form.AccountProfileForm();
		  accountProfileForm.setAccount(account);
		  request.setAttribute("accountProfileForm", accountProfileForm);  
	  }
  }else if (userId == null && username == null){ 
	  request.setAttribute("isOwner", "true"); 
	  com.jdon.jivejdon.presentation.form.AccountProfileForm accountProfileForm = new com.jdon.jivejdon.presentation.form.AccountProfileForm();
	  accountProfileForm.setAccount(account);
	  request.setAttribute("accountProfileForm", accountProfileForm);  
  
    } %>
</logic:present>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="noindex">
<title><bean:write name="accountProfileForm" property="account.username"/>的博客</title> 
<%
    String domainUrl = com.jdon.jivejdon.util.ToolsUtil.getAppURL(request);
%>
<link rel="canonical" href="<%=domainUrl %>/blog/<bean:write name="accountProfileForm" property="account.username"/>" />
  <link href="//cdn.jdon.com/common/jivejdon5.css" rel="stylesheet" type="text/css"/>
  <link href="//cdn.jdon.com/common/blog/themes/default/style/blog.css" rel="stylesheet" type="text/css"/>
<script src="//cdn.jdon.com/common/js/prototype.js"></script>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >
<script src="//cdn.jdon.com/common/login2.js"></script>
    <logic:present name="principal" >
<script src="<html:rewrite page="/account/protected/js/account.jsp"/>"></script>
    </logic:present>
<script >

function loadWLJS(myfunc){
  if (typeof(TooltipManager) == 'undefined') {     
     $LAB
     .script('/common/js/window_def.js').wait()
     .wait(function(){
          myfunc();          
     })    
  }else
     myfunc();
}

var nof = function(){
}

function loadWLJSWithP(param,myfunc){
  if (typeof(TooltipManager) == 'undefined') {     
     $LAB
     .script('/common/js/window_def.js').wait()
     .wait(function(){
          myfunc(param);          
     })    
  }else
     myfunc(param);
}
var wtitlename;
var url;
function openPopUpWindow(wtitlename, url){
   this.wtitlename = wtitlename;
   this.url=url;
   loadWLJS(openPopUpBlogW);
}     

popupW=null; 
var openPopUpBlogW = function(){
    if (popupW == null) {             
       popupW = new Window({className: "mac_os_x", width:600, height:380, title: wtitlename}); 
       popupW.setURL(url);
       popupW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myW) {    	  
          if (myW == popupW){        	        	
            popupW = null;   
            Windows.removeObserver(this);
          }
        }
       }
     Windows.addObserver(myObserver);
     } 
 }     
 

</script>
</head> 

<body >
<div class="topbar"> 
   <div class="topbar_inner"> 
      <div class="topbar_inner_left"><a href="<html:rewrite page="/"/>" target="_blank"><html:img page="/images/jdonsmall.gif" border="0" width="120" height="35" alt="jdon.com" /></a></div>
      <div class="topbar_inner_right"> 
    <logic:present name="principal" >
         欢迎<a href="<%=request.getContextPath()%>/blog/<bean:write name="principal" />"></a><bean:write name="principal" /></a> 
         <a href="<%=request.getContextPath()%>/account/protected/editAccountForm.shtml?action=edit&username=<bean:write name="principal" />"> 注册资料修改 </a> |
         <logic:notPresent name="isOwner" >
           <a hr