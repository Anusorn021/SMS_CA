package therabbit.project_test04.util;

/**
 * Created by root on 8/30/16.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Nutherabbit on 6/7/2559.
 */
public class compress {


    String[] arrayOfEng = null;
    String[] arrayOfThai = null;
    String[] arrayOfFull = null;
    int f = 0;
    int f2 = 0;
    public static void main(String[] args){
        compress c = new compress();
        String pathe = "/root/AndroidStudioProjects/Project_test03/app/src/main/java/therabbit/project_test03/6.txt";
        String pathth = "/root/AndroidStudioProjects/Project_test03/app/src/main/java/therabbit/project_test03/5.txt";
        String pathfull = "/root/AndroidStudioProjects/Project_test03/app/src/main/java/therabbit/project_test03/7.txt";
        String str = "fห นั้นadหd";
        String temstr = str;
        String data = null;
        BufferedReader dataIns = null;
        String temp = "";

        System.out.println("Text : "+str.length()+" "+str);

        try {
            dataIns = new BufferedReader(new InputStreamReader( new FileInputStream(pathe), "UTF-8")); //TIS-620
            while ((data = dataIns.readLine()) != null) {
                temp = data;
            }
            dataIns.close();
            dataIns = null;
            c.arrayOfEng = temp.split("\\s+");
            temp = null;
            //c.arrayOfEng[c.arrayOfEng.length-1] = " ";

            dataIns = new BufferedReader(new InputStreamReader( new FileInputStream(pathth), "UTF-8")); //TIS-620
            while ((data = dataIns.readLine()) != null) {
                temp = data;
            }
            dataIns.close();
            dataIns = null;
            c.arrayOfThai = temp.split("\\s+");
            temp = null;
            //c.arrayOfThai[c.arrayOfThai.length-1] = " ";

            dataIns = new BufferedReader(new InputStreamReader( new FileInputStream(pathfull), "UTF-8")); //TIS-620
            while ((data = dataIns.readLine()) != null) {
                temp = data;
            }
            dataIns.close();
            dataIns = null;
            c.arrayOfFull = temp.split("\\s+");
            temp = null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        c.arrayOfFull[c.arrayOfFull.length-1] = " ";


        System.out.print("Map eng  "+c.arrayOfEng.length+" : ");
        for (int i = 0; i < c.arrayOfEng.length; i++) {
            System.out.print(c.arrayOfEng[i]+" ");
        }
        System.out.println();

        System.out.print("Map thai "+c.arrayOfThai.length+" : ");
        for (int i = 0; i < c.arrayOfThai.length; i++) {
            System.out.print(c.arrayOfThai[i]+" ");
        }System.out.println();

        System.out.print("Map full "+c.arrayOfFull.length+" : ");
        for (int i = 0; i < c.arrayOfFull.length; i++) {
            System.out.print(c.arrayOfFull[i]+" ");
        }

        System.out.println();
        String xx = c.ThaitoEng(str);
        System.out.println("Compression length : "+xx.length()+" : "+xx);
        String xxx = c.EngtoThai(xx);
        System.out.println("Decompression length : "+xxx.length()+" : "+xxx);


        //System.out.println();
        //String xx = c.Compression(str);




    }
    public String Compression(String text){
        System.out.println(arrayOfThai[0]);
        System.out.println(text.charAt(12));
        boolean xx = arrayOfThai[76].equals(text.charAt(12)+"");
        System.out.println(xx);
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < arrayOfThai.length; j++) {
                System.out.println(i+"  "+j);
                if (arrayOfThai[j].equals(String.valueOf(text.charAt(i)))){
                    System.out.println(arrayOfThai[j]);
                }
            }
        }
        return "";
    }
    public String ThaitoEng(String text){
        String tem = "";

        for (int i = 0; i < text.length(); i++) {

            for (int j = 1; j < arrayOfThai.length; j++) {
                if (arrayOfThai[j].equals(String.valueOf(text.charAt(i)))){
                    f ++;
                    if (f==1 && f2==0 && tem.equals("")) tem = "<"+tem+arrayOfEng[j];
                    else if (f==1 && f2==0 && !tem.equals("")) tem = tem+"<"+arrayOfEng[j];
                    else tem = tem+arrayOfEng[j];

                }
            }

            for (int j = 0; j < arrayOfFull.length; j++) {
                if (arrayOfFull[j].equals(String.valueOf(text.charAt(i)))){

                    f2++;
                    if (f2==1 && f>=1){

                        tem = tem+">"+arrayOfFull[j];

                    }
                    /*else if (f2==1 && f==1){
                        tem = tem+">"+arrayOfFull[j];
                    }*/
                    else {
                        tem = tem+arrayOfFull[j];
                    }
                    f=0; f2=0;
                }

            }

        }

        if (tem.lastIndexOf('<') > tem.lastIndexOf('>') ) tem = tem + ">"; //ถ้าตัวสุดท้ายเป็นภาษาไทยเอา mask ปิดใส่ด้วย

        return tem;
    }

    public String EngtoThai(String text){
        // eng to thai
        String xxx = "";
        String f = "";

        ArrayList<Integer> a = new ArrayList();
        ArrayList<Integer> b = new ArrayList();
        ArrayList<String> con = new ArrayList<>();
        ArrayList<String> ncon = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            if (String.valueOf(text.charAt(i)).equals("<")) a.add(i);
            if (String.valueOf(text.charAt(i)).equals(">")) b.add(i);
        }

        if (!a.isEmpty()){
            if (String.valueOf(text.charAt(0)).equals("<")) f = "";
            else f = text.substring(0,a.get(0));

            for (int i = 0; i < a.size(); i++) {
                con.add(text.substring(a.get(i)+1, b.get(i)));

                if (i==a.size()-1)ncon.add(text.substring(b.get(i)+1));
                else ncon.add(text.substring(b.get(i)+1,a.get(i+1)));
            }

            for (int i = 0; i < con.size(); i++) {
                String ttcon = con.get(i);
                String ttncon = ncon.get(i);
                for (int j = 0; j < ttcon.length(); j++) {
                    for (int k = 1; k < arrayOfEng.length; k++) {
                        if (arrayOfEng[k].equals(String.valueOf(ttcon.charAt(j)))){
                            xxx = xxx+arrayOfThai[k];
                        }
                    }
                }
                xxx = xxx+ttncon;
            }

            if (!f.equals("")) xxx = f+xxx;
        }
        else xxx = text;

        return xxx;
    }

}