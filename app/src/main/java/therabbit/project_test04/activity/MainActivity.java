package therabbit.project_test04.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import therabbit.project_test04.R;
import therabbit.project_test04.fragment.Fragment_Box_SMS;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String path_program = Environment.getExternalStorageDirectory() + File.separator + "SMS_Secure_CA";
    private Runnable mPendingRunnable;
    public Toolbar toolbar;
    private SharedPreferences shared;
    private String sharedKey = "SMSCA";
    private String pass_key = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared = getBaseContext().getSharedPreferences(sharedKey, Context.MODE_PRIVATE);

        String pass = shared.getString(pass_key,"null");
        //confirmPass();
        Log.d("XXX ",pass);



        /* ############# check folder is create ############# */
        File folder = new File(path_program);
        boolean success = true;
        if (!folder.exists()) {
            folder.mkdirs();

        }
        folder = new File(path_program + "/keyme");
        success = true;
        if (!folder.exists()) {
            folder.mkdirs();

        }
        folder = new File(path_program + "/keyfriends");
        success = true;
        if (!folder.exists()) {
            folder.mkdirs();

        }



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateSMSActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        TextView textView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.xxx);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, Fragment_Box_SMS.newInstance())
                    .commit();
        }
    }

    public void confirmPass(){
        final String pass = shared.getString(pass_key,"null");
        LayoutInflater li = LayoutInflater.from(getBaseContext());
        View promptsView = li.inflate(R.layout.dialog_delete_key,null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialogCustom2);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("Confirm passphrase!");

        alertDialogBuilder.setMessage(R.string.confirmPassDialog);
        alertDialogBuilder.setPositiveButton("Ok", null);

        alertDialogBuilder.setCancelable(false);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        final AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (!pass.equals(userInput.getText().toString())){
                            Toast.makeText(getBaseContext(), "Passphrase is incorrect!", Toast.LENGTH_LONG).show();
                            userInput.setText("");
                        }
                        else mAlertDialog.dismiss();
                    }
                });
            }
        });
        mAlertDialog.show();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.show_key) {
            File file = new File(path_program+"/keyme");
            File[] s = file.listFiles();
            if (s.length > 0){
                Intent intent = new Intent(getApplicationContext(),MyKeyActivity.class);
                startActivityForResult(intent,0);
                overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );
            }
            else Toast.makeText(getBaseContext(),"Not Key Pair!", Toast.LENGTH_SHORT).show();

            return true;
        }
        if (id == R.id.action_settings) {
            System.out.println(item.getTitle());
            return true;
        }
        if (id == R.id.gen_key_menu) {

            Intent intent = new Intent(getApplicationContext(), GenerateKeyActivity.class);
            startActivity(intent);
        }
        if (id == R.id.exit_on_menu) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_genkey){
            Intent intent = new Intent(getApplicationContext(),GenerateKeyActivity.class);
            startActivityForResult(intent,0);
            overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );
        }
        if (id == R.id.nav_my_key){
            File file = new File(path_program+"/keyme");
            File[] s = file.listFiles();
            if (s.length > 0){
                Intent intent = new Intent(getApplicationContext(),MyKeyActivity.class);
                startActivityForResult(intent,0);
                overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );
            }
            else Toast.makeText(getBaseContext(),"Not Key Pair!", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.nav_import_key) {
            Intent intent = new Intent(getApplicationContext(), ImportKeyActivity.class);
            startActivityForResult(intent,0);
            overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );
            System.out.println(item.getTitle());
        } else if (id == R.id.nav_friends_key) {
            long startTime = System.nanoTime();
            /*getSupportFragmentManager().beginTransaction()

                    .replace(R.id.contentContainer, FragmentMyFriendsKey.newInstance())
                    .addToBackStack(null)
                    .commit();*/
            Intent intent2 = new Intent(getApplicationContext(),MyFriendsActivity.class);
            startActivityForResult(intent2,0);
            overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );
            long endTime = System.nanoTime();
            long duration = ((endTime - startTime) / 1000000);
            Log.d("Time(ms) ", duration + " ms");
            /*Intent intent = new Intent(getApplicationContext(), MyFriendsActivity.class);
            startActivity(intent);*/

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(),About.class);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {

            finish();
            System.exit(0);

        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Handler handler = new Handler();
        mPendingRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                drawer.closeDrawer(GravityCompat.START);
            }
        };
        handler.postDelayed(mPendingRunnable,500);

        return true;
    }



}
