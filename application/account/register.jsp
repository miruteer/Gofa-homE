
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="java.util.*"%>
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.spi.component.block.ErrorBlockerIF"%>
<%
    ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", this.getServletContext());
    if (errorBlocker.checkCount(request.getRemoteAddr(), 3)){
        response.sendError(404);
        return;
    }
    Integer limit = (Integer)request.getSession().getServletContext().getAttribute("limit");
    if (limit != null){
        if (limit <= 0){