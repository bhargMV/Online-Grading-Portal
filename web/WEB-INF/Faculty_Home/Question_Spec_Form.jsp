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
        <title>Question Specifications</title>
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
            
            <% int editGraders = (Integer) request.getAttribute("editGraders");
                int editMS = (Integer) request.getAttribute("editMS");
                int editSheets = (Integer) request.getAttribute("editSheets");
            %>

            <div class="col-md-10 col-md-offset-1">
                <h2 align="center"><%=session.getAttribute("c_num") + " " + session.getAttribute("c_name") + " " + session.getAttribute("p_name")%></h2>
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h2 class="panel-title"><B>Question summary</B></h2>
                        <%  int gradersPerGp = (Integer) request.getAttribute("gradersPerGp");
                            int totGps = (Integer) request.getAttribute("totGps");
                            float tot_marks = (Float) request.getAttribute("tot_marks");

                            int totMS = (Integer) request.getAttribute("totMS");
                            int totSheets = (Integer) request.getAttribute("totSheets");
                        %>
                    </div>
                    <div class="panel-body">
                        <div>
                            <form onsubmit="reloadForm()" action="Question_Summary_Form_Processor">
                                <label>
                                    Enter total marks :
                                </label>
                                
                                <input type="text" name="totMarks" value="<%=tot_marks%>" required="required">
                                <br><br>
                                <label>
                                    Enter total grader groups :
                                </label>
                                
                                <input type="text" name="totGps" value="<%=totGps%>" required="required">
                                <br>
                                <label>
                                    Enter total graders per group :
                                </label>
                                
                                <input type="text" name="graders_per_gp" value="<%=gradersPerGp%>" required="required">
                                <br><br>

                                <label>
                                    Enter total number of part marks :
                                </label>
                                <input type="text" name="tot_ms" value="<%=totMS%>" required="required">
                                <br><br>

                                <label>
                                    Enter total number of pages alloted for answer :
                                </label>
                                <input type="text" name="tot_sheets" value="<%=totSheets%>" required="required">
                                <br>
                                <button class="btn btn-primary" type="submit" disabled="disabled">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>

                <div id="form">
                    <form action="Question_Spec_Form_Processor" method="POST">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h2 class="panel-title">
                                    Graders
                                </h2>
                            </div>
                            <div class="panel-body">
                                <div>
                                    <ul>
                                        <%

                                        if(editGraders == 1) {
                                        ArrayList graderGps = (ArrayList) request.getAttribute("evaluators");
                                        Iterator graderGps_it = graderGps.iterator();
                                        int graderGpCtr = 1;

                                            while(graderGps_it.hasNext()) {
                                                CustomList graderGp = (CustomList) graderGps_it.next();
                                                String[] graderGpDetail = graderGp.getTitle().split(",",-1);
                                                String dist = graderGpDetail[0];
                                                String reconcilorName = graderGpDetail[1];
                                                String reconcilorId = new String();
                                                if(reconcilorName.length()!=0)
                                                reconcilorId = graderGpDetail[2];

                                                Iterator graders_it = graderGp.getList().iterator();
                                             %>
                                             <li>
                                                 <h4><B>Group</B><%=(" "  + graderGpCtr + " (Distribution : " + dist + ")")%></h4>
                                                 <ul>
                                                    <%
                                                        int gradersCtr = 1;
                                                        while(graders_it.hasNext()) {
                                                            String graderInfo[] = ((String)graders_it.next()).split(",");
                                                    %>
                                                    <li>


                                                    <select name="<%="gid_"+graderGpCtr+"_"+gradersCtr%>" required>

                                                        <option value="<%=graderInfo[1]%>"><%=graderInfo[0]%></option>
                                                        <%
                                                        Iterator allGraders_it = ((ArrayList)request.getAttribute("allGraders")).iterator();

                                                        while(allGraders_it.hasNext()) {
                                                            String[] listGraderInfo = ((String)allGraders_it.next()).split(",");
                                                        %>
                                                        <option value="<%=listGraderInfo[1]%>"><%=listGraderInfo[0]%></option>
                                                        <% } %>


                                                    </select>
                                                    </li>
                                                    <% gradersCtr++;
                                                        } %>
                                                 </ul>
                                                 <% if(reconcilorName.length() != 0) {
                                                        %>
                                                        <br>
                                                        <label>
                                                            Reconciler :
                                                        </label>
                                                        <select name="<%="rid_" + graderGpCtr%>" required>
                                                            <option selected="selected" value="<%=reconcilorId%>"><%=reconcilorName%></option>
                                                            <%
                                                            Iterator allGraders_it = ((ArrayList)request.getAttribute("allGraders")).iterator();

                                                            while(allGraders_it.hasNext()) {
                                                                String[] listGraderInfo = ((String)allGraders_it.next()).split(",");
                                                            %>
                                                            <option value="<%=listGraderInfo[1]%>"><%=listGraderInfo[0]%></option>
                                                            <% } %>
                                                        </select>
                                                 <%
                                                    }
                                                 %>
                                             </li>
                                             <% graderGpCtr++;
                                                }
                                               }
                                             else {

                                                
                                                int graderGpCtr = 1;
                                                while(graderGpCtr<=totGps) {
                                             %>
                                             <li>
                                                 <h4><B>Group</B><%=(" "  + graderGpCtr)%></h4>
                                                 <label>
                                                     Distribution
                                                 </label>
                                                 <input type="input" name="<%="dist_"+graderGpCtr%>" required>

                                                 <ul>
                                                    <%
                                                        int gradersCtr = 1;
                                                        while(gradersCtr<=gradersPerGp) {
                                                    %>
                                                    <li>
                                                        <select name="<%="gid_"+graderGpCtr+"_"+gradersCtr%>" required>

                                                            <option disabled="disabled" selected="selected">select grader</option>
                                                            <%
                                                            Iterator allGraders_it = ((ArrayList)request.getAttribute("allGraders")).iterator();

                                                            while(allGraders_it.hasNext()) {
                                                                String[] listGraderInfo = ((String)allGraders_it.next()).split(",");
                                                            %>
                                                            <option value="<%=listGraderInfo[1]%>"><%=listGraderInfo[0]%></option>
                                                            <% } %>


                                                        </select>
                                                    </li>
                                                    <% gradersCtr++;
                                                        } %>
                                                 </ul>
                                                 <% if(gradersPerGp>1) {
                                                        %>
                                                        <br>
                                                        <label>
                                                            Reconciler :
                                                        </label>
                                                        <select name="<%="rid_" + graderGpCtr%>" required>
                                                            <option disabled="disabled" selected="selected">select reconciler</option>
                                                            <%
                                                            Iterator allGraders_it = ((ArrayList)request.getAttribute("allGraders")).iterator();

                                                            while(allGraders_it.hasNext()) {
                                                                String[] listGraderInfo = ((String)allGraders_it.next()).split(",");
                                                            %>
                                                            <option value="<%=listGraderInfo[1]%>"><%=listGraderInfo[0]%></option>
                                                            <% } %>
                                                        </select>
                                                 <%
                                                    }
                                                 %>
                                             </li>
                                             <%     graderGpCtr++;
                                                }
                                            }

                                         %>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h2 class="panel-title">
                                    Marking scheme
                                </h2>
                            </div>
                            <div class="panel-body">
                                <div>
                                    <label>
                                        Marking Scheme : 
                                    </label>
                                    <nbsp><nbsp>
                                    <%  if(editMS == 1) {
                                            ArrayList ms = (ArrayList) request.getAttribute("markingScheme");
                                            Iterator ms_it = ms.iterator();
                                            int ms_ctr = 1;
                                            while(ms_it.hasNext()) {
                                                float marks = (Float) ms_it.next();
                                    %>
                                                <input type="text" style="width:50px" name="<%="ms_"+ms_ctr%>" value="<%=marks%>" required>
                                    <%
                                                ms_ctr++;
                                            }
                                        }
                                        else {
                                            int ms_ctr = 1;
                                            totMS = (Integer) request.getAttribute("totMS");
                                            
                                            while(ms_ctr <= totMS) {
                                    %>
                                                <input type="text" style="width:50px" name="<%="ms_"+ms_ctr%>" required>
                                    <%
                                                ms_ctr++;
                                            }
                                        }
                                    %>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h2 class="panel-title">
                                    Page numbers for answer
                                </h2>
                            </div>
                            <div class="panel-body">
                                <div>
                                    <label>
                                        Sheets :
                                    </label>
                                    <nbsp><nbsp>
                                    <% 
                                       if(editSheets == 1) {
                                           ArrayList sheets = (ArrayList) request.getAttribute("sheets");
                                           Iterator sheets_it = sheets.iterator();
                                           int sheets_ctr = 1;
                                           while(sheets_it.hasNext()) {
                                               int sheet = (Integer) sheets_it.next();
                                    %>
                                                <input type="text" style="width:30px" name="<%="sheet_"+sheets_ctr%>" value="<%=sheet%>" required>
                                    <%
                                                sheets_ctr++;
                                           }
                                        }
                                        else {
                                            totSheets = (Integer) request.getAttribute("totSheets");
                                            int sheets_ctr = 1;
                                            while(sheets_ctr <= totSheets) {
                                    %>
                                                <input type="text" style="width:30px" name="<%="sheet_"+sheets_ctr%>" required>
                                    <%          sheets_ctr++;
                                            }
                                        }
                            %>
                                </div>
                            </div>
                        </div>

                        <center>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </center>
                    </form>
                    <br><br>
                </div>
            </div>
        </div>
    </body>
</html>
