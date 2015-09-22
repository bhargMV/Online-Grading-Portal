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
public class Question_Spec_Form_Processor extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("hello");
            
            HttpSession session = request.getSession();

            int p_id = (Integer) session.getAttribute("p_id");
            int q_id = (Integer) session.getAttribute("q_id");

            int graderGpCtr = 1;
            int graderCtr = 1;

            int msCtr = 1;
            int sheetCtr = 1;

            //String tempStr = request.getParameter("gid_1_1");
            String tempStr = new String();

            MyDB m = new MyDB();

            boolean flag=true;
            float dist=0f;
            int graderGpId = 0;

            
            while(true) {
                //out.println("outerwhile");
                tempStr=request.getParameter("gid_"+graderGpCtr+"_1");

                if(tempStr==null)
                    break;

                String rid = request.getParameter("rid_"+graderGpCtr);
                dist=Float.parseFloat(request.getParameter("dist_"+graderGpCtr));

                if(rid == null) {
                    graderGpId = m.insertNewGraderGp(p_id,q_id,dist);
                }

                else {
                    graderGpId = m.insertNewGraderGp(p_id,q_id,dist,Integer.parseInt(rid));
                }

                int graderId = Integer.parseInt(tempStr);
                m.insertNewGrader(graderGpId,graderId);

                graderCtr=2;
                while(true) {

                    out.println("innerwhile");
                    tempStr=request.getParameter("gid_"+graderGpCtr+"_"+graderCtr);

                    if(tempStr == null)
                        break;

                    graderId = Integer.parseInt(tempStr);
                    m.insertNewGrader(graderGpId,graderId);

                    graderCtr++;
                }
                graderGpCtr++;
            }
            
            
            int ms_id = m.getMarkingSchemeIdByPIdAndQId(p_id, q_id);
            int seq = 1;
            while(true) {

                tempStr = request.getParameter("ms_"+msCtr);

                if(tempStr == null)
                    break;

                float partMarks = Float.parseFloat(tempStr);
                m.insertPartMarks(ms_id,partMarks,seq);
                seq++;
                msCtr++;
            }

            
            int sheetListId = m.getSheetListIdByQId(q_id);
            while(true) {

                tempStr = request.getParameter("sheet_"+sheetCtr);

                if(tempStr == null)
                    break;

                int sheetId = Integer.parseInt(tempStr);
                m.insertSheet(sheetListId,sheetId);
                sheetCtr++;
            }

            request.setAttribute("p_id",p_id);
            RequestDispatcher rd = request.getRequestDispatcher("Paper_Spec_Fetcher");
            //out.println(rd);
            rd.forward(request, response);

            
        } finally {
            out.close();
        }
    }
}
