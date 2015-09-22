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
        <title>Admin Home</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
        <link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>
        <script type='text/javascript'>

            window.history.forward(1);
            function noBack() {
                window.history.forward(1);
            }
        </script>
    </head>
    <body>
        <div class="page-header" >
            <h3 style="color:#398439"><center><B>Welcome Admin</B></center></h3>
        </div>
        <div class="row">
           
            <div class="col-md-10 col-md-offset-1">
                
                <nav class="navbar navbar-inverse">
                    <div class="collapse navbar-collapse">


                        <ul class="nav navbar-nav">
                            <li class="active"><a href="<%=request.getContextPath()%>/Admin_Home_Preprocessor?tab=Allotment">COURSE ALLOTMENT</a></li>
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
            

            <% if(request.getAttribute("tab") == null || ((String)request.getAttribute("tab")).compareTo("Allotment") == 0) {
            %>
           
                <h3 align="center" style="color:#d58512"><B>Current Courses and Allotment</B></h3>

                <a href="<%=request.getContextPath()%>/New_Course_Form" class="btn btn-primary">Add New Course</a>
                <br>
                <br>
                <br>


                <% Iterator allotedCourses_it = ((ArrayList)request.getAttribute("allotedCourses")).iterator();
                   Iterator unallotedCourses_it = ((ArrayList)request.getAttribute("unallotedCourses")).iterator();
                %>

                <table class="table table-hover">
                    <tr>
                        <th>Course number</th>
                        <th>Course Name</th>
                        <th>Course Coordinator</th>
                        <th></th>
                    </tr>

                    <%
                        while(allotedCourses_it.hasNext()) {
                            String courseInfo[] = ((String)allotedCourses_it.next()).split(",");
                            String num = courseInfo[0];
                            String c_name = courseInfo[1];
                            String f_name = courseInfo[2];
                    %>

                    <tr>
                        <td><%=num%></td>
                        <td><%=c_name%></td>
                        <td><%=f_name%></td>
                        <td>
                            <form action="Course_Allotment_Modifier" method="post">
                                <input type="hidden" name="prevCC" value="<%=f_name%>">
                                <input type="hidden" name="c_name" value="<%=c_name%>">
                                <button type="submit" class="btn btn-primary" name="c_num" value="<%=num%>">Change allotment</button>
                            </form>
                        </td>
                    </tr>
                    <% }
                       while(unallotedCourses_it.hasNext()) {
                            String courseInfo[] = ((String)unallotedCourses_it.next()).split(",");
                            String num = courseInfo[0];
                            String c_name = courseInfo[1];
                    %>
                    <tr>
                        <td><%=num%></td>
                        <td><%=c_name%></td>
                        <td>
                            <form action="Course_Allotment_Modifier" method="post">
                                <input type="hidden" name="c_name" value="<%=c_name%>">
                                <button type="submit" class="btn btn-primary" name="c_num" value="<%=num%>">Add Course Coordinator</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>

                </table>
                <br>
                <a href="<%=request.getContextPath()%>/Course_Adder" class="btn btn-primary">Add New Course</a>

                
            </div>
            <% } %>
            
            
      
        </div>
    </body>
</html>

