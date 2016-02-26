package ieeesbcet.com.ieeecss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    int workshop=0;
    String name;
    int dueamt;
    String due;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = df.format(c.getTime());
    // Now formattedDate have current date/time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        name = i.getExtras().getString("name");

        dueamt = i.getExtras().getInt("due");

        TextView n = (TextView) findViewById(R.id.Name);
        n.setText(name);

        TextView d = (TextView) findViewById(R.id.Due);
        if (dueamt==0)
            d.setText("DUE : NO");
        else
            d.setText("DUE : YES");

//


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.embedded:
                if (checked)
                    workshop = 1;
                    break;
            case R.id.python:
                if (checked)
                    workshop = 2;
                break;
            case R.id.web:
                if (checked)
                    workshop = 3;
                break;

        }

        Button s = (Button) findViewById(R.id.save);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("workshop update : "+ workshop);
                List<Attendance> candidate = Attendance.find(Attendance.class, "name = ?", name);
                Attendance c = candidate.get(0);
                c.wrkshp = workshop;
                c.attendance++;
                c.lastupdate = formattedDate;
                c.save();

                finish();

//                Intent intent3 = new Intent(SecondActivity.this, MainActivity.class);
//                startActivity(intent3);

            }
        });


    }

}
