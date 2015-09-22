/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Faculty;

import Common.MyDB;
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
public class Fac_Assign_Marks extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
             HttpSession session = request.getSession();

            String[] allMarks= request.getParameterValues("marks");
            //out.println(allMarks[0]);
            int job_id = (Integer) session.getAttribute("job_id");
            //out.println("job : " + job_id_str);
            //String[] job_desc = job_str.split(",");

            int doc_id = (Integer)session.getAttribute("doc_id");
            int q_id = (Integer)session.getAttribute("q_id");
            int stage = (Integer)session.getAttribute("stage");

            int ms_len = (Integer) session.getAttribute("ms");
            int j=0;
            ArrayList marks = new ArrayList();
            
            if(allMarks != null) {
                out.println(allMarks.length);
                out.println(allMarks[0]);
                String[] val = allMarks[0].split(",");
                float partMarks = Float.parseFloat(val[1]);
                int seq = Integer.parseInt(val[0]);
                out.println(" " +seq);
                for(int i=1;i<=ms_len;++i) {

                    if(seq == i) {
                            marks.add(partMarks);
                            j++;
                            if(j<allMarks.length) {
                                val = allMarks[j].split(",");
                                partMarks = Float.parseFloat(val[1]);
                                seq = Integer.parseInt(val[0]);

                            }
                            else seq = -1;
                    }

                    else {
                        marks.add(0f);
                    }
                    out.println("added" + i +":"+marks.get(i-1));

                }

            }

            else {
                for(int i=0;i<ms_len;++i)
                    marks.add(0f);
            }

            MyDB m = new MyDB();
            //out.println(sum);
            boolean reconciliationRequired;

            if(stage == 2) {
                reconciliationRequired = false;
            }

            else {
                reconciliationRequired = m.reconciliationRequired(doc_id,q_id);
            }
            
            int ml_id = m.getMlIdFromJobId(job_id);
            System.out.println("ml_id : " + ml_id);

            if(ml_id == 0) {
                ml_id = m.insertMlIdForJobId(job_id,stage,reconciliationRequired);
                System.out.println("new_ml_id : " + ml_id);
                m.insertMarksForMlId(ml_id,marks);
            }

            else
            m.updateMarksForMlId(ml_id,marks);

            m.setStatusForJobId(job_id,2);

            if(reconciliationRequired)
            if(m.readyForReconciliation(doc_id,q_id)) {
                m.elevateToReconciliation(doc_id,q_id);
            }  
            
            String comment= (String)request.getParameter("comments");
            String sequence = (String)request.getParameter("sequence");
            if(!(comment.trim()).equals(""))
            {
                    int seq= Integer.parseInt(sequence)+1;                    
                    m.addComment(doc_id,q_id,comment,2,(String)session.getAttribute("webmail"),seq);
                    
            }

            RequestDispatcher rd = request.getRequestDispatcher("/Get_Next_Job");
            rd.forward(request, response);
        
        } finally {
            out.close();
        }
    }
}
