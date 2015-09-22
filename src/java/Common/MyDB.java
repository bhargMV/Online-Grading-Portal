/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author V.BhargavaMourya
 */
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

public class MyDB {

    String url = "jdbc:mysql://127.0.0.1/";
    String dbName = "grader";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String passwd = "";

    public int verifyLogin(String webmail, String password) {
        String query = "SELECT * FROM users WHERE webmail=? and passwd=?;";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, webmail);
            prest.setString(2, password);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("type");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getFacultyNameAndId(String webmail) {
        String query = "SELECT * FROM faculty WHERE webmail=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, webmail);

            rs = prest.executeQuery();

            if (rs.next()) {
                temp = temp + rs.getString("name");
                temp = temp + ",";
                temp = temp + rs.getInt("id");
                
            }
            
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
       return temp;
    }

    public ArrayList getCoursesOfThisFaculty(int fid) {
        String query = "SELECT * FROM courseallotment WHERE cc_id=?;";
        ArrayList courses = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, fid);

            rs = prest.executeQuery();
            String temp = "";
            String CourseName = "";
            while (rs.next()) {
                temp = temp + rs.getString("num");
                CourseName = getCourseName(temp);
                temp = temp + ",";
                temp = temp + CourseName;
                courses.add(temp);
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }

    public String getCourseName(String num) {
        String query = "SELECT * FROM courses WHERE num=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, num);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public ArrayList getPendingJobsOfThisFaculty(int fid) {
        String query = "SELECT * FROM jobs WHERE person_id=? and status=? ORDER BY doc_id, q_id;";
        ArrayList jobs = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, fid);
            prest.setInt(2, 0);

