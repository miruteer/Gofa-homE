<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page session="false"  %>
<%@ page trimDirectiveWhitespaces="true" %>	

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta http-equiv="" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="robots" content="noindex">
<link rel="stylesheet" href="/common/jivejdon5.css" type="text/css" />

<script>
function shareto(id){
var pic="";
var url=encodeURIComponent(window.top.document.location.href);
var title=encodeURIComponent(window.top.document.title);
var _gaq = _gaq || [];

if(id=="fav"){
addBookmark(document.title);
return;
}else if(id=="qzone"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'QZone', 1]);
window.open(' http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url='+url);
return;
}else if(id=="sina"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'SinaT', 1]);
//window.open(' http://v.t.sina.com.cn/share/share.php?title='+title+'&url='+url+'&source=bookmark','_blank');
window.open(" http://service.weibo.com/share/share.php?url="+url+"&appkey=610475664&title="+title,"_blank","width=615,height=505");
return;
}else if(id=="facebook"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'facebook', 1]);
window.open(' http://facebook.com/share.php?u=' + url + '&t=' + title,'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes');
return;
}else if(id=="twitter"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'twitter', 1]);
window.open('http://twitter.com/share?url=' + url + '&text=' + title,'_blank','scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes');
return;
}else if(id=="googlebuzz"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'GoogleBuzz', 1]);
window.open(' https://plus.google.com/share?url='+url,'_blank');
return;
}else if(id=="douban"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'Douban', 1]);
var d=document,e=encodeURIComponent,s1=window.getSelection,s2=d.getSelection,s3=d.selection,s=s1?s1():s2?s2():s3?s3.createRange().text:'',r=' http://www.douban.com/recommend/?url='+e(d.location.href)+'&title='+e(d.title)+'&sel='+e(s)+'&v=1',x=function(){if(!window.open(r,'douban','toolbar=0,resizable=1,scrollbars=yes,status=1,width=450,height=330'))location.href=r+'&r=1'};
if(/Firefox/.test(navigator.userAgent)){setTimeout(x,0)}else{x()}
return;
}else if(id=="renren"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'RenRen', 1]);
window.open(' http://www.connect.renren.com/share/sharer?url='+url+'&title='+title,'_blank','resizable=no');
return;
}else if(id=="xianguo"){
_gaq.push(['_trackEvent', 'SocialShare', 'Share', 'XianGuo', 1]);
window.open(' http://xianguo.com/service/submitdigg/?link='+