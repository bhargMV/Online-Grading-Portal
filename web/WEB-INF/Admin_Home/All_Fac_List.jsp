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
        <title>Faculty List</title>
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
       <div class="page-header" >
            <h3></h3>
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <nav class="navbar navbar-inverse">
                    <div class="collapse navbar-collapse">


                        <ul class="nav navbar-nav">
                            <li><a href="<%=request.getContextPath()%>/Admin_Home_Preprocessor?tab=Allotment">ADD/MODIFY COURSE ALLOTMENT</a></li>
                            <li class="active"><a href="<%=request.getContextPath()%>/All_Fac_List_Fetcher">FACULTY LIST</a></li>
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
                <h3 style="color:#d58512" align="center"><b>All Faculty List</b></h3>
                
                <a  class="btn btn-primary" href="<%=request.getContextPath()%>/Fac_Adding_Form">Add Faculty Member</a>
                <br><br>
                <%
                    Iterator allFacIt = ((ArrayList)request.getAttribute("allFac")).iterator();
                %>
                <form action="Fac_Remover" method="post" onsubmit="return confirm('Confirm Removal?');">
                    <table class="table-condensed">
                        <tr>
                            <th>Name</th>
                            <th>Webmail</th>
                            <th>Remove Faculty</th>
                        </tr>

                        <%
                            int ctr =1;
                            while(allFacIt.hasNext()) {
                                String facInfo[] = ((String)allFacIt.next()).split(",");
                                String name = facInfo[0];
                                String id = facInfo[1];
                                String webmail = facInfo[2];
                        %>

                        <tr>
                            <td><%=name%></td>
                            <td><%=webmail%></td>
                            
                            <td>
                                <input type="checkbox" value="<%=id%>" name="facToRemove">
                            </td>
                        </tr>
                        <%
                            ctr++;
                            } %>
                    </table>
                    <br>
                    <button type="submit" class="btn btn-primary">Confirm Removal</button>
                </form>

            </div>



        </div>
    </body>
</html>