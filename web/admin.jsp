<%-- 
    Document   : admin
    Created on : Jan 21, 2020, 4:58:18 PM
    Author     : truongtn
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="truongtn.entity.Comment"%>
<%@page import="truongtn.entity.Article"%>
<%@page import="truongtn.entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
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
        <%
            }
        %>
        <form action="MainController">
            Search: <input type="text" name="txtSearch"/>
            <input type="submit" name="action" value="Search"/>
        </form>
        <%
            Article[] articleArr = (Article[])request.getAttribute("LIST");
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Author</th>
                    <th>Title</th>
                    <th>Short Description</th>
                    <th>Content</th>
                    <th>Posting Date</th>
                    <th>Status</th>
                    <th>Delete</th>
                    <th>Approval</th>
                </tr>
            </thead>
            <tbody>
                <form action="MainController">
                <%
                    ArrayList<Article> articleDelete = new ArrayList<>();
                    for (int i = 0; i < articleArr.length; i++) {
                %>
                <tr>
                    <td><%=i%></td>
                    <td><%=articleArr[i].getAccount().getName()%></td>
                    <td><%=articleArr[i].getTitle()%></td>
                    <td><%=articleArr[i].getShortDescription()%></td>
                    <td><%=articleArr[i].getContent()%></td>
                    <td><%=articleArr[i].getPostingDate()%></td>
                    <td><%=articleArr[i].getStatus()%></td>
                    <td><input type="checkbox" name="chkDelete" value="<%=articleArr[i].getId()%>"></td>         
                    <td><input type="checkbox" name="chkApproval" value="<%=articleArr[i].getId()%>"/></td>
                </tr>
                <%
                    }
                %>
                <tr>
                    <td colspan="7"></td>
                    <td colspan="2"><input type="submit" name="action" value="Update Article"/></td>
                </tr>
            </form>
            </tbody>
        </table>

    </body>
</html>
