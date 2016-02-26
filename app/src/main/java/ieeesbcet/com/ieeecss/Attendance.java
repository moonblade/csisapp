package ieeesbcet.com.ieeecss;

/**
 * Created by sajin on 2/23/16.
 */

import com.orm.SugarRecord;


public class Attendance extends SugarRecord {

    String name;
    int paid;
    int tobepaid;
    String email;
    String phone;
    String sex;
    int mtype;
    String memid;
    String section;
    String college;
    String food;
    int acco1;
    int acco2;
    int acco3;
    int due;
    int wrkshp;
    int attendance;
    String lastupdate;

    public Attendance(){

    }

    public Attendance(String name, int paid ,int tobepaid, String email, String phone,String sex,int mtype, String memid,
                      String section, String college, String food, int acco1, int acco2,
                      int acco3, int due, int wrkshp , int attendance,String lastupdate)
    {
        this.name = name;
        this.paid = paid;
        this.tobepaid = tobepaid;
        this.email = email;
        this.phone=  phone;
        this.sex = sex;
        this.mtype = mtype;
        this.memid = memid;
        this.section = section;
        this.college = college;
        this.food = food;
        this.acco1 = acco1;
        this.acco2 = acco2;
        this.acco3 = acco3;
        this.due = due;
        this.wrkshp = wrkshp;
        this.attendance = attendance;
        this.lastupdate = lastupdate;
    }

    public String toString(){
        return name;
    }

}
