package therabbit.project_test04.fragment;

import android.graphics.Bitmap;

/**
 * Created by Nutherabbit on 10/9/2559.
 */
public class items {
    private String name = "";
    private String add = "";
    private Bitmap bb = null;
    private boolean checked = false;

    public items() {
    }

    public items(String name) {
        this.name = name;
    }

    public items(String name,String ad,Bitmap b, boolean checked) {
        this.name = name;
        this.add = ad;
        this.bb = b;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return add;
    }

    public void setAddress(String ad) {
        this.add = ad;
    }
    public Bitmap getImg() {
        return bb;
    }

    public void setImg(Bitmap b) {
        this.bb = b;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString() {
        return name;
    }

    public void toggleChecked() {
        checked = !checked;
    }
}
