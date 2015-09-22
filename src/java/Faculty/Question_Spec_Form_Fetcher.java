/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Faculty;

import Common.MyDB;
import Common.CustomList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tanay
 */
public class Question_Spec_Form_Fetcher extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            int p_id = (Integer) session.getAttribute("p_id");
            String formType = (String)request.getParameter("form_type");

            MyDB m = new MyDB();
            if(formType.compareTo("new") == 0) {
                m.incrementTotQuesForPId(p_id);
                int totalGroups = Integer.parseInt((String)request.getParameter("tot_gps"));
                request.setAttribute("totGps",totalGroups);

                int gradersPerGp = Integer.parseInt((String)request.getParameter("graders_per_gp"));
                request.setAttribute("gradersPerGp",gradersPerGp);

                float tot_marks = Integer.parseInt((String)request.getParameter("tot_marks"));
                request.setAttribute("tot_marks", tot_marks);
                m.incrementMarksForPId(p_id,tot_marks);

                int totSheets = Integer.parseInt((String)request.getParameter("tot_sheets"));
                request.setAttribute("totSheets",totSheets);

                int tot_ms = Integer.parseInt((String)request.getParameter("tot_ms"));
                request.setAttribute("totMS",tot_ms);

                String q_name = (String)request.getParameter("q_name");

                int q_id = m.insertQuestion(p_id,q_name,tot_marks);
                session.setAttribute("q_id",q_id);

                int no_edit = 0;

                request.setAttribute("editGraders",no_edit);
                request.setAttribute("editMS",no_edit);
                request.setAttribute("editSheets",no_edit);

            }

            else if(formType.compareTo("edit") == 0){
                int edit = 1;

                int q_id = Integer.parseInt((String)request.getParameter("q_id"));
                session.setAttribute("q_id",q_id);

                request.setAttribute("editGraders",edit);
                request.setAttribute("editMS",edit);
                request.setAttribute("editSheets",edit);

                List<Integer> sheets = new ArrayList();

                ArrayList graderGpsInfo = m.getGraderGpInfoFromPIdAndQId(p_id,q_id);

                int totalGroups = graderGpsInfo.size();
                request.setAttribute("totGps",totalGroups);
                Iterator graderGpsInfo_it = graderGpsInfo.iterator();

                float tot_marks = m.getTotMarksByPIdAndQId(p_id, q_id);
                request.setAttribute("tot_marks", tot_marks);

                ArrayList graderGps = new ArrayList();
                int gradersPerGp = 0;

                while(graderGpsInfo_it.hasNext()) {

                        String graderGpInfo[] = ((String)graderGpsInfo_it.next()).split(",");
                        int gpId = Integer.parseInt(graderGpInfo[0]);
                        String dist = graderGpInfo[1];
                        ArrayList gradersInfo = m.getGradersIdAndNameFromGpId(gpId);
                        gradersPerGp = gradersInfo.size();
                        //out.println(gradersInfo);
                        //out.println("<br>");
                        String reconcilorInfo = m.getReconcilorIdAndNameByGpId(gpId);
                        CustomList graderGpCustomList = new CustomList("" + dist + "," + reconcilorInfo,gradersInfo);
                        //out.println(graderGpCustomList.getTitle());
                        //out.println("<br>");

                        graderGps.add(graderGpCustomList);
                }
                    //out.println(evalInfo);

                request.setAttribute("gradersPerGp",gradersPerGp);

                int ms_id = m.getMarkingSchemeIdByPIdAndQId(p_id,q_id);
                ArrayList ms = m.getMarkingSchemeByMsId(ms_id);
                int tot_ms = ms.size();

                
                //out.println(allGraders);

                sheets = m.getSheetsByPIdAndQId(p_id,q_id);
                int totSheets = sheets.size();
                request.setAttribute("totSheets",totSheets);

                request.setAttribute("evaluators",graderGps);
                //out.println(graders);
                request.setAttribute("markingScheme",ms);
                request.setAttribute("totMS",tot_ms);
                //out.println(markingSchemes);
                request.setAttribute("sheets",sheets);
                //out.println(sheets);
                //out.println(evaluators);

            }

            ArrayList allGraders = m.getAllGraders();
            request.setAttribute("allGraders",allGraders);

            RequestDispatcher rd = request.getRequestDispatcher("Question_Spec_Form");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}
