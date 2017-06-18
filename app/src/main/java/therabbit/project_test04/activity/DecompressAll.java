package therabbit.project_test04.activity;

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

import therabbit.project_test04.manager.Compresstion;
import therabbit.project_test04.manager.Decompress;
import therabbit.project_test04.manager.Decrypt;
import therabbit.project_test04.manager.Encrypt;
import therabbit.project_test04.manager.Sing;
import therabbit.project_test04.manager.Verify;
import therabbit.project_test04.util.CodeAllProcess;
import therabbit.project_test04.util.SmsLengthCalculator;

/**
 * Created by Nutherabbit on 26/9/2559.
 */

public class DecompressAll extends AsyncTask<String, Integer, Long> {
    ProgressDialog progressDialog;
    Context context;
    private Activity activity;
    private View view;
    byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f2e2f");
    byte[] encoding = Hex.decode("303132333435363738393a3b3c3d3e3f");
    IESParameterSpec params;
    SmsLengthCalculator smsLengthCalculator = new SmsLengthCalculator();
    CodeAllProcess allProcess = new CodeAllProcess();
    String public_key_name;
    String plantext;
    String sing_text;
    String sms_input;
    EditText editText;
    private boolean check_confrim = false;
    private long duration;
    private PrivateKey privateKey_onload;
    String original_text;
    private boolean show_agen = false;
    Compresstion compresstion = new Compresstion();
    Encrypt encrypt = new Encrypt();
    Sing sing = new Sing();
    private PublicKey publicKey_onload;
    String text_encrypt;
    String temp_sms = "";

    public DecompressAll(Context context1, Activity activity1, ProgressDialog progressDialog1, View view1, String editText1) {
        this.progressDialog = progressDialog1;
        this.context = context1;
        this.activity = activity1;
        this.view = view1;
        this.sms_input = editText1;
        progressDialog = new ProgressDialog(context);
        System.out.println(sms_input);

    }

    @Override
    protected void onPreExecute() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Long doInBackground(String... strings) {


        boolean en = false;
        boolean si = false;
        boolean com = false;
        if (sms_input.contains("@E$")) en = true;
        if (sms_input.contains("@S$")) si = true;
        if (sms_input.contains("¿")) com = true;
        long startTime = System.nanoTime();
        CodeAllProcess allProcess = new CodeAllProcess();
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
        return duration;
    }

    public String getTemp_sms() {
        return temp_sms;
    }

    @Override
    protected void onPostExecute(Long aLong) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;

        }


    }


}
