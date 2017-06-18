package therabbit.project_test04.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import therabbit.project_test04.R;

/**
 * Created by root on 9/1/16.
 */
public class ChatListAdepter extends BaseAdapter{

    public ArrayList<HashMap<String, String>> data;
    ArrayList<String> Body;
    ArrayList<String> Add;
    LayoutInflater inflater;
    private ViewHolder holder;
    private ViewHolder2 holder2;

    public ChatListAdepter(Activity activity, ArrayList<HashMap<String, String>> add) {
        this.data = add;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if (data.get(position).get("Type").toString().equals("1")) {
            convertView = null;
            if (convertView == null) {

                vi = inflater.inflate(R.layout.chat_sender_item, null, false);
                holder2 = new ViewHolder2();
                vi.setTag(holder2);


            } else holder2 = (ViewHolder2) vi.getTag();

            holder2.messageTextView2 = (TextView) vi.findViewById(R.id.message_text_sen);
            holder2.timeTextView = (TextView) vi.findViewById(R.id.time_text_sen);
            holder2.address = (TextView) vi.findViewById(R.id.text_address_sen);

            holder2.messageTextView2.setText(data.get(position).get("Body"));
            holder2.timeTextView.setText(data.get(position).get("Date"));
            holder2.address.setText(data.get(position).get("Address"));

        }

        if (data.get(position).get("Type").toString().equals("2")) {
            convertView = null;

            if (convertView == null) {
                vi = inflater.inflate(R.layout.chat_reciver_item, null, false);
                holder = new ViewHolder();
                vi.setTag(holder);

            }
            else {
                holder = (ViewHolder) vi.getTag();
            }
            holder.messageTextView = (TextView) vi.findViewById(R.id.message_text_ri);
            holder.timeTextView = (TextView) vi.findViewById(R.id.time_text_ri);
            holder.messageTextView.setText(data.get(position).get("Body"));
            holder.timeTextView.setText(data.get(position).get("Date"));

        }



        return vi;


    }
    public void updateAdapter(ArrayList<HashMap<String, String>> arrylst) {
        this.data= arrylst;
        notifyDataSetChanged();
    }

}


class ViewHolder{

    TextView messageTextView;
    TextView timeTextView;

}

class ViewHolder2{

    TextView messageTextView2;
    TextView timeTextView;
    TextView address;
}
