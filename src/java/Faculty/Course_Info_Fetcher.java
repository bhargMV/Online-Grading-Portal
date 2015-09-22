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
public class Course_Info_Fetcher extends HttpServlet {


    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            String course_string = request.getParameter("courseinfo");

            String c_num = new String();
            String c_name = new String();

            if(course_string == null) {
                c_num = (String)session.getAttribute("c_num");
                c_name = (String)session.getAttribute("c_name");
            }

            else {
                String[] course_info = course_string.split(",");
                c_num = course_info[0];
                c_name = course_info[1];
            }

            
            //out.println(job);
            
            int p_id;
            String p_name;
            //session.setAttribute("jobIdx",job_desc[7]);
            //session.setAttribute("jobId",job_desc[0]);

            MyDB m = new MyDB();

            ArrayList pids = m.getPaperIdsFromCourseNum(c_num);
            ArrayList paper_details = new ArrayList();

            Iterator it = pids.iterator();

            while (it.hasNext()) {
                p_id = (Integer) it.next();
                p_name = m.getPaperNameFromPId(p_id);
                paper_details.add((p_id + "," + p_name));
            }

            session.setAttribute("c_num",c_num);
            request.setAttribute("papers",paper_details);
            session.setAttribute("c_name",c_name);

            int totReg = m.getRegistrationCountByCourse(c_num);
            request.setAttribute("total_registrations",totReg);


            out.println(paper_details);
            out.println(c_num);
             
            RequestDispatcher rd = request.getRequestDispatcher("Course_Page");
            //out.println(rd);
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }



}
