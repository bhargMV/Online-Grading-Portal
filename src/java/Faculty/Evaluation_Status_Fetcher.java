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
public class Evaluation_Status_Fetcher extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();

            int p_id = (Integer)session.getAttribute("p_id");

            MyDB m = new MyDB();

            ArrayList pending = m.getPendingJobsByPId(p_id);

            request.setAttribute("pending",pending);
            //out.println(pending);
            RequestDispatcher rd = request.getRequestDispatcher("Evaluation_Status");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}
