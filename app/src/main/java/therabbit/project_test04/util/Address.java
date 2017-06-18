package therabbit.project_test04.util;

import java.util.ArrayList;

/**
 * Created by root on 9/1/16.
 */

public class Address {
    ArrayList name = new ArrayList();
    ArrayList sms_id = new ArrayList();

    public Address(ArrayList name,ArrayList id){

        this.sms_id = (id);
        this.name = name;
        System.out.println(this.sms_id);
        System.out.println(this.name);
    }
}
