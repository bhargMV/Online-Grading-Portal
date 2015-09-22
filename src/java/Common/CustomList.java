/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;
import java.util.ArrayList;

/**
 *
 * @author Tanay
 */
public class CustomList {
    String listTitle = "";
    ArrayList list = new ArrayList();

    public CustomList(String title, ArrayList contents) {
        listTitle = title;
        list = contents;
    }

    public CustomList() {
        listTitle = "";
        list = new ArrayList();
    }

    public String getTitle() {
        return listTitle;
    }

    public ArrayList getList() {
        return list;
    }
}
