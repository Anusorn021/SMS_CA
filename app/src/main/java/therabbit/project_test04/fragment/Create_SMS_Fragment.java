package therabbit.project_test04.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.encoders.Hex;

import java.io.File;
import java.io.FilenameFilter;
import java.security.PublicKey;
import java.util.ArrayList;

import therabbit.project_test04.AsyncTask.En_Com_AsyncTask;
import therabbit.project_test04.AsyncTask.Encrypt_AsyncTask;
import therabbit.project_test04.AsyncTask.Si_Com_AsyncTask;
import therabbit.project_test04.AsyncTask.Si_En_AsyncTask;
import therabbit.project_test04.AsyncTask.Si_En_Com_AsyncTask;
import therabbit.project_test04.AsyncTask.Sing_AsyncTask;
import therabbit.project_test04.R;
import therabbit.project_test04.activity.ShowChatActivity;
import therabbit.project_test04.manager.Compresstion;
import therabbit.project_test04.manager.Encrypt;
import therabbit.project_test04.util.SmsLengthCalculator;

/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class Create_SMS_Fragment extends Fragment implements View.OnClickListener, View.OnKeyListener{

    private final int PICK_CONTAC = 1001;
    Button option_btn;
    Button to_btn;
    Button send;
    Button process_btn;
    EditText edit_text_write_sms;
    ArrayList<Integer> mMultiSelected;
    private static final String[] Option = {"Sing", "Encrypt", "Compress"};
    EditText tel_send_edit;
    private String temp = "";
    private String path_program = Environment.getExternalStorageDirectory() + File.separator+ "SMS_Secure_CA";
    private int n = 0;
    private Toast mToast;
    PublicKey publicKey_onload;
    private ProgressDialog progressBar;
    Encrypt_AsyncTask encrypt_asyncTask = null;
    Sing_AsyncTask sing_asyncTask = null;
    View vv = null;
    private Integer select_option[] = {};
    private boolean show_agen = false;
    boolean[] check = {false,false,false};
    private String receiver_tel = "";
    private  String receiver_name;

    public Create_SMS_Fragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static Create_SMS_Fragment newInstance() {
        Create_SMS_Fragment fragment = new Create_SMS_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_sms, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        option_btn = (Button) rootView.findViewById(R.id.option_btn);
        option_btn.setOnClickListener(this);

        to_btn = (Button) rootView.findViewById(R.id.to_btn);
        to_btn.setOnClickListener(this);

        tel_send_edit = (EditText) rootView.findViewById(R.id.edit_text_send_tel);
        tel_send_edit.setOnKeyListener(this);
        tel_send_edit.setOnClickListener(this);


        send = (Button) rootView.findViewById(R.id.send_sms_btn);
        send.setOnClickListener(this);

        process_btn = (Button) rootView.findViewById(R.id.process_btn);
        process_btn.setOnClickListener(this);

        edit_text_write_sms  = (EditText) rootView.findViewById(R.id.edit_text_write_sms);
        edit_text_write_sms.addTextChangedListener(tw_edit_text_write_sms);
        edit_text_write_sms.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTAC) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    System.out.println(contactData);
                    Cursor c =  getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id =
                                c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =
                                c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            String phn_no = phones.getString(phones.getColumnIndex("data1"));
                            String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));

                            setReceiver_name(name);
                            phn_no = phn_no.replaceAll("\\s","");
                            tel_send_edit.setText(phn_no+" ["+name+"]");


                        }
                    }
                }
                break;
        }

    }

    private TextWatcher tw_edit_text_write_sms = new TextWatcher() {

        public void afterTextChanged(Editable s){

        }
        public void  beforeTextChanged(CharSequence s, int start, int count, int after){

        }
        public void  onTextChanged (CharSequence s, int start, int before,int count) {

        }
    };

    public void showDialogConfirm(int sms, final String en) {
        new MaterialDialog.Builder(getActivity())
                .title("The number of SMS after processing "+sms+" SMS")
                .content(en)
                .positiveText("confirm")
                .negativeText("cancel")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if (which.name().equals("POSITIVE")) {
                            edit_text_write_sms.setText(en);
                        }
                        else System.out.println("cancel");
                    }
                })
                .show();
    }

    public String find_Public_key_on_tel(final String tel){

        File dir = new File(path_program+"/keyfriends/");
        System.out.println("TT "+tel);
        String nn = null;
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("[^_]*_"+tel+".PublicKey"); /*#########  *tel*  ############*/
            }
        });
        for (File file : files){
            nn = file.getName();
        }
        return nn;
    }

    public void process_on_option(boolean[] option){
        // sign encrypt compress
        byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f2e2f");
        byte[] encoding   = Hex.decode("303132333435363738393a3b3c3d3e3f");
        IESParameterSpec params;
        params = new IESParameterSpec(derivation, encoding, 128, 256);
        Encrypt encrypt = new Encrypt();
        SmsLengthCalculator sms_cal = new SmsLengthCalculator();
        progressBar = new ProgressDialog(getContext());
        System.out.println(receiver_tel);
        /*if (option[2] || option[1]){
            String public_key_name = find_Public_key_on_tel(toNumber);
            try {
                if (!public_key_name.equals("")) {
                    load_public_key(public_key_name);
                    public_key_name = "";
                    toNumber = "";

                }
            }
            catch (NullPointerException xxxx){
                Toast.makeText(getActivity(),"Please import public key if sing or encrypt!", Toast.LENGTH_LONG).show();

            }
        }*/

        if (option[0] == false && option[1] == false && option[2] == false){
            // not select option
        }
        if (option[0] == true && option[1] == false && option[2] == false){
            // sing
            String public_key_name = find_Public_key_on_tel(receiver_tel);


            try {
                if (!public_key_name.equals("")) {

                    String[] str = new String[]{public_key_name,edit_text_write_sms.getText().toString()};
                    sing_asyncTask = new Sing_AsyncTask(getContext(),getActivity(),progressBar,getView(),edit_text_write_sms);
                    sing_asyncTask.execute(str);
                }
            }
            catch (NullPointerException xxx){
                Toast.makeText(getActivity(),"Please import public key if sing or encrypt!", Toast.LENGTH_LONG).show();

            }
        }
        if (option[0] == false && option[1] == true && option[2] == false){
            // encrypt
            String public_key_name = find_Public_key_on_tel(receiver_tel);

            try {
                if (!public_key_name.equals("")) {

                    String[] str = new String[]{public_key_name,edit_text_write_sms.getText().toString()};
                    encrypt_asyncTask = new Encrypt_AsyncTask(getContext(),getActivity(),progressBar,getView(),edit_text_write_sms);
                    encrypt_asyncTask.execute(str);
                }
            }
            catch (NullPointerException xxx){
                Toast.makeText(getActivity(),"Please import public key if sing or encrypt!", Toast.LENGTH_LONG).show();

            }


        }
        if (option[0] == false && option[1] == false && option[2] == true){
            // compress
            Compresstion compresstion = new Compresstion();
            String com = compresstion.ThaitoEng(edit_text_write_sms.getText().toString(),getActivity());

            int sms = sms_cal.getPartCount(com);
            showDialogConfirm(sms,com);

        }
        if (option[0] == true && option[1] == true && option[2] == false){
            // sing + encrypt
            String public_key_name = find_Public_key_on_tel(receiver_tel);

            try {
                if (!public_key_name.equals("")) {

                    String[] str = new String[]{public_key_name,edit_text_write_sms.getText().toString()};
                    Si_En_AsyncTask si_en_asyncTask = new Si_En_AsyncTask(getContext(),getActivity(),progressBar,getView(),edit_text_write_sms);
                    si_en_asyncTask.execute(str);
                }
            }
            catch (NullPointerException xxx){
                Toast.makeText(getActivity(),"Please import public key if sing or encrypt!", Toast.LENGTH_LONG).show();

            }
        }
        if (option[0] == true && option[1] == false && option[2] == true){
            // sing + compress
            String public_key_name = find_Public_key_on_tel(receiver_tel);

            try {
                if (!public_key_name.equals("")) {

                    String[] str = new String[]{public_key_name,edit_text_write_sms.getText().toString()};
                    Si_Com_AsyncTask si_com_asyncTask = new Si_Com_AsyncTask(getContext(),getActivity(),progressBar,getView(),edit_text_write_sms);
                    si_com_asyncTask.execute(str);
                }
            }
            catch (NullPointerException xxx){
                Toast.makeText(getActivity(),"Please import public key if sing or encrypt!", Toast.LENGTH_LONG).show();

            }
        }
        if (option[0] == false && option[1] == true && option[2] == true){
            // encrypt + compress
            String public_key_name = find_Public_key_on_tel(receiver_tel);

            try {
                if (!public_key_name.equals("")) {

                    String[] str = new String[]{public_key_name,edit_text_write_sms.getText().toString()};
                    En_Com_AsyncTask en_com_asyncTask = new En_Com_AsyncTask(getContext(),getActivity(),progressBar,getView(),edit_text_write_sms);
                    en_com_asyncTask.execute(str);
                }
            }
            catch (NullPointerException xxx){
                Toast.makeText(getActivity(),"Please import public key if sing or encrypt!", Toast.LENGTH_LONG).show();

            }
        }
        if (option[0] == true && option[1] == true && option[2] == true){
            // sing + encrypt + compress
            String public_key_name = find_Public_key_on_tel(receiver_tel);

            try {
                if (!public_key_name.equals("")) {

                    String[] str = new String[]{public_key_name,edit_text_write_sms.getText().toString()};
                    Si_En_Com_AsyncTask si_en_com_asyncTask = new Si_En_Com_AsyncTask(getContext(),getActivity(),progressBar,getView(),edit_text_write_sms);
                    si_en_com_asyncTask.execute(str);
                }
            }
            catch (NullPointerException xxx){
                Toast.makeText(getActivity(),"Please import public key if sing or encrypt!", Toast.LENGTH_LONG).show();

            }
        }
    }

    public void showDialogOption() {


        final ArrayList<Integer> integers = new ArrayList<>();

        new MaterialDialog.Builder(getContext())
                .title(R.string.titleDialogOption)
                .items(R.array.select_option)
                .itemsCallbackMultiChoice(select_option, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        StringBuilder str = new StringBuilder();
                        integers.clear();
                        check = new boolean[]{false,false,false};
                        for (int i = 0; i < which.length; i++) {
                            if (i > 0) str.append('\n');
                            str.append(which[i]);
                            str.append(": ");
                            str.append(text[i]);
                            integers.add(which[i]);
                            check[which[i]] = true;

                        }

                        if (integers != null){
                            select_option = integers.toArray(new Integer[integers.size()]);
                        }
                        else select_option = new Integer[]{};

                        //check_option(check);
                        return true; // allow selection

                    }
                })
                .negativeText(R.string.md_cancel_lable)
                .positiveText(R.string.md_choose_label)
                .show();
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_tel(String receiver_tel) {
        String temp = receiver_tel.replace('(',' ');
        if (temp.indexOf('[') != -1) temp = temp.substring(0,temp.indexOf('['));
        temp = temp.replace('-',' ');
        temp = temp.replace(')',' ');
        if (temp.charAt(0) == '+'){
            temp = temp.replace("+66","0");

        }
        temp = temp.replaceAll("\\s","");
        this.receiver_tel = temp;
    }

    @Override
    public void onClick(View view) {
        if (view == option_btn){

            if(edit_text_write_sms.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Please write sms!", Toast.LENGTH_SHORT).show();
            }
            else if (tel_send_edit.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Please select number to send!", Toast.LENGTH_SHORT).show();
            }
            else {
                showDialogOption();

            }

        }
        if (view == to_btn){

            Intent intent2 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent2, PICK_CONTAC);

        }
        if (view == send){
            if ((tel_send_edit.getText().toString()).equals("")){
                Toast.makeText(getActivity(),"Please select number to send!", Toast.LENGTH_SHORT).show();
            }
            else if(edit_text_write_sms.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Please write sms!", Toast.LENGTH_SHORT).show();
            }
            else {

                System.out.println(tel_send_edit.getText().toString().indexOf('['));
                String num = "";
                if (tel_send_edit.getText().toString().indexOf('[') != -1){
                    num = tel_send_edit.getText().toString().substring(0,tel_send_edit.getText().toString().indexOf('[')-1);
                }
                else {
                    num = tel_send_edit.getText().toString();
                    num = num.replace('-',' ');
                    num = num.replace(')',' ');
                    if (num.charAt(0) == '+'){
                        num = num.replace("+66","0");

                    }
                    num = num.replaceAll("\\s","");
                }

                System.out.println("Num "+num);
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(tel_send_edit.getText().toString(), null, edit_text_write_sms.getText().toString(), null, null);
                Toast.makeText(getActivity(), "SMS sent.", Toast.LENGTH_LONG).show();
            }

            catch (Exception e) {
                Toast.makeText(getActivity(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

                Intent intent2 = new Intent(getActivity(),ShowChatActivity.class);
                intent2.putExtra("ADD",num);
                startActivityForResult(intent2,0);
                getActivity().overridePendingTransition( R.anim.trans_left_in, R.anim.trans_left_out );

            }


        }
        if (view == process_btn){
            if(edit_text_write_sms.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Please write sms!", Toast.LENGTH_SHORT).show();
            }
            else if (tel_send_edit.getText().toString().equals("")){
                Toast.makeText(getActivity(),"Please select number to send!", Toast.LENGTH_SHORT).show();
            }
            else {
                setReceiver_tel(tel_send_edit.getText().toString());
                process_on_option(check);
                // if (show_agen == false) showDialogOption();

            }
        }
    }


    @Override
    public boolean onKey(View view, int i,KeyEvent keyEvent) {


        int l = tel_send_edit.getText().length();
        if (l > 12)  tel_send_edit.setText("");
        if ((keyEvent.getKeyCode()-7) >= 0 && (keyEvent.getKeyCode()-7) <= 9){
            if (tel_send_edit.getText().length() == 3) tel_send_edit.append("-");
        }

        if (l == 12){
            tel_send_edit.getText().delete(l-1,l);
        }


        return false;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

}
