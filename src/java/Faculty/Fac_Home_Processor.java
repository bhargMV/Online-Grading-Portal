/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Faculty;

import Common.MyDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author V.BhargavaMourya
 */
public class Fac_Home_Processor extends HttpServlet {

    
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
             HttpSession session = request.getSession();
             
            String str= request.getParameter("tab"); 
            
            
            MyDB m = new MyDB();
            
            if(str.equals("JOB LIST"))
            {
                //out.println(session.getAttribute("id"));
                //ArrayList jobs = m.getPendingJobsOfThisFaculty((Integer)session.getAttribute("id"));
                //out.println(session.getAttribute("id"));
                ArrayList jobs = m.getPaperWiseJobListFromFacId((Integer)session.getAttribute("id"));
                //out.println("HERE");
                //out.println(jobs);
                request.setAttribute("jobs",jobs);
                request.setAttribute("click","JOB LIST");
                RequestDispatcher rd = request.getRequestDispatcher("Fac_Home");
                rd.forward(request, response);
            }
            
            else if(str.equals("COURSES"))
            {
                //out.println(session.getAttribute("id"));
                ArrayList courses = m.getCoursesOfThisFaculty((Integer)session.getAttribute("id"));
                //out.println("HERE");
                //out.println((Integer)session.getAttribute("id"));
                //out.println(courses);
                request.setAttribute("courses",courses);
               request.setAttribute("click","COURSES");
                RequestDispatcher rd = request.getRequestDispatcher("Fac_Home");
               rd.forward(request, response);
            }
            
            else if(str.equals("LOG OUT"))
            {
                RequestDispatcher rd = request.getRequestDispatcher("Log_Out");
                rd.forward(request, response);                
            }
            
            else if(str.equals("GO BACK"))
            {
                 RequestDispatcher rd = request.getRequestDispatcher("Fac_Home");
                 rd.forward(request, response);
            }
            
        } finally {            
            out.close();
        }
    }

    
}
