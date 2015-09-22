/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Admin;

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
public class Admin_Home_Preprocessor extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();

            ArrayList allotedCourses = m.getAllotedCourses();
                   ArrayList unallotedCourses = m.getUnallotedCourses();

                   request.setAttribute("allotedCourses",allotedCourses);
                   request.setAttribute("unallotedCourses",unallotedCourses);

                   //out.println(allotedCourses);
                   //out.println(unallotedCourses);

                   RequestDispatcher rd = request.getRequestDispatcher("Admin_Home");
                   rd.forward(request, response);

            


        } finally {
            out.close();
        }
    }
}
