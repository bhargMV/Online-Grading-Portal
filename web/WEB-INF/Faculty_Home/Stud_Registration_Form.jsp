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
        <title>Student Registration</title>
        <link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>

        <script type='text/javascript'>

            window.history.forward(1);
            function noBack() {
                window.history.forward(1);
            }

            function updateFileSize() {
                var nBytes = 0,
                        oFiles = document.getElementById("uploadInput1").files,
                        nFiles = oFiles.length;
                for (var nFileId = 0; nFileId < nFiles; nFileId++) {
                    nBytes += oFiles[nFileId].size;
                }
                var sOutput = nBytes + " bytes";
                // optional code for multiples approximation
                for (var aMultiples = ["KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB"], nMultiple = 0, nApprox = nBytes / 1024; nApprox > 1; nApprox /= 1024, nMultiple++) {
                    sOutput = nApprox.toFixed(3) + " " + aMultiples[nMultiple] + " (" + nBytes + " bytes)";
                }
                // end of optional code
                uploaded = nFiles;
                document.getElementById("fileNum1").innerHTML = nFiles;
                document.getElementById("fileSize1").innerHTML = sOutput;
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

            <div class="col-md-10 col-md-offset-1">
                <h3 align="center" style="color:#d58512"><b><%=session.getAttribute("c_num") + " " + session.getAttribute("c_name")%></b></h3>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><B>Student Registration Form</B></h2>
                    </div>
                    <div class="panel-body">
                        <div>
                            <form enctype="multipart/form-data" onsubmit="reloadForm()" action="Registration_From_File" method="POST">
                                <h3>Registration By File</h3>
                                <p>
                                    Please upload a text file containing only one column of roll numbers
                                </p>
                                <table class="table table-hover" style="overflow: auto; max-height:  350px;">
                                    <tr>
                                        <td align="left"><B>File Chooser</B></td>                                
                                        <td align="left"><B>Register</B></td>
                                    </tr>
                                    <tr>
                                        <td align="left">
                                                <input class="btn btn-success btn-primary" id="uploadInput1" type="file" name="roll_list" onchange="updateFileSize();" required/>selected file: <span id="fileNum1">0</span>; total size: <span id="fileSize1">0</span>                                        </td >
                                        <td align="left">
                                            <button type="submit" class="btn btn-primary">Register students in file</button>
                                        </td>
                                    </tr>
                                </table>
                                
                            </form>
                        </div>
                        <br>
                        <div>
                            <% ArrayList allRollNo = (ArrayList) session.getAttribute("allRollNo");
                                Iterator allRollNo_it = allRollNo.iterator();

                                String nextReg = (String) request.getAttribute("nextReg");


                            %>

                            <% if(nextReg != null) {
                            %>

                            <a class="btn btn-primary" href="<%=request.getContextPath()%>/Course_Info_Fetcher">Registration Complete</a>
                            <% } %>

                            <form onsubmit="reloadForm()" action="Registration_Manual" method="POST">
                                
                                <h3>Roll Number wise registration</h3>
                                <label>Enter Roll No of student to register : </label>&nbsp;&nbsp;&nbsp;
                                <select name="rollNo">
                                    <option disabled="disabled" selected="selected">select roll number</option>
                                    <%
                                        while(allRollNo_it.hasNext()) {
                                            int rollNo = (Integer) allRollNo_it.next();
                                    %>
                                    <option value="<%=rollNo%>"><%=rollNo%></option>
                                    <%  } %>

                                </select>

                                   &nbsp;&nbsp;&nbsp; <button type="submit" class="btn btn-primary">Register</button>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
