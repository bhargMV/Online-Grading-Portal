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
public class Paper_Spec_Fetcher extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            String p_id_str = (request.getParameter("p_id"));
            int p_id = 0;
            

            if(p_id_str == null)
              p_id = (Integer) session.getAttribute("p_id");

            else
                p_id = Integer.parseInt(p_id_str);

            session.setAttribute("p_id",p_id);
            MyDB m = new MyDB();

            String paperDetails = m.getPaperDetailsFromPId(p_id);
            session.setAttribute("paperSummary",paperDetails);

            String paperName = m.getPaperNameFromPId(p_id);
            session.setAttribute("p_name", paperName);

            ArrayList qids = m.getQIdsFromPId(p_id);
            request.setAttribute("qids",qids);

            ArrayList qNames = m.getQNamesFromPId(p_id);
            request.setAttribute("qNames",qNames);
            //out.println(qids);

            ArrayList graders = new ArrayList();
            ArrayList markingSchemes = new ArrayList();
            ArrayList sheets = new ArrayList();

            Iterator it = qids.iterator();

            while(it.hasNext()) {
                int q_id = (Integer) it.next();
                //out.println("pid = " + p_id);
                //out.println("qid = " + q_id);
                ArrayList graderGpsInfo = m.getGraderGpInfoFromPIdAndQId(p_id,q_id);

                if(!graderGpsInfo.isEmpty()) {
                    Iterator graderGpsInfo_it = graderGpsInfo.iterator();

                    ArrayList graderGps = new ArrayList();
                    while(graderGpsInfo_it.hasNext()) {

                        String graderGpInfo[] = ((String)graderGpsInfo_it.next()).split(",");
                        int gpId = Integer.parseInt(graderGpInfo[0]);
                        String dist = graderGpInfo[1];
                        ArrayList gradersInfo = m.getGradersIdAndNameFromGpId(gpId);
                        //out.println(gradersInfo);
                        //out.println("<br>");
                        String reconcilorInfo = m.getReconcilorIdAndNameByGpId(gpId);
                        CustomList graderGpCustomList = new CustomList("" + dist + "," + reconcilorInfo,gradersInfo);
                        //out.println(graderGpCustomList.getTitle());
                        //out.println("<br>");

                        graderGps.add(graderGpCustomList);
                    }
                    //out.println(evalInfo);
                    graders.add(graderGps);
                }

                else
                    graders.add(new ArrayList());


                int ms_id = m.getMarkingSchemeIdByPIdAndQId(p_id,q_id);

                if(ms_id != 0) {
                    ArrayList ms = m.getMarkingSchemeByMsId(ms_id);
                    markingSchemes.add(ms);
                }

                else
                    markingSchemes.add(new ArrayList());

                sheets.add(m.getSheetsByPIdAndQId(p_id,q_id));
            }

            request.setAttribute("evaluators",graders);
            //out.println(graders);
            request.setAttribute("markingSchemes",markingSchemes);
            //out.println(markingSchemes);
            request.setAttribute("sheets",sheets);
            //out.println(sheets);
            //out.println(evaluators);

            ArrayList ExtraSheets = new ArrayList();
            ExtraSheets = m.getExtraSheetsForThisPaper(p_id);
            request.setAttribute("ExtraSheets",ExtraSheets);

            RequestDispatcher rd = request.getRequestDispatcher("Paper_Spec");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }



}

