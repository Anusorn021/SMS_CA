package therabbit.project_test04.manager;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by root on 8/30/16.
 */
public class Compresstion{

    String[] arrayOfEng = null;
    String[] arrayOfThai = null;
    String[] arrayOfFull = null;
    private  BufferedReader readerEng = null;
    private  BufferedReader readerThai = null;
    private  BufferedReader readerFull = null;

    int f = 0;
    int f2 = 0;


    public void readFile(Context context){

        String data = null;
        String temp = "";

        /*if (readerThai != null) readerThai2 = readerThai;
        if (readerEng != null) readerEng2 = readerEng;
        if (readerFull != null) readerFull2 = readerFull;*/

        try {
            readerEng = new BufferedReader(new InputStreamReader(context.getAssets().open("Eng.CA_Map")));
            //dataIns = new BufferedReader(new InputStreamReader( new FileInputStream(pathe), "UTF-8")); //TIS-620
            while ((data = readerEng.readLine()) != null) {
                temp = data;
            }
            readerEng.close();
            readerEng = null;
            arrayOfEng = temp.split("\\s+");
            temp = null;
            //c.arrayOfEng[c.arrayOfEng.length-1] = " ";

            readerThai = new BufferedReader(new InputStreamReader(context.getAssets().open("Thai.CA_Map")));
            //dataIns = new BufferedReader(new InputStreamReader( new FileInputStream(pathth), "UTF-8")); //TIS-620
            while ((data = readerThai.readLine()) != null) {
                temp = data;
            }
            readerThai.close();
            readerThai = null;
            arrayOfThai = temp.split("\\s+");
            temp = null;
            //c.arrayOfThai[c.arrayOfThai.length-1] = " ";

            readerFull = new BufferedReader(new InputStreamReader(context.getAssets().open("All.CA_Map")));
            //dataIns = new BufferedReader(new InputStreamReader( new FileInputStream(pathfull), "UTF-8")); //TIS-620
            while ((data = readerFull.readLine()) != null) {
                temp = data;
            }
            readerFull.close();
            readerFull = null;
            arrayOfFull = temp.split("\\s+");
            temp = null;

        } catch (IOException e) {
            e.printStackTrace();
        }
         arrayOfFull[arrayOfFull.length-1] = " ";


        System.out.print("Map eng  "+arrayOfEng.length+" : ");
        for (int i = 0; i < arrayOfEng.length; i++) {
            System.out.print(arrayOfEng[i]+" ");
        }
        System.out.println();

        System.out.print("Map thai "+arrayOfThai.length+" : ");
        for (int i = 0; i < arrayOfThai.length; i++) {
            System.out.print(arrayOfThai[i]+" ");
        }System.out.println();

        System.out.print("Map full "+arrayOfFull.length+" : ");
        for (int i = 0; i < arrayOfFull.length; i++) {
            System.out.print(arrayOfFull[i]+" ");
        }
        System.out.println();
    }
    public String ThaitoEng(String text,Context c){
        readFile(c);
        String tem = "";
        //text = text.replaceAll("\\s","");

        for (int i = 0; i < text.length(); i++) {

            for (int j = 1; j < arrayOfThai.length; j++) {
                if (arrayOfThai[j].equals(String.valueOf(text.charAt(i)))){
                    f ++;
                    if (f==1 && f2==0 && tem.equals("")) tem = "¿"+tem+arrayOfEng[j];
                    else if (f==1 && f2==0 && !tem.equals("")) tem = tem+"¿"+arrayOfEng[j];
                    else tem = tem+arrayOfEng[j];

                }
            }

            for (int j = 0; j < arrayOfFull.length; j++) {
                if (arrayOfFull[j].equals(String.valueOf(text.charAt(i)))){

                    f2++;
                    if (f2==1 && f>=1){

                        tem = tem+"€"+arrayOfFull[j];

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

        if (tem.lastIndexOf('¿') > tem.lastIndexOf('€') ) tem = tem + "€"; //ถ้าตัวสุดท้ายเป็นภาษาไทยเอา mask ปิดใส่ด้วย

        Log.d("SMS Length: ",tem);
        Log.d("Length : ",(tem).length()+"");
        return tem;
    }
}
