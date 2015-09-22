/*
 * To change this template, choose Tools | Templates
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
 * @author V.BhargavaMourya
 */
public class Stud_Home_Processor extends HttpServlet {

    
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
             
            String str= request.getParameter("tab"); 
            
            
            MyDB m = new MyDB();
            if(str.equals("LOG OUT"))
            {
                RequestDispatcher rd = request.getRequestDispatcher("Log_Out");
                rd.forward(request, response);                
            }
            
            else if(str.equals("GO BACK"))
            {
                    String webmail = (String)session.getAttribute("webmail");
                    String temp= m.getStudentNameAndId(webmail);
                    //out.println(temp);
                    String temp2[]= temp.split(",");
                    String name= temp2[0];
                    int id = Integer.parseInt(temp2[1]);
                    session.setAttribute("name", name);
                    session.setAttribute("id", id);
                    
                    ArrayList Evaluated = new ArrayList();
                    
                    ArrayList Doc_Paper = m.getDocId_PaperId(id);                    
                    Iterator it1 = Doc_Paper.iterator();
                    
                    while(it1.hasNext())
                    {
                        String dp[] = (it1.next()).toString().split(",");
                        //dp[0]= docid, dp[1]= pid
                        
                        ArrayList dp1=m.getEvaluatedQuestionsOfThisDoc(Integer.parseInt(dp[0]));
                        String dp2= m.getCourseNameAndExamNameFromPId(Integer.parseInt(dp[1]));
                       //dp2= c_id +getCourseName(x)  + rs.getString("p_name")+rs.getInt("tot_marks")+rs.getInt("tot_ques")
                
                        Float total = 0.0F;
                        Iterator it2= dp1.iterator();
                        
                        while(it2.hasNext())
                        {
                            String tempx= (String)it2.next();
                            //tempx = rs.getString("q_id") + "," + rs.getString("ml_id") + "," + rs.getString("seen")
                            String[] tempxy = tempx.split(",");
                            ArrayList awarded = m.getMarksForThisMlID(Integer.parseInt(tempxy[1]));
                            Iterator it3 = awarded.iterator();
                            
                            while(it3.hasNext())
                            {
                                total = total + (Float)it3.next();
                            }                            
                        } 
                        
                        Evaluated.add(dp[0]+","+dp2+","+total+","+dp[1]);
                        //doc_id + c_id + c_name + p_name + tot_marks + tot_ques + total_got + p_id
                        
                       // out.println(dp1);
                        
                    }
                    request.setAttribute("Evaluated", Evaluated);
                    //out.println(Evaluated);
                  RequestDispatcher rd = request.getRequestDispatcher("Stud_Home");
                   rd.forward(request, response);
            }
            
            else if(str.equals("BACK"))
            {
                int doc_id = Integer.parseInt(request.getParameter("doc_id"));
                int q_id = Integer.parseInt(request.getParameter("q_id"));
                String c_id = request.getParameter("c_id");
                String c_name = request.getParameter("c_name");
                String p_name = request.getParameter("p_name");
                int p_id = Integer.parseInt(request.getParameter("p_id"));
                //out.println(st);
                //doc_id + q_id + c_id+ c_name + p_name + p_id

                ArrayList Evaluated = new ArrayList();

                ArrayList dp1 = m.getEvaluatedQuestionsOfThisDoc(doc_id);
                Iterator it2 = dp1.iterator();

                String temp = "";
                String[] temp1;
                while (it2.hasNext()) {
                    temp = it2.next().toString();
                    temp1 = temp.split(",");
                //rs.getString("q_id") + "," + rs.getString("ml_id") + "," + rs.getString("seen")+","+qname

                    Evaluated.add(doc_id + "," + temp1[0] + "," + temp1[1] + "," + temp1[2] + "," + c_id + "," + c_name + "," + p_name + "," + p_id + "," + temp1[3]);
                }
           // out.println("Evaluated  "+ Evaluated);

            //doc_id + q_id + ml_id + seen + c_id+ c_name + p_name + p_id +q_name
                // out.println(dp1);
                //UN-EVALUATED--------------------------------
                ArrayList UnEvaluated = new ArrayList();

            //ArrayList dp2 = m.getUnEvaluatedQuestionsOfThisDoc(Integer.parseInt(dp[0]),Integer.parseInt(dp[7]));
                ArrayList dp2 = new ArrayList();
                dp2 = m.getUnEvaluatedQuestionsOfThisDoc(doc_id, p_id);
                Iterator it3 = dp2.iterator();
                while (it3.hasNext()) {
                    temp = it3.next().toString();
                    temp1 = temp.split(",");
                    UnEvaluated.add(doc_id + "," + temp1[0] + "," + c_id + "," + c_name + "," + p_name + "," + p_id + "," + temp1[1]);
                }

            //out.println("UnEvaluated  "+UnEvaluated);
                //doc_id + q_id + c_id+ c_name + p_name + p_id + q_name
                //----------------------------
                request.setAttribute("Evaluated", Evaluated);
                request.setAttribute("UnEvaluated", UnEvaluated);
                //out.println(UnEvaluated);
                RequestDispatcher rd = request.getRequestDispatcher("Questions_Browser");
                rd.forward(request, response);
            }
            
           
            
