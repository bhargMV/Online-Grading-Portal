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
public class Course_Alloter extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();

            String c_num = (String)session.getAttribute("c_num");
            int ccid = Integer.parseInt(request.getParameter("cc"));

            m.allotCourse(c_num,ccid);

            RequestDispatcher rd = request.getRequestDispatcher("Admin_Home_Preprocessor");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}