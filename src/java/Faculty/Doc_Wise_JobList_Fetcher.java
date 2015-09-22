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
public class Doc_Wise_JobList_Fetcher extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            //out.println("hello");

            int q_id = Integer.parseInt((String) request.getParameter("q_id"));
            session.setAttribute("q_id", q_id);
            int p_id = (Integer)session.getAttribute("p_id");
            //out.println("jobGroup = " + jobGroup);

             MyDB m = new MyDB();

            ArrayList jobs = m.getDocWiseJobListFromFacIdPIdAndQid((Integer)session.getAttribute("id"),p_id,q_id);
            request.setAttribute("jobs",jobs);
            String q_no = m.getQNameFromQId(q_id);
            session.setAttribute("q_no",q_no);

            //out.println("" + (Integer)session.getAttribute("id") + p_id + q_id);

            //out.println("jobs="+jobs);

            RequestDispatcher rd = request.getRequestDispatcher("Doc_Wise_JobList");
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

}

