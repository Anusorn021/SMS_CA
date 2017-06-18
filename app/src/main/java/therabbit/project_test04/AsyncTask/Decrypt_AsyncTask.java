package therabbit.project_test04.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.encoders.Hex;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import therabbit.project_test04.fragment.ChatListAdepter;
import therabbit.project_test04.manager.Decompress;
import therabbit.project_test04.manager.Decrypt;
import therabbit.project_test04.manager.Encrypt;
import therabbit.project_test04.manager.Verify;
import therabbit.project_test04.util.CodeAllProcess;
import therabbit.project_test04.util.SmsLengthCalculator;

/**
 * Created by root on 8/28/16.
 */
public class Decrypt_AsyncTask extends AsyncTask<String, Integer, String>{

    ProgressDialog progressDialog;
    Context context;
    private Activity activity;
    private View view;
    byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f2e2f");
    byte[] encoding   = Hex.decode("303132333435363738393a3b3c3d3e3f");
    IESParameterSpec params;

    Encrypt encrypt = new Encrypt();
    SmsLengthCalculator smsLengthCalculator = new SmsLengthCalculator();
    CodeAllProcess allProcess = new CodeAllProcess();
    private PublicKey publicKey_onload;
    String public_key_name;
    String plantext;
    String encrypt_text;
    EditText editText;
    private long duration;
    String xx = "";
    Decrypt decrypt = new Decrypt();
    String temp_sms = "";
    ChatListAdepter adepter;
    ArrayList<HashMap<String, String>> Listdata;
    private HashMap<String, String> map = new HashMap<>();
    private ArrayList<HashMap<String, String>> mapArrayList = new ArrayList<>();

    public Decrypt_AsyncTask(Context context1, Activity activity1, ProgressDialog progressDialog1, ChatListAdepter zzz, ArrayList<HashMap<String, String>> listData){
        this.progressDialog = progressDialog1;
        this.context = context1;
        this.activity = activity1;
        this.Listdata = listData;
        this.adepter = zzz;
        progressDialog = new ProgressDialog(context);

    }

    @Override
    protected void onPreExecute() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Decrypting...");
        progressDialog.setMessage("please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        long startTime = System.nanoTime();
        boolean en = false;
        boolean si = false;
        boolean com = false;
        String vv[] = strings;
        String sms_input = vv[0];
        if (sms_input.contains("@E$")) en = true;
        if (sms_input.contains("@S$")) si = true;
        if (sms_input.contains("¿")) com = true;


        if (en) {
            Decrypt decrypt = new Decrypt();

            PrivateKey privateKey = allProcess.load_private_key();
            byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f2e2f");
            byte[] encoding = Hex.decode("303132333435363738393a3b3c3d3e3f");
            IESParameterSpec params;
            params = new IESParameterSpec(derivation, encoding, 128, 256);
            temp_sms = sms_input.substring(3);
            String de = decrypt.Decrypt(params, privateKey, temp_sms);
            temp_sms = de;



        } else {
            temp_sms = sms_input;
        }

        if (temp_sms.contains("@S$")) si = true;
        else si = false;
        if (temp_sms.contains("¿")) com = true;
        else com = false;


        if (si) {
            Verify verify = new Verify();
            PublicKey publicKey = allProcess.load_public_key_me();
            verify.Verify(temp_sms, publicKey);
            String xxx = verify.getText();
            System.out.println("Verify " + xxx);
            temp_sms = xxx;
        } else {
            temp_sms = temp_sms;

        }

        if (temp_sms.contains("¿")) com = true;
        else com = false;

        if (com) {
            Decompress decompress = new Decompress();
            String mg = decompress.EngtoThai(temp_sms, context);
            System.out.println("Decompress " + mg);
            temp_sms = mg;

        } else {
            temp_sms = temp_sms;
        }





        long endTime = System.nanoTime();


        duration = ((endTime - startTime) / 1000000);

        return temp_sms;
    }

    @Override
    protected void onPostExecute(String temp_sms) {
        super.onPostExecute(temp_sms);
        for (int j = 0; j < Listdata.size(); j++) {
            map = Listdata.get(j);
            String id = Listdata.get(j).get("ID");
            String body = Listdata.get(j).get("Body");
            String date = temp_sms;
            String type = Listdata.get(j).get("Type");
            String add = Listdata.get(j).get("Address");
            map.put("ID",id);
            map.put("Body",body);
            map.put("Date",date);
            map.put("Type",type);
            map.put("Address",add);
            mapArrayList.add(map);
        }
        adepter.updateAdapter(mapArrayList);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }


    }




}
