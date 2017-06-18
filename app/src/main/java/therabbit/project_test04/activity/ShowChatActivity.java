package therabbit.project_test04.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import therabbit.project_test04.AsyncTask.De_AsyncTask;
import therabbit.project_test04.R;
import therabbit.project_test04.fragment.ChatListAdepter;


public class ShowChatActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> ListData;
    ArrayList<HashMap<String, String>> ListOfAdepter;
    HashMap<String, String> data;
    ListView chat_list_view;
    private ImageView enterChatView1, setting;
    private EditText input_msg;
    private Integer select_option[] = {};
    boolean[] check = {false, false, false};
    private boolean show_agen = false;
    private TextView date_box;
    private Handler handler = new Handler();

    De_AsyncTask decrypt_asyncTask;
    ProgressDialog progressBar;
    ChatListAdepter adepter;
    private MenuItem icon;
    private MenuItem titel;
    private SharedPreferences shared;
    private String sharedKey = "SMSCA";
    private String pass_key = "pass";
    String num;
    private boolean isNumber = false;
    private final String[] m_Codes = {"376", "971", "93", "355", "374", "599",
            "244","672", "54", "43", "61", "297", "994", "387", "880", "32", "226", "359", "973", "257", "229", "590", "673", "591", "55", "975", "267", "375", "501", "1", "61", "243",
            "236", "242", "41", "225", "682", "56", "237", "86", "57", "506", "53", "238", "61", "357", "420", "49", "253", "45", "213", "593", "372", "20", "291", "34", "251", "358", "679", "500",
            "691", "298", "33", "241", "44", "995", "233", "350", "299", "220", "224", "240", "30", "502", "245", "592", "852", "504", "385", "509", "36", "62", "353", "972", "44", "91", "964", "98", "39",
            "962", "81", "254", "996", "855", "686", "269", "850", "82", "965", "7", "856", "961", "423", "94", "231", "266", "370", "352", "371", "218", "212", "377", "373", "382", "261", "692", "389", "223",
            "95", "976", "853", "222", "356", "230", "960", "265", "52", "60", "258", "264", "687", "227", "234", "505", "31", "47", "977", "674", "683", "64", "968", "507", "51", "689", "675", "63", "92",
            "48", "508", "870", "1", "351", "680", "595", "974", "40", "381", "7", "250", "966", "677", "248", "249", "46", "65", "290", "386", "421", "232", "378", "221", "252", "597", "239", "503", "963", "268", "235", "228", "66", "992",
            "690", "670", "993", "216", "676", "90", "688", "886", "255", "380", "256", "1", "598", "998", "39", "58", "84", "678", "681", "685", "967", "262", "27", "260", "263"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showchat2);
        shared = getBaseContext().getSharedPreferences(sharedKey, Context.MODE_PRIVATE);

        chat_list_view = (ListView) findViewById(R.id.chat_list);
        enterChatView1 = (ImageView) findViewById(R.id.enter_chat1);
        enterChatView1.setOnClickListener(clickListener);
        setting = (ImageView) findViewById(R.id.optio_btn_on_chatlist);
        setting.setOnClickListener(clickListener);
        input_msg = (EditText) findViewById(R.id.input_msg);
        input_msg.setOnKeyListener(keyListener);
        input_msg.addTextChangedListener(watcher1);
        date_box = (TextView) findViewById(R.id.date_on_sms_box);
        progressBar = new ProgressDialog(this);

        initInstances();

        try {
            Intent intent = getIntent();
            num = intent.getStringExtra("ADD");
            setTitle(num);
            ArrayList<HashMap<String, String>> ChatListOnClick = new ArrayList<HashMap<String, String>>();
            final ArrayList<HashMap<String, String>> SMSALL;
            ArrayList arrayList = intent.getParcelableArrayListExtra("List");

            if (arrayList ==  null){
                SMSALL = getSMS();
                HashMap<String, String> map;
                for (int i = 0; i < SMSALL.size(); i++) {
                    if (num.equals(SMSALL.get(i).get("Address"))){
                        map = new HashMap<>();
                        map.put("ID", SMSALL.get(i).get("ID"));
                        map.put("Type", SMSALL.get(i).get("Type"));
                        map.put("Body", SMSALL.get(i).get("Body"));
                        map.put("Address", SMSALL.get(i).get("Address"));
                        map.put("Date", SMSALL.get(i).get("Date"));
                        ChatListOnClick.add(map);
                    }
                }
                ListOfAdepter = ChatListOnClick;
            }else {
                ListData = (ArrayList<HashMap<String, String>>) arrayList.get(0);
                ListOfAdepter = ListData;
            }


        }catch (NullPointerException e){
            e.printStackTrace();
        }


        Collections.reverse(ListOfAdepter);
        adepter = new ChatListAdepter(this, ListOfAdepter);
        chat_list_view.setAdapter(adepter);

    }

    public ArrayList<HashMap<String, String>> getSMS() {

        Uri inboxURI = Uri.parse("content://sms");
        String[] reqCols = new String[]{"_id", "address", "body", "thread_id", "service_center", "person", "date", "protocol", "read", "status", "type", "reply_path_present", "subject", "locked"};
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(inboxURI, reqCols, null, null, null);
        HashMap<String, String> map = new HashMap<>();
        ArrayList<HashMap<String, String>> arr = new ArrayList<HashMap<String, String>>();

        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("address")) != null) {

                String add = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));
                String id = c.getString(c.getColumnIndex("_id"));
                String type = c.getString(c.getColumnIndex("type"));

                try {
                    Integer.parseInt(add.charAt((add.length()-1))+"");
                    isNumber = true;
                }catch (NumberFormatException xx){
                    isNumber = false;
                }
                if (isNumber){
                    for (int i = 0; i < m_Codes.length; i++) {
                        String m = m_Codes[i];
                        add = add.replace("+"+m,"0");
                    }

                    add = add.replace('-',' ');
                    add = add.replaceAll("\\(","");
                    add = add.replaceAll("\\)","");
                    add = add.replaceAll("\\s","");
                }

                int dat = c.getColumnIndex("date");
                long timeMillis = c.getLong(dat);
                Date date = new Date(timeMillis);
                String formattedDate = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a").format(date);

                map = new HashMap<>();
                map.put("ID", id);
                map.put("Type", type);
                map.put("Body", body);
                map.put("Address",  add);
                map.put("Date", formattedDate);
                arr.add(map);

            }
            c.moveToNext();
        }
        c.close();
        return arr;
    }


    public void showDialogOption() {

        final ArrayList<Integer> integers = new ArrayList<>();

        new MaterialDialog.Builder(this)
                .title(R.string.titleDialogOption)
                .items(R.array.select_option)
                .itemsCallbackMultiChoice(select_option, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder str = new StringBuilder();
                        integers.clear();
                        check = new boolean[]{false, false, false};
                        for (int i = 0; i < which.length; i++) {
                            integers.add(which[i]);
                            check[which[i]] = true;

                        }
                        if (integers.size() > 0) {
                            select_option = integers.toArray(new Integer[integers.size()]);
                            setting.setImageResource(R.drawable.ic_verified_user_green_a700_24dp);
                        } else {
                            select_option = new Integer[]{};
                            if (input_msg.getText().toString().equals(""))setting.setImageResource(R.drawable.ic_verified_user_grey_500_24dp);
                            if (!input_msg.getText().toString().equals(""))setting.setImageResource(R.drawable.ic_verified_user_red_600_24dp);
                        }

                        return true; // allow selection


                    }
                })
                .negativeText(R.string.md_cancel_lable)
                .positiveText(R.string.md_choose_label)
                .show();
    }


    private final TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (input_msg.getText().toString().equals("")) {

            } else {
                enterChatView1.setImageResource(R.drawable.ic_chat_send);
                setting.setImageResource(R.drawable.ic_verified_user_grey_500_24dp);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 0) {
                enterChatView1.setImageResource(R.drawable.ic_chat_send);
                if (select_option.length > 0) setting.setImageResource(R.drawable.ic_verified_user_green_a700_24dp);
                else setting.setImageResource(R.drawable.ic_verified_user_grey_500_24dp);
            } else {
                enterChatView1.setImageResource(R.drawable.ic_chat_send_active);
                if (select_option.length > 0) setting.setImageResource(R.drawable.ic_verified_user_green_a700_24dp);
                else setting.setImageResource(R.drawable.ic_verified_user_red_600_24dp);
            }
        }
    };

    /* onclick img send sms*/
    private ImageView.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == enterChatView1) {
                if ((input_msg.getText().toString()).equals("")) {
                    Toast.makeText(getBaseContext(), "Please write message", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("MSG " + input_msg.getText());
                    for (int i = 0; i < select_option.length; i++) {
                        System.out.print(select_option[i]+" ");
                    }
                    System.out.println();
                    input_msg.setText("");
                }
            }

            if (v == setting) {
                showDialogOption();
            }


        }
    };

    /* input onkey*/
    private EditText.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press

                EditText editText = (EditText) v;

                if (v == input_msg) {
                    if ((editText.getText().toString()).equals("")) {
                        Toast.makeText(getBaseContext(), "Please write message", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("MSG2 " + input_msg.getText());


                        for (int i = 0; i < check.length; i++) {
                            System.out.println(check[i]);

                        }
                        for (int i = 0; i < select_option.length; i++) {
                            System.out.println(select_option[i]);
                        }
                    }

                }

                input_msg.setText("");

                return true;
            }
            return false;

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.decrypt_menu, menu);
        icon = menu.findItem(R.id.decrypt_icon);
        titel = menu.findItem(R.id.Decrypt_titel);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.decrypt_icon:
                if (titel.getTitle().equals("Decrypt")) {
                    confirmPass();
                    break;
                }
                if (titel.getTitle().equals("Cancel")) {
                    adepter.updateAdapter(ListOfAdepter);
                    icon.setIcon(R.drawable.ic_visibility_off_white_24dp);
                    titel.setTitle("Decrypt");
                    break;
                }

            case R.id.Decrypt_titel:

                if (titel.getTitle().equals("Decrypt")) {
                    confirmPass();
                    break;
                }
                if (titel.getTitle().equals("Cancel")) {
                    adepter.updateAdapter(ListOfAdepter);
                    icon.setIcon(R.drawable.ic_visibility_off_white_24dp);
                    titel.setTitle("Decrypt");
                    break;
                }

            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
                break;
            default:
                return super.onOptionsItemSelected(item);


        }
        return true;
    }

    public void confirmPass() {
        final String pass = shared.getString(pass_key, "null");

        LayoutInflater li = LayoutInflater.from(getBaseContext());
        View promptsView = li.inflate(R.layout.dialog_delete_key, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom2);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("Confirm passphrase!");

        alertDialogBuilder.setMessage(R.string.confirmPassDialog);
        alertDialogBuilder.setPositiveButton("Ok", null);
        alertDialogBuilder.setNegativeButton("Cancel", null);

        alertDialogBuilder.setCancelable(false);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        final AlertDialog mAlertDialog = alertDialogBuilder.create();
        final ShowChatActivity dd = this;
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (!pass.equals(userInput.getText().toString())) {
                            Toast.makeText(getBaseContext(), "Passphrase is incorrect!", Toast.LENGTH_SHORT).show();
                            userInput.setText("");

                        } else {
                            HashMap<String, String> map = new HashMap<String, String>();
                            ProgressDialog progressBar = new ProgressDialog(dd);
                            String temp = "";
                            for (int j = 0; j < ListOfAdepter.size(); j++) {
                                map = ListOfAdepter.get(j);
                                String body = map.get("Body");
                                temp = temp + body + "##SS##";

                            }
                            decrypt_asyncTask = new De_AsyncTask(ShowChatActivity.this, progressBar, adepter, ListOfAdepter,num);
                            decrypt_asyncTask.execute(temp);
                            icon.setIcon(R.drawable.ic_visibility_white_24dp);
                            titel.setTitle("Cancel");
                            mAlertDialog.dismiss();
                        }
                    }
                });
                Button b2 = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAlertDialog.dismiss();
                    }
                });

            }
        });
        mAlertDialog.show();
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
