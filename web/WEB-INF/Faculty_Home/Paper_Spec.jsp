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
        <title>Paper Specifications</title>
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
        </div>


        <%
                String paperName = (String) session.getAttribute("p_name");
                String courseName = (String) session.getAttribute("c_name");
                String caption = courseName + "\n" + paperName;

                ArrayList qids = (ArrayList) request.getAttribute("qids");
                Iterator qid_it = qids.iterator();

                ArrayList qNames = (ArrayList) request.getAttribute("qNames");
                Iterator qNames_it = qNames.iterator();

                ArrayList allGraders = (ArrayList) request.getAttribute("evaluators");
                Iterator allGraders_it = allGraders.iterator();

                ArrayList markingSchemes = (ArrayList) request.getAttribute("markingSchemes");
                Iterator all_ms_it = markingSchemes.iterator();

                ArrayList sheets = (ArrayList) request.getAttribute("sheets");
                Iterator all_sheets_it = sheets.iterator();

                String[] paperSummary = ((String) session.getAttribute("paperSummary")).split(",");
                String totMarks = paperSummary[0];
                String totQues = paperSummary[1];
                
                int max_sheets=0;
                
                ArrayList ExtraSheets = (ArrayList)request.getAttribute("ExtraSheets");
                Iterator ESIterator = ExtraSheets.iterator();
                
                while(ESIterator.hasNext())
                {
                    int x= (Integer)ESIterator.next();
                    if(max_sheets<x )
                        max_sheets =x;
                }
                
        %>
         <form action="Upload_Scans" method="POST">
        <h3 align="center" style="color:#d58512"><b><%=caption%> </b> &nbsp;&nbsp;&nbsp;&nbsp;
                
                <button class="btn btn-success" type="submit" name="upload" value="Upload Scans">Upload Scans</button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a class="btn btn-success" href="<%=request.getContextPath()%>/Evaluation_Status_Fetcher">Get Evaluation Status</a>
            
        </h3>
        </form>
        
        <br>

        <div class="col-md-10 col-md-offset-1">

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h2 class="panel-title"><B>Question paper summary</B></h2>
                </div>
                <div class="panel-body">
                    <B><p>Total questions : <%=totQues%></p></B>
                    <B><p>Total marks : <%=totMarks%></p></B>
                    
                </div>
            </div>

            <ul>
                <%
                    
                    while(qid_it.hasNext()) {
                        String q_id = "" + (Integer) qid_it.next();

                        String q_no = "" + (String) qNames_it.next();

                        ArrayList q_graders = (ArrayList) allGraders_it.next();
                        Iterator q_graders_it = q_graders.iterator();
                        int graderGp_total = q_graders.size();
                        

                        ArrayList q_markingScheme = (ArrayList) all_ms_it.next();
                        Iterator q_ms_it = q_markingScheme.iterator();
                        int ms_total = q_markingScheme.size();

                        ArrayList q_sheets = (ArrayList) all_sheets_it.next();
                        Iterator q_sheets_it = q_sheets.iterator();
                        int sheets_total = q_sheets.size();

                %>
                <input type="hidden" name="totalGraderGps" value="<%=graderGp_total%>" required="required">
                <input type="hidden" name="totalMS" value="<%=ms_total%>">
                <input type="hidden" name="totalSheets" value="<%=sheets_total%>">

                <li>
                    <div class="panel panel-default">
                        <form class="form-inline" action="Question_Spec_Form_Fetcher" method="POST">
                            <input type="hidden" name="edit" value="edit">
                            <div class="panel-heading">
                                    <div class="form-group">
                                        <h4 class="panel-title">
                                            Question <%=q_no%>&nbsp;&nbsp;

                                            <button class="btn btn-default" type="submit" name="q_id" value="<%=q_id%>" disabled="disabled">Edit</button>
                                            &nbsp;&nbsp;
                                            
                                            <a class="btn btn-default" href="<%=request.getContextPath()%>/Question_Remover?q_id=<%=""+q_id%>">Remove</a>
                                            
                                        </h4>
                                    </div>



                            </div>
                            <div class="panel-body">
                                <h4>Graders</h4>
                                <ul>
                                    <%
                                        int graderGpCtr = 1;
                                        while(q_graders_it.hasNext()) {

                                            CustomList graderGp = (CustomList) q_graders_it.next();
                                            String[] graderGpDetail = graderGp.getTitle().split(",",-1);
                                            String dist = graderGpDetail[0];
                                            String reconcilorName = graderGpDetail[1];
                                            String reconcilorId = new String();
                                            if(reconcilorName.length()!=0)
                                                reconcilorId = graderGpDetail[2];

                                            Iterator graders_it = graderGp.getList().iterator();
                                            int graders_total = graderGp.getList().size();
                                    %>
                                    <input type="hidden" name="totalGraderPerGp" value="<%=graders_total%>">
                                    <li>
                                        <h5><B>Group</B><%=(" "  + graderGpCtr + " (Distribution : " + dist + ")")%></h5>
                                        <ul>
                                            <%
                                                int gradersCtr = 1;
                                                while(graders_it.hasNext()) {
                                                    String graderInfo[] = ((String)graders_it.next()).split(",");
                                            %>
                                            <input type="hidden" name="gname<%="" + graderGpCtr + gradersCtr%>" value="<%=graderInfo[0]%>">
                                            <input type="hidden" name="gid<%="" + graderGpCtr + gradersCtr%>" value="<%=graderInfo[1]%>">
                                            <li><%=graderInfo[0]%></li>
                                            <% gradersCtr++;
                                                } %>
                                        </ul>
                                        <% if(reconcilorName.length() !=0) {
                                         %>
                                         <input type="hidden" name="rname<%=graderGpCtr%>" value="<%=reconcilorName%>">
                                         <input type="hidden" name="rid<%="" + graderGpCtr%>" value="<%=reconcilorId%>">
                                         <B>Reconciler : </B><%=reconcilorName%>
                                         <%}%>
                                    </li>
                                    <%  graderGpCtr++;
                                        } %>
                                </ul>
                                <h4>Marking Scheme</h4>

                                <%
                                    int msCtr = 1;
                                    String ms = new String();
                                    if(q_ms_it.hasNext()) {
                                    ms = ms + (Float) q_ms_it.next();
                                %>
                                <input type="hidden" name="ms<%="" + msCtr%>" value="<%=ms%>">
                                <%  }
                                    while(q_ms_it.hasNext()) {
                                        msCtr++;
                                        float part_ms = (Float) q_ms_it.next();
                                        ms += " - " + part_ms;
                                %>
                                <input type="hidden" name="ms<%="" + msCtr%>" value="<%=part_ms%>">
                                <%    }
                                %>

                                <p><%=ms%></p>

                                <h4>Page numbers allocated for answer</h4>

                                <%
                                    int sheetCtr = 1;
                                    String pageNos=new String();
                                    if(q_sheets_it.hasNext()) {
                                    pageNos = pageNos + (Integer) q_sheets_it.next();
                                %>
                                <input type="hidden" name="sheet<%="" + sheetCtr%>" value="<%=pageNos%>">

                                <%  }
                                    while(q_sheets_it.hasNext()) {
                                        sheetCtr++;
                                        
                                        int pageNo = (Integer) q_sheets_it.next();
                                        if(pageNo>max_sheets)
                                            max_sheets=pageNo;
                                %>
                                <input type="hidden" name="sheet<%="" + sheetCtr%>" value="<%=pageNo%>">
                                <%
                                        pageNos += " , " + pageNo;
                                    }
                                    //total_sheets = total_sheets+ sheetCtr;
                                %>

                                <p><%=pageNos%></p>
                            </div>
                        </form>
                    </div>
                </li>
                <% }  session.setAttribute("total_sheets", max_sheets); %>
               
                
            </ul>
            <form action="New_Question_Form" method="POST">
                <center><button class="btn btn-default" type="submit" name="q_id" value=<%=-1%>>Add Question</button></center>

            </form>
            <br><br><br>
        </div>


    </body>
</html>