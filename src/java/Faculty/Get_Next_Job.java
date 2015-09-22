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
public class Get_Next_Job extends HttpServlet {

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

            int job_id = (Integer) session.getAttribute("job_id");
            int status = (Integer) session.getAttribute("status");
            int fac_id = (Integer) session.getAttribute("id");
            int p_id = (Integer) session.getAttribute("p_id");
            int q_id = (Integer) session.getAttribute("q_id");

            MyDB m = new MyDB();

            String next_job = m.getNextJob(job_id,fac_id,p_id,q_id,status);

            request.setAttribute("job",next_job);

            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/Sheet_Selector");
            //out.println(rd);
            rd.forward(request, response);
            
        } finally {
            out.close();
        }
    }

}