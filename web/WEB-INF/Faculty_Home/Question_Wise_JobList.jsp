<%-- 
    Document   : Question_Wise_Job_List
    Created on : 11 Feb, 2014, 4:31:56 PM
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
        <title>Question Wise Jobs</title>
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
                            <li id="tab_jobs" class="active"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=JOB LIST" data-toggle="tab">PENDING JOBS</a></li>
                            <li id="tab_courses"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=COURSES" data-toggle="tab">COURSES</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a  href="<%=request.getContextPath()%>/Log_Out" data-toggle="tab">Logout</a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <h3 align="center" style="color: #398439"><b><%=session.getAttribute("c_num") + " " + session.getAttribute("c_name")%>
                        <%=session.getAttribute("p_name")%></b></h3>


                <br><br>
                <table class="table table-hover">
                    <tr>
                        <th>Question number</th>
                        <th></th>
                    </tr>

                    <%
                        ArrayList jobs = (ArrayList) request.getAttribute("jobs");
                        Iterator jobs_it= jobs.iterator();

                        while(jobs_it.hasNext()) {

                            String[] jobDesc = ((String)jobs_it.next()).split(",");
                            String q_id = jobDesc[0];
                            int count = Integer.parseInt(jobDesc[1]);
                            String q_name = jobDesc[2];
                    %>
                    <tr>
                        <td><%=q_name%></td>
                        <td>
                            <form action="Doc_Wise_JobList_Fetcher" method="post">
                                <button class="btn btn-primary" name="q_id" value="<%=q_id%>">
                                    Get job list
                                    <% if(count>0){%>
                                    <span class="label label-info">
                                        <%="" + count%>
                                    </span>
                                    <% } %>
                                </button>
                            </form>
                        </td>
                    </tr>
                    <% } %>

                </table>
            </div>
        </div>
    </body>
</html>

