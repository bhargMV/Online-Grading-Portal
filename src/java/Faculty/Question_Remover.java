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
public class Question_Remover extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            
            int q_id = Integer.parseInt(request.getParameter("q_id"));
            int p_id = (Integer)session.getAttribute("p_id");
           

            MyDB m = new MyDB();
            
            float marks = m.getTotMarksByPIdAndQId(p_id,q_id);
            m.reduceMarksForPId(p_id,marks);
            m.reduceTotQuesForPId(p_id);
            m.deleteQuestion(q_id);

            request.setAttribute("p_id",p_id);
            RequestDispatcher rd = request.getRequestDispatcher("Paper_Spec_Fetcher");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}
