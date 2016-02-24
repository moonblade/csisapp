package ieeesbcet.com.ieeecss;

/**
 * Created by sajin on 2/23/16.
 */
import com.orm.SugarRecord;

import java.math.BigInteger;
import java.sql.Date;

public class Attendance extends SugarRecord {

    String Name;
//    String Email;
//    BigInteger Phone;
//    String Branch;
//    int MemId;
//    String Food;
//    int Attendance;
//    Date LastUpdate;

    public Attendance(){

    }

    public Attendance(String Name) //, String Email, BigInteger Phone, String Branch, int MemId, String Food, int Attendance,Date LastUpdate
    {
        this.Name = Name;
//        this.Email = Email;
//        this.Phone = Phone;
//        this.Branch = Branch;
//        this.MemId = MemId;
//        this.Food = Food;
//        this.Attendance = Attendance;
//        this.LastUpdate = LastUpdate;
    }

}
