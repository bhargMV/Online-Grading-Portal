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
        <title>Job List</title>
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

                <h3 align="center" style="color:#398439"><b><%=session.getAttribute("c_num") + "       " + session.getAttribute("c_name")+"     "+
                session.getAttribute("p_name")+" "+
                "Question " + session.getAttribute("q_no")%></b></h3>


                <br><br>
                <table class="table table-hover">
                    <tr>
                        <th>Type of job</th>
                        <th>Evaluate</th>
                    </tr>

                    <%
                        ArrayList jobs = (ArrayList) request.getAttribute("jobs");
                        Iterator jobs_it= jobs.iterator();

                        while(jobs_it.hasNext()) {
                            String job = (String)jobs_it.next();
                            String[] jobDesc = job.split(",");
                            int stage = Integer.parseInt(jobDesc[1]);
                            int status = Integer.parseInt(jobDesc[2]);
                    %>


                    <tr>
                        <td><%if(stage == 1) { %>
                            Grading
                            <% } else if(stage == 2) { %>
                            Reconciliation
                            <% } %>
                            <% if(status == 1) {
                                out.println(" (Re-evaluation)");
                                }
                            %>
                        </td>
                        <td>
                            <form action="Sheet_Selector" method="post">
                                <% if(status == 0 || status == 1) { %>
                                <button class="btn btn-primary" name="job" value="<%=job%>">
                                    Grade
                                </button>
                                <% } else if(status == 2) { %>
                                <button class="btn btn-success" name="job" value="<%=job%>">
                                    Done
                                </button>
                                <% } else if(status == 3) { %>
                                <button class="btn btn-info" name="job" value="<%=job%>">
                                    Finalized
                                </button>
                                <% } else if(status == 4) { %>
                                <button class="btn btn-default" name="job" disabled="disabled">
                                    Waiting
                                </button>
                                <% } %>
                            </form>
                        </td>
                    </tr>
                    <% } %>

                </table>
            </div>
        </div>
    </body>
</html>

