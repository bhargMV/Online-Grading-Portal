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
public class Questions_Browser_Processor extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();            
            
            int doc_id = Integer.parseInt(request.getParameter("doc_id"));
            String c_id = request.getParameter("c_id");
            String p_name = request.getParameter("p_name");
            String c_name = request.getParameter("c_name");
            int tot_marks = Integer.parseInt(request.getParameter("tot_marks"));
            float total_got = Float.parseFloat(request.getParameter("total_got"));
            int p_id =  Integer.parseInt(request.getParameter("p_id"));
            
            //doc_id + c_id + c_name + p_name + tot_marks + tot_ques + total_got + p_id
            
            
            MyDB m = new MyDB();

            ArrayList Evaluated = new ArrayList();          
            ArrayList dp1 = m.getEvaluatedQuestionsOfThisDoc(doc_id);             
            Iterator it2 = dp1.iterator();
            
            String temp="";
            String[] temp1;
            while (it2.hasNext()) 
            {                
                temp=  it2.next().toString(); //q_id + "," + rs.getString("ml_id") + "," + rs.getInt("viewing_status")+","+qname
                temp1 = temp.split(",");
                                
                Evaluated.add(doc_id + "," +temp1[0] + ","+temp1[1] + ","+temp1[2] + "," + c_id + "," + c_name + "," + p_name+ "," + p_id+ "," + temp1[3]);
            }
            //out.println("Evaluated  "+ Evaluated);
            
            //doc_id + q_id + ml_id + seen + c_id+ c_name + p_name + p_id +q_name
            // out.println(dp1);

            
            //UN-EVALUATED--------------------------------
            ArrayList UnEvaluated = new ArrayList();
            
            //ArrayList dp2 = m.getUnEvaluatedQuestionsOfThisDoc(Integer.parseInt(dp[0]),Integer.parseInt(dp[7]));
            
             ArrayList dp2= new ArrayList();
             dp2 = m.getUnEvaluatedQuestionsOfThisDoc(doc_id,p_id);
            Iterator it3 = dp2.iterator();
            while (it3.hasNext()) 
            {
                temp=  it3.next().toString();
                temp1 = temp.split(",");               
                UnEvaluated.add(doc_id + "," +temp1[0] + "," + c_id + "," + c_name + "," + p_name+ "," + p_id+ "," +temp1[1]);
            }
            
            //out.println("UnEvaluated  "+UnEvaluated);
            //doc_id + q_id + c_id+ c_name + p_name + p_id + q_name
            //----------------------------
            
            request.setAttribute("Evaluated", Evaluated);
            request.setAttribute("UnEvaluated", UnEvaluated);
            //out.println(UnEvaluated);
            RequestDispatcher rd = request.getRequestDispatcher("Questions_Browser");
            rd.forward(request, response);
            //out.println(str);
                  
        } finally {
            out.close();
        }
    }

}
