package bd.ac.kuet.ebb;

/**
 * Created by Shanto on 11/25/2017.
 */

public class QBasic {

    public String Cattagory;
    public String PublishDate;
    public String Year;
    public String Board;
    public String Set;
    public String Marks;
    public String Time;
    public String Note;



    public QBasic()
    {

    }

    public boolean isValid()
    {
        return Cattagory != null;
    }

    public QBasic(String cattagory, String year, String set, String board, String marks, String time, String note, String publishDate) {
        Cattagory = cattagory;
        Year = year;
        Set = set;
        Board = board;
        Marks = marks;
        Time = time;
        Note = note;
        PublishDate = publishDate;
    }
}
