<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" media="all" href="/common/js/calendar-win2k-cold-1.css" title="win2k-cold-1"/>
<script type="text/javascript" src="/common/js/calendar.js"></script>
<bean:parameter name="query" id="query" value=""/>

<html:form action="/message/threadViewQuery.shtml" method="post" onsubmit="return loadWLJSWithP(this, c