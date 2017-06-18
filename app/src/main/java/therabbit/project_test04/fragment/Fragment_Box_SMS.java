package therabbit.project_test04.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import therabbit.project_test04.R;
import therabbit.project_test04.activity.ShowChatActivity;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class Fragment_Box_SMS extends Fragment implements View.OnClickListener {

    EditText sms_box;
    Button select_sms;

    TextView lblMsg, lblNo;
    ListView listView;
    ImageView imageView;
    ArrayList<Bitmap> Image = new ArrayList<Bitmap>();
    ArrayList<String> disPlayName = new ArrayList<String>();
    ProgressDialog dialog;
    ArrayList<HashMap<String, String>> ChatListOnClick;
    HashMap<String, String> data;
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
    List<String> code = new ArrayList<>(Arrays.asList(m_Codes));

    public Fragment_Box_SMS() {
        super();
    }

    @SuppressWarnings("unused")
    public static Fragment_Box_SMS newInstance() {
        Fragment_Box_SMS fragment = new Fragment_Box_SMS();
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
        View rootView = inflater.inflate(R.layout.fragment_box_sms, container, false);
        initInstances(rootView, savedInstanceState);

        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        //sms_box = (EditText)rootView.findViewById(R.id.edit_text_sms_box);
        //select_sms = (Button)rootView.findViewById(R.id.select_sms_sms_box);
        //select_sms.setOnClickListener(this);


        listView = (ListView) rootView.findViewById(R.id.list_box_sms);


        try {

            ArrayList<String> strings2 = new ArrayList<>();
            HashMap<String, String> hashMap;
            final ArrayList<HashMap<String, String>> ListOnPageSMSBox = new ArrayList<>();
            final ArrayList<HashMap<String, String>> SMSALL = getSMS();



            for (int i = 0; i < SMSALL.size(); i++) {
                String tem = SMSALL.get(i).get("Address");
                if (!strings2.contains(tem)){
                    hashMap = new HashMap<>();
                    strings2.add(tem);
                    hashMap.put("Address", tem);
                    hashMap.put("Body", SMSALL.get(i).get("Body"));
                    hashMap.put("Date",SMSALL.get(i).get("Date"));
                    ListOnPageSMSBox.add(hashMap);
                }
            }

            /* get image form contace*/
            Image.clear();
            disPlayName.clear();
            for (int i = 0; i < ListOnPageSMSBox.size(); i++) {
                String str = ListOnPageSMSBox.get(i).get("Address");
                Image.add(retrieveContactPhoto(getActivity(),str));
                disPlayName.add(retrieveContactDisplay_name(getActivity(),str));
            }


            SMSBoxAdepter SMSBoxAdepter = new SMSBoxAdepter(getActivity(), ListOnPageSMSBox,disPlayName,Image);


            listView.setAdapter(SMSBoxAdepter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    String num = ListOnPageSMSBox.get(position).get("Address");
                    ChatListOnClick = new ArrayList<HashMap<String, String>>();
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

                    Intent intent = new Intent(getActivity(), ShowChatActivity.class);
                    ArrayList list = new ArrayList();
                    list.add(ChatListOnClick);
                    intent.putParcelableArrayListExtra("List", list);
                    intent.putExtra("ADD",disPlayName.get(position));
                    startActivityForResult(intent, 0);
                    getActivity().overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


                }
            });

        } catch (NullPointerException cc) {
        }

        System.out.println(disPlayName);

    }

    public ArrayList<HashMap<String, String>> getSMS() {

        Uri inboxURI = Uri.parse("content://sms");
        String[] reqCols = new String[]{"_id", "address", "body", "thread_id", "service_center", "person", "date", "protocol", "read", "status", "type", "reply_path_present", "subject", "locked"};
        ContentResolver cr = getActivity().getContentResolver();
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

                System.out.println("ADD "+add);



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

    @Override
    public void onClick(View view) {
        if (view == select_sms) {

            Intent intent2 = new Intent(Intent.ACTION_PICK, Uri.parse("content://sms/inbox"));
            startActivityForResult(intent2, 1001);
        }

    }

    private void readContacts(){
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String img = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println(("Name: " + name + ", Phone No: " + phoneNo + ", Img " + img));
                    }
                    pCur.close();
                }
            }
        }
    }


    private String retrieveContactDisplay_name(Context context, String number) {

        String img_url;
        Bitmap bp = null;
        Cursor phonesCursor = null;
        String name = "";


        try {
            Uri phoneUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            phonesCursor = context.getContentResolver().query(phoneUri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        if (phonesCursor != null && phonesCursor.moveToFirst()) {
            name = phonesCursor.getString(0);

        } else {
            name = number;
        }
        return name;

    }

    private Bitmap retrieveContactPhoto(Context context, String number) {

        String img_url;
        Bitmap bp = null;
        Cursor phonesCursor = null;


        try {
            Uri phoneUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            phonesCursor = context.getContentResolver().query(phoneUri, new String[]{ContactsContract.PhoneLookup.PHOTO_URI}, null, null, null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        if (phonesCursor != null && phonesCursor.moveToFirst()) {
            img_url = phonesCursor.getString(0);

            try {
                bp = MediaStore.Images.Media
                        .getBitmap(context.getContentResolver(),
                                Uri.parse(img_url));

            }catch (NullPointerException e){
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bp = null;
        }
        return bp;

    }




}
