<%-- 
    Document   : welcome
    Created on : Jan 8, 2020, 4:37:58 AM
    Author     : truongtn
--%>

<%@page import="truongtn.entity.Article"%>
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
            Account acc = (Account) session.getAttribute("USER");
            if (acc != null) {
        %>
        <h1>Welcome <%=acc.getName()%></h1> 
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="LogOut"/>
        </form>
        Click <a href="post.jsp"> here </a>to post a new article<br/>
        <%
            if (acc.getRole().equals("member")) {
                session.setAttribute("MEMBER", acc);
        %>
        Click <a href="register_user.jsp">here</a> to register user
        <%
            }
        } else {
        %>
        <h1>Your are welcome</h1>
        Click <a href="MainController?action=login">here</a> to login</br>
        Click <a href="MainController?action=loginPost"> here </a> to post a new article
        <%
            }
        %>
        <form action="MainController">
            Search: <input type="text" name="txtSearch"/>
            <input type="submit" name="action" value="Search"/>
        </form>
        <%
            Article[] articleArr = (Article[]) request.getAttribute("LIST");
            if (articleArr != null) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Short Description</th>
                    <th>Author</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (int i = 0; i < articleArr.length; i++) {
                %>
            <form action="MainController" method="POST">
                <tr>
                    <td><a href="MainController?action=read&txtArticleId=<%=articleArr[i].getId()%>"><%=articleArr[i].getTitle()%></a></td>
                    <td><%=articleArr[i].getShortDescription()%></td>
                    <td><%=articleArr[i].getAccount().getName()%></td>
                    <td><%=articleArr[i].getPostingDate()%></td>
                </tr>
            </form>
            <%
                }
            %>
        </tbody>
    </table>

    <%
    } else {
    %>
    Not found
    <%
        }
    %>

</body>
</html>
