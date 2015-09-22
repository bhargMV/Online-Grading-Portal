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
public class Question_Wise_JobList_Fetcher extends HttpServlet {

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
            
            String jobGroup = (String) request.getParameter("jobGroup");
            //out.println("jobGroup = " + jobGroup);

            String jobGroup_desc[] = jobGroup.split(",");
            
            int p_id = Integer.parseInt(jobGroup_desc[0]);
            String c_num = jobGroup_desc[1];
            String c_name = jobGroup_desc[2];
            String p_name = jobGroup_desc[3];

            session.setAttribute("p_id",p_id);
            session.setAttribute("c_num",c_num);
            session.setAttribute("c_name",c_name);
            session.setAttribute("p_name",p_name);

            MyDB m = new MyDB();

            ArrayList jobs = m.getQuestionWiseJobListFromFacIdAndPId((Integer)session.getAttribute("id"),p_id);
            request.setAttribute("jobs",jobs);

            //out.println(jobs);

            RequestDispatcher rd = request.getRequestDispatcher("Question_Wise_JobList");
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

}
