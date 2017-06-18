package therabbit.project_test04.fragment;

import android.graphics.Bitmap;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Nutherabbit on 10/9/2559.
 */
public class SelectViewHolder {
        public CheckBox checkBox;
        public Bitmap bitmap;
        public TextView textView;

        public SelectViewHolder() {
        }

        public SelectViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

}
