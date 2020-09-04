<%-- 
    Document   : active
    Created on : Jan 12, 2020, 2:03:41 PM
    Author     : truongtn
--%>

<%@page import="truongtn.entity.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            function validate(form) {
                var inputCode = form.txtCode.value;
                var verifyCode = form.txtVerify.value;
                if(!(inputCode === verifyCode)){
                    alert("Verify code is not correct. Please try again");
                    return false;
                }
            }
        </script>
    </head>
    <body>
        
        <%
            Account acc = (Account) session.getAttribute("MEMBER");
            if (acc != null) {
        %>
        Hello <%= acc.getName()%> !!
        <form action="MainController" method="POST" onsubmit="return validate(this);" name="form">
            Input your verify code: <input type="text" name="txtCode"/>
            <input type="hidden" name="txtVerify" value="<%=session.getAttribute("VERIFY")%>"/>
            <input type="submit" name="action" value="Verify"/>
        </form>
        <%
            }
        %>
    </body>
</html>
