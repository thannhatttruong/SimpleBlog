<%-- 
    Document   : post
    Created on : Jan 19, 2020, 1:13:05 PM
    Author     : truongtn
--%>

<%@page import="truongtn.entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
            Account acc = (Account)session.getAttribute("USER");
        %>
        <h1>Hello <%=acc.getName()%>!!</h1>
        <form action="MainController" method="POST">
            <input type="hidden" name="email" value="<%=acc.getEmail()%>"/>
            Title: <input type="text" name="txtTitle"/>
            Short description: <input type="text" name="txtShortDescription"/>
            Content: <input type="text" name="txtContent"/>
            <input type="hidden" name="author" value="<%=acc.getName()%>"/>
            <input type="submit" name="action" value="Post Article"/>
        </form>
    </body>
</html>
