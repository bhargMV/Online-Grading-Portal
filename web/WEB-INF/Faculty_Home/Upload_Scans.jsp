<%-- 
    Document   : Upload_Scans
    Created on : Mar 20, 2014, 12:10:00 PM
    Author     : venishetty
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
        <title>Upload Scans</title>
        <link rel='shortcut icon' href='Images/favicon.ico' type='image/x-icon'/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>


        <script type='text/javascript'>

            var uploaded;
            window.history.forward(1);
            function noBack() {
                window.history.forward(1);
            }
            function isNumberKey(evt) {
                var charCode = (evt.which) ? evt.which : event.keyCode
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
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
            
            function updateFolderSize() {
                var nBytes = 0,
                        oFiles = document.getElementById("uploadInput2").files,
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
                document.getElementById("fileNum2").innerHTML = nFiles;
                document.getElementById("fileSize2").innerHTML = sOutput;
            }

            
        </script>

    </head>
    <body>
        <div class="page-header">
            <h1><center><B>Welcome <% out.println((String) session.getAttribute("name"));%></B></center></h1>
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
                <% //int Total_Sheets = Integer.parseInt(request.getParameter("total_sheets"));
                    int total_sheets = (Integer) session.getAttribute("total_sheets");
                %>
                <% //out.print(session.getAttribute("c_num")); %> <% //out.print(session.getAttribute("p_id"));%>


                
                                        
                <fieldset>               
                    <form enctype="multipart/form-data" method="post" action="Scans_Upload_Processor"  >



                        <table class="table table-hover" style="overflow: auto; max-height:  350px;">
                            <tr>
                                <td align="center"><B>Roll No. List</B></td>                                
                                <td align="center"><B>Select File & Upload</B></td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <table class="table table-hover" style="overflow: auto; max-height:  350px;">
                                        <tr>
                                            <td align="center"><B>Input File</B></td>                                           
                                        </tr>
                                        <tr>
                                            <td align="center"><input class="btn btn-success btn-primary" id="uploadInput1" type="file" name="roll" onchange="updateFileSize();" required/>selected file: <span id="fileNum1">0</span>; total size: <span id="fileSize1">0</span></td>
                                            
                                        </tr>
                                    </table>
                                </td>
                                

                                <td align="center">
                                    <table class="table table-hover" style="overflow: auto; max-height:  350px;">
                                        <tr>
                                            <td align="center"><B>Select File</B></td>
                                            <td align="center"><B>Upload</B></td>
                                        </tr>
                                        <tr>
                                            <td align="center"><input class="btn btn-success btn-primary" id="uploadInput2" type="file" name="seating" accept="pdf/*" onchange="updateFolderSize();" required/>selected files: <span id="fileNum2">0</span>; total size: <span id="fileSize2">0</span></td>
                                            <td align="center"><input class="btn btn-success btn-primary" type="submit" value="Upload"/></td>
                                        </tr>
                                    </table>
                                </td>

                            </tr>
                        </table>


                       

                    </form>
                </fieldset>                      
                        
                <table class="table table-hover" style="overflow: auto; max-height:  350px;">
                    <tr>
                        <td align="center"><B>Delete All Scans :</B></td>
                    
                        <td align="left"><form method="post" action="Scans_Delete_Processor"  ><input class="btn btn-warning btn-danger" type="submit" value="Delete" onclick="return Confirm();"/></form></td>
                    
                    
                    </tr>
                </table>
                <%    ArrayList err = (ArrayList) request.getAttribute("err");

                       if ( err != null) { %>
                <div class="col-md-4">
                    <%  Iterator it1 = err.iterator();

                        while (it1.hasNext()) {
                            out.println("<h5><font color='green'>" + it1.next() + "</font></h5>");
                          }

                          err.clear(); %>
                </div>
                <% }
                %>

                <div class="col-md-8">
                    <h3>Instructions:</h3>
                    <ui>
                        <li> <b>In case any wrong upload, just  re-upload.</b></li>
                        <li><b>Roll No. List file should be of .text or .txt format &  each row must contain one roll number</b></li>
                        <li><b>Upload single file of PDF format.</b></li>
                        <li><b>Delete all the scans if no longer used.</b></li>
                    </ui>
                </div>

            </div>
        </div>
    </body>
</html>
