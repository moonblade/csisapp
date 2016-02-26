package ieeesbcet.com.ieeecss;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BarCode extends AppCompatActivity {

    static String urlip;
    String msg, res;
    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

        // url to create new product 100.84.249.115   192.168.1.6   10.42.0.1   192.168.0.102
    private static String url_create_product;// = urlip; //"http://" + urlip + "/csis/aura/insert.php";



    // JSON Node names
    private static final String TAG_SUCCESS = "success";



    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    private Button scanButton;
    private ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = df.format(c.getTime());
    // Now formattedDate have current date/time


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        urlip = i.getExtras().getString("urlip");

        System.out.print("on receiving it is::::::::" + urlip);
        url_create_product = urlip;

        setContentView(R.layout.barcodescanner);

        initControls();
    }

    private void initControls() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(BarCode.this, mCamera, previewCb,
                autoFocusCB);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanButton = (Button) findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                }
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            releaseCamera();
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
//                Log.i("bloo",);
                for (Symbol sym : syms) {

                    Log.i("<<<<<<Asset Code>>>>> ",
                            "<<<<Bar Code>>> " + sym.getData());
//                    String scanResult = sym.getData().trim();
                    Log.d("blah","scan result is :::::::::: is ::::::::::::::::::" + sym.getData());

                    //minesss

//                    scanResult = scanResult.replace("\n", "");
//                    String input = "abcabc pattern1foopattern2 abcdefg pattern1barpattern2 morestuff";
//                    List<String> strings = Arrays.asList( scanResult.replaceAll("^.*?EMAIL:", "").split("TEL.*?(EMAIL:|$)"));//                    String yess = strings
//                    System.out.println( scanResult);

/*
                    String e = scanResult.substring(scanResult.indexOf("EMAIL:") + 6, scanResult.indexOf("TEL")-1);
                    System.out.println("url is ::::::::::::::::::" + e);



                    System.out.println("url is ::::::::::::::::::" + url_create_product);

                    showAlertDialog(scanResult);

                    List<Attendance> candidate = Attendance.find(Attendance.class, "email = ?", e);

                    Attendance c = candidate.get(0);
                    String name = c.name;
                    int due = c.due;

                    Intent intent2 = new Intent(BarCode.this, SecondActivity.class);

                    intent2.putExtra("name", name);
                    intent2.putExtra("due", due);

                    startActivity(intent2);

        */

                  /*  Toast.makeText(BarcodeScanner.this, scanResult,
                            Toast.LENGTH_SHORT).show();*/

                    barcodeScanned = true;


                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };


    private void showAlertDialog(String message) {

        msg = message;
        System.out.println(msg);


        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

//                        new CreateNewProduct().execute();

                        List<Attendance> candidate = Attendance.find(Attendance.class, "email = ?", msg);

                        Attendance c = candidate.get(0);
                        if ((c.lastupdate).equals(formattedDate))
                            Toast.makeText(BarCode.this, "Attendence Marked", Toast.LENGTH_SHORT).show();
                        else {
//                            c.attendance++;
//                            c.lastupdate = formattedDate;
//                            Toast.makeText(BarCode.this, "Attendence Already Marked", Toast.LENGTH_SHORT).show();
//                            c.save();
                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })

                .show();
    }

    private void showResponseAlertDialog(String message) {

//        msg = message;
//        System.out.println(msg);

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })

                .show();
    }


    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BarCode.this); //my insert
            pDialog.setMessage("Marking Attendance..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Marking Attendance
         * */
        protected String doInBackground(String... args) {
//            String name = inputName.getText().toString();
//            String price = inputPrice.getText().toString();
//            String description = inputDesc.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("name", name));
//            params.add(new BasicNameValuePair("price", price));
//            params.add(new BasicNameValuePair("description", description));
       params.add(new BasicNameValuePair("msg", msg));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

//            if(json==null)
//                return null;

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
             int success = json.getInt(TAG_SUCCESS);
                res = json.getString("message");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            showResponseAlertDialog(res);
            res="";

        }

    }

}