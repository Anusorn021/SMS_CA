package therabbit.project_test04.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.security.PublicKey;

import therabbit.project_test04.R;
import therabbit.project_test04.util.CodeAllProcess;

public class ImportKeyActivity extends AppCompatActivity implements View.OnClickListener {


    public Toolbar toolbar;
    ImageButton select;
    TextView showfile;
    Button phonenumber;
    Button importKey;
    EditText tel_friends;
    EditText text_key;
    private static final int FILE_SELECT_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.impoer_key_activity);
        initInstances();
        setTitle("Import public key");

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        //setSupportActionBar(toolbar);

        select = (ImageButton) findViewById(R.id.bb);
        select.setOnClickListener(this);
        showfile = (TextView) findViewById(R.id.text);
        phonenumber = (Button) findViewById(R.id.phone_number);
        phonenumber.setOnClickListener(this);
        importKey = (Button) findViewById(R.id.import_public_kry);
        importKey.setOnClickListener(this);
        tel_friends = (EditText) findViewById(R.id.tel_my_friend);
        tel_friends.setFocusable(false);
        text_key = (EditText) findViewById(R.id.text_public_key);
        text_key.setFocusable(false);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("XXX", "File Uri: " + uri.toString());
                    // Get the path
                    String path = uri.getPath();
                    Log.d("XXX", "File Path: " + path);
                    showfile.setText(path);
                }
                break;
            case 1001:
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id =
                                c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =
                                c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            String phn_no = phones.getString(phones.getColumnIndex("data1"));
                            String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));
                            String temp = phn_no.replace('(', ' ');
                            temp = temp.replace('-', ' ');
                            temp = temp.replace(')', ' ');
                            temp = temp.replaceAll("\\s", "");
                            tel_friends.setText(temp + " [" + name + "]");
                        }
                    }
                }
                break;
        }


    }

    @Override
    public void onClick(View view) {

        if (view == select) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(
                        Intent.createChooser(intent, "Select a Public Key to Import"),
                        FILE_SELECT_CODE);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        if (view == phonenumber) {
            Intent intent2 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent2, 1001);
        }
        if (view == importKey) {
            String path = showfile.getText().toString();
            final String number = tel_friends.getText().toString();
            final String text_pu = text_key.getText().toString();
            String saveNamePuplic = "";

            if ((!path.equals("")) && (number.equals("") && text_pu.equals(""))){
                if (path.endsWith(".PublicKey")){
                    saveNamePuplic = path.substring(path.lastIndexOf('/')+1,path.length());
                    final CodeAllProcess allProcess = new CodeAllProcess();
                    final PublicKey publicKey = allProcess.load_public_key_with_path(path);
                    boolean cc = allProcess.savePublicKey(publicKey,saveNamePuplic);
                    if (cc)Toast.makeText(this, "Import public key success", Toast.LENGTH_SHORT).show();
                    else {
                        final String finalSaveNamePuplic = saveNamePuplic;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        allProcess.savePublicKeyOverW(publicKey, finalSaveNamePuplic);
                                        Toast.makeText(getBaseContext(), "Import public key success", Toast.LENGTH_SHORT).show();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Public Key is exist are you sure overwrite?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                    }
                }
                else {
                    Toast.makeText(this, "File is not Public Key type", Toast.LENGTH_SHORT).show();
                }
            }
            else if ((path.equals("")) && (!number.equals("") && !text_pu.equals(""))){

                String num = number.substring(0,number.indexOf('[')-1);
                final String name = number.substring(number.indexOf('[')+1,number.lastIndexOf(']'));
                System.out.println(num);
                System.out.println(name);
                final CodeAllProcess allProcess = new CodeAllProcess();
                File file = new File(allProcess.path_program_keyfriends);
                File[] s = file.listFiles();
                boolean cc = false;
                for (int i = 0; i < s.length; i++) {
                    String tempN = s[i].getName().substring(0,s[i].getName().indexOf('_'));
                    String tempA = s[i].getName().substring(s[i].getName().indexOf('_')+1,s[i].getName().length()-10);
                    if (num.equals(tempA) && name.equals(tempN)){
                        cc = true;
                        break;
                    }
                }


                if (cc == true){
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    allProcess.savePublicKeyWithText(name,number,text_pu);
                                    Toast.makeText(getBaseContext(), "Import public key success", Toast.LENGTH_SHORT).show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Public Key is phone number is exist are you sure overwrite?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                else {
                    allProcess.savePublicKeyWithText(name,num,text_pu);
                    Toast.makeText(getBaseContext(), "Import public key success", Toast.LENGTH_SHORT).show();
                }

                //allProcess.savePublicKeyWithText(name,num,text_pu);
            }
            else {
                Toast.makeText(this, "Please select import with file or import with text",
                        Toast.LENGTH_SHORT).show();
            }


        }
    }

}
