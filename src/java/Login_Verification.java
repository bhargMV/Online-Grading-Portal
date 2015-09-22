/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

public class Login_Verification extends HttpServlet {

    
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            String str = request.getParameter("login");
            
            if(str.equals("   LOGIN   "))
            {
                String webmail = request.getParameter("uid");
                String password = request.getParameter("pwd");
                
                                
                ArrayList err = new ArrayList();
                request.setAttribute("err", err);
                
                HttpSession session = request.getSession();

                MyDB m = new MyDB();
                
                int type = m.verifyLogin(webmail,password);
                //out.println(type);
                if(type==2)
                {
                    session.setAttribute("type", type);
                    String temp= m.getFacultyNameAndId(webmail);
                    if(!temp.equals(""))
                    { 
                    session.setAttribute("webmail",webmail);
                    //out.println(temp);
                    String temp2[]= temp.split(",");
                    String name= temp2[0];
                    int id = Integer.parseInt(temp2[1]);
                    session.setAttribute("name", name);
                    session.setAttribute("id", id);
                    
                    ArrayList jobs = m.getPaperWiseJobListFromFacId(id);                
                    request.setAttribute("jobs",jobs);
                    request.setAttribute("click","JOB LIST");
                   
                   RequestDispatcher rd = request.getRequestDispatcher("Fac_Home");
                   rd.forward(request, response);
                    }
                    
                    else
                    {
                        err.add("You aren't registered to access this site!!");                
                        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                        rd.forward(request, response);
                        
                    }
                    
                    
                }
                
                else if(type==3)
                {
                    session.setAttribute("type", type);
                    session.setAttribute("webmail",webmail);
                    String temp= m.getStudentNameAndId(webmail);
                    //out.println(temp);
                    if(!temp.equals(""))
                    {                       
                    
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
                            //tempx = rs.getString("q_id") + "," + rs.getString("ml_id") + "," + rs.getString("seen")+","+qname
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
                        
                      // out.println(dp[0]+","+dp2+","+total+","+dp[1]);
                        
                    }
                    request.setAttribute("Evaluated", Evaluated);
                    //out.println(Evaluated);
                  RequestDispatcher rd = request.getRequestDispatcher("Stud_Home");
                   rd.forward(request, response);
                    }
                    
                    else
                    {
                        err.add("You aren't registered to access this site!!");                
                        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                        rd.forward(request, response);
                        
                    }
                }

                else
                if(type==1)
                {
                   session.setAttribute("type", type);
                   ArrayList allotedCourses = m.getAllotedCourses();
                   ArrayList unallotedCourses = m.getUnallotedCourses();

                   request.setAttribute("allotedCourses",allotedCourses);
                   request.setAttribute("unallotedCourses",unallotedCourses);

                   //out.println(allotedCourses);
                   //out.println(unallotedCourses);

                   RequestDispatcher rd = request.getRequestDispatcher("Admin_Home");
                   rd.forward(request, response);

                }
                
                else
                {
                err.add("USER ID OR PASSWORD MISMATCH!!");
                
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
                }
                
            }
            
            
            
            
            
        } finally {            
            out.close();
        }
    }

    
}
