<%--
    Document   : Evaluation_Status
    Created on : 2 Apr, 2014, 11:34:23 AM
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
        <title>JSP Page</title>
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
            <h1><center><B>Welcome <% out.println((String) session.getAttribute("name"));%></B></center></h1>
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
        </div>
        <%
            ArrayList marks=(ArrayList) request.getAttribute("allMarks");
            Iterator marks_it = marks.iterator();
        %>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <h3 align="center" style="color:#d58512"><%="Final Marks : " + session.getAttribute("c_num") + " " + session.getAttribute("c_name") + " " +session.getAttribute("p_name")%></h3>

                        


                        <table class="table table-condensed">
                            <tr>
                                <th>Roll Number</th>
                                <th>Name</th>
                                <th>Marks Obtained</th>

                            </tr>

                            <%  while(marks_it.hasNext()){
                                    String marksInfo[] = ((String)marks_it.next()).split(",");
                                    int rollNo=Integer.parseInt(marksInfo[0]);
                                    String name=marksInfo[1];

                                    float studMarks=Float.parseFloat(marksInfo[2]);

                            %>

                            <tr>
                                <td><%=rollNo%></td>
                                <td><%=name%></td>
                                <td><%=studMarks%></td>


                            </tr>
                            <% } %>
                        </table>
          
            </div>
        </div>
    </body>
</html>

