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
            ArrayList pending=(ArrayList) request.getAttribute("pending");
            Iterator pending_it = pending.iterator();
        %>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <%
                    if(pending.isEmpty()) {
                %>
                        
                
                        <h3 align="center" style="color:#d58512">Evaluation Complete</h3>
                        <form action="Marks_Fetcher" method="POST">
                            <button type="submit" class="btn btn-primary" id="marks">Tabulate marks</button>
                        </form>
                 <% }else { %>
                        
                        <h3 align="center" style="color:#d58512"><%="Pending Jobs : " +session.getAttribute("c_num") + " " + session.getAttribute("c_name") + " " +session.getAttribute("p_name")%></h3>

                        <table class="table table-condensed">
                            <tr>
                                <th>Grader Name</th>
                                <th>webmail</th>
                                <th>Question Number</th>
                                <th>Job status</th>
                            </tr>

                            <%  while(pending_it.hasNext()){
                                    String jobInfo[] = ((String)pending_it.next()).split(",");
                                    String name=jobInfo[0];
                                    String webmail=jobInfo[1];
                                    String qNo=jobInfo[2];
                                    int status=Integer.parseInt(jobInfo[3]);
                                    String statusMsg = new String();
                                    
                                    if(status == 0) statusMsg = "Pending";
                                    else if(status == 1) statusMsg = "Pending (Re-evaluation)";
                                    else if(status == 4) statusMsg = "Reconcilor Waiting";
                            %>

                            <tr>
                                <td><%=name%></td>
                                <td><%=webmail%></td>
                                <td><%=qNo%></td>
                                <td><%=statusMsg%></td>

                            </tr>
                            <% } %>
                        </table>
                <% } %>
            </div>
        </div>
    </body>
</html>

