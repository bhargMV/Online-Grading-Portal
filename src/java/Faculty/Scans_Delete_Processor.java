/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Faculty;

import Common.MyDB;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author venishetty
 */
@WebServlet(name = "Scans_Delete_Processor", urlPatterns = {"/Scans_Delete_Processor"})
public class Scans_Delete_Processor extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();

            int total_sheets = (Integer) session.getAttribute("total_sheets");
            int p_id = (Integer) session.getAttribute("p_id");
            String path = (String) getServletContext().getInitParameter("Directory") + (Integer) p_id + "\\";

            ArrayList err = new ArrayList();

            ArrayList DocIds = new ArrayList();
               m.deleteDocIds(p_id);
            
            if ((new File(path)).exists()) {
                try {
                    delete(new File(path));  // deletes the directory if it does already exist       
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }

            }
            
            err.add("Deleted Successfully!!");
            request.setAttribute("err", err);
            RequestDispatcher rd = request.getRequestDispatcher("Paper_Spec_Fetcher");
            rd.forward(request, response);

        } finally {
            out.close();
        }
    }

    public static void delete(File file)
            throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }
}
