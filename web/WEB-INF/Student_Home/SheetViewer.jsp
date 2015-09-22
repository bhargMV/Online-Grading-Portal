<%--
    Document   : SheetViewer
    Created on : 21, january, 2014
    Author     : Bhargava Mourya
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
        <title>Sheet Viewer</title>
        <link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
        <title>Checking Interface</title>
    </head>
    <body><% String caption = (String) request.getAttribute("caption");%>
        <div class="row">
            <table align="center" >
                <tr style="width: 100%; ">
                    <td>
                        <h3 align="center"><%= caption%></h3>
                    </td>
                    <td style=" position: absolute; top:3%; right: 3.5%; float: right;">
                        <form action="Stud_Home_Processor" method="POST">
                            <% String sheet = (String)request.getAttribute("sheet"); 
                                int doc_id = Integer.parseInt(request.getParameter("doc_id"));
                             int q_id = Integer.parseInt(request.getParameter("q_id"));
                             int ml_id = Integer.parseInt(request.getParameter("ml_id"));
                             int seen = Integer.parseInt(request.getParameter("seen"));
                             String c_id = request.getParameter("c_id");
                             String c_name = request.getParameter("c_name");
                             String p_name = request.getParameter("p_name");                                
                             int p_id = Integer.parseInt(request.getParameter("p_id"));
                            
                            %>                            
                            <input type="hidden" name="doc_id" value="<%=doc_id%>">
                            <input type="hidden" name="q_id" value="<%=q_id%>">
                            <input type="hidden" name="c_id" value="<%=c_id%>">
                            <input type="hidden" name="ml_id" value="<%=ml_id%>">
                            <input type="hidden" name="seen" value="<%=seen%>">
                            <input type="hidden" name="c_name" value="<%=c_name%>">
                            <input type="hidden" name="p_name" value="<%=p_name%>">                           
                            <input type="hidden" name="p_id" value="<%=p_id%>">
                            <input class="btn btn-primary" type="submit" name="tab" value="BACK">                            
                            <input class="btn btn-primary" type="submit" name="tab" value="LOG OUT">

                        </form>
                    </td>
                </tr>
            </table>
        </div>
        <div class="col-md-9" style="max-height: 600px; overflow: auto "  >
            <table  border='1'>
                <%
                    ArrayList sheets = (ArrayList) request.getAttribute("sheets");

                    Iterator it1 = sheets.iterator();
                    if (it1 != null) {

                        String path;
                        while (it1.hasNext()) {
                            path = (String) getServletContext().getInitParameter("data")+"//"+(String) it1.next();

                            path =  path + ".pdf";
                            //out.println(path);
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

            <div class="col-md-12" 
                <br/>
                <h4>Marking Awarded</h4>

                <%
                    ArrayList awarded = (ArrayList) request.getAttribute("awarded");
                    ArrayList partmarks = (ArrayList) request.getAttribute("partmarks");

                    Iterator it2 = awarded.iterator();
                    Iterator it3 = partmarks.iterator();

                    while (it2.hasNext()) { %>
                <table  border='1'>
                    <tr>
                        <td>

                            <h4><% out.println(it2.next() + "  /  " + it3.next()); %></h4>

                        </td>
                    </tr>




                </table>
                <%
                    }

                %>

                <br/>
               
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
                        <td style="border-color: white"><p style="color:red; font-weight: bolder">YOU:</p></td>

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
                    <form action="Stud_Home_Processor" method="post">
                        <h4>Comments:</h4> <textarea  class="form-control" name="comments" style="resize: none; max-height:300px; min-height:150px;" > </textarea> <br/>
                        <input type="checkbox" name="review" value="yes"><b>Mark for review?</b>
                        <input type="hidden" name="doc_id" value="<%=doc_id%>">
                        <input type="hidden" name="q_id" value="<%=q_id%>">
                        <input type="hidden" name="c_id" value="<%=c_id%>">
                        <input type="hidden" name="ml_id" value="<%=ml_id%>">
                        <input type="hidden" name="seen" value="<%=seen%>">
                        <input type="hidden" name="c_name" value="<%=c_name%>">
                        <input type="hidden" name="p_name" value="<%=p_name%>">                           
                        <input type="hidden" name="p_id" value="<%=p_id%>">
                        <input type="hidden" name="sequence" value="<%=comments[3].get(0)%>" /><br/>
                        <input class="btn btn-primary" type="submit"  name="tab" value="SUBMIT">
                    </form>

            </div>

        </div>




    </body>
</html>

