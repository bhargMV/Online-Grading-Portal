
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<html>
    <head>
   <title>Online Grader - Login Page</title>


<script type='text/javascript' src='scripts/jquery-1.7.2.min.js'></script>

<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/buttons.css" rel="stylesheet" type="text/css">
<link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>

</head>

        <body>

            <script>
                function seturl(form, action) {
                    form.action = action;
                    return true;
                }

                $('#login_fail').hide();
            </script>

            <div id='loginbox'>
                <h1><img src='Images/iit_logo.gif' alt='IIT Logo' title='IIT Logo' /><span>&nbsp;&nbsp;Indian Institute of Technology Guwahati</span></h1>		

                <h2>Online Grader V1.0</h2>

                <form name="login_form" method="post" action="Login_Verification">
                    <fieldset>
                        <legend>Login:</legend>
                        <%

                            ArrayList err = (ArrayList) request.getAttribute("err");

                            if (err != null) {
                                Iterator it1 = err.iterator();

                                while (it1.hasNext()) {
                                    out.println("<b><font color='red'>" + it1.next() + "</font></b>");
                                }

                                err.clear();
                            }

                        %>
                        <table align="center">
                            <tr id="firstRow"><td align="center">User ID:</td><td><input type="text" name="uid" /> @iitg.ernet.in</td></tr>
                            <tr><td align="center">Password:</td><td><input type="password" name="pwd" /></td></tr>

                            <tr id="buttonRow">
                                <td> </td>
                                <td><input type="submit" class="button simple" name="login" value="   LOGIN   ">&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="reset" class="button simple" value="Clear"></td>
                            </tr>
                        </table>
                </form>
                <div id="msg1" class="msg">
                    For Authentication Purpose please use your IITG E-mail a/c Credentials. If you do not have an IITG email ID please contact Computer & Comm. Centre.
                </div>
            </div>

            <br>
            <div id="msg2" class="msg">
                For any comment/suggestion, please mail to webmaster@iitg.ernet.in.
            </div>

            <!--	<div id="msg2" class="msg">
                            If you cannnot move past this screen, it means your login has failed due to incorrect User ID or Password. Try again.
                    </div>
            -->




        </body>
</html>
