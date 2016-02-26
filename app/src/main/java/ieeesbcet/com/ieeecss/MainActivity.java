package ieeesbcet.com.ieeecss;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orm.SugarContext;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    Attendance db;
    JSONArray dbArray;
    JSONObject dbResponse;
    public String urlip;
    JSONParser jsonParser = new JSONParser();
    JSONArrayParser jsonArrayParser = new JSONArrayParser();
    String urldb;
    String urlup;

    String res;
    Attendance db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById(R.id.button);
        SugarContext.init(this);
        Attendance attendance = new Attendance("as",123, 123, "asdf", "23434", "Male", 234, "23434", "2344", "afasjkldsf", "adfj", 4, 5, 6, 43, 5, 3454, "2016-01-01");
        try {
            attendance.save();
        }catch (ExceptionInInitializerError e){
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BarCode.class);

                System.out.print("here it is :::::::::::: " + urlip);

                intent.putExtra("urlip", urlip);

                startActivity(intent);
            }
        });


        final Button down = (Button) findViewById(R.id.downsync);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                down.setEnabled(false);
                new DownSync().execute();


            }
        });


        Button up = (Button) findViewById(R.id.upsync);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                down.setEnabled(true);
                new UpSync().execute();


            }
        });


        final EditText et = (EditText) findViewById(R.id.serverurl);
        Button btnurl= (Button) findViewById(R.id.urlbtn);
        btnurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlip =  et.getText().toString();
                urldb = "http://"+urlip+"/csis/aura/downsync.php";
                urlup = "http://"+urlip+"/csis/aura/upsync.php";

                Toast set = Toast.makeText(getApplicationContext(), "URL Set", Toast.LENGTH_SHORT);
                set.show();
            }
        });

    }



    class DownSync extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {


            Attendance.deleteAll(Attendance.class);

            List<Attendance> AttendenceList = Attendance.find(Attendance.class,null,null);

            System.out.println("hgfhfgfghdffffffffffffffffffffffffffffffffffffffffffffffff" + AttendenceList.size());

            List<NameValuePair> param = new ArrayList<NameValuePair>();
            //            params.add(new BasicNameValuePair("name", name));
//            params.add(new BasicNameValuePair("price", price));
//            params.add(new BasicNameValuePair("description", description));
            String msg="get";
            param.add(new BasicNameValuePair("msg", msg));

//            JSONObject json = jsonParser.makeHttpRequest(urldb,
//                    "POST", param);

            dbArray = jsonArrayParser.makeHttpRequest(urldb, "POST", param);



//            List<Attendance> dbdata = Attendance.listAll(Attendance.class);
//
//            int i =0;
//            while (i!= dbdata.size())
//            {
//                System.out.println(dbdata.get(i).Name + "---"+ dbdata.get(i).MemId + "---" +  dbdata.get(i).Attendance );
//                i++;
//            }





//            if(json==null)
//                return null;

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
//                try {
//                    int success = json.getInt("success");
//                    res = json.getString("message");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }




            return null;
        }



        protected void onPostExecute(String file_url) {

            try{
                for (int i = 0; i < dbArray.length(); i++) {
//
//                System.out.println(p.get(0) + "......" + p.get(1));


//
                    JSONObject jsonobject = dbArray.getJSONObject(i);
                    String name = jsonobject.getString("Name");
                    String email = jsonobject.getString("Email");
                    String sex = jsonobject.getString("Sex");
                    String memid = jsonobject.getString("Memid");
                    String section = jsonobject.getString("Section");
                    String college = jsonobject.getString("College");
                    String food = jsonobject.getString("Food");

                    int paid = Integer.parseInt(jsonobject.getString("Paid"));
                    int tobepaid = Integer.parseInt(jsonobject.getString("To be paid"));
                    int memtype = Integer.parseInt(jsonobject.getString("Memtype"));
                    int acco1 = Integer.parseInt(jsonobject.getString("Acco1"));
                    int acco2 = Integer.parseInt(jsonobject.getString("Acco2"));
                    int acco3 = Integer.parseInt(jsonobject.getString("Acco3"));
                    int due = Integer.parseInt(jsonobject.getString("Due"));
                    int workshop = Integer.parseInt(jsonobject.getString("Workshop"));
                    int attendance = Integer.parseInt(jsonobject.getString("Attendance"));

                    String phone = jsonobject.getString("Phone");
                    String lastupdate = jsonobject.getString("LastUpdate");



                    System.out.println(name + "..." + email+ "..."  + attendance + "..." + lastupdate);



                    db = new Attendance(name, paid, tobepaid, email, phone, sex, memtype, memid, section, college, food, acco1, acco2,acco3, due, workshop, attendance, lastupdate);
                    Log.i("attendance", db.toString());
                    db.save();

//                    System.out.println("Attendance is :::::::: " + a * 2);
//                    Log.d("CREATE RESPONSE " + i , jsonobject.toString());
                }

            }catch(JSONException e){
                e.printStackTrace();
            }

            List<Attendance> AttendenceList = Attendance.find(Attendance.class,null,null);

            System.out.println("hgfhfgfghdffffffffffffffffffffffffffffffffffffffffffffffff" + AttendenceList.size());


        }


    }

    class UpSync extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> par = new ArrayList<NameValuePair>();

            Calendar c = Calendar.getInstance();
            System.out.println("Current time =&gt; "+c.getTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            // Now formattedDate have current date/time
            System.out.println("DATE is " + formattedDate);


            List<Attendance> AttendenceList = Attendance.find(Attendance.class, "lastupdate = ?", formattedDate);
//            long numberOfEntries = Attendance.count(Attendance.class, "LastUpdate = ?", new String[]{formattedDate});

            for (int i=0;i<AttendenceList.size();i++){


                Attendance a = AttendenceList.get(i);
                System.out.println(a.name + "..." + a.email+ "..."  + a.attendance + "..." + a.lastupdate);
                String msg = a.email;
                String workshop = a.wrkshp+"";
                par.clear();
                par.add(new BasicNameValuePair("msg", msg));
                par.add(new BasicNameValuePair("workshop", workshop));
                dbResponse = jsonParser.makeHttpRequest(urlup, "POST", par);
            }




            //            params.add(new BasicNameValuePair("name", name));
//            params.add(new BasicNameValuePair("price", price));
//            params.add(new BasicNameValuePair("description", description));




//            List<Attendance> dbdata = Attendance.listAll(Attendance.class);
//
//            int i =0;
//            while (i!= dbdata.size())
//            {
//                System.out.println(dbdata.get(i).Name + "---"+ dbdata.get(i).MemId + "---" +  dbdata.get(i).Attendance );
//                i++;
//            }





//            if(json==null)
//                return null;

            // check log cat fro response
//            Log.d("Create Response", json.toString());

            // check for success tag
//                try {
//                    int success = json.getInt("success");
//                    res = json.getString("message");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }




            return null;
        }

    }
}
