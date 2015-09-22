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
        <title>Questions Browser</title>
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
                            
                            <li id="tab" class="active"><a href="<%=request.getContextPath()%>/Stud_Home_Processor?tab=GO BACK" data-toggle="tab">GO BACK</a></li>
                           
                        </ul>
                         <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a  href="<%=request.getContextPath()%>/Log_Out" data-toggle="tab">Logout</a>
                            </li>
                        </ul>
                    </div>
                </nav>
       
                            
        <table class="table table-hover">

            


    <tr>
    
        <td  valign="top">
            <div class="col-md-2"></div>
            <div class="col-md-8">
            <%
                ArrayList Evaluated = (ArrayList) request.getAttribute("Evaluated");

                Iterator it = Evaluated.iterator();
            %>
            
            
            <br>
            <h3 style="color: red; font-weight: bold"><center>Evaluated Questions</center></h3>
            
            <table class="table table-hover" style="overflow: auto; max-height:  350px;">
                <tr>                                
                    <th>Course Name</th>
                    <th>Exam</th>
                    <th>Question No.</th>
                    <th>Seen</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        String temp[] = it.next().toString().split(",");
                        //doc_id + q_id + ml_id + seen + c_id+ c_name + p_name + p_id +q_name
                        String val = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + "," + temp[5] + "," + temp[6] + "," + temp[7];
                %>
                <tr>                                
                    <td><%= temp[5]%></td>
                    <td><%= temp[6]%></td>
                    <td><%= temp[8]%></td>
                    <td>
                        <form action="Stud_Sheet_Selector" method="POST">
                            <input type="hidden" name="doc_id" value="<%=temp[0]%>">
                            <input type="hidden" name="q_id" value="<%=temp[1]%>">
                            <input type="hidden" name="ml_id" value="<%=temp[2]%>">
                            <input type="hidden" name="seen" value="<%=temp[3]%>">
                            <input type="hidden" name="c_id" value="<%=temp[4]%>">
                            <input type="hidden" name="c_name" value="<%=temp[5]%>">
                            <input type="hidden" name="p_name" value="<%=temp[6]%>">
                            <input type="hidden" name="p_id" value="<%=temp[7]%>">
                             <input type="hidden" name="q_name" value="<%=temp[8]%>">
                            <% if (temp[3].equals("0")) {%>
                            <button class="btn btn-warning" type="submit" name="sheet" value=<%=val%>>
                                NO
                            </button>
                            <%} else {%>
                            <button class="btn btn-info" type="submit" name="sheet" value=<%=val%>>
                                YES
                            </button>
                            <% } %>

                            <br>
                        </form>
                    </td>
                </tr>

                <%

                    }

                %>
            </table>
        
            
            <%
                ArrayList UnEvaluated = (ArrayList) request.getAttribute("UnEvaluated");

                it = UnEvaluated.iterator();
            %>
            
            <br>
            <h3 style="color: red; font-weight: bold"><center>Un Evaluated Questions</center></h3>
            <table class="table table-hover" style="background-color:lemonchiffon ;overflow: auto; max-height: 350px">
                <tr>                                
                    <th>Course Name</th>
                    <th>Exam</th>
                    <th>Question No.</th>
                    <th>View</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        String temp[] = it.next().toString().split(",");
                        String val = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + "," + temp[5];
                        //doc_id + q_id + c_id+ c_name + p_name + p_id + p_name
                %>
                <tr>                                
                    <td><%= temp[3]%></td>
                    <td><%= temp[4]%></td>
                    <td><%= temp[6]%></td>
                    <td>
                        <form action="Stud_Sheet_Selector" method="POST">
                            <input type="hidden" name="doc_id" value="<%=temp[0]%>">
                            <input type="hidden" name="q_id" value="<%=temp[1]%>">
                            <input type="hidden" name="c_id" value="<%=temp[2]%>">
                            <input type="hidden" name="c_name" value="<%=temp[3]%>">
                            <input type="hidden" name="p_name" value="<%=temp[4]%>">                           
                            <input type="hidden" name="p_id" value="<%=temp[5]%>">
                             <input type="hidden" name="q_name" value="<%=temp[6]%>">
                             
                            <button class="btn btn-info" type="submit" name="unevsheet" value=<%=val%>>
                                GO
                            </button>
                           
                            <br>
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
</body>
</html>
