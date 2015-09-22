/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Faculty;

import Common.MyDB;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.oreilly.servlet.multipart.FilePart;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import javax.servlet.http.Part;
import javax.swing.text.html.HTML;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;




import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.commons.httpclient.methods.multipart.Part;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author venishetty
 */
public class Scans_Upload_Processor extends HttpServlet {

    private boolean writeToFile;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            MyDB m = new MyDB();

            int total_sheets = (Integer) session.getAttribute("total_sheets");
            int p_id = (Integer) session.getAttribute("p_id");
            String path = (String) getServletContext().getInitParameter("Directory")+"//";
            
            if (!(new File(path)).exists()) {
                (new File(path)).mkdir();    // creates the directory if it does not exist        
            }
            
             path = path+ (Integer) p_id + "//";

            System.out.println();
           ArrayList err = new ArrayList();
           ArrayList rollList = new ArrayList();
           ArrayList DocIds = new ArrayList();
            
            if (!(new File(path)).exists()) {
                (new File(path)).mkdir();    // creates the directory if it does not exist        
            }

            
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
                                while ((line = br.readLine()) != null) {
                                    rollList.add(Integer.parseInt(line.trim()));
                                }
                                br.close();

                                DocIds = m.getDocIds(p_id, rollList);
                                Iterator it = DocIds.iterator();

                                while (it.hasNext()) {
                                    int temp = (Integer) it.next();
                                    if ((new File(path + temp + "//")).exists()) {
                                        try {
                                            delete(new File(path + temp + "//"));  // deletes the directory if it does already exist       
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            System.exit(0);
                                        }

                                    }

                                }
                                m.CreateDocIds(p_id, rollList);

                                DocIds = m.getDocIds(p_id, rollList);
                                it = DocIds.iterator();
                                while (it.hasNext()) {
                                    int temp = (Integer) it.next();
                                    if (!(new File(path + temp + "//")).exists()) {
                                        (new File(path + temp + "//")).mkdir();    // creates the directory if it does not exist        
                                    }

                                }
                            } 
                            
                            else {
                                // To Store Split Files

                                if ((new File(path + "-1" + "//")).exists()) {
                                    try {
                                        delete(new File(path + "-1" + "//"));  // deletes the directory if it does already exist       
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        System.exit(0);
                                    }

                                }

                                if (!(new File(path + "-1" + "//")).exists()) {
                                    (new File(path + "-1" + "//")).mkdir();    // creates the directory if it does not exist        
                                }

                                //Splitting PDF
                                int n = 0; // no.of pages
                                try {
                                    File savedFile = new File(path + "-1" + "//" + str);
                                    item.write(savedFile);
                                    
                                    String inFile = path + "-1" + "//" + str;
                                    System.out.println("Reading " + inFile);
                                    PdfReader reader = new PdfReader(inFile);
                                    n = reader.getNumberOfPages();
                                    
                                    // Reply User if PDF has invalid number of scans
                                    if(n != total_sheets * DocIds.size() )
                                    {
                                        m.deleteDocIds(p_id, rollList);
                                        Iterator it = DocIds.iterator();

                                        while (it.hasNext()) {
                                            int temp = (Integer) it.next();
                                            if ((new File(path + temp + "//")).exists()) {
                                                try {
                                                    delete(new File(path + temp + "//"));  // deletes the directory if it does already exist       
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    System.exit(0);
                                                }

                                            }

                                        }
                                        err.add("PDF has missing scans!! It must have " + total_sheets * DocIds.size() + " pages");
                                        
                                        break;
                                    }
//                                    postData();
                                    
                                    System.out.println("Number of pages : " + n);
                                    int i = 0;
                                    while (i < n) {
                                        String outFile = (i + 1) + ".pdf";
                                        System.out.println("Writing " + outFile);
                                        Document document = new Document(reader.getPageSizeWithRotation(1));
                                        PdfCopy writer = new PdfCopy(document, new FileOutputStream(path + "-1" + "//" + outFile));
                                        document.open();
                                        PdfImportedPage page = writer.getImportedPage(reader, ++i);
                                        writer.addPage(page);
                                        document.close();
                                        writer.close();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                // Placing files in Corresponding directories
                                //System.out.println(DocIds);
                                for (int i = 1; i <= n; i++) 
                                 {
                                 int temp2 = (i - 1) / total_sheets;
                                 int d_id = (Integer) DocIds.get(temp2);
                                 String itemName = "";
                                 
                                 if ((i % total_sheets) == 0) {
                                 itemName = ((Integer) total_sheets).toString();
                                 } else {
                                 itemName = ((Integer) (i % total_sheets)).toString();
                                 }
                                        
                                     File source = new File(path+"-1"+"//"+i+".pdf");
                                     File desc = new File(path+"-1//"+p_id+"_"+d_id+"_"+itemName+".pdf");

                                     try {
                                         FileUtils.copyFile(source, desc);
                                         uploadFile(path+"-1//"+p_id+"_"+d_id+"_"+itemName+".pdf", (String) getServletContext().getInitParameter("UploadPhP"));
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                 }

                                try {
                                    delete(new File(path + "-1" + "//"));  // deletes the directory if it does already exist  
                                    delete(new File(path));  // deletes all docs in this paper id
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.exit(0);
                                }
                                
                                err.add("Scans  sucessfully saved !");
                            }

                           
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                
                
                request.setAttribute("err", err);
                
                RequestDispatcher rd = request.getRequestDispatcher("Paper_Spec_Fetcher");
                rd.forward(request, response);
            } else {
                // Normal request. request.getParameter will suffice.
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }
    
    
 
    public static void uploadFile(String resourceUrl,String url) throws HttpException, IOException{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
//             System.out.println("*********Sednign seque"+url);
//             System.out.println("*********Sednign file"+resourceUrl);
            HttpPost httppost = new HttpPost(url);

            FileBody bin = new FileBody(new File(resourceUrl));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("bin", bin)
                    .addPart("comment", comment)
                    .build();


            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");

                HttpEntity resEntity = response.getEntity();
                                if (resEntity != null) {
                BufferedReader rd = new BufferedReader
  (new InputStreamReader(resEntity.getContent()));
    
String line = "";
while ((line = rd.readLine()) != null) {
 System.out.println(line );
} 

                    
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
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
