<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notPresent name="query">
    <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table class="table table-striped">
    <tbody>
    <tr>
        <td align="middle">
          <html:form action="/query/searchAction.shtml" method="get"
                     styleClass="search">
                <input type="text" name="query"
                       value="<bean:write name="query"/>" id="queryId" size="40"/>
                <html:submit value="道场搜索"/>
            </html:form>            
        </td>
    </tr>
    </tbody>
</table>

<table class="tabl