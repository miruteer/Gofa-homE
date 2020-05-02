<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.jdon.jivejdon.spi.component.block.ErrorBlockerIF"%>
<%@page import="java.util.UUID"%>
<%@page import="java.util.*"%>
<%@page import="com.jdon.jivejdon.presentation.form.SkinUtils"%>
<%
    ErrorBlockerIF errorBlocker = (ErrorBlockerIF)
            WebAppUtil.getComponentInstance("errorBlocker",
                    this.getServletConfig().getServletContext());
    if (errorBlocker.checkRate(request.getRemoteAddr(), 3)){
        return;
    }
    Integer limit = (Integer)request.getSession().getServletContext().getAttribute("limit");
    if (limit != null){
        if (limit <= 0){
            return;
        }
    }

    Calendar startingCalendar = Calendar.getInstance();
    startingCalendar.setTime(new Date());
    if (startingCalendar.get(Calendar.HOUR_OF_DAY) < 10 || startingCalendar.get(Calendar.HOUR_OF_DAY) > 16) {
        out.println("<p><br> 为防止垃圾广告，本段时间关闭注册，工作时间内正常开放。<br>期间可用<a href='/account/oauth/sinaCallAction.shtml'>微博账号登入</a>。");
        return;
    }

    String randstr = (String)request.getParameter("randstr");
    if(randstr == null) return;
    <%-- if (!SkinUtils.verifyQQRegisterCode((String)request.getParameter("registerCode"), randstr,request.getRemoteAddr())) {
        return;
    } --%>
    request.getSession().setAttribute("randstr", randstr);



%>
<bean:define id="title"  value=" 用户注册" />
<%@ include file="../common/IncludeTop.jsp" %>
<script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>

<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
%>

<div class="box">
    <h2 class="text-center">注册</h2>
    <div class="text-center">

        直接登录：<a href="<%=request.getContextPath()%>/account/oauth/sinaCallAction.shtml"  target="_blank" >
        <img src='/images/sina.png' width="13" height="13" alt="登录" border="0" />新浪微博
    </a>

        <%--<%--%>
        <%--Calendar startingCalendar = Calendar.getInstance();--%>
        <%--startingCalendar.setTime(new Date());--%>
        <%--if (startingCalendar.get(Calendar.HOUR_OF_DAY) < 7 || startingCalendar.get(Calendar.HOUR_OF_DAY) > 24) {--%>
        <%--out.println("<p><br> 为防止夜晚有人溜进来贴小广告，暂时关闭注册，工作时间正常开放，谢谢。<br>可用新浪微博 直接登入。");--%>
        <%--return;--%>
        <%--}--%>
        <%----%>
        <%--%>	--%>
    </div>

    <div class="box">
        <div class="row">
            <html:fo