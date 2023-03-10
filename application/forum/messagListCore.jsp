
<%@ page session="false" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page trimDirectiveWhitespaces="true" %>


  <bean:define id="forumThread" name="messageListForm" property="oneModel"/>
  <bean:define id="forum" name="forumThread" property="forum"/>
  <bean:define id="title" name="forumThread" property="name"/>
  <bean:define id="pagestart" name="messageListForm" property="start"/>
  <bean:define id="pagecount" name="messageListForm" property="count"/>
  <%
    int pagestartInt = ((Integer) pageContext.getAttribute("pagestart")).intValue();
    int pagecountInt = ((Integer) pageContext.getAttribute("pagecount")).intValue();
    int currentPageNo = 1;
    if (pagecountInt > 0)
      currentPageNo = (pagestartInt / pagecountInt) + 1;
    String titleStr = (String) pageContext.getAttribute("title");
    if (titleStr == null)
      response.sendError(404);
    if (currentPageNo > 1)
      titleStr = titleStr + "  - 第" + currentPageNo + "页";
    pageContext.setAttribute("title", titleStr);
    pageContext.setAttribute("currentPageNo", currentPageNo);
  %>
  <%@ include file="header.jsp" %>

<!-- /////////////////////////////////////////Content -->
<div id="page-content" class="single-page container">
  <div class="row">
    <!-- /////////////////左边 -->
    <div id="main-content" class="col-md-8">
      <div class="box">

        <logic:empty name="forumThread">
          <bean:define id="forumThread" name="messageListForm" property="oneModel"/>
          <bean:define id="forum" name="forumThread" property="forum"/>
        </logic:empty>

        <!--  内容-->
        <div id="messageListBody">
          
          <%@include file="messageListBody.jsp" %>
                  
          <logic:equal name="forumMessage" property="root" value="true">
              <div class="diggArea list-inline  top-social" >
                <DIV class=diggNum id="digNumber_<bean:write name="forumMessage" property="messageId"/>">
                  <logic:notEqual name="forumMessage" property="digCount" value="0">
                    <bean:write name="forumMessage" property="digCount"/>
                  </logic:notEqual>
                </DIV>
	            <DIV class="diggLink top8"
                     id="textArea_<bean:write name="forumMessage" property="messageId"/>"><a
                    href="javascript:digMessage('<bean:write name="forumMessage" property="messageId"/>')"><i class="fa fa-thumbs-o-up"></i></a>
                </DIV> 
              </div>
              <div style="margin: 0 auto;width: 85px">
	             <ul class="list-inline  top-social">
		            <li><a href="javascript:shareto('sina')"><i class="fa fa-weibo"></i></a></li>
		            <li><a href="javascript:shareto('weixin')"><i class="fa fa-weixin"></i></a></li>
		            <li><a href="javascript:shareto('qzone')"><i class="fa fa-qq"></i></a></li>		
	              </ul>
              </div>
              <logic:notEmpty name="forumMessage" property="reBlogVO.threadFroms">          
                <logic:iterate id="threadFrom" name="forumMessage" property="reBlogVO.threadFroms">                
                  <div class="reblogfrom" id='<bean:write name="threadFrom" property="threadId"/>'></div>                  
                </logic:iterate>                  
              </logic:notEmpty>
              <logic:notEmpty name="forumMessage" property="reBlogVO.threadTos">
                <logic:iterate id="threadTo" name="forumMessage" property="reBlogVO.threadTos">
                  <div class="reblogto" id='<bean:write name="threadTo" property="threadId"/>'></div>
                </logic:iterate>
              </logic:notEmpty>              
          </logic:equal>

          <div class="box">
							<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-7573657117119544" crossorigin="anonymous"></script>
							        <!-- 自适应主广告 -->
									<ins class="adsbygoogle"
     									style="display:block"
     									data-ad-client="ca-pub-7573657117119544"
     									data-ad-slot="5184711902"
     									data-ad-format="auto"
     									data-full-width-responsive="true"></ins>
							<script>
     					  	(adsbygoogle = window.adsbygoogle || []).push({});
          		</script>		
          </div>

        </div>

        <div id="pageEnd"></div>
        <div id="nextPageContent"></div>

        <%-- <!-- 导航区  -->
        <div class="post_pages_end">
          <div class="table-button-left">
            <div class="table-button-right">
              <logic:greaterThan name="messageListForm" property="numPages" value="1">
                <div class="tres">
                  有<b><bean:write name="messageListForm" property="numPages"/></b>页 
                  <MultiPagesREST:pager actionFormName="messageListForm" page="" paramId="thread" paramName="forumThread" paramProperty="threadId">
                    <MultiPagesREST:prev name=" 上一页 "/>
                    <MultiPagesREST:index displayCount="3"/>
                    <MultiPagesREST:next name=" 下一页 "/>
                  </MultiPagesREST:pager>
                </div>
              </logic:greaterThan>
            </div>
          </div>
        </div> --%>
      </div>
      <%if (request.getSession(false) != null){%>
       <div class="box">
        <jsp:include page="../message/messagePostReply2.jsp" flush="true">
          <jsp:param name="forumId" value="${forumThread.forum.forumId}"/>
          <jsp:param name="pmessageId" value="${forumThread.rootMessage.messageId}"/>
          <jsp:param name="parentMessageSubject" value="${forumThread.name}"/>
        </jsp:include>
       </div>
      <%}%>
    </div>
    <!-- /////////////////右边 -->
    <div id="sidebar" class="col-md-4">
      <!-- Start Widget -->
      <div class="widget wid-follow">
        <div class="content">
          <ul class="list-inline">
            <form role="form" class="form-horizontal" method="post" action="/query/threadViewQuery.shtml">
              <input type="text" placeholder="Search" value="" name="query" id="v_search" class="form-control">
            </form>
          </ul>
        </div>
      </div>
			<div class="widget">
        <div class="content">
          <div class="post wrap-vid">
              <ul>                
                <logic:notEmpty name="forumThread" property="tags">          
                  <div class="widget_tag_cloud">
                   <div class="tagcloud">
                     <logic:iterate id="threadTag" name="forumThread" property="tags"> 
                        <a href="<%=request.getContextPath()%>/tag-<bean:write name="threadTag" property="tagID"/>/" class="tag-cloud-link"><bean:write name="threadTag" property="title"/></a>
                           &nbsp;&nbsp; 
                     </logic:iterate>
                   </div>
                  </div>
                </logic:notEmpty> 
                <logic:empty name="forumThread" property="tags">    
                  <div id="tagHotList"></div>   
							      <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('https://cdn.jdon.com/query/tagHotList', function (xhr) {				
  	                                  document.getElementById("tagHotList").innerHTML = xhr.responseText;
			                     });
							      </script> -->
							     </div>
                </logic:empty>          
              </ul>
          </div>
        </div>				
			</div>
     
      <!-- Start Widget -->
      <div class="widget wid-post">
        <div class="content">
          <div class="post wrap-vid">
              <ul>          
                 <logic:notEmpty name="forumThread" property="tags">          
                  <div id="threadTagList" class="scrolldiv"></div>             
                   <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('/forum/threadTagList.shtml?threadId=<bean:write name="forumThread" property="threadId"/>', function (xhr) {				
  	                                  document.getElementById("threadTagList").innerHTML = xhr.responseText;
			                     });
							     </script> -->
							   </div>	
                 </logic:notEmpty>		
                 <logic:empty name="forumThread" property="tags">    
                  <div id="digList" class="scrolldiv"></div>   
							     <div class="lazyload" >
							     <!-- 
							     <script>
							  	  load('https://cdn.jdon.com/query/threadDigList', function (xhr) {				
  	                                  document.getElementById("digList").innerHTML = xhr.responseText;
			                     });
							      </script> -->
							     </div>
                </logic:empty>   				   
              </ul>
          </div>
        </div>
      </div>

    </div>
  </div>    