            rs = prest.executeQuery();
            int job_id;
            int doc_id;
            int q_id;
            int p_id;
            String c_id;
            String PaperName;
            String CourseName = "";
            while (rs.next()) {
                job_id = rs.getInt("id");
                doc_id = rs.getInt("doc_id");
                q_id = rs.getInt("q_id");
                p_id = getPaperIdFromDocId(doc_id);
                PaperName = getPaperNameFromPId(p_id);
                c_id = getCourseIdFromPaperId(p_id);
                CourseName = getCourseName(c_id);

                jobs.add(((Integer) job_id).toString() + "," + ((Integer) p_id).toString() + "," + ((Integer) q_id).toString() + "," + PaperName + "," + CourseName + "," + ((Integer) doc_id).toString());
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public ArrayList getJobListFromFacId(int fid) {
        String query = "SELECT j.id as job_id, j.doc_id as doc_id, j.q_id as q_id, j.status as status, d.p_id as p_id FROM jobs j, docs d WHERE j.person_id =1 AND j.doc_id = d.doc_id ORDER BY d.p_id, j.q_id, d.doc_id;";
        ArrayList jobs = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            //prest.setInt(1, fid);
            //prest.setInt(2, 0);
            rs = prest.executeQuery();
            int job_id;
            int doc_id;
            int q_id;
            int p_id;
            int status;
            String c_id;
            String PaperName;
            String CourseName = "";
            while (rs.next()) {
                job_id = rs.getInt("job_id");
                doc_id = rs.getInt("doc_id");
                q_id = rs.getInt("q_id");
                p_id = rs.getInt("p_id");
                status = rs.getInt("status");
                PaperName = getPaperNameFromPId(p_id);
                c_id = getCourseIdFromPaperId(p_id);
                CourseName = getCourseName(c_id);

                jobs.add(((Integer) job_id).toString() + "," + ((Integer) p_id).toString() + "," + ((Integer) q_id).toString() + "," + PaperName + "," + CourseName + "," + ((Integer) doc_id).toString() + "," + status);
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public int getPaperIdFromDocId(int doc_id) {
        String query = "SELECT * FROM docs WHERE doc_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //tatement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("p_id");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getPaperNameFromPId(int p_id) {
        String query = "SELECT * FROM exam WHERE p_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getString("p_name");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getCourseIdFromPaperId(int p_id) {
        String query = "SELECT * FROM exam WHERE p_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getString("c_id");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<Integer> getSheetsByJobId(int job_id) {
        String query = "SELECT * FROM jobs WHERE id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            // stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, job_id);

            rs = prest.executeQuery();

            if (rs.next()) {

                int doc_id = rs.getInt("doc_id");
                int q_id = rs.getInt("q_id");
                int p_id = getPaperIdFromDocId(doc_id);

                List<Integer> sheets = getSheetsByPIdAndQId(p_id, q_id);

                return sheets;
            }
            conn.close();
            //.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Integer> dummy = new ArrayList();
        return dummy;
    }

    public List<Integer> getSheetsByPIdAndQId(int p_id, int q_id) {
        String query = "SELECT s.sheet_id as sheet FROM sheets s, qpaperspec q WHERE q.p_id=? AND q.q_id=? AND q.sheet_list_id=s.sheet_list_id ORDER BY sheet;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);
            prest.setInt(2, q_id);

            List<Integer> sheets = new ArrayList();

            rs = prest.executeQuery();

            while (rs.next()) {
                int sheet = rs.getInt("sheet");
                sheets.add(sheet);

            }

            conn.close();
            //stmt.close();
            prest.close();
            return sheets;

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Integer> dummy = new ArrayList();
        return dummy;
    }

    public void insertMarksForMlId(int ml_id, ArrayList marks) {
        String query = "Insert INTO marks values(?,?,?);";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            int seq = 1;
            prest.setInt(1, ml_id);
            for (int i = 1; i <= marks.size(); i++) {

                prest.setInt(2, i);
                prest.setFloat(3, (Float) marks.get(i - 1));
                prest.executeUpdate();
            }

            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateMarksForMlId(int ml_id, ArrayList marks) {
        String query = "UPDATE marks SET marks=? WHERE ml_id=? AND seq=?";
        String query1 = "UPDATE marksallotment SET viewing_status=? WHERE ml_id=?";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            int seq = 1;
            prest.setInt(2, ml_id);
            for (int i = 1; i <= marks.size(); i++) {

                prest.setInt(3, i);
                prest.setFloat(1, (Float) marks.get(i - 1));
                prest.executeUpdate();
            }
            
            PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(query1);
            
            prest1.setInt(1,0);
            prest1.setInt(2,ml_id);
            
            prest1.executeUpdate();

            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getQIdFromJobId(int job_id) {
        String query = "SELECT q_id FROM jobs WHERE id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, job_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("q_id");
            }
            conn.close();
            // stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getDocIdFromJobId(int job_id) {
        String query = "SELECT doc_id FROM jobs WHERE id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, job_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("doc_id");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList getMarkingSchemeByMsId(int ms_id) {
        String query = "SELECT * FROM markingscheme WHERE ms_id=? ORDER BY seq;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, ms_id);

            rs = prest.executeQuery();

            ArrayList marks = new ArrayList();

            while (rs.next()) {
                marks.add(rs.getFloat("marks"));
            }

            conn.close();
            //stmt.close();
            prest.close();
            return marks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public int getMarkingSchemeIdByPIdAndQId(int p_id, int q_id) {
        String query = "SELECT ms_id FROM qpaperspec WHERE p_id=? AND q_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);
            prest.setInt(2, q_id);

            int ms_id = 0;

            rs = prest.executeQuery();

            if (rs.next()) {
                ms_id = rs.getInt("ms_id");
            }

            conn.close();
            //stmt.close();
            prest.close();
            return ms_id;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return -1;
    }

    public void setStatusForJobId(int job_id, int status) {

        String query = "UPDATE jobs SET status= ? WHERE id= ?";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, status);
            prest.setInt(2, job_id);

            prest.executeUpdate();

            conn.close();
            // stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getJobIdByDocIdAndQId(int doc_id, int q_id) {

        String query = "SELECT id FROM jobs WHERE doc_id=? AND q_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            int next_job_id = -1;

            if (rs.next()) {
                next_job_id = rs.getInt("id");
            }

            conn.close();
            //stmt.close();
            prest.close();
            return next_job_id;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return -1;
    }

    public int getNextDocIdFromPIdAndDocId(int p_id, int doc_id) {

        String query = "SELECT doc_id FROM docs WHERE doc_id>? AND p_id=? ORDER BY doc_id LIMIT 1;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, p_id);

            rs = prest.executeQuery();

            int next_doc_id = -1;

            if (rs.next()) {
                next_doc_id = rs.getInt("id");
            }

            conn.close();
            //stmt.close();
            prest.close();
            return next_doc_id;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return -1;
    }

    public int getMlIdFromJobId(int job_id) {
        String query = "SELECT m.ml_id as ml_id FROM marksallotment m, jobs j WHERE m.doc_id = j.doc_id AND m.q_id = j.q_id AND m.grader_id=j.grader_id AND j.id=?;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, job_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("ml_id");
            }

            conn.close();
            //stmt.close();
            prest.close();
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int insertMlIdForJobId(int job_id) {
        String query = "Insert INTO marksallotment(doc_id,q_id) values(?,?);";
        String incr = "SELECT LAST_INSERT_ID();";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int doc_id = getDocIdFromJobId(job_id);
            int q_id = getQIdFromJobId(job_id);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            prest.executeUpdate();

            PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            rs = prest.getGeneratedKeys();

            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            conn.close();
            //stmt.close();
            prest.close();

            return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList getCheckedByMlId(int ml_id) {
        String query = "SELECT marks FROM marks WHERE ml_id=? ORDER BY seq";
        ArrayList checked = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, ml_id);

            rs = prest.executeQuery();
            float marks;

            while (rs.next()) {
                marks = rs.getFloat("marks");

                if (marks > 0f) {
                    checked.add(true);
                } else {
                    checked.add(false);
                }
            }
            conn.close();
            //stmt.close();
            prest.close();
            return checked;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return checked;
    }

    public String getStudentNameAndId(String webmail) {
        String query = "SELECT * FROM students WHERE webmail=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, webmail);

            rs = prest.executeQuery();

            if (rs.next()) {
                temp = temp + rs.getString("name");
                temp = temp + ",";
                temp = temp + rs.getInt("roll_no");
                
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public ArrayList getPaperIdsFromCourseNum(String c_num) {
        String query = "SELECT p_id FROM exam WHERE c_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            // stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, c_num);

            rs = prest.executeQuery();

            ArrayList pids = new ArrayList();

            while (rs.next()) {

                int p_id = rs.getInt("p_id");
                pids.add(p_id);
            }

            conn.close();
            //.close();
            prest.close();
            return pids;

        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList dummy = new ArrayList();
        return dummy;
    }

    public ArrayList getDocId_PaperId(int webmail) {
        String query = "SELECT * FROM docs WHERE stud_rollNo=?;";
        ArrayList temp = new ArrayList();
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, webmail);

            rs = prest.executeQuery();

            while (rs.next()) {
                temp.add(((Integer) rs.getInt("doc_id")).toString() + "," + ((Integer) rs.getInt("p_id")).toString());
            }
            conn.close();
            //stmt.close();
            prest.close();
            return temp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public String getCourseNameAndExamNameFromPId(int p_id) {
        String query = "SELECT * FROM exam WHERE p_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                String x = rs.getString("c_id");
                return (x + "," + getCourseName(x) + "," + rs.getString("p_name") + "," + rs.getInt("tot_marks") + "," + rs.getInt("tot_ques"));
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList getEvaluatedQuestionsOfThisDoc(int doc_id) {
        String query = "SELECT * FROM marksallotment WHERE doc_id=? ";
        ArrayList L = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            //prest.setInt(2,

            rs = prest.executeQuery();
            String qname;
            int q_id;
            while (rs.next()) {
                if ((Integer) rs.getInt("viewing_status") != -1) {
                    
                    q_id=rs.getInt("q_id");
                    qname= getQNameFromQId(q_id);
                    L.add(q_id + "," + rs.getString("ml_id") + "," + rs.getInt("viewing_status")+","+qname);
                }
            }
            conn.close();
            //stmt.close();
            prest.close();
            return L;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return L;
    }

    public void toggleViewingStatus(int doc_id, int q_id) {
        String query = "UPDATE marksallotment SET viewing_status=? WHERE doc_id=? AND q_id=? AND viewing_status=?";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, 1);
            prest.setInt(2, doc_id);
            prest.setInt(3, q_id);
            prest.setInt(4, 0);

            prest.executeUpdate();

            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList getPartMarksOfThisQuestion(int p_id, int q_id) {
        int ms_id = getMarkingSchemeIdByPIdAndQId(p_id, q_id);
        return getMarkingSchemeByMsId(ms_id);
    }

    public ArrayList getPartMarksAwarded(int doc_id, int q_id) {
        int ml_id = getMlIDFromDocIdAndQIdChecked(doc_id, q_id);

        return getMarksForThisMlID(ml_id);
    }

    public int getMlIDFromDocIdAndQIdChecked(int doc_id, int q_id) {
        String query = "select * from marksallotment where doc_id=? and q_id=? and viewing_status <> -1";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("ml_id");
            }

            conn.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int getMlIDFromDocIdAndQId(int doc_id, int q_id) {
        String query = "select * from marksallotment where doc_id=? and q_id=? ";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("ml_id");
            }

            conn.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList getMarksForThisMlID(int ml_id) {
        ArrayList awarded = new ArrayList();

        String query = "select * from marks where ml_id=? order by seq asc";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, ml_id);

            rs = prest.executeQuery();

            while (rs.next()) {
                awarded.add(rs.getFloat("marks"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return awarded;

    }

    public ArrayList[] getAllComments(int doc_id, int q_id) {
        ArrayList comments[] = new ArrayList[4];

        for (int i = 0; i < 4; i++) {
            comments[i] = new ArrayList();
        }

        String query = "select * from comments where doc_id=? and q_id=? order by seq asc";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            int sequence = 0;

            while (rs.next()) {
                comments[0].add(rs.getString("comment"));
                comments[1].add(rs.getInt("type"));
                comments[2].add(rs.getString("by"));

                sequence = rs.getInt("seq");
            }
            comments[3].add(sequence);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void addComment(int doc_id, int q_id, String comment, int type, String webmail, int seq) {
        String query = "Insert INTO comments values(?,?,?,?,?,?);";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);
            prest.setString(3, comment);
            prest.setInt(4, type);
            prest.setString(5, webmail);
            prest.setInt(6, seq);

            prest.executeUpdate();

            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdatePendingJobs(int doc_id, int q_id) {
        String query = "select * from jobs where doc_id=? and q_id=? and stage=?";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);
            prest.setInt(3, 2);

            rs = prest.executeQuery();

            int sequence = 0;

            if (rs.next()) {
                String query1 = "UPDATE jobs SET status=? WHERE doc_id=? AND q_id=? AND stage=?";
                String temp = "";
                try {
                    Class.forName(driver).newInstance();
                    Connection conn1 = DriverManager.getConnection(url + dbName, userName, passwd);
                    //Statement stmt = null;
                    //ResultSet rs1 = null;
                    PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(query1);

                    int seq = 1;
                    prest1.setInt(1, 1);
                    prest1.setInt(2, doc_id);
                    prest1.setInt(3, q_id);
                    prest1.setInt(4, 2);

                    prest1.executeUpdate();

                    conn.close();
                    //stmt.close();
                    prest.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                String query1 = "UPDATE jobs SET status=? WHERE doc_id=? AND q_id=? AND stage=?";
                String temp = "";
                try {
                    Class.forName(driver).newInstance();
                    Connection conn1 = DriverManager.getConnection(url + dbName, userName, passwd);
                    //Statement stmt = null;
                    //ResultSet rs1 = null;
                    PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(query1);

                    int seq = 1;
                    prest1.setInt(1, 1);
                    prest1.setInt(2, doc_id);
                    prest1.setInt(3, q_id);
                    prest1.setInt(4, 1);

                    prest1.executeUpdate();

                    conn.close();
                    //stmt.close();
                    prest.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList getUnEvaluatedQuestionsOfThisDoc(int doc_id, int p_id) {
        ArrayList UnEvaluated = new ArrayList();

        String query = "select distinct q_id from qpaperspec Q where Q.p_id = ? and Q.q_id not in (select q_id from marksallotment M where M.doc_id=? and (M.viewing_status=? or M.viewing_status=?))";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);
            prest.setInt(2, doc_id);
            prest.setInt(3, 0);
             prest.setInt(4, 1);
             

            rs = prest.executeQuery();
            String qname;
            while (rs.next()) {
                qname= getQNameFromQId(rs.getInt("q_id"));
                UnEvaluated.add(rs.getInt("q_id")+","+qname);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(UnEvaluated);
        return UnEvaluated;
    }

// By TANAY
    public ArrayList getDocWiseJobListFromFacIdPIdAndQid(int fid, int p_id, int q_id) {
        String query = "SELECT j.id as job_id, j.stage as stage, j.status as status, j.doc_id as doc_id from docs d, jobs j WHERE j.grader_id=? AND j.doc_id=d.doc_id AND d.p_id=? AND j.q_id=? ORDER BY j.status;";
        ArrayList jobs = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, fid);
            prest.setInt(2, p_id);
            prest.setInt(3, q_id);

            rs = prest.executeQuery();

            int job_id;
            int stage;
            int status;
            int doc_id;

            while (rs.next()) {
                job_id = rs.getInt("job_id");
                stage = rs.getInt("stage");
                status = rs.getInt("status");
                doc_id = rs.getInt("doc_id");

                String job = "" + job_id + "," + stage + "," + status + "," + doc_id;
                jobs.add(job);
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public String getPaperDetailsFromPId(int p_id) {
        String query = "SELECT tot_marks, tot_ques FROM exam WHERE p_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return "" + rs.getFloat("tot_marks") + "," + rs.getInt("tot_ques");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList getQIdsFromPId(int p_id) {
        String query = "SELECT DISTINCT q_id FROM qpaperspec WHERE p_id=? ORDER BY q_id;";
        ArrayList courses = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();
            ArrayList qids = new ArrayList();

            while (rs.next()) {

                qids.add(rs.getInt("q_id"));
            }
            conn.close();
            //stmt.close();
            prest.close();
            return qids;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();

    }

    public ArrayList getQNamesFromPId(int p_id) {
        String query = "SELECT DISTINCT q_name FROM qpaperspec WHERE p_id=? ORDER BY q_id;";


        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();
            ArrayList qnames = new ArrayList();

            while (rs.next()) {

                qnames.add(rs.getString("q_name"));
            }
            conn.close();
            //stmt.close();
            prest.close();
            return qnames;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();

    }

    public String getQNameFromQId(int q_id) {
        String query = "SELECT q_name FROM qpaperspec WHERE q_id=?;";


        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, q_id);

            rs = prest.executeQuery();
            String qname = new String();

            while (rs.next()) {

                qname = rs.getString("q_name");
            }
            conn.close();
            //stmt.close();
            prest.close();
            return qname;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String();

    }

    public ArrayList getGraderGpInfoFromPIdAndQId(int p_id, int q_id) {

        String query = "SELECT grader_gp_id, dist FROM gradersallotment WHERE p_id=? AND q_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            ArrayList graderGpInfo = new ArrayList();

            while (rs.next()) {
                graderGpInfo.add("" + rs.getInt("grader_gp_id") + "," + rs.getFloat("dist"));

            }

            conn.close();
            //stmt.close();
            prest.close();
            return graderGpInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public ArrayList getGradersIdAndNameFromGpId(int gpId) {
        String query = "SELECT f.name as name, f.id as id FROM faculty f, gradergroups g WHERE g.grader_gp_id=? AND g.grader_id = f.id;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, gpId);

            rs = prest.executeQuery();
            ArrayList graders = new ArrayList();

            while (rs.next()) {

                graders.add(rs.getString("name") + "," + rs.getInt("id"));
            }
            conn.close();
            //stmt.close();
            prest.close();
            return graders;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public String getReconcilorIdAndNameByGpId(int gpId) {
        String query = "SELECT f.name as name, f.id as id FROM faculty f, gradersallotment g WHERE g. grader_gp_id = ? AND g.reconcilor_id=f.id;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, gpId);

            rs = prest.executeQuery();

            if (rs.next()) {
                return (rs.getString("name") + "," + rs.getInt("id"));
            }

            conn.close();
            //stmt.close();
            prest.close();
            return "";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String();
    }

    public float getTotMarksByPIdAndQId(int p_id, int q_id) {
        String query = "SELECT tot_marks FROM qpaperspec WHERE p_id=? AND q_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);
            prest.setInt(2, q_id);

            float tot_marks = 0f;

            rs = prest.executeQuery();

            if (rs.next()) {
                tot_marks = rs.getInt("tot_marks");
            }
            conn.close();
            //stmt.close();
            prest.close();
            return tot_marks;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return -1;
    }

    public ArrayList getAllGraders() {
        String query = "SELECT name, id FROM faculty ORDER BY name;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            rs = prest.executeQuery();
            ArrayList graders = new ArrayList();
            while (rs.next()) {
                graders.add(rs.getString("name") + "," + rs.getInt("id"));
            }

            conn.close();
            //stmt.close();
            prest.close();
            return graders;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public ArrayList getQuestionWiseJobListFromFacIdAndPId(int fid, int p_id) {
        String query = "SELECT j.q_id as q_id, q.q_name as name, count(case when status=0 then 1 when status=1 then 1 else null end) as count from jobs j, docs d, qpaperspec q where d.doc_id = j.doc_id and j.grader_id=? AND d.p_id=? AND j.q_id=q.q_id group by j.q_id;";
        ArrayList jobs = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, fid);
            prest.setInt(2, p_id);

            rs = prest.executeQuery();
            int q_id;
            int count;
            String q_name = new String();
            while (rs.next()) {
                q_id = rs.getInt("q_id");
                count = rs.getInt("count");
                q_name = rs.getString("name");

                jobs.add(((Integer) q_id).toString() + "," + count + "," + q_name);
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public ArrayList getAllEvaluationsForReconciliation(int doc_id, int q_id) {

        String query = "SELECT id FROM jobs WHERE doc_id=? AND q_id=? AND stage=1 AND status<>0";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            int job_id;
            ArrayList marks = new ArrayList();

            while (rs.next()) {

                job_id = rs.getInt("id");
                int ml_id = getMlIdFromJobId(job_id);
                ArrayList checked = getCheckedByMlId(ml_id);

                marks.add(checked);
            }
            conn.close();
            //stmt.close();
            prest.close();
            return marks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public ArrayList getPaperWiseJobListFromFacId(int fid) {
        String query = "SELECT d.p_id as p_id, count(case when status=0 then 1 when status=1 then 1 else null end) as count from jobs j, docs d where d.doc_id = j.doc_id and j.grader_id=? group by d.p_id;";
        ArrayList jobs = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, fid);
            //prest.setInt(2, 0);

            rs = prest.executeQuery();

            int p_id;
            int count;
            String c_id;
            String PaperName;
            String CourseName = "";
            while (rs.next()) {
                p_id = rs.getInt("p_id");
                count = rs.getInt("count");
                PaperName = getPaperNameFromPId(p_id);
                c_id = getCourseIdFromPaperId(p_id);
                CourseName = getCourseName(c_id);

                jobs.add(((Integer) p_id).toString() + "," + c_id + "," + CourseName + "," + PaperName + "," + count);
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public String getPrevJob(int job_id, int fac_id, int p_id, int q_id, int status) {

        String query = "SELECT j.id as job_id, j.stage as stage, j.status as status, j.doc_id as doc_id FROM jobs j, docs d WHERE j.grader_id=? AND j.status=? AND j.doc_id=d.doc_id AND d.p_id=? AND j.q_id=? AND j.id<? ORDER BY id DESC limit 1;";
        String query1 = "SELECT j.id as job_id, j.stage as stage, j.status as status, j.doc_id as doc_id FROM jobs j, docs d WHERE j.grader_id=? AND j.status<? AND j.doc_id=d.doc_id AND d.p_id=? AND j.q_id=? ORDER BY status DESC, id DESC LIMIT 1;";
        String query2 = "SELECT j.id as job_id, j.stage as stage, j.status as status, j.doc_id as doc_id FROM jobs j, docs d WHERE j.grader_id=? AND j.doc_id=d.doc_id AND d.p_id=? AND j.q_id=? AND status<>4 ORDER BY status DESC, id DESC LIMIT 1;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, fac_id);
            prest.setInt(2, status);
            prest.setInt(3, p_id);
            prest.setInt(4, q_id);
            prest.setInt(5, job_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                int next_prev_id = rs.getInt("job_id");
                int stage = rs.getInt("stage");
                int next_status = rs.getInt("status");
                int doc_id = rs.getInt("doc_id");

                String job = "" + next_prev_id + "," + stage + "," + next_status + "," + doc_id;

                conn.close();
                prest.close();
                return job;
            }

            PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(query1);

            prest1.setInt(1, fac_id);
            prest1.setInt(2, status);
            prest1.setInt(3, p_id);
            prest1.setInt(4, q_id);

            rs = prest1.executeQuery();

            if (rs.next()) {

                int next_prev_id = rs.getInt("job_id");
                int stage = rs.getInt("stage");
                int next_status = rs.getInt("status");
                int doc_id = rs.getInt("doc_id");

                String job = "" + next_prev_id + "," + stage + "," + next_status + "," + doc_id;

                conn.close();
                prest1.close();
                return job;
            }

            PreparedStatement prest2 = (PreparedStatement) conn.prepareStatement(query2);

            prest2.setInt(1, fac_id);
            prest2.setInt(2, p_id);
            prest2.setInt(3, q_id);

            rs = prest2.executeQuery();

            if (rs.next()) {

                int next_prev_id = rs.getInt("job_id");
                int stage = rs.getInt("stage");
                int next_status = rs.getInt("status");
                int doc_id = rs.getInt("doc_id");

                String job = "" + next_prev_id + "," + stage + "," + next_status + "," + doc_id;

                conn.close();
                prest2.close();
                return job;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return new String();
    }

    public String getNextJob(int job_id, int fac_id, int p_id, int q_id, int status) {

        String query = "SELECT j.id as job_id, j.stage as stage, j.status as status, j.doc_id as doc_id FROM jobs j, docs d WHERE j.grader_id=? AND j.status=? AND j.doc_id=d.doc_id AND d.p_id=? AND j.q_id=? AND j.id>? ORDER BY id limit 1;";
        String query1 = "SELECT j.id as job_id, j.stage as stage, j.status as status, j.doc_id as doc_id FROM jobs j, docs d WHERE j.grader_id=? AND j.status>? AND status<>4 AND j.doc_id=d.doc_id AND d.p_id=? AND j.q_id=? ORDER BY status, id LIMIT 1;";
        String query2 = "SELECT j.id as job_id, j.stage as stage, j.status as status, j.doc_id as doc_id FROM jobs j, docs d WHERE j.grader_id=? AND j.doc_id=d.doc_id AND d.p_id=? AND j.q_id=? ORDER BY status, id LIMIT 1;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, fac_id);
            prest.setInt(2, status);
            prest.setInt(3, p_id);
            prest.setInt(4, q_id);
            prest.setInt(5, job_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                int next_job_id = rs.getInt("job_id");
                int stage = rs.getInt("stage");
                int next_status = rs.getInt("status");
                int doc_id = rs.getInt("doc_id");

                String job = "" + next_job_id + "," + stage + "," + next_status + "," + doc_id;

                conn.close();
                prest.close();
                return job;
            }

            PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(query1);

            prest1.setInt(1, fac_id);
            prest1.setInt(2, status);
            prest1.setInt(3, p_id);
            prest1.setInt(4, q_id);

            rs = prest1.executeQuery();

            if (rs.next()) {

                int next_job_id = rs.getInt("job_id");
                int stage = rs.getInt("stage");
                int next_status = rs.getInt("status");
                int doc_id = rs.getInt("doc_id");

                String job = "" + next_job_id + "," + stage + "," + next_status + "," + doc_id;

                conn.close();
                prest1.close();
                return job;
            }

            PreparedStatement prest2 = (PreparedStatement) conn.prepareStatement(query2);

            prest2.setInt(1, fac_id);
            prest2.setInt(2, p_id);
            prest2.setInt(3, q_id);

            rs = prest2.executeQuery();

            if (rs.next()) {

                int next_job_id = rs.getInt("job_id");
                int stage = rs.getInt("stage");
                int next_status = rs.getInt("status");
                int doc_id = rs.getInt("doc_id");

                String job = "" + next_job_id + "," + stage + "," + next_status + "," + doc_id;

                conn.close();
                prest2.close();
                return job;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return new String();
    }

    public boolean reconciliationRequired(int doc_id, int q_id) {

        String query = "SELECT COUNT(id) as total_graders FROM jobs WHERE doc_id=? AND q_id=? AND stage=2;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            int total_graders;

            while (rs.next()) {
                total_graders = rs.getInt("total_graders");

                if (total_graders == 0) {
                    return false;
                }

            }

            conn.close();
            //stmt.close();
            prest.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public int getGraderIdFromJobId(int job_id) {
        String query = "SELECT grader_id FROM jobs WHERE id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, job_id);

            rs = prest.executeQuery();

            if (rs.next()) {
                return rs.getInt("grader_id");
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int insertMlIdForJobId(int job_id, int stage, boolean reconciliationRequired) {
        String query = "Insert INTO marksallotment(doc_id,q_id,grader_id,stage,viewing_status) values(?,?,?,?,?);";
        String incr = "SELECT LAST_INSERT_ID();";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int doc_id = getDocIdFromJobId(job_id);
            int q_id = getQIdFromJobId(job_id);
            int grader_id = getGraderIdFromJobId(job_id);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);
            prest.setInt(3, grader_id);
            prest.setInt(4, stage);

            if (stage == 1 && reconciliationRequired) {
                prest.setInt(5, -1);
            } else {
                prest.setInt(5, 0);
            }

            prest.executeUpdate();

            PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            rs = prest.getGeneratedKeys();

            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            conn.close();
            //stmt.close();
            prest.close();

            return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean readyForReconciliation(int doc_id, int q_id) {

        String query = "SELECT status FROM jobs WHERE doc_id=? AND q_id=? AND stage=1;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            rs = prest.executeQuery();

            int status;

            while (rs.next()) {
                status = rs.getInt("status");

                if ((status != 2 && status != 3)) {
                    return false;
                }

            }

            conn.close();
            //stmt.close();
            prest.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    public void elevateToReconciliation(int doc_id, int q_id) {

        String query = "UPDATE jobs SET status=0 WHERE doc_id=? AND q_id=? AND stage=2";
        String query2 = "UPDATE jobs SET status=3 WHERE doc_id=? AND q_id=? AND stage=1";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, doc_id);
            prest.setInt(2, q_id);

            prest.executeUpdate();

            PreparedStatement prest2 = (PreparedStatement) conn.prepareStatement(query2);

            prest2.setInt(1, doc_id);
            prest2.setInt(2, q_id);

            prest2.executeUpdate();

            conn.close();
            // stmt.close();
            prest2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList getAllCourses() {
        String query = "SELECT * FROM courseallotment;";
        ArrayList courses = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            rs = prest.executeQuery();
            String temp = "";
            String CourseName = "";
            while (rs.next()) {
                temp = temp + rs.getString("num");
                CourseName = getCourseName(temp);
                temp = temp + ",";
                temp = temp + CourseName;
                temp = temp + ",";
                temp = temp + rs.getInt("sem");
                temp = temp + ",";
                temp = temp + rs.getInt("year");
                courses.add(temp);
                temp = "";
            }
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;

    }

    public ArrayList getExtraSheetsForThisPaper(int p_id) {
        String query = "SELECT * FROM extrasheetspec where p_id=?;";
        ArrayList ExtraSheets = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);
            rs = prest.executeQuery();

            while (rs.next()) {
                int sheet_list_id = rs.getInt("sheet_list_id");

                String query1 = "SELECT * FROM sheets where sheet_list_id=?;";

                try {
                    Class.forName(driver).newInstance();
                    Connection conn1 = DriverManager.getConnection(url + dbName, userName, passwd);
                    //Statement stmt = null;
                    ResultSet rs1 = null;

                    PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(query1);

                    prest1.setInt(1, sheet_list_id);
                    rs1 = prest1.executeQuery();

                    while (rs1.next()) {
                        ExtraSheets.add(rs1.getInt("sheet_id"));
                    }

                    conn1.close();
                    //stmt.close();
                    prest1.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ExtraSheets;
    }

    public void CreateDocIds(int p_id, ArrayList roll) {
     
        deleteDocIds(p_id,roll);

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            
             Iterator it1 = roll.iterator();
            
            while(it1.hasNext())
            {
                String query = "INSERT into docs (stud_rollNo,p_id) values(?,?);";
                int rno = (Integer) it1.next();

                PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = null;

                prest.setInt(1, rno);
                prest.setInt(2, p_id);
                prest.executeUpdate();

                rs = prest.getGeneratedKeys();

                int id=0;

                if(rs.next())
                id=rs.getInt(1);

                prest.close();

                addJobsForDoc(p_id,id);

            }
           
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList getDocIds(int p_id, ArrayList roll) {
        ArrayList DocIds = new ArrayList();
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);

            Iterator it = roll.iterator();
            
            while(it.hasNext())
            {
                int rno = (Integer) it.next();
                String query = "SELECT * FROM docs WHERE p_id=? AND stud_rollNo=?";

                ResultSet rs = null;
                PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

                prest.setInt(1, p_id);
                prest.setInt(2, rno);

                rs = prest.executeQuery();

                while (rs.next()) {
                    DocIds.add(rs.getInt("doc_id"));
                }
                
                prest.close();
            }
            conn.close();
           

        } catch (Exception e) {
            e.printStackTrace();
        }
        return DocIds;
    }

    public void deleteDocIds(int p_id)
    {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);

            String query = "DELETE FROM docs WHERE p_id=?";

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);
            
            prest.executeUpdate();

            conn.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDocIds(int p_id, ArrayList roll) 
    {
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            
            Iterator it = roll.iterator();
            
            while(it.hasNext())
            {
                int rno= (Integer)it.next();
                String query = "DELETE FROM docs WHERE p_id=? AND stud_rollNo=? ";

                PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

                prest.setInt(1, p_id);
                prest.setInt(2,rno);

                prest.executeUpdate();
                prest.close();
            }

            

            conn.close();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //ADMIN FUNCTIONS
    
    public ArrayList getAllotedCourses()
    {
        String query = "SELECT ca.num as num, f.name as f_name, c.name as c_name FROM courses c, courseallotment ca, faculty f WHERE ca.num=c.num AND ca.cc_id = f.id ORDER BY ca.num;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);
            rs = prest.executeQuery();

            ArrayList courses=new ArrayList();
            while (rs.next()) {
                courses.add(rs.getString("num")+ "," + rs.getString("c_name") + "," + rs.getString("f_name"));
            }


            conn.close();
            //stmt.close();
            prest.close();
            return courses;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public ArrayList getUnallotedCourses()
    {
        String query = "SELECT num, name FROM courses WHERE num NOT IN (SELECT num FROM courseallotment) ORDER BY num;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);
            rs = prest.executeQuery();

            ArrayList courses=new ArrayList();
            while (rs.next()) {
                courses.add(rs.getString("num")+ "," + rs.getString("name"));
            }


            conn.close();
            //stmt.close();
            prest.close();
            return courses;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public void addCourse(String c_num, String c_name) {
        String query = "Insert INTO courses(num,name) values(?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, c_num);
            prest.setString(2, c_name);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void allotCourse(String c_num, int ccid) {
        String delQuery = "DELETE FROM courseallotment WHERE num like ?;";
        String query = "Insert INTO courseallotment(num,cc_id) values(?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(delQuery);
            prest1.setString(1, c_num);

            prest1.executeUpdate();

            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, c_num);
            prest.setInt(2, ccid);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deAllotCourse(String c_num) {
        String query = "DELETE FROM courseallotment WHERE num like ?;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;


            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, c_num);
            

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList getAllFacInfo() {
        String query = "SELECT name, id, webmail FROM faculty ORDER BY name;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;


            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            rs = prest.executeQuery();
            ArrayList graders=new ArrayList();
            while (rs.next()) {
                graders.add (rs.getString("name") + "," + rs.getInt("id") + "," + rs.getString("webmail"));
            }


            conn.close();
            //stmt.close();
            prest.close();
            return graders;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public ArrayList getAllStudInfo() {
        String query = "SELECT name, roll_no, webmail FROM students ORDER BY roll_no;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;


            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            rs = prest.executeQuery();
            ArrayList graders=new ArrayList();
            while (rs.next()) {
                graders.add (rs.getString("name") + "," + rs.getInt("roll_no") + "," + rs.getString("webmail"));
            }


            conn.close();
            //stmt.close();
            prest.close();
            return graders;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public void addFaculty(String name, String webmail) {
        String query = "Insert INTO faculty(name,webmail) values(?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1, name);
            prest.setString(2, webmail);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addStudent(int rollNo, String name, String webmail) {
        String query = "Insert INTO students(roll_no,name,webmail) values(?,?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1,rollNo);
            prest.setString(2, name);
            prest.setString(3, webmail);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeFaculty(int id) {
        String query = "delete from faculty where id=? ;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, id);


            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeStudent(int rollNo) {
        String query = "delete from students where roll_no=? ;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, rollNo);


            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int addPaper(String c_num,String p_name) {
        String query = "Insert INTO exam(c_id,p_name) values(?,?);";
         String incr = "SELECT LAST_INSERT_ID();";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setString(1, c_num);
            prest.setString(2, p_name);


            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            rs = prest.getGeneratedKeys();

            int id=0;

            if(rs.next())
            id=rs.getInt(1);

            conn.close();
            //stmt.close();
            prest.close();

            return id;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteQuestion(int q_id) {
        String query = "DELETE FROM qpaperspec WHERE q_id=?;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, q_id);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int insertNewGraderGp(int p_id,int q_id,float dist) {
        String query = "Insert INTO gradersallotment(p_id,q_id,dist) values(?,?,?);";
         String incr = "SELECT LAST_INSERT_ID();";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, p_id);
            prest.setInt(2, q_id);
            prest.setFloat(3, dist);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            rs = prest.getGeneratedKeys();

            int id=0;

            if(rs.next())
            id=rs.getInt(1);

            conn.close();
            //stmt.close();
            prest.close();

            return id;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int insertNewGraderGp(int p_id,int q_id,float dist,int rid) {
        String query = "Insert INTO gradersallotment(p_id,q_id,dist,reconcilor_id) values(?,?,?,?);";
         String incr = "SELECT LAST_INSERT_ID();";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, p_id);
            prest.setInt(2, q_id);
            prest.setFloat(3, dist);
            prest.setInt(4, rid);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            rs = prest.getGeneratedKeys();

            int id=0;

            if(rs.next())
            id=rs.getInt(1);

            conn.close();
            //stmt.close();
            prest.close();

            return id;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insertNewGrader(int graderGpId, int graderId) {
        String query = "Insert INTO gradergroups(grader_gp_id,grader_id) values(?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, graderGpId);
            prest.setInt(2, graderId);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertPartMarks(int ms_id,float marks, int seq) {
        String query = "Insert INTO markingscheme(ms_id,marks,seq) values(?,?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, ms_id);
            prest.setFloat(2, marks);
            prest.setInt(3, seq);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getSheetListIdByQId(int q_id) {
        String query = "SELECT sheet_list_id FROM qpaperspec WHERE q_id=?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;


            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, q_id);

            int sheet_list_id=0;

            rs = prest.executeQuery();

            if(rs.next()) {
                sheet_list_id = rs.getInt("sheet_list_id");
            }


            conn.close();
            //stmt.close();
            prest.close();
            return sheet_list_id;

        } catch (Exception e) {
            e.printStackTrace();


          }
      return -1;
    }

    public void insertSheet(int sheetListId, int sheetId) {
        String query = "Insert INTO sheets(sheet_list_id,sheet_id) values(?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, sheetListId);
            prest.setInt(2, sheetId);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void registerStud(String c_num, int rollNum) {
        String query = "Insert INTO registration(c_num,s_roll_no) values(?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setString(1, c_num);
            prest.setInt(2, rollNum);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deregisterStud(String c_num, int rollNum) {
        String query = "DELETE FROM registration where c_num like ? AND s_roll_no=?;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setString(1, c_num);
            prest.setInt(2, rollNum);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList getAllStudRegistered(String c_num)
    {
        String query = "SELECT r.s_roll_no as roll_no, s.name as name FROM registration r, students s where r.c_num like ? AND s.roll_no = r.s_roll_no ORDER BY r.s_roll_no;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;


            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1,c_num);

            rs = prest.executeQuery();
            ArrayList allStud=new ArrayList();
            while (rs.next()) {
                allStud.add ("" + rs.getInt("roll_no") + "," + rs.getString("name"));
            }


            conn.close();
            //stmt.close();
            prest.close();
            return allStud;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public ArrayList getAllStudentsInfo()
    {
        String query = "SELECT roll_no FROM students ORDER BY roll_no;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;


            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            rs = prest.executeQuery();
            ArrayList allStud=new ArrayList();
            while (rs.next()) {
                allStud.add (rs.getInt("roll_no"));
            }


            conn.close();
            //stmt.close();
            prest.close();
            return allStud;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public int insertQuestion(int p_id,String q_name,float tot_marks) {
        String query = "Insert INTO qpaperspec(p_id,ms_id,sheet_list_id,tot_marks,q_name) values(?,?,?,?,?);";
         String incr = "SELECT LAST_INSERT_ID();";

        String queryMsId = "Select max(ms_id) as maxMsId FROM qpaperspec;";
        String querySheetListId1 = "Select max(sheet_list_id) as maxSheetListId FROM qpaperspec;";
        String querySheetListId2 = "Select max(sheet_list_id) as maxSheetListId FROM extrasheetspec;";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            int ms_id = 1;
            int sheetListId = 1;

            ResultSet rs1 = null;
            PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(queryMsId);
            rs1 = prest1.executeQuery();

            if(rs1.next()){
                ms_id = rs1.getInt("maxMsId") + 1;
            }

            ResultSet rs2 = null;
            PreparedStatement prest2 = (PreparedStatement) conn.prepareStatement(querySheetListId1);
            rs2 = prest2.executeQuery();

            if(rs2.next()){
                sheetListId = rs2.getInt("maxSheetListId") + 1;
            }

            ResultSet rs3 = null;
            PreparedStatement prest3 = (PreparedStatement) conn.prepareStatement(querySheetListId2);
            rs3 = prest3.executeQuery();

            if(rs3.next()){

                int sheetListId1 = rs3.getInt("maxSheetListId") + 1;
                if(sheetListId1 > sheetListId)
                    sheetListId = sheetListId1;
            }

            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, p_id);
            prest.setInt(2, ms_id);
            prest.setInt(3, sheetListId);
            prest.setFloat(4, tot_marks);
            prest.setString(5, q_name);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            rs = prest.getGeneratedKeys();

            int id=0;

            if(rs.next())
            id=rs.getInt(1);

            conn.close();
            //stmt.close();
            prest.close();

            return id;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getRegistrationCountByCourse(String c_num)
    {
        String query = "SELECT count(s_roll_no) as tot_reg FROM registration WHERE c_num like ?;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setString(1,c_num);

            rs = prest.executeQuery();

            int totReg = 0;

            if(rs.next())
                totReg = rs.getInt("tot_reg");

            conn.close();
            //stmt.close();
            prest.close();
            return totReg;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addJobsForDoc(int p_id,int doc_id) {

        try {
            ArrayList q_ids = getQIdsFromPId(p_id);
            Iterator qids_it = q_ids.iterator();

            while(qids_it.hasNext()) {
                int q_id = (Integer)qids_it.next();

                //System.out.println("For q : " + q_id);

                int gp_id = getGraderGpIdForQId(q_id);
                //System.out.println("gp_id : " + gp_id);

                ArrayList graderIds = getGraderIdsByGpId(gp_id);
                //System.out.println("graders : " + graderIds);
                int r_id = getReconcilorIdByGpId(gp_id);
                //System.out.println("r_id : " + r_id);

                Iterator gid_it = graderIds.iterator();

                while(gid_it.hasNext()){
                    int g_id = (Integer)gid_it.next();
                    addGraderJob(doc_id,q_id,g_id);
                    //System.out.println("grader job adding  : " + g_id);
                }

                if(r_id != 0) {
                    addReconcilorJob(doc_id,q_id,r_id);
                //System.out.println("rec job adding  : " + r_id);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getGraderGpIdForQId(int q_id) {
        String query = "SELECT grader_gp_id,dist from gradersallotment WHERE q_id=?;";

        ArrayList jobs = new ArrayList();

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, q_id);

            rs = prest.executeQuery();

            ArrayList allGroupIds = new ArrayList();
            ArrayList dists = new ArrayList();
            float tot_dist = 0;
            while(rs.next()){
                allGroupIds.add(rs.getInt("grader_gp_id"));
                tot_dist += rs.getFloat("dist");
                dists.add(tot_dist);
            }

            double rand = (Math.random()*tot_dist);
            int idx = 0;
            System.out.println("dists  : " + dists);
            System.out.println("rand  : " + rand);

            while(idx<dists.size()) {
                if(rand <= new Double(dists.get(idx).toString())) {
                    System.out.println("Found at idx  : " + idx);
                    break;
                }

                idx++;
            }

            conn.close();
            //stmt.close();
            prest.close();

            return new Integer(allGroupIds.get(idx).toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList getGraderIdsByGpId(int gp_id) {
        String query = "SELECT grader_id from gradergroups WHERE grader_gp_id = ?;";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, gp_id);

            rs = prest.executeQuery();
            ArrayList gids = new ArrayList();

            while (rs.next()) {

                gids.add(rs.getInt("grader_id"));
            }
            conn.close();
            //stmt.close();
            prest.close();
            return gids;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();

    }

    public int getReconcilorIdByGpId(int gp_id) {
        String query = "SELECT reconcilor_id from gradersallotment WHERE grader_gp_id = ?;";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, gp_id);

            rs = prest.executeQuery();
            int rid=0;

            if(rs.next()) {

                rid = rs.getInt("reconcilor_id");

                if(rs.wasNull())
                    rid = 0;
            }
            conn.close();
            //stmt.close();
            prest.close();
            return rid;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;

    }

    public void addGraderJob(int doc_id,int q_id,int g_id) {
        String query = "Insert INTO jobs(grader_id,stage,status,doc_id,q_id) values(?,?,?,?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, g_id);
            prest.setInt(2, 1);
            prest.setInt(3, 0);
            prest.setInt(4, doc_id);
            prest.setInt(5, q_id);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addReconcilorJob(int doc_id,int q_id,int g_id) {
        String query = "Insert INTO jobs(grader_id,stage,status,doc_id,q_id) values(?,?,?,?,?);";

        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, g_id);
            prest.setInt(2, 2);
            prest.setInt(3, 4);
            prest.setInt(4, doc_id);
            prest.setInt(5, q_id);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removePaper(int p_id) {
        String query = "DELETE FROM exam WHERE p_id=?;";


        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, p_id);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();


            conn.close();
            //stmt.close();
            prest.close();




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public ArrayList getPendingJobsByPId(int p_id) {
        String query = "SELECT f.name as name, f.webmail as webmail, q.q_name as q_name, j.status as status  FROM jobs j, faculty f, docs d, qpaperspec q WHERE d.p_id=? AND j.doc_id=d.doc_id AND j.grader_id=f.id AND j.status <> 2 AND j.status <> 3 AND q.q_id = j.q_id ORDER BY j.grader_id, j.status, j.q_id;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();

            ArrayList pending =new ArrayList();

            while (rs.next()) {
                pending.add(rs.getString("name") + "," + rs.getString("webmail") + "," + rs.getString("q_name") + "," + rs.getInt("status"));
            }
            conn.close();
            //stmt.close();
            prest.close();


            return pending;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }
    
    public ArrayList getAllStudMarksForPaper(int p_id) {
        String query = "SELECT s.roll_no as roll_no, s.name as name, sum(m.marks) as marks from students s, marksallotment ma, marks m, docs d WHERE d.p_id=? AND d.stud_rollNo=s.roll_no AND ma.doc_id=d.doc_id AND ma.viewing_status<>-1 AND ma.ml_id=m.ml_id GROUP BY s.roll_no ORDER BY s.roll_no;";
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;

            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query);

            prest.setInt(1, p_id);

            rs = prest.executeQuery();

            ArrayList marks =new ArrayList();

            while (rs.next()) {
                marks.add(rs.getInt("roll_no") + "," + rs.getString("name") + "," + rs.getFloat("marks"));
            }
            conn.close();
            //stmt.close();
            prest.close();


            return marks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }
    
    
    
    public void incrementMarksForPId(int p_id, float incr) {
        String query = "UPDATE exam SET tot_marks=tot_marks+? WHERE p_id=?";
        String temp = "";
        try{
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            //incr = 2.1f;
            prest.setInt(2, p_id);
            prest.setFloat(1, incr);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void incrementTotQuesForPId(int p_id) {
        String query = "UPDATE exam SET tot_ques=tot_ques+1 WHERE p_id=?";
        String temp = "";
        try{
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, p_id);
    

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void reduceMarksForPId(int p_id, float incr) {
        String query = "UPDATE exam SET tot_marks=tot_marks-? WHERE p_id=?";
        String temp = "";
        try{
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            //incr = 2.1f;
            prest.setInt(2, p_id);
            prest.setFloat(1, incr);

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void reduceTotQuesForPId(int p_id) {
        String query = "UPDATE exam SET tot_ques=tot_ques-1 WHERE p_id=?";
        String temp = "";
        try{
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, passwd);
            //Statement stmt = null;
            ResultSet rs = null;
            PreparedStatement prest = (PreparedStatement) conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            prest.setInt(1, p_id);
    

            prest.executeUpdate();

            //PreparedStatement prest1 = (PreparedStatement) conn.prepareStatement(incr);
            //rs = prest1.executeQuery();
            conn.close();
            //stmt.close();
            prest.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   
}
