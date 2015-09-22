/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Student;

import Common.MyDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author venishetty
 */
public class Stud_Sheet_Selector extends HttpServlet {

   
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try 
        {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();
            ArrayList sheets = new ArrayList();
            
            String str = (String)request.getParameter("sheet");
            String str2 =(String) request.getParameter("unevsheet");
            
            
            
            
            
            //String temp2[] = str2.split(",");
            
            if(str!=null)
            {
                 //doc_id + q_id + ml_id + seen + c_id+ c_name + p_name + p_id
                
                int doc_id = Integer.parseInt(request.getParameter("doc_id"));
                int q_id = Integer.parseInt(request.getParameter("q_id"));
                int ml_id = Integer.parseInt(request.getParameter("ml_id"));
                int seen = Integer.parseInt(request.getParameter("seen"));
                String c_id = request.getParameter("c_id");
                String c_name = request.getParameter("c_name");
                String p_name = request.getParameter("p_name");                                
                int p_id = Integer.parseInt(request.getParameter("p_id"));
                String q_name = request.getParameter("q_name");
               
                String caption = c_name + " " + p_name + " question " + q_name;
                request.setAttribute("caption", caption);

                ArrayList abc = (ArrayList) m.getSheetsByPIdAndQId(p_id, q_id);
                Iterator it = abc.iterator();
                while (it.hasNext()) {
                    sheets.add(p_id + "_" + doc_id + "_" + it.next().toString());
                }

                request.setAttribute("sheets", sheets);

            // modifying Seen/ Unseen
                m.toggleViewingStatus(doc_id, q_id);

                ArrayList partmarks = new ArrayList();
                ArrayList awarded = new ArrayList();

                partmarks = m.getPartMarksOfThisQuestion(p_id,q_id);
                awarded = m.getPartMarksAwarded(doc_id, q_id);

                ArrayList[] comments = m.getAllComments(doc_id, q_id);

                request.setAttribute("partmarks", partmarks);
                request.setAttribute("awarded", awarded);
                request.setAttribute("comments", comments);
                
                request.setAttribute("sheet", str);
                
                request.setAttribute("doc_id", doc_id);
                request.setAttribute("q_id", q_id);
                request.setAttribute("ml_id", ml_id);
                request.setAttribute("seen", seen);
                request.setAttribute("c_id", c_id);
                request.setAttribute("c_name", c_name);
                request.setAttribute("p_name",p_name);
                request.setAttribute("p_id", p_id);
                request.setAttribute("q_name", q_name);
            //out.println(sheets);

                RequestDispatcher rd = request.getRequestDispatcher("SheetViewer");
                rd.forward(request, response);
            }
            
            else
            {
                //out.println(str2);
              
            //doc_id + q_id + c_id+ c_name + p_name + p_id
                 int doc_id = Integer.parseInt(request.getParameter("doc_id"));
                int q_id = Integer.parseInt(request.getParameter("q_id"));                
                String c_id = request.getParameter("c_id");
                String c_name = request.getParameter("c_name");
                String p_name = request.getParameter("p_name");                                
                int p_id = Integer.parseInt(request.getParameter("p_id"));
                String q_name = request.getParameter("q_name");

                String caption = c_name + " " + p_name + " question " + q_name;
                request.setAttribute("caption", caption);

                ArrayList abc = (ArrayList) m.getSheetsByPIdAndQId(p_id, q_id);
                Iterator it = abc.iterator();
                while (it.hasNext())
                {
                    sheets.add(p_id + "_" + doc_id + "_" + it.next().toString());
                }

                request.setAttribute("sheets", sheets);
                request.setAttribute("doc_id", doc_id);
                request.setAttribute("q_id", q_id);
                request.setAttribute("c_id", c_id);
                request.setAttribute("c_name", c_name);
                request.setAttribute("p_name",p_name);
                request.setAttribute("p_id", p_id);
                request.setAttribute("q_name", q_name);
                
                RequestDispatcher rd = request.getRequestDispatcher("BlankSheetViewer");
                rd.forward(request, response);
            }
            
        } finally {
            out.close();
        }
    }

}
