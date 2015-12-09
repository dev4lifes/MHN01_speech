package com.soft.secretary;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final int REQ_CODE_SPEECH_INPUT = 20;
    private TextView txtSpeechInput;
    private TextView txtdap;
    private TextView txtname;
    private Context context;



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  this.getWindow().requestFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.activity_main);
      isInternetOn();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtSpeechInput= (TextView)findViewById(R.id.txtSpeechInput);
        txtdap=(TextView)findViewById(R.id.dap);
        txtname=(TextView)findViewById(R.id.name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Bật nhận diện giọng nói", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                promptSpeechInput();
            }


        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //dem
//        final Activity MyActivity = this;
//        mWebView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                //Make the bar disappear after URL is loaded, and changes string to Loading...
//                MyActivity.setTitle("Loading...");
//                MyActivity.setProgress(progress * 100); //Make the bar disappear after URL is loaded
//
//                // Return the app name after finish loading
//                if (progress == 100)
//                    MyActivity.setTitle(gettit());
//            }
//        });

    }


    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,new Locale("vi"));
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
    public  void name(){

        boolean firstrun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if (firstrun){
//			Toast.makeText(this, "lan dau", Toast.LENGTH_LONG).show();


//        SharedPreferences pre=getSharedPreferences ("name",MODE_PRIVATE);
//        String user =pre.getString("name", "");
//        txtname.setText("Xin chào"+ user.toString() +"Tôi có thể giúp gì cho bạn ?");


    }}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    if (txtSpeechInput.getText().toString().startsWith("tìm đường")) {
                        //   txtSpeechInput.getText().toString().replace("com","")
                        Toast.makeText(MainActivity.this, txtSpeechInput.getText(), Toast.LENGTH_LONG).show();
                    } else if (txtSpeechInput.getText().toString().startsWith("hôm nay là ngày") || txtSpeechInput.getText().toString().startsWith("ngày hôm nay") || txtSpeechInput.getText().toString().startsWith("ngày hôm nay là") || txtSpeechInput.getText().toString().startsWith("hôm nay ngày") || txtSpeechInput.getText().toString().startsWith("Hôm nay là ngày") || txtSpeechInput.getText().toString().startsWith("Ngày hôm nay") || txtSpeechInput.getText().toString().startsWith("Ngày hôm nay là") || txtSpeechInput.getText().toString().startsWith("Hôm nay ngày")) {

                        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

                        Date date = new Date();
                        txtdap.setText(dateFormat.format(date));


                    }
                 else if (txtSpeechInput.getText().toString().startsWith("Năm nay là năm")){

                    DateFormat dateFormat = new SimpleDateFormat("yyyy");

                    Date date = new Date();
                    txtdap.setText(dateFormat.format(date));


                } else if (result.get(0).toString().startsWith("tìm kiếm", 0)) {

                        Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();

                        String a = result.get(0).replace("tìm kiếm", "");


                        Intent intent = new Intent(this, MyWebView.class);
                        intent.putExtra("url", a);
                        startActivity(intent);


                    } else if (result.get(0).toString().startsWith("Facebook")) {
                        Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(this, MyWebView.class);
                        intent.putExtra("url", "m.facebook.com");
                        startActivity(intent);




                } else if (result.get(0).toString().startsWith("mày là ai")||(result.get(0).toString().startsWith("mày là đứa nào"))||(result.get(0).toString().startsWith("mày tên là gì"))) {
                    Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
                        String a = result.get(0).replace("mày là ai", "mày là ai ?");
                        txtdap.setText("Mình là ");

                    } else if (result.get(0).toString().startsWith("bao giờ có người yêu")||(result.get(0).toString().startsWith("bao giờ mới có người yêu"))) {
                        Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();

                        txtdap.setText("0966248794(với nữ) 0968585496(với nam)");
                    } else if (result.get(0).toString().startsWith("danh ngôn")||(result.get(0).toString().startsWith("danh ngôn hay"))) {
                        Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
                       ArrayList list= new ArrayList();
                        list.add("a");
                        list.add("b");
                        list.add("c");

                        txtdap.setText("Mình là đệ của anh Nam :v");


                    }
                    else if (result.get(0).toString().startsWith("Tìm kiếm", 0)) {
                        Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
                        String a = result.get(0).replace("Tìm kiếm", "");

                        Intent intent = new Intent(this, MyWebView.class);
                        intent.putExtra("url", a);
                        startActivity(intent);

                    }
                    else {

                        Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, MyWebView.class);
                        intent.putExtra("url", result.get(0));
                        startActivity(intent);
                    }

                }
            }
        }
    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ico_mic)
                    .setTitle("Hello")

                    .setMessage("xác nhận thoát ra !" )
                    .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startService(new Intent(MainActivity.this, ChatService.class));
                           finish();

                        }

                    })
                    .setPositiveButton("Không", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })


                    .show();

        }
    }

    @Override
    protected void onDestroy() {
        startService(new Intent(MainActivity.this, ChatService.class));
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(MainActivity.this, "ABC", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // check Internet conenction.
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                //may phuong thuc nay bo roi
            // if connected with internet


            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, "không có kết nối mạng vui lòng thử lại", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}
