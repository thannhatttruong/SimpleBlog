<%-- 
    Document   : register
    Created on : Jan 8, 2020, 7:12:44 AM
    Author     : truongtn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            var ck_name = /^[A-Za-z0-9/t]{4,30}$/;
            var ck_email = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            function validate(form) {
                var name = form.txtName.value;
                var email = form.txtEmail.value;
                var password = form.txtPassword.value;
                var confirm = form.txtConfirm.value;
                if (name == "") {
                    alert("Name is required!");
                    return false;
                }
                if (!ck_email.test(email)) {
                    alert("Email format isn't correst!");
                    return false;
                }
                if (!ck_name.test(password)) {
                    alert("Password is required, no character, length from 4 to 30!");
                    return false;
                }

                if (password !== confirm) {
                    alert("Confirm password error!");
//                    form.getElementById("confirm").innerHTML = "Confirm password error";
                    return false;
                }
                return true;
            }

//            function reportErrors(errors){
//                var msg = "Please try again";
//                for(var i = 0; i < errors.length; i++){
//                    var numError = i + 1;
//                    msg += "\n" + numError + "." + errors[i];
//                }
//                alert(msg);
//            }
        </script>
    </head>
    <body>
        
        <h1>Register member page</h1>
        <%
            String error = (String) request.getParameter("ERROR");
            if (error != null) {
        %>
            <%=error%>. Please try again!
        <%
            }
        %>
        <form action="MainController" method="POST" onsubmit="return validate(this);" name="form">
            Name: <input type="text" name="txtName" id="name"/><br/>
            Role: <select name="txtRole">
                <option value="user">User</option>
                <option value="member" selected>Member</option>
            </select><br/>
            Email <input type="text" name="txtEmail"/><br/>
            Password: <input type="password" name="txtPassword"/><br/>
            Confirm password: <input type="password" name="txtConfirm" id="confirm"/><br/>
            <input type="submit" name="action" value="Register"/>
            <input type="reset" value="reset"/>
        </form>  
    </body>
</html>