            else if(str.equals("SUBMIT"))
            {
                String webmail = (String) session.getAttribute("webmail");

                String comment = (String) request.getParameter("comments");

                int doc_id = Integer.parseInt(request.getParameter("doc_id"));
                int q_id = Integer.parseInt(request.getParameter("q_id"));
                int ml_id = Integer.parseInt(request.getParameter("ml_id"));
                int seen = Integer.parseInt(request.getParameter("seen"));
                String c_id = request.getParameter("c_id");
                String c_name = request.getParameter("c_name");
                String p_name = request.getParameter("p_name");
                int p_id = Integer.parseInt(request.getParameter("p_id"));

                //doc_id + q_id + ml_id + seen + c_id+ c_name + p_name + p_id = sheet[]
                String sequence = (String) request.getParameter("sequence");
                //out.println(comment+" "+webmail+"  "+str1+ " "+ sequence);

                if (!(comment.trim()).equals("")) {
                    int seq = Integer.parseInt(sequence) + 1;
                    m.addComment(doc_id, q_id, comment, 3, webmail, seq);

                }

                if (!(request.getParameterValues("review") == null)) {
                    m.UpdatePendingJobs(doc_id, q_id);
                }

                //out.println(st);
                //doc_id + q_id + ml_id + seen + c_id+ c_name + p_name + p_id
                ArrayList Evaluated = new ArrayList();
                ArrayList dp1 = m.getEvaluatedQuestionsOfThisDoc(doc_id);
                Iterator it2 = dp1.iterator();

                String temp = "";
                String[] temp1;
                while (it2.hasNext()) {
                    temp = it2.next().toString();
                    temp1 = temp.split(",");
                //rs.getString("q_id") + "," + rs.getString("ml_id") + "," + rs.getString("seen")+","+qname

                    Evaluated.add(doc_id + "," + temp1[0] + "," + temp1[1] + "," + temp1[2] + "," + c_id + "," + c_name + "," + p_name + "," + p_id + "," + temp1[3]);
                }
           // out.println("Evaluated  "+ Evaluated);

            //doc_id + q_id + ml_id + seen + c_id+ c_name + p_name + p_id +q_name
                // out.println(dp1);
                //UN-EVALUATED--------------------------------
                ArrayList UnEvaluated = new ArrayList();

            //ArrayList dp2 = m.getUnEvaluatedQuestionsOfThisDoc(Integer.parseInt(dp[0]),Integer.parseInt(dp[7]));
                ArrayList dp2 = new ArrayList();
                dp2 = m.getUnEvaluatedQuestionsOfThisDoc(doc_id, p_id);
                Iterator it3 = dp2.iterator();
                while (it3.hasNext()) {
                    temp = it3.next().toString();
                    temp1 = temp.split(",");
                    UnEvaluated.add(doc_id + "," + temp1[0] + "," + c_id + "," + c_name + "," + p_name + "," + p_id + "," + temp1[1]);
                }

            //out.println("UnEvaluated  "+UnEvaluated);
                //doc_id + q_id + c_id+ c_name + p_name + p_id + q_name
                //----------------------------
                request.setAttribute("Evaluated", Evaluated);
                request.setAttribute("UnEvaluated", UnEvaluated);
                //out.println(UnEvaluated);
                RequestDispatcher rd = request.getRequestDispatcher("Questions_Browser");
                rd.forward(request, response);
            }
            
        } finally {            
            out.close();
        }
    }

    
}
