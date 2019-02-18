package bd.ac.kuet.ebb;

/**
 * Created by Shanto on 10/27/2017.
 */

public class Info_Institute {


    public String Name;
    public String Estd;
    public String Eiin;
    public String Board;
    public String Version_;
    public String Head;
    public String Division;
    public String Room;
    public String Seat;
    public String Teacher;
    public String Address;
    public String Gpo;
    public String Phone;
    public String Mobile;
    public String Website;
    public String Email;

    public Info_Institute()
    {

    }

    public Info_Institute(String name, String estd, String eiin, String board, String version_, String head, String division, String seat, String room, String address, String teacher, String gpo, String phone, String mobile, String website, String email) {
        Name = name;
        Estd = estd;
        Eiin = eiin;
        Board = board;
        Version_ = version_;
        Head = head;
        Division = division;
        Seat = seat;
        Room = room;
        Address = address;
        Teacher = teacher;
        Gpo = gpo;
        Phone = phone;
        Mobile = mobile;
        Website = website;
        Email = email;
    }
}
