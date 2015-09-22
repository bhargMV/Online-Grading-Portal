<%--
    Document   : Fac_Home
    Created on : Oct 2, 2013, 4:48:07 PM
    Author     : V.BhargavaMourya
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
        <title>Faculty Home</title>
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
            <h3 style="color:#398439 "><center><B>Welcome <% out.println((String) session.getAttribute("name"));%></B></center></h3>
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <nav class="navbar navbar-inverse">
                    <div class="collapse navbar-collapse">


                        <ul class="nav navbar-nav">
                            <%  if(request.getAttribute("click")!=null) {
                                if(((String)request.getAttribute("click")).compareTo("JOB LIST") == 0) {
                                %>
                            <li id="tab_jobs" class="active"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=JOB LIST" data-toggle="tab">PENDING JOBS</a></li>
                            <li id="tab_courses"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=COURSES" data-toggle="tab">COURSES</a></li>
                            <% } else if(((String)request.getAttribute("click")).compareTo("COURSES") == 0) { %>
                            <li id="tab_jobs"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=JOB LIST" data-toggle="tab">PENDING JOBS</a></li>
                            <li id="tab_courses" class="active"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=COURSES" data-toggle="tab">COURSES</a></li>
                            <%   }
                                }
                            else { %>
                            <li id="tab_jobs"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=JOB LIST" data-toggle="tab">PENDING JOBS</a></li>
                            <li id="tab_courses"><a href="<%=request.getContextPath()%>/Fac_Home_Processor?tab=COURSES" data-toggle="tab">COURSES</a></li>
                            <% } %>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a  href="<%=request.getContextPath()%>/Log_Out" data-toggle="tab">Logout</a>
                            </li>
                        </ul>
                    </div>
                </nav>
                            
                
                          
                <%  if(request.getAttribute("click")!=null) {
                if(((String)request.getAttribute("click")).compareTo("JOB LIST") == 0) {
                %>
                <br><br><br>
                <table class="table table-hover">
                    <tr>
                        <th>Course number</th>
                        <th>Course name</th>
                        <th>Paper name</th>
                        <th></th>
                    </tr>

                    <%
                        ArrayList jobs = (ArrayList) request.getAttribute("jobs");
                        Iterator jobs_it= jobs.iterator();

                        while(jobs_it.hasNext()) {

                            String[] jobDesc = ((String)jobs_it.next()).split(",");
                            String p_id = jobDesc[0];
                            String c_num = jobDesc[1];
                            String c_name = jobDesc[2];
                            String p_name = jobDesc[3];
                            int count = Integer.parseInt(jobDesc[4]);

                            String val = p_id + "," + c_num  + "," + c_name + "," + p_name;
                    %>
                    <tr>
                        <td><%=c_num%></td>
                        <td><%=c_name%></td>
                        <td><%=p_name%></td>
                        <td>
                            <form action="Question_Wise_JobList_Fetcher" method="POST">
                                <button class="btn btn-primary" type="submit" name="jobGroup" value="<%=val%>">
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
                    <%
                    }
                }
            %>

            <%  if(request.getAttribute("click")!=null) {
                if(((String)request.getAttribute("click")).compareTo("COURSES") == 0) {
            %>
            <br><br><br>
            <table class="table table-hover">
                <tr>
                    <th>Course number</th>
                    <th>Course name</th>
                    
                    <th></th>
                </tr>
                <%
                ArrayList courses = (ArrayList) request.getAttribute("courses");
                Iterator courses_it = courses.iterator();

                while(courses_it.hasNext()) {
                    String[] courseInfo = ((String)courses_it.next()).split(",");
                    String val = courseInfo[0] + "," + courseInfo[1];
                %>
                <tr>
                    <td><%=courseInfo[0]%></td>
                    <td><%=courseInfo[1]%></td>
                    
                    <td>
                        <form action="Course_Info_Fetcher" method="POST">
                            <button class="btn btn-primary" type="submit" name="courseinfo" value="<%=val%>">Visit course page</button>                            
                        </form>
                    </td>
                </tr>
                <% } %>

            </table>
            <%
             }
                }
            %>
               

            </div>
        </div>
    </body>
</html>
