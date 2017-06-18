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

import java.security.PrivateKey;
import java.security.PublicKey;

import therabbit.project_test04.R;
import therabbit.project_test04.manager.Compresstion;
import therabbit.project_test04.manager.Encrypt;
import therabbit.project_test04.manager.Sing;
import therabbit.project_test04.util.CodeAllProcess;
import therabbit.project_test04.util.SmsLengthCalculator;

/**
 * Created by root on 8/29/16.
 */
public class Si_En_Com_AsyncTask extends AsyncTask<String, Integer, Long>{
    ProgressDialog progressDialog;
    Context context;
    private Activity activity;
    private View view;
    byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f2e2f");
    byte[] encoding   = Hex.decode("303132333435363738393a3b3c3d3e3f");
    IESParameterSpec params;
    SmsLengthCalculator smsLengthCalculator = new SmsLengthCalculator();
    CodeAllProcess allProcess = new CodeAllProcess();
    String public_key_name;
    String plantext;
    String sing_text;
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

    public Si_En_Com_AsyncTask(Context context1, Activity activity1, ProgressDialog progressDialog1, View view1, EditText editText1){
        this.progressDialog = progressDialog1;
        this.context = context1;
        this.activity = activity1;
        this.view = view1;
        this.editText = editText1;
        original_text = editText1.getText().toString();
        progressDialog = new ProgressDialog(context);

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

        long startTime = System.nanoTime();
        public_key_name = strings[0];
        plantext = strings[1];
        params = new IESParameterSpec(derivation, encoding, 128, 256);
        privateKey_onload = allProcess.load_private_key();
        publicKey_onload = allProcess.load_public_key(public_key_name);

        System.out.println("Plan text "+original_text);
        // compress
        String text_com = compresstion.ThaitoEng(plantext,context);
        System.out.println("Compress "+text_com);
        // sing
        String text_sing = sing.Signature(text_com,privateKey_onload);
        text_sing = "@S$"+text_com+"#S#"+text_sing;
        System.out.println("Sing "+text_sing);
        // encrypt
        text_encrypt = encrypt.Encrypt(params,publicKey_onload,text_sing);
        System.out.println("Encrypt "+text_encrypt);
        long endTime = System.nanoTime();

        duration = ((endTime - startTime) / 1000000);
        Log.d("Length Time(ms) ", duration + " ms");
        return duration;
    }

    @Override
    protected void onPostExecute(Long aLong) {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;

            int sms = smsLengthCalculator.getPartCount(text_encrypt);
            showDialogConfirm(sms,text_encrypt);
            Log.d("SMS Length",text_encrypt);
            Log.d("Length All",(text_encrypt).length()+"");

        }


    }


    public void showDialogConfirm(int sms, final String en) {
        new MaterialDialog.Builder(context)
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize()
                .title("The number of SMS after processing "+sms+" SMS")
                .content(en)
                .positiveText("confirm")
                .negativeText("cancel")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (dialog.isPromptCheckBoxChecked()) {
                            show_agen = true;
                            check_confrim = true;
                        }
                        else {
                            check_confrim = false;
                            show_agen = false;
                        }
                        //showToast("Prompt checked? " + dialog.isPromptCheckBoxChecked());

                        if (which.name().equals("POSITIVE")) {

                            editText.setText(en);
                        }
                        else System.out.println("cancel");
                    }

                })
                .checkBoxPromptRes(R.string.dont_ask_again, false, null)
                .show();
    }
}
