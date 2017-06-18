package therabbit.project_test04.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import therabbit.project_test04.R;

/**
 * Created by Nutherabbit on 8/9/2559.
 */
public class KeyMyFriendsAdepter extends ArrayAdapter<items> {

    private final LayoutInflater inflater;
    ArrayList<String> namekey = new ArrayList<>();
    ArrayList<String> addresskey = new ArrayList<>();
    ArrayList<Bitmap> imgkey = new ArrayList<>();
    ArrayList<CheckBox> arrayList = new ArrayList();
    ArrayList<Boolean> cc = new ArrayList<>();
    ArrayList<items> temp = new ArrayList<items>();
    private ViewHolder holder;
    View vv;
    ArrayList arrayList_view = new ArrayList();

    public KeyMyFriendsAdepter(Activity act, ArrayList<items> list) {
        super(act, R.layout.row, R.id.key_name);
        //this.namekey = name;
        //this.addresskey = add;
        this.temp = list;
        System.out.println("Temp " + temp);
        inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return temp.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView name;
        ImageView imagKey = null;
        CheckBox checkBox;
        vv = convertView;
        items planet = (items)temp.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.key_myfriends_adepter, null);
            //holder = new ViewHolder();
            name = (TextView) convertView.findViewById(R.id.key_name);
            imagKey = (ImageView) convertView.findViewById(R.id.key_img);
            checkBox = (CheckBox) convertView.findViewById(R.id.check_list);

            convertView.setTag(new SelectViewHolder(name, checkBox));
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    items planet = (items) cb.getTag();
                    planet.setChecked(cb.isChecked());
                }
            });

            //vi.setTag(holder);
        } else {
            SelectViewHolder viewHolder = (SelectViewHolder) convertView
                    .getTag();
            checkBox = viewHolder.getCheckBox();
            name = viewHolder.getTextView();

            //holder = (ViewHolder) vi.getTag();
        }

        name.setText(temp.get(position).getName() + " (" + temp.get(position).getAddress() + ")");
        //holder.checkBox.isSelected();
        arrayList.add(checkBox);
        cc.add(checkBox.isSelected());
        checkBox.setTag(planet);
        // Display planet data
        checkBox.setChecked(planet.isChecked());
        arrayList_view.add(checkBox);

        try {
            if (temp.get(position).getImg() != null) imagKey.setImageBitmap(temp.get(position).getImg());
            else imagKey.setImageResource(R.drawable.icon_102120);
        }catch (NullPointerException xxx){}



        return convertView;
    }

    /*static class ViewHolder{

            TextView name;
            ImageView imagKey;
            CheckBox checkBox;

        }*/
    void xx() {
        for (int i = 0; i < arrayList.size(); i++) {
            final CheckBox checkBox = arrayList.get(i);
            checkBox.setVisibility(View.VISIBLE);
            /*final int j = i;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cc.get(j) == false){
                        cc.set(j,true);
                    }
                    else if (cc.get(j) == true){
                        cc.set(j,false);
                    }
                    System.out.println(cc);
                }
            });*/

        }

    }
    void cancel() {
        for (int i = 0; i < arrayList.size(); i++) {
            final CheckBox checkBox = arrayList.get(i);
            checkBox.setVisibility(View.INVISIBLE);
            checkBox.setSelected(false);

        }
        if (arrayList_view != null){
            for (int i = 0; i < arrayList_view.size(); i++) {
                ((CheckBox) arrayList_view.get(i)).setChecked(false);
            }

        }

    }
    public void All(){
        for (int i = 0; i < arrayList_view.size(); i++) {
            ((CheckBox) arrayList_view.get(i)).setChecked(true);
        }
    }
    public ArrayList get(){
        return arrayList_view;
    }


}
