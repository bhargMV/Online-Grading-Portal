/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Faculty;

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
public class Registration_Manual extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            String c_num = (String)session.getAttribute("c_num");
            int rollNo = Integer.parseInt((String)request.getParameter("rollNo"));

            MyDB m = new MyDB();

            m.registerStud(c_num,rollNo);

            request.setAttribute("nextReg","1");
            RequestDispatcher rd = request.getRequestDispatcher("Stud_Registration_Form");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}