package therabbit.project_test04.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.encoders.Hex;

import java.security.PublicKey;

import therabbit.project_test04.manager.Encrypt;
import therabbit.project_test04.util.CodeAllProcess;
import therabbit.project_test04.util.SmsLengthCalculator;

/**
 * Created by root on 8/28/16.
 */
public class Encrypt_AsyncTask extends AsyncTask<String, Integer, Long>{

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

    public Encrypt_AsyncTask(Context context1,Activity activity1, ProgressDialog progressDialog1,View view1,EditText editText1){
        this.progressDialog = progressDialog1;
        this.context = context1;
        this.activity = activity1;
        this.view = view1;
        this.editText = editText1;
        progressDialog = new ProgressDialog(context);

    }

    @Override
    protected void onPreExecute() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Encrypting...");
        progressDialog.setMessage("please wait ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected Long doInBackground(String... strings) {
        long startTime = System.nanoTime();
        long startTime2 = System.currentTimeMillis();
        public_key_name = strings[0];
        plantext = strings[1];
        params = new IESParameterSpec(derivation, encoding, 128, 256);
        publicKey_onload = allProcess.load_public_key(public_key_name);


        encrypt_text = encrypt.Encrypt(params,publicKey_onload,plantext);
        long endTime = System.nanoTime();

        Log.d("Encrypt 1: ",encrypt_text);
        duration = ((endTime - startTime) / 1000000);
        long endTime2 = System.currentTimeMillis();
        long seconds = (endTime2 - startTime2) / 1000;
        Log.d("Time(ms) ", duration + " ms");
        Log.d("Time(s) ", seconds + " s");
        return duration;
    }

    @Override
    protected void onPostExecute(Long aLong) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;

            int sms = smsLengthCalculator.getPartCount(encrypt_text);
            showDialogConfirm(sms,encrypt_text);
            Log.d("SMS Length: ",encrypt_text);
            Log.d("Length : ",encrypt_text.length()+"");
        }


    }

    public void showDialogConfirm(int sms, final String en) {
        new MaterialDialog.Builder(context)
                .title("The number of SMS after processing "+sms+" SMS")
                .content(en)
                .positiveText("confirm")
                .negativeText("cancel")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if (which.name().equals("POSITIVE")) {
                            editText.setText(en);
                        }
                        else System.out.println("cancel");
                    }
                })
                .show();
    }



}
