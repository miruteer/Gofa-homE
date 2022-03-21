<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../blog/header.jsp" %>

   <div class="mainarea_right"> 
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">
		     <span class="topsel">
		          我的主题
		    </span>
		    
		    <a href="<%=request.getContextPath()%>/blog/messages/<bean:write name="accountProfileForm" property="account.username"/>" class="post-tag">
		        我的评论
		    </a>
		    </div> 
		    <div class="title_right"> 
		   <a href="<%=request.getContextPath()%>/message/post.jsp?to=<bean:write name="accountProfileForm" property="account.username"/>">
           <img src="/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>
                 <h3>关注者</h3>
<!-- second query result -->
<logic:present name="accountListForm">
<logic:greaterThan name="accountListForm" property="allCount" value="0">

<logic:iterate indexId="i"   id="account" name="accountListForm" property="list" >

  
  
  <div class="linkblock">  
<table width="100%"><tr><td width="50%" align="center">
         <a href