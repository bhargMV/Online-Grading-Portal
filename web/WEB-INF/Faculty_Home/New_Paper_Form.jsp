<%--
    Document   : Course_Page
    Created on : 21 Jan, 2014, 12:40:40 PM
    Author     : Tanay
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.Object"%>
<%@page import="Common.CustomList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Paper</title>
        <link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>

        <script type='text/javascript'>

            window.history.forward(1);
            function noBack() {
                window.history.forward(1);
            }
            function isNumericKey(e)
            {
                var charInp = window.event.keyCode; 
                 if (charInp > 31 && (charInp < 48 || charInp > 57)) 

                 {
                        return false;
                 }
                  return true;

            }
        </script>
    </head>
    <body>
        <div class="page-header">
           
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <nav class="navbar navbar-inverse">
                    <div class="collapse navbar-collapse">


                        <ul class="nav navbar-nav">
                            <li><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=JOB LIST">PENDING JOBS</a></li>
                            <li class="active"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=<%="COURSES"%>">COURSES</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a  href="<%=request.getContextPath()%>/Log_Out" data-toggle="tab">Logout</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>

            <div class="col-md-10 col-md-offset-1">
                <h3 align="center" style="color:#398439"><b><%=session.getAttribute("c_num") + " " + session.getAttribute("c_name") %></b></h3>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><B>New Paper Form</B></h2>
                    </div>
                    <div class="panel-body">
                        <div>
                            <form onsubmit="reloadForm()" action="Paper_Adder" method="POST">



                                <label>
                                    Enter paper name
                                </label>

                                <input required type="text" name="p_name" maxlength="30">
                                <br><br>


                                
                                <button class="btn btn-primary" type="submit">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