</div>



  <div id="reply" style="display:none">

    <input type="hidden" id="replySubject" name="replySubject" value="<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>"/>
  </div>

  <%@include file="../common/IncludeBottomBody.jsp"%>

	<div id="to_top" style="display:block; width:20px; height:20px;position:fixed;  bottom:50px; right:40px; border-radius:10px 10px 10px 10px;   text-decoration:none;" onClick="document.documentElement.scrollTop = document.body.scrollTop =0"><a href="#" title="返回顶部"><i class="fa fa-arrow-circle-up"></i></a></div>

  <%-- include LAB.js --%>
  <%-- <%@ include file="../account/loginAJAX.jsp" %> --%>
<input type="hidden" id="contextPath"  name="contextPath" value="<%= request.getContextPath()%>" >

<script src="https://cdn.jdon.com/common/login2.js"></script>
<script src="https://cdn.jdon.com/common/js/jquery.lazyload-any.js"></script>
<script>       
    $('.lazyload').lazyload();
</script>
<script src="https://cdn.jdon.com/common/messageList9.js"></script>
<script>        
  $(document).ready(function() {              
      $('.reblogfrom').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?threadId='+ obj.id,obj.id); 
      });
      $('.reblogto').each(function(i, obj) {        
        scrollLoadByElementId('/forum/thread.shtml?threadId='+ obj.id,obj.id); 
       });
  });      
</script>
<script type="text/javascript"> 
$(function() { 
    var elm = $('.scrolldiv'); 
    var startPos = $(elm).offset().top; 
    if (window.matchMedia('(min-width: 992px)').matches) { 
    $.event.add(window, "scroll", function() { 
        var p = $(window).scrollTop(); 
        $(elm).css('position',((p) > startPos) ? 'fixed' : 'static'); 
        $(elm).css('top',((p) > startPos) ? '20px' : ''); 
    }); 
    };
}); 
</script>

<bean:parameter name="thread" id="thread" value=""/>
<bean:define id="pagestart" name="messageListForm" property="start" />
<bean:define id="pagecount" name="messageListForm" property="count" />
<bean:define id="pageallCount" name="messageListForm" property="allCount" />
<%  
    int pageStartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
    int pageCountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
    int pageAllcountInt = ((Integer)pageContext.getAttribute("pageallCount")).intValue();
    int pageNo = (pageAllcountInt / pageCountInt);
    if(pageAllcountInt % pageCountInt !=0){ 
        pageNo = pageNo + 1;
    }    
%>
<script>
function scrollLoader(url){
  var start = "<%=pageStartInt+pageCountInt%>";
  var loading = false;
  $(window).scroll(function() {
    var hT = $('#nextPageContent').offset().top,
       hH = $('#nextPageContent').outerHeight(),
       wH = $(window).height(),
       wS = $(this).scrollTop();       
    if (wS > (hT+hH-wH) && !loading){           
         loading = true;          
         if (start <= <%=pageAllcountInt%> ){                  
           surl = (url.indexOf("?")==-1)?(url+"?"):(url+"&");           
           load(surl +'start=' + start +'&count=<%=pageCountInt%>&noheader=on', function (xhr) {
               document.getElementById("nextPageContent").innerHTML = document.getElementById("nextPageContent").innerHTML + xhr.responseText;               
               start = start/1 + <%=pageCountInt%>;                              
               loading = false;
           });          
         }   
    }
   });
}
scrollLoader('/forum/messageListBodyNoheader.shtml?thread=<bean:write name="thread"/>');   
</script>   

  </body>
  </html>