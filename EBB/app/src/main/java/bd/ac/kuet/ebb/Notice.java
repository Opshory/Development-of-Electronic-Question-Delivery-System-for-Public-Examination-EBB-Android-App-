package bd.ac.kuet.ebb;

/**
 * Created by Shanto on 11/1/2017.
 */

public class Notice {

    public String Tittle;
    public String Description;
    public String Date;
    public String Time;
    public String Attactment;

    public Notice()
    {

    }

    public Notice(String tittle, String description, String date, String time, String attactment) {
        Tittle = tittle;
        Description = description;
        Date = date;
        Time = time;
        Attactment = attactment;
    }
}
