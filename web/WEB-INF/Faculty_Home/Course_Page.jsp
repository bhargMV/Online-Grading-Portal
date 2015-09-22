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
        <title>Course Page</title>
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
        <% int totStudRegistered = (Integer) request.getAttribute("total_registrations"); %>
        <div class="page-header">
           
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <nav class="navbar navbar-inverse">
                    <div class="collapse navbar-collapse">
                        <ul class="nav navbar-nav">
                            <li id="tab_jobs"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=JOB LIST" data-toggle="tab">PENDING JOBS</a></li>
                            <li id="tab_courses" class="active"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=COURSES" data-toggle="tab">COURSES</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a  href="<%=request.getContextPath()%>/Log_Out" data-toggle="tab">Logout</a>
                            </li>
                        </ul>
                    </div>
                </nav>

                                <h3 align="center" style="color:#398439"><b><%=session.getAttribute("c_num") + " " + session.getAttribute("c_name")%></b></h3>
                <br>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            Registration details
                        </h3>
                    </div>
                    <div class="panel-body">
                        
                        <form action="Stud_List_Fetcher" method="POST">
                            <%="" + totStudRegistered + " students registered  "%>
                            <button type="submit" class="btn btn-small btn-default">Get list</button>
                        </form>
                        <br>
                        <form action="Stud_Registration_Form_Fetcher" method="POST">
                            <input type="submit" class="btn btn-primary" value="Register students">
                        </form>
                    </div>
                </div>
                <h3 align="center">Existing papers</h3>
                <table class="table table-hover">
                    <tr>
                        <th>Paper name</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <%
                    ArrayList papers = (ArrayList) request.getAttribute("papers");
                    Iterator papers_it = papers.iterator();

                    while(papers_it.hasNext()) {
                        String[] paper = ((String)papers_it.next()).split(",");
                        String p_id = paper[0];
                        String p_name = paper[1];
                    %>

                    <tr>
                        <td><%=p_name%></td>
                        <td>
                            <form action="Paper_Spec_Fetcher" method="post">
                                <input type="hidden" name="form_type" value="edit">
                                <button type="submit" class="btn btn-primary" name="p_id" value="<%=p_id%>">View paper specifications</button>
                            </form>
                        </td>
                        <td>
                            <form action="Paper_Remover" method="post">
                                <input type="hidden" name="form_type" value="edit">
                                <button type="submit" class="btn btn-warning" name="p_id" value="<%=p_id%>">Remove Paper</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </table>
                <br><br>
                <hr>
                <form action="New_Paper_Form" method="post">
                    <center><button type="submit" class="btn btn-primary">Add paper</button></center>
                </form>

            </div>
        </div>
    </body>
</html>
