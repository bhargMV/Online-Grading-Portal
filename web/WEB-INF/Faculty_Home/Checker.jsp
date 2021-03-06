<%--
    Document   : Checker
    Created on : 9 Oct, 2013, 6:42:06 AM
    Author     : Tanay
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.Object"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Grading Interface</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
        <link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>
        <title>Checking Interface</title>
    </head>
    <body>
        <div class="row">
            <table align="center" >
                <tr style="width: 100%; ">
                    <td align="center">
                        <h1 align="center">Checking Interface</h1>
                    </td>
                    <td style=" position: absolute; top:3%; right: 3.5%; float: right;">
                        <form action="Fac_Home_Processor" method="POST">
                            <input class="btn btn-primary" type="submit" name="tab" value="GO BACK">                            
                            <input class="btn btn-primary" type="submit" name="tab" value="LOG OUT">

                        </form>
                    </td>
                </tr>
                <tr style="width: 100%; ">
                    <td>
                        <h2 align="center"><%= (String) session.getAttribute("c_num") + " " + (String) session.getAttribute("c_name") + " " + (String) session.getAttribute("p_name") + " queston " + (String) session.getAttribute("q_no")%></h2>
                    </td>
                </tr>
            </table>
        </div>
        <div class="col-md-9" style="max-height: 600px; overflow: auto " >
            <table border='1'>
                <%
                    ArrayList sheets = (ArrayList) request.getAttribute("sheets");


                    Iterator it1 = sheets.iterator();
                    if (it1 != null) {
                        //"file:///C:/Users/Tanay/OnlineGrader/docs/" + it1.next() + ".jpg"
                        String path;
                        while (it1.hasNext()) {
                            path = (String) getServletContext().getInitParameter("data")+"//"+(String) it1.next();

                            path =  path + ".pdf";
%>

                <tr>
                    <td>
                          <iframe src="<%=path %>" height="1300" width="1024" allowfullscreen="" frameborder="0" ></iframe> 
                    </td>
                </tr>
                <%

                        }
                    }%>

            </table>
        </div>

        <div class="col-md-3">
            
            <div class="col-md-8 col-md-offset-2" >
  
                <form action="Get_Prev_Job" method="POST">
                    <button class="btn btn-primary" type="submit">Previous answer sheet</button>
                </form>
                <br>
                <h4>Marking Scheme</h4>
                <form action="Fac_Assign_Marks" method="POST">
                    <ul><%  ArrayList ms = (ArrayList) request.getAttribute("markingScheme");
                        int job_id = (Integer) session.getAttribute("job_id");

                        int status = (Integer) session.getAttribute("status");
                        ArrayList checked = (ArrayList) request.getAttribute("checked");

                        Iterator it2 = ms.iterator();
                        int ctr = 1;
                        String val;
                        if (it2 != null) {
                            Object m;

                            while (it2.hasNext()) {
                                m = it2.next();
                                val = "" + ctr + "," + m;
                        %>

                        <li>
                            <% if (status == 1 || status == 2) {
                                    if ((Boolean) checked.get(ctr - 1)) {%>
                            <input type="checkbox" name="marks" value="<%= val%>" checked="checked"><%= m%>
                            <% } else {%>
                            <input type="checkbox" name="marks" value="<%= val%>"><%= m%>

                            <%  }
                            } else if (status == 3) {
                                    if ((Boolean) checked.get(ctr - 1)) {%>
                            <input type="checkbox" name="marks" value="<%= val%>" disabled="disabled" checked="checked"><%= m%>
                            <% } else {%>
                            <input type="checkbox" name="marks" value="<%= val%>" disabled="disabled"><%= m%>

                            <%  }
                            } else {%>
                            <input type="checkbox" name="marks" value="<%= val%>"><%= m%>
                            <% }%>

                        </li>
                        <%
                                    ctr++;
                                }
                                session.setAttribute("ms", ctr - 1);
                            }
                        %>
                    </ul>
                    
                    <h4>Discussions :</h4>
                <%                   
                    ArrayList[] comments = (ArrayList[]) request.getAttribute("comments");

                    Iterator it4 = comments[0].iterator();
                    Iterator it5 = comments[1].iterator();
                    Iterator it6 = comments[2].iterator();
                    //comment , type , by 
                    //comments[3] has a sequence number of recent comment
                    
                    //request.setAttribute("sequence",comments[3].get(0));
                   
                    //request.setAttribute("sheet",sheet);
                    //out.println("STR" + sheet);

                %>
                <table style="height:150px; overflow:scroll; display:block; border-color: #31b0d5;" border="1"> 
                    <% while (it4.hasNext()) {
                            int type = (Integer) it5.next();
                    %>
                    <tr style="border-color: white">
                        <% if (type == 3) {%>
                        <td style="border-color: white"><p style="color:red; font-weight: bolder">STUDENT:</p></td>

                        <% } else {
                        %> 
                        <td style="border-color: white"><p style="color:red; font-weight: bolder">GRADER:</p></td>

                        <% } %>
                    </tr>
                    <tr style="border-color: white">
                        <td style="border-color: white"><% out.println(it4.next() + "<hr>");
                    }%> </td>

                    </tr>
                </table>
                    
                    <br/>
                    
                    <% if (status == 3) { %>
                    <h3><span class="label label-info">Marks finalized</span></h3>
                    
                    <% } else { %>
                    
                    <h4>Comments:</h4> <textarea  class="form-control" name="comments" style="resize: none; max-height:300px; min-height:150px;" > </textarea> <br/>
                    <button class="btn btn-primary" type="submit">Submit marks</button>
                    <input type="hidden" name="sequence" value="<%=comments[3].get(0)%>" /><br/>
                    <% } %>
                  
                </form>
                <br>
                <form action="Get_Next_Job" method="POST">
                    <button class="btn btn-primary" type="submit">Next answer sheet</button>
                </form>
            </div>
        </div>
    </body>
</html>

