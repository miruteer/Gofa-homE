
<%@ page session="false"  %>
<%
response.setContentType("text/css");
%>
.overlay_alert {
	background-color: #85BBEF;
	filter:alpha(opacity=60);
	-moz-opacity: 0.6;
	opacity: 0.6;
}

.alert_nw {
	width: 5px;
	height: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/top_left.gif) no-repeat bottom left;			
}

.alert_n {
	height: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/top.gif) repeat-x bottom left;			
}

.alert_ne {
	width: 5px;
	height: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/top_right.gif) no-repeat bottom left			
}

.alert_e {
	width: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/right.gif) repeat-y 0 0;			
}

.alert_w {
	width: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/left.gif) repeat-y 0 0;			
}

.alert_sw {
	width: 5px;
	height: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/bottom_left.gif) no-repeat 0 0;			
}

.alert_s {
	height: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/bottom.gif) repeat-x 0 0;			
}

.alert_se, .alert_sizer {
	width: 5px;
	height: 5px;
	background: transparent url(<%=request.getContextPath()%>/common/js/styles/alert/bottom_right.gif) no-repeat 0 0;			
}

.alert_close {
	width:0px;
	height:0px;
	display:none;
}

.alert_minimize {
	width:0px;
	height:0px;
	display:none;
}

.alert_maximize {
	width:0px;
	height:0px;
	display:none;
}

.alert_title {
	float:left;
	height:1px;
	width:100%;
}

.alert_content {
	overflow:visible;
	color: #000;
	font-family: Tahoma, Arial, sans-serif;
  font: 12px arial;
	background: #FFF;
}

/* For alert/confirm dialog */
.alert_window {
	background: #FFF;
	padding:20px;
	margin-left:auto;
	margin-right:auto;
	width:400px;
}

.alert_message {
  font: 12px arial;
	width:100%;
	color:#F00;
	padding-bottom:10px;
}

.alert_buttons {
	text-align:center;
	width:100%;
}

.alert_buttons input {
	width:20%;
	margin:10px;
}

.alert_progress {
	float:left;
	margin:auto;
	text-align:center;
	width:100%;
	height:16px;
	background: #FFF url('<%=request.getContextPath()%>/common/js/styles/alert/progress.gif') no-repeat center center
}

