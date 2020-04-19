<%-- 
    Document   : error
    Created on : Jan 19, 2020, 1:49:43 PM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1>Error page</h1>
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error != null) {
                out.println(error);
        %>
        Click <a href="detail.jsp">here</a> to comeback home page
        <%
            }
        %>

    </body>
</html>
