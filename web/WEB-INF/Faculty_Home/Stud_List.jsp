<%-- 
    Document   : Stud_List
    Created on : 29 Mar, 2014, 2:59:57 PM
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
        <title>Student List</title>
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
            <% Iterator allReg_it = ((ArrayList)request.getAttribute("allReg")).iterator();
            %>
            <div class="col-md-10 col-md-offset-1">
                <h3 align="center" style="color:#398439"><b><%=session.getAttribute("c_num") + " " + session.getAttribute("c_name")%></b></h3>
                <h3 align="center" style="color:#d58512">All Registered Students</h3>
                <br>

                <form action="Registration_Remover" method="post">
                    <table class="table-condensed">
                        <tr>
                            <th>Roll Number</th>
                            <th>Name</th>
                            <th>Deregister</th>
                        </tr>

                        <%
                            while(allReg_it.hasNext()) {
                                String studInfo[] = ((String)allReg_it.next()).split(",");
                                String rollNo = studInfo[0];
                                String name = studInfo[1];
                        %>

                        <tr>
                            <td><%=rollNo%></td>
                            <td><%=name%></td>
                            <td>
                                <input type="checkbox" name="studToDeregister" value="<%=rollNo%>">
                            </td>
                        </tr>
                        <% } %>
                    </table>
                    <br>
                    <button type="submit" class="btn btn-primary">Confirm Deregistration</button>
                </form>
            </div>
        </div>
    </body>
</html>

