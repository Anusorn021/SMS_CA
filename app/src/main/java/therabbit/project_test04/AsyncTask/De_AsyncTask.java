package therabbit.project_test04.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.encoders.Hex;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import therabbit.project_test04.fragment.ChatListAdepter;
import therabbit.project_test04.manager.Decompress;
import therabbit.project_test04.manager.Decrypt;
import therabbit.project_test04.manager.Verify;
import therabbit.project_test04.util.CodeAllProcess;

/**
 * Created by Nutherabbit on 28/9/2559.
 */

public class De_AsyncTask extends AsyncTask<String, Integer, String> {

    CodeAllProcess allProcess = new CodeAllProcess();
    String temp_sms = "";
    ProgressDialog progressDialog;
    Context context;
    Verify verify = new Verify();
    Decompress decompress = new Decompress();
    Decrypt decrypt = new Decrypt();
    String temp = "";
    ChatListAdepter chatListAdepter;
    HashMap<String, String> map = new HashMap<String, String>();
    HashMap<String, String> map2 = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> mapArrayList = new ArrayList<HashMap<String, String>>();
    String add;
    long startTime = System.nanoTime();


    private ArrayList arrayList2;

    public De_AsyncTask(Context context, ProgressDialog pro, ChatListAdepter zzz, ArrayList<HashMap<String, String>> listData, String num){
        this.context = context;
        this.progressDialog = pro;
        this.chatListAdepter = zzz;
        progressDialog = new ProgressDialog(this.context);
        this.mapArrayList = listData;
        this.add = num;
    }
    protected String doInBackground(String... params)   {
        long startTime = System.nanoTime();
        boolean en = false;
        boolean si = false;
        boolean com = false;
        String vv[] = params;
        ArrayList arrayList = new ArrayList();

        String data = vv[0];
        arrayList2 = new ArrayList();
        int cnt = 1;
        for (String item : data.split("##SS##")) {
            arrayList.add(item);
            cnt++;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            String sms_input = (String) arrayList.get(i);

            if (sms_input.contains("@E$")) en = true;
            else en = false;
            if (sms_input.contains("@S$")) si = true;
            else si = false;
            if (sms_input.contains("¿")) com = true;
            else com = false;

            System.out.println("SMS Input "+sms_input);


            if (en) {
                PrivateKey privateKey = allProcess.load_private_key();
                byte[] derivation = Hex.decode("202122232425262728292a2b2c2d2e2f2e2f");
                byte[] encoding = Hex.decode("303132333435363738393a3b3c3d3e3f");
                IESParameterSpec paramss;
                paramss = new IESParameterSpec(derivation, encoding, 128, 256);
                temp_sms = sms_input.substring(3);
                String de = decrypt.Decrypt(paramss, privateKey, temp_sms);
                temp_sms = de;


            } else {
                temp_sms = sms_input;
            }

            if (temp_sms.contains("@S$")) si = true;
            else si = false;
            if (temp_sms.contains("¿")) com = true;
            else com = false;


            if (si) {
                PublicKey publicKey = allProcess.seach_public_key(add);
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

                String mg = decompress.EngtoThai(temp_sms, context);
                System.out.println("Decompress " + mg);
                temp_sms = mg;

            } else {
                temp_sms = temp_sms;
            }

            temp = temp+temp_sms+"##SS##";
            arrayList2.add(temp_sms);
            temp_sms = "";
        }
        long endTime = System.nanoTime();


        long duration = ((endTime - startTime) / 1000000);
        Log.d("Time(ms) ", duration + " ms");





        return temp;
    }

    protected void onProgressUpdate(Integer... values) {

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

    protected void onPostExecute(String result)  {
        ArrayList<HashMap<String, String>> mapArrayList2 = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < mapArrayList.size(); i++) {
            map = mapArrayList.get(i);
            String id = map.get("ID");
            String body;
            if (map.get("Type").equals("1")) body = (String) arrayList2.get(i);
            else body = map.get("Body");
            String date = map.get("Date");
            String type = map.get("Type");
            String add = map.get("Address");

            map2 = new HashMap<>();

            map2.put("ID",id);
            map2.put("Body",body);
            map2.put("Date",date);
            map2.put("Type",type);
            map2.put("Address",add);
            mapArrayList2.add(map2);
            System.out.println("MAP "+map2);
        }

        chatListAdepter.updateAdapter(mapArrayList2);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


}