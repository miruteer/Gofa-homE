<%@ page contentType="text/html; charset=UTF-8"%>
<script language="JavaScript" type="text/javascript">
	<!-- 
		function sendAction(url){ 	 		
   			$("smsg_form").action= url;
 			$("smsg_form").submit();
  		}
  
		function delAction(url)
		{
			 if (confirm( 'Delete this message ! \n\nAre you sure ? '))
        		{
              		$("smsg_form").action=url;
              		$("smsg_form").submit();
             		return true;
         	}else{
              return false;
        	}
		}
		function checkPost(theForm) {
    		 	var check = false;
     			 if (($("smsg_form").messageTo.value  != "")
          			&& ($("smsg_form").messageTitle.value  != "")){
          			check = true;
          			return check;
     			 }else{
         			 alert("请输入发送对象和标题！");
          			return check;
      			}
		}
	//-->
	</script>
<table cellpadding="2" cellspacing="0" border="0">
		<tr>
			<td align="right">发往 <br>
			</td>
			<td><html:text property="messageTo" size="12" maxlength="12"
				tabindex="99" styleId="messageTo"></html:text>(用户名，也可在贴中用“@用户名 (空格)”通知它) <br>
