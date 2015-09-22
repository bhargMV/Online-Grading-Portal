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
public class Stud_Remover extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();

            String[] studToRemove = request.getParameterValues("studToRemove");
            
            if(studToRemove!=null) {
            for(int i=0;i<studToRemove.length;++i) {

                m.removeStudent(Integer.parseInt(studToRemove[i]));
            }
            }
            RequestDispatcher rd = request.getRequestDispatcher("All_Stud_List_Fetcher");
            //out.println(rd);
            rd.forward(request, response);


        } finally {
            out.close();
        }
    }
}