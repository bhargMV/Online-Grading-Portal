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
        <title>Register Students</title>
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
            
            function isNumericKey(e)
            {
                var charInp = window.event.keyCode; 
                 if (charInp > 31 && (charInp < 48 || charInp > 57)) 

                 {
                        return false;
                 }
                  return true;

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
                            <li><a href="<%=request.getContextPath()%>/Admin_Home_Preprocessor?tab=Allotment">ADD/MODIFY COURSE ALLOTMENT</a></li>
                            <li><a href="<%=request.getContextPath()%>/All_Fac_List_Fetcher">FACULTY LIST</a></li>
                            <li class="active"><a href="<%=request.getContextPath()%>/All_Stud_List_Fetcher">STUDENT LIST</a></li>
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

                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><B>Student Addition Form</B></h2>
                    </div>
                    <div class="panel-body">
                        <div>
                            <form enctype="multipart/form-data" onsubmit="reloadForm()" action="Stud_Adder_By_File" method="POST">
                                <h3 style="color:#d58512"><b>Registration By File</b></h3>
                                <p>
                                    Please upload a text file containing comma separated fields in the following order : Roll no, Name, Webmail
                                </p>
                                <table class="table table-hover" style="overflow: auto; max-height:  350px;">
                                    <tr>
                                        <td align="left"><B>File Chooser</B></td>                                
                                        <td align="left"><B>Register</B></td>
                                    </tr>
                                    <tr>
                                        <td align="left">
                                            <input class="btn btn-success btn-primary" id="uploadInput1" type="file" name="roll_list" onchange="updateFileSize();" required="required"/>selected file: <span id="fileNum1">0</span>; total size: <span id="fileSize1">0</span>
                                        </td >
                                        <td align="left">
                                            <button type="submit" class="btn btn-primary">Register students in file</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <br>
                        <div>
                            <form onsubmit="reloadForm()" action="Stud_Adder" method="POST">
                                <h3 style="color:#d58512"><b>Registration By Entry</b></h3>
                                <br>

                                <label>Enter Student's Roll Number </label>&nbsp;&nbsp;&nbsp;
                                <input type="text" name="rollNo" style="width:80px" maxlength="8" onkeypress="return isNumericKey(event);" required="required">

                                <br><br>
                                <label>Enter Student's Name </label>&nbsp;&nbsp;&nbsp;
                                <input type="text" name="name" style="width:150px" maxlength="30" required="required">

                                <br><br>
                                <label>Enter Student's webmail </label>&nbsp;&nbsp;&nbsp;
                                <input type="text" name="webmail" style="width:100px" maxlength="30" required="required">
                                <br><br>

                                    <button type="submit" class="btn btn-primary">Confirm</button>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
