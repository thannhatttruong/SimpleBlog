<%-- 
    Document   : registered
    Created on : Jan 18, 2020, 1:50:23 PM
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
            Account acc = (Account) session.getAttribute("MEMBER");
            if (acc != null) {
        %>
        Hello <%=acc.getName()%> !!
        
        <form action="MainController" method="POST">
            Input your email to receive code: <input type="text" name="txtEmailVerify"/>
            <input type="submit" name="action" value="Send Code"/>
        </form>
            <%
            String error = (String)request.getAttribute("ERROR");
                if(error != null){
                %>
                Email is not correct. Please try again!
                <%
                }
            %>
        Click <a href="index.jsp"> here </a>to continue!
        <%
        } else {
        %>
        Error! An error occurred. Please click<a href="index.jsp"> here </a>to comeback login
        <%
            }
        %>
    </body>
</html>
