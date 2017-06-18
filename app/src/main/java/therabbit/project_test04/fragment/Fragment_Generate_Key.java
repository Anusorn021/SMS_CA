package therabbit.project_test04.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import therabbit.project_test04.R;
import therabbit.project_test04.manager.generate_key;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class Fragment_Generate_Key extends Fragment implements View.OnKeyListener {

    EditText show_key;
    EditText edit_name;
    EditText edit_pass;
    EditText edit_tel;
    private String passphrase;
    RadioGroup radioGroup_key_size;
    private RadioButton radioSexButton;
    Button gen_key;
    Button copy;
    private KeyPair keypair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String path_program = Environment.getExternalStorageDirectory() +File.separator+ "SMS_Secure_CA/keyme";
    private SharedPreferences shared;
    private String sharedKey = "SMSCA";
    private String pass_key = "pass";
    public Fragment_Generate_Key() {
        super();
    }

    @SuppressWarnings("unused")
    public static Fragment_Generate_Key newInstance() {
        Fragment_Generate_Key fragment = new Fragment_Generate_Key();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        shared = getContext().getSharedPreferences(sharedKey, Context.MODE_PRIVATE);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generate_keypair, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(final View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here


        edit_name = (EditText)rootView.findViewById(R.id.edit_text_input_name);
        edit_pass = (EditText)rootView.findViewById(R.id.edit_text_input_pass);
        edit_tel = (EditText) rootView.findViewById(R.id.edit_text_tel);
        edit_tel.setOnKeyListener(this);

        show_key = (EditText) rootView.findViewById(R.id.edit_text_show_key);
        show_key.setFocusable(false);

        radioGroup_key_size=(RadioGroup)rootView.findViewById(R.id.radio_group);

        gen_key = (Button) rootView.findViewById(R.id.genkey_btn);
        gen_key.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup_key_size.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioSexButton = (RadioButton) rootView.findViewById(selectedId);
                int key_size = Integer.parseInt(radioSexButton.getText().toString());
                String name = edit_name.getText().toString();
                passphrase = edit_pass.getText().toString();
                String tel = edit_tel.getText().toString();
                tel = tel.replace("-","");

                generate_key gen = new generate_key();


                /* ############# check private key in folder ############# */
                File[] fileList = getFileList(path_program);


                if (!name.equals("") && !passphrase.equals("") && !tel.equals("")){
                    if (fileList.length == 0){
                        keypair = gen.genkey(key_size);
                        publicKey = keypair.getPublic();
                        privateKey = keypair.getPrivate();

                        gen.saveKey(publicKey,privateKey,name+"_"+tel);
                        Toast.makeText(getActivity(),"generate key pair success! ", Toast.LENGTH_SHORT).show();
                        String public_key = org.spongycastle.util.encoders.Base64.toBase64String(publicKey.getEncoded());
                        Log.d("Public key ",public_key);
                        show_key.setText(public_key);

                        // save passphrase
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString(pass_key,passphrase);
                        editor.commit();
                    }
                    else Toast.makeText(getActivity(),"Key pair is exist!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Please input information!", Toast.LENGTH_SHORT).show();
                }


            }

        });

        copy = (Button)rootView.findViewById(R.id.copy);
        copy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /* ############# copy to clipboard ############# */
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", show_key.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getActivity(),
                        "Copy Success!", Toast.LENGTH_SHORT).show();

            }

        });

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

    private static File[] getFileList(String dirPath) {
        File dir = new File(dirPath);

        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".PrivateKey");
            }
        });
        return fileList;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        int l = edit_tel.getText().length();
        if (l > 12)  edit_tel.setText("");
        if ((keyEvent.getKeyCode()-7) >= 0 && (keyEvent.getKeyCode()-7) <= 9){
            if (edit_tel.getText().length() == 3) edit_tel.append("-");
        }

        if (l == 12){
            edit_tel.getText().delete(l-1,l);
        }
        return false;
    }
}
