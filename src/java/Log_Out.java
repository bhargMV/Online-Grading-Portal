/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
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
public class Log_Out extends HttpServlet {

    
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        response.setHeader( "Pragma", "no-cache" );
        response.setHeader( "Cache-Control", "no-cache" );
        response.setDateHeader( "Expires", 0 );
        try {
            response.setHeader("Cache-Control","no-cache"); 
            response.setHeader("Cache-Control","no-store"); 
            response.setDateHeader("Expires", 0); 
            HttpSession session=request.getSession();
            //request.removeAttribute("click");
            session.invalidate();
            
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        } finally {            
            out.close();
        }
    }

  
}
