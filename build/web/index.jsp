<%-- 
    Document   : login
    Created on : Jan 8, 2020, 4:37:32 AM
    Author     : truongtn
--%>

<%@page import="truongtn.utils.APIWrapper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1>Hello World!</h1>
        <%
            String action = (String)request.getAttribute("ACTION");
        %>
        <form action="MainController" method="POST">
            Username: <input type="text" name="txtUsername"/><br/>
            Password: <input type="password" name="txtPassword"/><br/>
            <input type="hidden" name="ACTION" value="<%=action%>"/>
            <input type="submit" name="action" value="Login"/>
        </form>
        
        <%
            String error = (String)request.getAttribute("ERROR");
            if(error != null){
            %>
            <font color="red"><%=error%></font><br/>
            <%
            }
        %>
        <a href="<%=APIWrapper.getDialogLink()%>">Login via Facebook</a><br/>
        Click <a href="MainController?action=register">here</a> to register account<br/>
        <a href="MainController?action=continue">Continue to without authenticate</a>
        
    </body>
</htmlz
