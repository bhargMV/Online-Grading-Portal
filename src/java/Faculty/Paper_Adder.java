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
public class Paper_Adder extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            String c_num = (String)session.getAttribute("c_num");
            String p_name = request.getParameter("p_name");
            

            MyDB m = new MyDB();

            int p_id = m.addPaper(c_num,p_name);

            session.setAttribute("p_id",p_id);
            RequestDispatcher rd = request.getRequestDispatcher("Paper_Spec_Fetcher");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}
