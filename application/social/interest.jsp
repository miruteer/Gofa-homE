
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../blog/header.jsp" %>



   <div class="mainarea_right">
   
     <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注的标签</div> 		   
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content_reply>正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
    
   
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注过的板块</div> 
		    <div class="title_right"> <a href="<%=request.getContextPath()%>/message/post.jsp">
            <img src="/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=forum >正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
	  
	  
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注过的主题</div> 
		    <div class="title_right"> <a href="<%=request.getContextPath()%>/message/post.jsp">
            <img src="/images/newtopic.gif" width="110" height="20" border="0" alt="发表新帖子" /></a></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
	  
	  
	   <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">近期关注过的作者</div> 		   
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">