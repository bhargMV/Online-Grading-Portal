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
        <title>New Course</title>
        <link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>

        <script type='text/javascript'>

            window.history.forward(1);
            function noBack() {
                window.history.forward(1);
            }
        </script>
    </head>
    <body>
        <div class="page-header">
            <h3></h3>
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <nav class="navbar navbar-inverse">
                    <div class="collapse navbar-collapse">


                        <ul class="nav navbar-nav">
                            <li class="active"><a href="<%=request.getContextPath()%>/Admin_Home_Preprocessor?tab=Allotment">ADD/MODIFY COURSE ALLOTMENT</a></li>
                            <li><a href="<%=request.getContextPath()%>/All_Fac_List_Fetcher">FACULTY LIST</a></li>
                            <li><a href="<%=request.getContextPath()%>/All_Stud_List_Fetcher">STUDENT LIST</a></li>
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
                
                     
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><B>New Course Form</B></h2>
                    </div>
                    <div class="panel-body">
                        <div>
                            <form onsubmit="reloadForm()" action="Course_Adder" method="POST">

                                <label>
                                    Course number : <%=" "%>
                                </label>
                                <input type="text" name="c_num" maxlength="5" style="width:100px" required="required">
                                <br><br>

                                <label>
                                    Course name : <%=" "%>
                                </label>
                                <input style="width:600px" type="text" name="c_name" maxlength="80" required="required">
                                <br><br><br><br>

                                <button class="btn btn-primary" type="submit">Add Course</button>
                                
                            </form>
                        </div>
                    </div>
                </div>
                                
                
            </div>
        </div>
    </body>
</html>
