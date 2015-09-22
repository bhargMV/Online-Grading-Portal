<%--
    Document   : Stud_Home
    Created on : Oct 2, 2013, 4:48:07 PM
    Author     : V.BhargavaMourya
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.Object"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Home</title>
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
            <h3 style="color:#398439"><center><B>Welcome <% out.println((String) session.getAttribute("name"));%></B></center></h3>
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <nav class="navbar navbar-inverse">
                    <div class="collapse navbar-collapse">

                         <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a  href="<%=request.getContextPath()%>/Log_Out" data-toggle="tab">Logout</a>
                            </li>
                        </ul>
                    </div>
                </nav>
       
                            
        <table class="table table-hover">


    <tr>
        <td valign="top" >
            <%
                //doc_id + c_id + c_name + p_name + tot_marks + tot_ques + total_got + p_id
                ArrayList Evaluated = (ArrayList) request.getAttribute("Evaluated");

                Iterator it = Evaluated.iterator();
            %>

            <br>
            <div class="col-md-2"></div>
            <div class="col-md-8">
            <h3 style="color: red; font-weight: bold"><center>Exams</center></h3>
            <table class="table table-hover" style="overflow: auto; max-height: 700px">
                
                <tr>
                    <th>Course ID</th>
                    <th>Course Name</th>
                    <th>Exam</th>
                    <th>Questions</th>
                    <th>Marks</th>
                    <th>Browse</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        String temp[] = it.next().toString().split(",");
                        String val = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + "," + temp[5] + "," + temp[6] + "," + temp[7];
                %>
                <tr>                                
                    <td><%= temp[1]%></td>
                    <td><%= temp[2]%></td>
                    <td><%= temp[3]%></td>
                    <td><%= temp[5]%></td>
                    <td><%= temp[6] + "/" + temp[4]%> </td>
                    <td>
                        <form action="Questions_Browser_Processor" method="POST">
                            <input type="hidden" name="doc_id" value="<%=temp[0]%>">
                            <input type="hidden" name="c_id" value="<%=temp[1]%>">
                            <input type="hidden" name="c_name" value="<%=temp[2]%>">
                            <input type="hidden" name="p_name" value="<%=temp[3]%>">
                            <input type="hidden" name="tot_marks" value="<%=temp[4]%>">
                            <input type="hidden" name="tot_ques" value="<%=temp[5]%>">
                            <input type="hidden" name="total_got" value="<%=temp[6]%>">
                            <input type="hidden" name="p_id" value="<%=temp[7]%>">
                           
                            <button class="btn btn-warning" type="submit" name="exam" value=<%=val%>>
                                GO
                            </button>

                        </form>
                    </td>
                </tr>

                <%

                    }

                %>
            </table>

            </div>
            <div class="col-md-2"></div>
        </td>
    </tr>


</table>
 </div>
        </div>
    </body>
</html>
