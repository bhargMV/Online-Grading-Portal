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
        <title>Course Allotment Form</title>
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
                <h3 align="center" style="color:#398439"><b>Course allotment for<%= " " + (String)session.getAttribute("c_num") + " " + (String)session.getAttribute("c_name")%></b></h3>
                <br>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><B>Course Allotment Form</B></h2>
                    </div>
                    <div class="panel-body">
                        <div>

                            <%
                                String prevCC = request.getParameter("prevCC");

                                if(prevCC!=null) {
                            %>


                            <form action="Allotment_Remover" method="post" onsubmit="return confirm('Confirm Remove Allotment?');">
                                <label>
                                    Current Course Coordinator<%=" : " + prevCC%>
                                </label>
                                <button class="btn btn-warning">Remove Allotment</button>
                            </form>
                            <br>
                            <br>
                            <% } %>
                            <form onsubmit="reloadForm();" action="Course_Alloter" method="POST">

                                <label>
                                    Select Course Coordinator
                                </label>

                                <select name="cc" required="required">
                                    <option selected="selected" disabled="disabled">select course coordinator</option>

                                    <% Iterator allFac_it = ((ArrayList) request.getAttribute("allFac")).iterator();

                                        while(allFac_it.hasNext()) {
                                            String facInfo[] = ((String)allFac_it.next()).split(",");

                                            String facName = facInfo[0];
                                            String facId = facInfo[1];
                                    %>

                                    <option value ="<%=facId%>"><%=facName%></option>
                                    <% } %>
                                </select>
                                <br>
                                <br>
                                <button class="btn btn-primary" type="submit">Confirm Allotment</button>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

