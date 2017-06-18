package therabbit.project_test04.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;

import therabbit.project_test04.R;
import therabbit.project_test04.util.CodeAllProcess;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class FragmentMyFriendsKey extends Fragment {

    private String path_program_keyfriends = Environment.getExternalStorageDirectory() + File.separator+ "SMS_Secure_CA/keyfriends/";
    ListView friends_list;
    private ArrayList<Bitmap> img;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    CheckBox checkBox;
    private Toolbar toolbar;
    private items[] xxx;
    private boolean cc = false;
    private KeyMyFriendsAdepter adepter = null;
    Menu mm = null;
    private ArrayList<items> list;

    public FragmentMyFriendsKey() {
        super();
    }

    @SuppressWarnings("unused")
    public static FragmentMyFriendsKey newInstance() {
        FragmentMyFriendsKey fragment = new FragmentMyFriendsKey();
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
        View rootView = inflater.inflate(R.layout.fragment_myfriends_key, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        //((MyFriendsActivity) getActivity()).getDelegate().setSupportActionBar(toolbar);

        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (cc == true){
            inflater.inflate(R.menu.tolbar_menu,menu);
            cc = false;
        }
        //else inflater.inflate(R.menu.main,menu);
        mm = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        MenuInflater menuInflater = new SupportMenuInflater(getContext());

        int id = item.getItemId();
        if (id == R.id.checkAll){
            System.out.println("CheckAll");
            adepter.All();
            return true;
        }
        if (id == R.id.delete){
            System.out.println("Delete");
            File file = null;
            ArrayList<File> tosend = new ArrayList();
            ArrayList<Uri> files = new ArrayList<Uri>();

            ArrayList<CheckBox> ll = new ArrayList();
            ll = adepter.get();
            System.out.println(ll.size());
            System.out.println(list.size());

            for (int i = 0; i < ll.size(); i++) {
                if (ll.get(i).isChecked()){
                    file = new File(path_program_keyfriends+list.get(i).getName()+"_"+list.get(i).getAddress()+".PublicKey");
                    file.delete();



                    System.out.println(adepter);

                }
            }
            Toast.makeText(getActivity(),"Delete success!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            getActivity().overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
            return true;
        }
        if (id == R.id.export_text) {
            System.out.println("Export");
            File file = null;
            ArrayList<String> tosend = new ArrayList();
            ArrayList<Uri> files = new ArrayList<Uri>();

            ArrayList<CheckBox> ll = new ArrayList();
            ll = adepter.get();
            System.out.println(ll.size());
            System.out.println(list.size());

            for (int i = 0; i < ll.size(); i++) {
                if (ll.get(i).isChecked()){
                    file = new File(list.get(i).getName()+"_"+list.get(i).getAddress()+".PublicKey");
                    tosend.add(file.toString());
                    files.add(Uri.fromFile(file));



                }
            }
            if (tosend.size() == 1){
                CodeAllProcess allProcess = new CodeAllProcess();
                PublicKey publicKey = allProcess.load_public_key(tosend.get(0));
                String text = "AAAAAAA";
                String link = org.spongycastle.util.encoders.Base64.toBase64String(publicKey.getEncoded());
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                Intent chooserIntent = Intent.createChooser(shareIntent, "Share");

                // for 21+, we can use EXTRA_REPLACEMENT_EXTRAS to support the specific case of Facebook
                // (only supports a link)
                // >=21: facebook=link, other=text+link
                // <=20: all=link
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                    Bundle facebookBundle = new Bundle();
                    facebookBundle.putString(Intent.EXTRA_TEXT, link);
                    Bundle replacement = new Bundle();
                    replacement.putBundle("com.facebook.katana", facebookBundle);
                    chooserIntent.putExtra(Intent.EXTRA_REPLACEMENT_EXTRAS, replacement);
                } else {
                    shareIntent.putExtra(Intent.EXTRA_TEXT, link);
                }

                chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chooserIntent);
            }
            else {

                Toast.makeText(getActivity(),"Please select one by one", Toast.LENGTH_SHORT).show();
            }
            System.out.println(tosend);





            /*Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Avshajnsajklms");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));*/


            return true;
        }
        if (id == R.id.export_file){

            File file = null;
            ArrayList<Uri> files = new ArrayList<Uri>();
            ArrayList<CheckBox> ll = new ArrayList();
            ll = adepter.get();
            for (int i = 0; i < ll.size(); i++) {
                if (ll.get(i).isChecked()){
                    file = new File(path_program_keyfriends+list.get(i).getName()+"_"+list.get(i).getAddress()+".PublicKey");
                    files.add(Uri.fromFile(file));



                }
            }
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Here are some files.");
            intent.setType("application/*");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            startActivity(intent);
            return true;
        }
        if (id == R.id.cancel) {
            System.out.println("Cancel");
            if (adepter != null) {
                adepter.cancel();

                mm.clear();
                //menuInflater.inflate(R.menu.main,mm);


            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        CodeAllProcess allProcess = new CodeAllProcess();
        ArrayList<String> xx = allProcess.find_Public_name_On_friends();

        img  = new ArrayList<>();

        xxx = (items[]) getActivity().getLastNonConfigurationInstance();
        list = new ArrayList<items>();
        for (int i = 0; i < xx.size(); i++) {
            String vv = xx.get(i);
            int st = vv.indexOf("_");
            int en = vv.indexOf(".");
            String str = vv.substring(st+1,en);
            address.add(str);
            String str2 = vv.substring(0,vv.indexOf("_"));
            name.add(str2);
            img.add(allProcess.getContactsDetails(getActivity(),str));
            list.add(new items(str2,str,allProcess.getContactsDetails(getActivity(),str),false));
        }


        friends_list = (ListView) rootView.findViewById(R.id.list_key_my_friends);

        adepter = new KeyMyFriendsAdepter(getActivity(),list);
        friends_list.setAdapter(adepter);





        friends_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                cc = true;
                SelectViewHolder viewHolder = (SelectViewHolder) view.getTag();

                viewHolder.checkBox.setVisibility(View.VISIBLE);
                adepter.xx();


                getActivity().invalidateOptionsMenu();

                friends_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        items planet = (list.get(i));
                        System.out.println(planet);
                        planet.toggleChecked();
                        SelectViewHolder viewHolder = (SelectViewHolder) view
                                .getTag();
                        viewHolder.getCheckBox().setChecked(planet.isChecked());
                    }
                });

                /*System.out.println(adepter.getCount());
                System.out.println(R.id.check_list);
                System.out.println(adapterView);
                checkBox = (CheckBox) adapterView.findViewById(R.id.check_list);
                view.setVisibility(view.VISIBLE);
                view.setSelected(true);
                adepter.xx();
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.setTitle("asasas");
                toolbar.inflateMenu(R.menu.tolbar_menu);*/
                //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);


                /*System.out.println(checkBox.isSelected());
                friends_list.setItemsCanFocus(false);
                for (int j = 0; j < adepter.getCount(); j++) {
                    friends_list.setItemChecked(j,true);
                    checkBox.setVisibility(view.VISIBLE);
                }*/
                return true;
            } //list is my listView


        });


    }


    public Object onRetainNonConfigurationInstance() {
        return xxx;
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
