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
        <title>New Question</title>
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
                <h3 align="center" style="color:#398439"><b><%=session.getAttribute("c_num") + " " + session.getAttribute("c_name") + " " + session.getAttribute("p_name")%></b></h3>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><B>Question summary</B></h2>
                    </div>
                    <div class="panel-body">
                        <div>
                            <form onsubmit="reloadForm()" action="Question_Spec_Form_Fetcher" method="POST">

                                <input type="hidden" name="form_type" value="new">
                                
                                <table>
                                    <tr>
                                        <td> <label>
                                                Enter question name tag :
                                            </label>
                                        </td>
                                        <td> <input required type="text" name="q_name" ></td>
                                    </tr>
                                    <tr>
                                        <td><label>
                                                Enter total marks :
                                            </label>
                                        </td>


                                        <td><input required type="text" name="tot_marks" ></td>
                                    </tr>
                                    <tr>
                                        <td><label>
                                                Enter total grader groups :
                                            </label>

                                        </td>
                                        <td> <input required type="text" name="tot_gps" onkeypress="return isNumericKey(event);"></td>
                                    </tr>
                                    <tr>
                                        <td><label>
                                                Enter total graders per group :
                                            </label>
                                        </td>


                                        <td><input required type="text" name="graders_per_gp" onkeypress="return isNumericKey(event);"></td>
                                    </tr>
                                    <tr>
                                        <td><label>
                                                Enter total number of part marks :
                                            </label>
                                        </td>
                                        <td><input required type="text" name="tot_ms" onkeypress="return isNumericKey(event);"></td>
                                    </tr>
                                    <tr>
                                        <td><label>
                                                Enter total number of pages alloted for answer :
                                            </label>
                                        </td>
                                        <td> <input required type="text" name="tot_sheets" onkeypress="return isNumericKey(event);"></td>
                                    </tr>

                                </table>

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
