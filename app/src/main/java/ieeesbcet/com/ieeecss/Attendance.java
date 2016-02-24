package ieeesbcet.com.ieeecss;

/**
 * Created by sajin on 2/23/16.
 */
import com.orm.SugarRecord;


public class Attendance extends SugarRecord {

    String name;

    public Attendance(){

    }

    public Attendance(String Name) //, String Email, BigInteger Phone, String Branch, int MemId, String Food, int Attendance,Date LastUpdate
    {
        this.name = Name;
//        this.Email = Email;
//        this.Phone = Phone;
//        this.Branch = Branch;
//        this.MemId = MemId;
//        this.Food = Food;
//        this.Attendance = Attendance;
//        this.LastUpdate = LastUpdate;
    }

    public String toString(){
        return name;
    }

}
