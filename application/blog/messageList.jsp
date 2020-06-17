<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%@ include file="header.jsp" %>

   <div class="mainarea_right"> 
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">
		     <a href="<%=request.getContextPath()%>/blog/<bean:write name="accountProfileForm" property="account.username"/>" class="post-tag">
		          我的主题
		    </a>
		    
		      <span class="topsel">
		       我的评论
		     </span>
		    </div> 
		    <div class="title_right"> 
		    <a href="<%=request.getContextPath()%>/message/post.jsp">
            <img src="<%=request.getContextPath() %>/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div>
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>
                
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="a