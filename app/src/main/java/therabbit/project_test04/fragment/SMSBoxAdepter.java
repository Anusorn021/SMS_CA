package therabbit.project_test04.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;

import therabbit.project_test04.R;

/**
 * Created by root on 8/31/16.
 */
public class SMSBoxAdepter extends BaseAdapter{
    private LayoutInflater inflater ;
    ArrayList<HashMap<String,String>> data;
    private ViewHolder holder;
    ArrayList<Bitmap> bitmaps;
    ArrayList<String> disPlayName;

    public SMSBoxAdepter(Activity act, ArrayList<HashMap<String, String>> map, ArrayList<String> disPlayName, ArrayList<Bitmap> bit){
        this.data = map;
        this.bitmaps = bit;
        this.disPlayName = disPlayName;

        inflater = (LayoutInflater) act
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vi = view;
        if (view == null){
            vi = inflater.inflate(R.layout.row,null);
            holder = new ViewHolder();
            holder.address = (TextView) vi.findViewById(R.id.lblNumber);
            holder.body = (TextView) vi.findViewById(R.id.lblMsg);
            //holder.imageView1 = (ImageView) vi.findViewById(R.id.list_image);
            holder.date_box = (TextView) vi.findViewById(R.id.date_on_sms_box);
            holder.circularImageView = (CircularImageView) vi.findViewById(R.id.list_image);
// Set Border
            holder.circularImageView.setBorderColor(Color.parseColor("#F5F5F5"));
            holder.circularImageView.setBorderWidth(0);
            holder.circularImageView.setMinimumWidth(60);
            holder.circularImageView.setMinimumHeight(60);
            holder.circularImageView.addShadow();
            holder.circularImageView.setShadowRadius(0);
            holder.circularImageView.setShadowColor(Color.parseColor("#4FC3F7"));

            vi.setTag(holder);
        }
        else holder = (ViewHolder) vi.getTag();

        holder.address.setText(disPlayName.get(position));
        holder.body.setText(data.get(position).get("Body"));
        holder.date_box.setText(data.get(position).get("Date"));

        if (bitmaps != null) {

            if (bitmaps.get(position) != null )holder.circularImageView.setImageBitmap(bitmaps.get(position));
            else {
                holder.circularImageView.setImageResource(R.drawable.ic_xxx );
            }
        }

        return vi;


    }
    static class ViewHolder{

        CircularImageView circularImageView;

        TextView address;
        TextView body;
        ImageView imageView1;
        TextView date_box;
    }
}
