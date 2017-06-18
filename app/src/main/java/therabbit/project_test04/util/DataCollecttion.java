package therabbit.project_test04.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 9/1/16.
 */
public class DataCollecttion {
    ArrayList id = new ArrayList();
    ArrayList type = new ArrayList();
    ArrayList body = new ArrayList();
    ArrayList addre = new ArrayList();
    ArrayList<Address> ad = new ArrayList<>();

    public DataCollecttion(ArrayList i,ArrayList ty,ArrayList bo,ArrayList add,Address a){
        this.id = i;
        this.type = ty;
        this.body = bo;
        this.ad.add(a);
        this.addre = add;
        System.out.println(id);
        System.out.println(type);
        System.out.println(body);
        System.out.println(ad);
        for (int j = 0; j < ad.size(); j++) {
            if (addre.get(j).equals(ad.get(j).name)){

                final ArrayList<HashMap<String, HashMap<String, String>>> myList5 = new ArrayList<>();
                HashMap<String, HashMap<String, String>> data5 = null;
                data5 = new HashMap<String, HashMap<String, String>>();
                myList5.add(data5);
            }
        }
    }
}
