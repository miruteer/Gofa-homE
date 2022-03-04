
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>上传文件</title>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-store">
    <META HTTP-EQUIV="Expires" CONTENT="0">   
    <%@ include file="../../common/headerBody.jsp" %>
    
  </head>
<body>
<html:errors />

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>


<logic:equal name="upLoadFileForm" property="authenticated" value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="upLoadFileForm" property="authenticated" value="true">


<% int tempId = 0; 
   int allSize=0; 
%>

<html:form action="/message/upload/saveUploadAction.shtml" method="post" >
        <input type="hidden" name="action"  value="delete">
        <input type="hidden" name="forward" id="forward" value="read">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#dddddd">
          <tr class="list" height="24">
            <td width="20">&nbsp;</td>
            <td width="250">文件</td>
            <td width="50">大小</td>                  
            <td width="52">&nbsp;</td>
             <td width="100"></td>     
          </tr>
    
     <logic:iterate id="upLoadFile" name="upLoadFileListForm" property="list" >
          
                <tr><td height="1" colspan="4" bgcolor="#cccccc"></td></tr>
                    <tr class="list">
            <td width="20" bgcolor="#EFECEC">
            <logic:equal  name="upLoadFile"  property="image"  value="true">
               <html:link page="/message/upload/show.jsp" paramId="id" paramName="upLoadFile" paramProperty="id" target="_blank">
                <img width="40" height="40" src="<%=request.getContextPath() %>/message/uploadShowAction.shtml?id=<bean:write name="upLoadFile"  property="id"/>"  border='0' />
                </html:link>
            </logic:equal>
            </td>
            <td width="250" bgcolor="#EFECEC"><bean:write name="upLoadFile"  property="name"/></td>
            <td width="50"  bgcolor="#EFECEC"><bean:write name="upLoadFile"  property="size"/>K</td>
            <td width="52" bgcolor="#EFECEC"> 
            <a href="<%=request.getContextPath()%>/message/upload/saveUploadAction.shtml?action=delete&id=<bean:write name="upLoadFile"  property="id"/>" target="target_upload">删除</a> </td>
            <td width="100" bgcolor="#EFECEC"><input type="button" onClick="insertText(<%=tempId+1%>)" value="插入文中"/> </td>            
          </tr>
          <bean:define id="count" name="upLoadFile"  property="size" type="java.lang.Integer"/>
          