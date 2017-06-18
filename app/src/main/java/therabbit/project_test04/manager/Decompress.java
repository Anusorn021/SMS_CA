package therabbit.project_test04.manager;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Nutherabbit on 26/9/2559.
 */

public class Decompress {
    String[] arrayOfEng = null;
    String[] arrayOfThai = null;
    String[] arrayOfFull = null;
    private BufferedReader readerEng = null;
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

    }

    public String EngtoThai(String text,Context c){

        // eng to thai
        readFile(c);
        String xxx = "";
        String f = "";

        ArrayList<Integer> a = new ArrayList();
        ArrayList<Integer> b = new ArrayList();
        ArrayList<String> con = new ArrayList<>();
        ArrayList<String> ncon = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            if (String.valueOf(text.charAt(i)).equals("¿")) a.add(i);
            if (String.valueOf(text.charAt(i)).equals("€")) b.add(i);
        }

        if (!a.isEmpty()){
            if (String.valueOf(text.charAt(0)).equals("¿")) f = "";
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
