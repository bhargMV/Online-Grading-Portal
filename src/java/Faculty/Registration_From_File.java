/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Faculty;

import Common.MyDB;
import static Faculty.Scans_Upload_Processor.delete;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;







/**
 *
 * @author venishetty
 */
public class Registration_From_File extends HttpServlet {

    private boolean writeToFile;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();

            //int total_sheets = (Integer) session.getAttribute("total_sheets");
            //int p_id = (Integer) session.getAttribute("p_id");
            String c_num= (String)session.getAttribute("c_num");
            
             String path = (String) getServletContext().getInitParameter("Directory")+"//";
            
            if (!(new File(path)).exists()) {
                (new File(path)).mkdir();    // creates the directory if it does not exist        
            }
            
            path = path + c_num + "//";

             if (!(new File(path)).exists())
             {
                                    (new File(path)).mkdir();  // deletes the file if it does already exist
             }
            

           ArrayList err = new ArrayList();
           ArrayList rollList = new ArrayList();
           
           boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if (isMultipart) {

                // Create a factory for disk-based file items
                FileItemFactory factory = new DiskFileItemFactory();

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Parse the request
                List /* FileItem */ items = upload.parseRequest(request);

                // Process the uploaded form items
                Iterator iter = items.iterator();

                while (iter.hasNext())
                {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField())
                    {
                    }
                    else
                    {
                        try {

                            String str = item.getName();

                            String ext = FilenameUtils.getExtension(str);

                            if (ext.equals("txt") || ext.equals("text"))
                            {
                                if ((new File(path + str)).exists()) {
                                    (new File(path + str)).delete();  // deletes the file if it does already exist
                                }

                                File savedFile = new File(path + str);

                                item.write(savedFile);

                                BufferedReader br = new BufferedReader(new FileReader(path + str));
                                String line;
                                while ((line = br.readLine()) != null && line.compareTo("")!=0) {
                                    rollList.add(Integer.parseInt(line.trim()));
                                }
                                br.close();

                                if ((new File(path + str)).exists()) {
                                    (new File(path + str)).delete();  // deletes the file if it does already exist
                                }
                                
                            }




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


                request.setAttribute("err", err);

                Iterator rollList_it = rollList.iterator();

                while(rollList_it.hasNext()) {

                    m.registerStud(c_num, (Integer)rollList_it.next());
                }

                RequestDispatcher rd = request.getRequestDispatcher("Course_Info_Fetcher");
                rd.forward(request, response);
            }
            else {
                // Normal request. request.getParameter will suffice.
            }

            try {
                delete(new File(path));  // deletes all docs in this paper id
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }


        } catch (Exception e) {
            e.printStackTrace();
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
