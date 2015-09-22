/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Faculty;

import Common.MyDB;
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
public class Sheet_Selector extends HttpServlet {

    
    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            String job = request.getParameter("job");

            if(job == null)
                job = (String) request.getAttribute("job");

            out.println("job = " + job);
            
            String[] job_desc = job.split(",");
            int p_id = (Integer) session.getAttribute("p_id");
            int q_id = (Integer) session.getAttribute("q_id");
            int doc_id = Integer.parseInt(job_desc[3]);
            String papername = (String) session.getAttribute("p_name");
            String coursename = (String) session.getAttribute("c_name");
            int job_id = Integer.parseInt(job_desc[0]);
            int status = Integer.parseInt(job_desc[2]);
            int stage = Integer.parseInt(job_desc[1]);
            
            session.setAttribute("job_id",job_id);
            session.setAttribute("doc_id",doc_id);
            session.setAttribute("status",status);
            session.setAttribute("stage",stage);
            
            
            MyDB m = new MyDB();

            String qname = m.getQNameFromQId(q_id);

            session.setAttribute("q_no",qname);

            List<Integer> sheetIds = m.getSheetsByPIdAndQId(p_id,q_id);
            int ms_id = m.getMarkingSchemeIdByPIdAndQId(p_id,q_id);
            ArrayList marks = m.getMarkingSchemeByMsId(ms_id);
            ArrayList sheets = new ArrayList();

            Iterator it=sheetIds.iterator();

            while(it.hasNext()) {
                String s_id = it.next().toString();
                sheets.add(p_id + "_" + doc_id + "_" + s_id);
            }

            if(status == 1 || status == 2 || status == 3) {
                int ml_id = m.getMlIdFromJobId(job_id);
                ArrayList checked = m.getCheckedByMlId(ml_id);
                request.setAttribute("checked", checked);
            }

            if(stage == 2) {
                ArrayList evaluations = m.getAllEvaluationsForReconciliation(doc_id,q_id);
                request.setAttribute("evaluations",evaluations);
                out.println(evaluations);
            }

            request.setAttribute("sheets",sheets);
            request.setAttribute("markingScheme",marks);
            //out.println(ms_id);
            //out.println(marks);
            //out.println(sheets);
            
            if(stage == 1) {
                ArrayList[] comments = m.getAllComments(doc_id, q_id);
                request.setAttribute("comments", comments);
                RequestDispatcher rd = request.getRequestDispatcher("Checker");
                rd.forward(request, response);
            }

            else {
                 ArrayList[] comments = m.getAllComments(doc_id, q_id);
                request.setAttribute("comments", comments);
                RequestDispatcher rd = request.getRequestDispatcher("Reconcilor");
                rd.forward(request, response);
            }
            
        } finally {
            out.close();
        }
    }



}
