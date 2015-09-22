<%--
    Document   : SheetViewer
    Created on : 21, january, 2014
    Author     : Bhargava Mourya
--%>

<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.Object"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="application/pdf; charset=UTF-8">
        <title>Blank Sheet Viewer</title>
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
                            <% 
                             String sheet = (String)request.getAttribute("sheet"); 
                             int doc_id = Integer.parseInt(request.getParameter("doc_id"));
                             int q_id = Integer.parseInt(request.getParameter("q_id"));                
                             String c_id = request.getParameter("c_id");
                             String c_name = request.getParameter("c_name");
                             String p_name = request.getParameter("p_name");                                
                             int p_id = Integer.parseInt(request.getParameter("p_id"));
                            
                            %>
                            <input type="hidden" name="doc_id" value="<%=doc_id%>">
                            <input type="hidden" name="q_id" value="<%=q_id%>">
                            <input type="hidden" name="c_id" value="<%=c_id%>">
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
                        // (String) getServletContext().getInitParameter("Directory");
                        while (it1.hasNext()) {
                            path = (String) getServletContext().getInitParameter("data")+"//"+(String) it1.next();

                            path = path + ".pdf";
                            //path="docs/11/79/1.pdf";
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
                

            </div>

        </div>




    </body>
</html>

