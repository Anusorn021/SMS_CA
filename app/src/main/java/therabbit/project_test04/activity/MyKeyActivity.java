package therabbit.project_test04.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.spongycastle.util.encoders.Base64;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import therabbit.project_test04.R;
import therabbit.project_test04.util.CodeAllProcess;

public class MyKeyActivity extends AppCompatActivity {

    EditText pri;
    EditText pub;
    Button export;
    Button delete;
    public String path_program_keyme = Environment.getExternalStorageDirectory() + File.separator+ "SMS_Secure_CA/keyme/";
    private String path_program = Environment.getExternalStorageDirectory() + File.separator + "SMS_Secure_CA";

    public Toolbar toolbar;
    private SharedPreferences shared;
    private String sharedKey = "SMSCA";
    private String pass_key = "pass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_key);
        initInstances();
        setTitle("My Key Pair");
        shared = getBaseContext().getSharedPreferences(sharedKey, Context.MODE_PRIVATE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        pri = (EditText) findViewById(R.id.textPrivate);
        pub = (EditText) findViewById(R.id.textPublic);
        int x = height*80/100;
        pri.getLayoutParams().height = x/2;
        pub.getLayoutParams().height = x/2;
        System.out.println(height);
        final CodeAllProcess allProcess = new CodeAllProcess();
        File file = new File(path_program_keyme);
        File[] s = file.listFiles();
        final ArrayList<Uri> files = new ArrayList<Uri>();

        String pp = "";
        String pu = "";
        for (int i = 0; i < s.length; i++) {
            if (s[i].getName().endsWith(".PrivateKey")) pp = s[i].getName();
            if (s[i].getName().endsWith(".PublicKey")) {
                pu = s[i].getName();
                files.add(Uri.fromFile(s[i]));
            }
        }
        final PublicKey publicKey = allProcess.load_public_key_with_me(pu);
        pub.setText(Base64.toBase64String(publicKey.getEncoded()));
        PrivateKey privateKey = allProcess.load_private_key();
        pri.setText(Base64.toBase64String(privateKey.getEncoded()));

        export = (Button) findViewById(R.id.exportMyPublicKey);
        delete = (Button) findViewById(R.id.deleteMyKeyPair);


        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
                intent.setType("application/*");
                intent.putExtra(Intent.EXTRA_STREAM, files);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File dir = new File(path_program+"/keyme");
                final File ll[] = dir.listFiles();
                if (ll.length != 0){
                    showDeleteDialog();
                }
                else {
                    Toast.makeText(getBaseContext(),"Not Key Pair!", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }

    public void showDeleteDialog(){
        File dir = new File(path_program+"/keyme");
        final String pass = shared.getString(pass_key,"null");
        System.out.println(pass);
        final File ll[] = dir.listFiles();
        LayoutInflater li = LayoutInflater.from(getBaseContext());
        View promptsView = li.inflate(R.layout.dialog_delete_key,null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialogCustom2);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("Warning!");
        alertDialogBuilder.setIcon(R.drawable.ic_highlight_off_red_a400_48dp);
        alertDialogBuilder.setMessage(R.string.MSG_Dialog_delete_key);
        alertDialogBuilder.setPositiveButton("Ok", null);
        alertDialogBuilder.setNegativeButton("Cancel", null);
        alertDialogBuilder.setCancelable(true);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        final AlertDialog mAlertDialog = alertDialogBuilder.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (pass.equals(userInput.getText().toString())){
                            for (int i = 0; i < ll.length; i++) {
                                ll[i].delete();

                            }
                            pub.setText("");
                            pri.setText("");
                            Toast.makeText(getBaseContext(),"Delete Key Pair Success", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                        else Toast.makeText(getBaseContext(), "Passphrase is incorrect!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        mAlertDialog.show();


    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.tolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
