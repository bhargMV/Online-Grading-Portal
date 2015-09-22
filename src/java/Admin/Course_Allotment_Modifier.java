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
public class Course_Allotment_Modifier extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();

            String c_num = request.getParameter("c_num");
            String c_name = request.getParameter("c_name");
            session.setAttribute("c_num",c_num);
            session.setAttribute("c_name",c_name);

            ArrayList allFaculty = m.getAllGraders();
            request.setAttribute("allFac",allFaculty);

            RequestDispatcher rd = request.getRequestDispatcher("Course_Allotment_Form");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}
