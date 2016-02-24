package ieeesbcet.com.ieeecss;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
//import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    Attendance db;
    JSONArray dbArray;
    public String urlip;
    JSONParser jsonParser = new JSONParser();
    JSONArrayParser jsonArrayParser = new JSONArrayParser();
    String urldb = "http://10.42.0.1/csis/aura/downsync.php";
    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BarCode.class);

                System.out.print("here it is :::::::::::: " + urlip);

                intent.putExtra("urlip", urlip);

                startActivity(intent);
            }
        });


        Button down = (Button) findViewById(R.id.downsync);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DownSync().execute();






            }
        });

        final EditText et = (EditText) findViewById(R.id.serverurl);
        Button btnurl= (Button) findViewById(R.id.urlbtn);
        btnurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlip =  et.getText().toString();
                Toast set = Toast.makeText(getApplicationContext(), "URL Set", Toast.LENGTH_SHORT);
                set.show();
            }
        });

    }



    class DownSync extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {




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
                    String phone = jsonobject.getString("Phone");
                    String branch = jsonobject.getString("Branch");
                    String memid = jsonobject.getString("MemId");
                    String food = jsonobject.getString("Food");
                    String attendance = jsonobject.getString("Attendance");
                    String lastupdate = jsonobject.getString("LastUpdate");

                    System.out.println(name + "..." + email+ "..." + phone+ "..." + branch+ "..." + memid+ "..." + food + "..." + attendance + "..." + lastupdate);

                    BigInteger ph = new BigInteger(phone);
                    int id = Integer.parseInt(memid);
                    Date sqlDate = java.sql.Date.valueOf(lastupdate);
                    int a = Integer.parseInt(attendance);

                    Attendance db = new Attendance("nasfg"); //, email, ph, branch, id, food, a, sqlDate
                    db.save();




//                    System.out.println("Attendance is :::::::: " + a * 2);
//                    Log.d("CREATE RESPONSE " + i , jsonobject.toString());
                }

            }catch(JSONException e){
                e.printStackTrace();

            }


        }
    }
}
