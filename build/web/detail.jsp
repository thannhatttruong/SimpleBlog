<%-- 
    Document   : detail
    Created on : Jan 12, 2020, 10:58:32 AM
    Author     : truongtn
--%>

<%@page import="truongtn.entity.Comment"%>
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
        
        <%@include file="welcome.jsp"%>
        <%            
        Article article = (Article) session.getAttribute("ARTICLE");
            if (article != null) {
        %>
        <h2><font color="Blue"><%=article.getAccount().getName()%></font></h2>
        <h3>Title: </h3><%=article.getTitle()%>
        <h3>Short Description: </h3> <%=article.getShortDescription()%>
        <h3>Content: </h3> <%=article.getContent()%>           
        <pre><%=article.getPostingDate()%></pre>  
        <%
            if (acc != null) {
        %>
        <form action="MainController">
            Input your comment: <br/>
            <textarea name="txtComment" rows="4" cols="20">
            </textarea><br/>
            <input type="submit" name="action" value="Post Comment"/>
        </form>
        <%
        } else {
        %>
        You must <a href="MainController?action=login">login</a> to comment!!
        <%
            }
            Comment[] a = new Comment[article.getComments().size()];
            Comment[] comments = article.getComments().toArray(a);
            int length = comments.length;
            if (length > 0) {
        %>
        <h3>Comment:</h3>
        <%
            for (int i = 0; i < length; i++) {
        %>
        <text style="color: blue"><%=comments[i].getAccount().getName()%>:</text> <%=comments[i].getContent()%>
        <pre><%=comments[i].getTime()%></pre>
        <%
            }
        } else {
        %>
        <h3>No comment</h3>
        <%
                }
            }%>
            
    </body>
</html>
