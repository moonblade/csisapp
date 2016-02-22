package ieeesbcet.com.ieeecss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String urlip;
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
}
