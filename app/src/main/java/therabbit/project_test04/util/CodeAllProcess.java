package therabbit.project_test04.util;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import org.spongycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

/**
 * Created by root on 8/28/16.
 */
public class CodeAllProcess extends Activity {
    private String path_program = Environment.getExternalStorageDirectory() +File.separator+ "SMS_Secure_CA/";
    public String path_program_keyfriends = Environment.getExternalStorageDirectory() +File.separator+ "SMS_Secure_CA/keyfriends/";
    public String path_program_keyme = Environment.getExternalStorageDirectory() +File.separator+ "SMS_Secure_CA/keyme/";
    private PublicKey publicKey_onload;
    private PrivateKey privateKey_onload;

    public PublicKey load_public_key(String public_name){
        try {
            // Read Public Key.
            File filePublicKey = new File(path_program_keyfriends+public_name);
            FileInputStream fis = new FileInputStream(path_program_keyfriends+public_name);
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            publicKey_onload = publicKey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey_onload;

    }
    public PublicKey load_public_key_with_path(String path){
        try {
            // Read Public Key.
            File filePublicKey = new File(path);
            FileInputStream fis = new FileInputStream(path);
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            publicKey_onload = publicKey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey_onload;

    }
    public PublicKey load_public_key_me(){
        try {
            // Read Public Key.
            File filePublicKey = new File(path_program_keyme);
            String xx[] = filePublicKey.list();
            String public_name = null;
            for (int i = 0; i < xx.length; i++) {
                if (xx[i].endsWith(".PublicKey")){
                    public_name = xx[i];
                }
            }
            System.out.println(public_name);
            FileInputStream fis = new FileInputStream(path_program_keyme+public_name);
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            publicKey_onload = publicKey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey_onload;

    }

    public PublicKey seach_public_key(String add){
        try {
            // Read Public Key.
            File filePublicKey = new File(path_program_keyfriends);
            String xx[] = filePublicKey.list();
            String public_name = null;
            for (int i = 0; i < xx.length; i++) {
                if (xx[i].endsWith(add+".PublicKey")){
                    public_name = xx[i];
                }
            }
            System.out.println(public_name);
            FileInputStream fis = new FileInputStream(path_program_keyfriends+public_name);
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            publicKey_onload = publicKey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey_onload;

    }

    public PublicKey load_public_key_with_me(String public_name){
        try {
            // Read Public Key.
            File filePublicKey = new File(path_program_keyme+public_name);
            FileInputStream fis = new FileInputStream(path_program_keyme+public_name);
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            publicKey_onload = publicKey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey_onload;

    }
    public ArrayList<String> find_Public_name_On_friends(){

        File dir = new File(path_program_keyfriends);
        ArrayList<String> nn = new ArrayList<>();
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".PublicKey");
            }
        });
        for (File file : files){
            nn.add(file.getName());
        }
        return nn;
    }
    public String find_Public_name_On_me(){

        File dir = new File(path_program_keyme);
        String nn = null;
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".PublicKey");
            }
        });
        for (File file : files){
            nn = file.getName();
        }
        return nn;
    }
    public String find_Private_Key_name(){

        File dir = new File(path_program_keyme);
        String nn = null;
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".PrivateKey");
            }
        });
        for (File file : files){
            nn = file.getName();
        }
        return nn;
    }
    public PrivateKey load_private_key(){
        String private_name = find_Private_Key_name();
        System.out.println(private_name);
        try {

            File filePrivateKey = new File(path_program_keyme+private_name);
            FileInputStream fis = new FileInputStream(path_program_keyme+private_name);
            byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
            fis.read(encodedPrivateKey);
            fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("EC");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                    encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            privateKey_onload = privateKey;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey_onload;

    }

    public Bitmap getContactsDetails(Activity activity,String address) {
        Bitmap bp = null;
        String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + " = '" + address + "'";
        Cursor phones = activity.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, selection,
                null, null);
        while (phones.moveToNext()) {
            String image_uri = phones.getString(phones.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            if (image_uri != null) {
                try {
                    bp = MediaStore.Images.Media
                            .getBitmap(activity.getContentResolver(),
                                    Uri.parse(image_uri));

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return bp;
    }

    public boolean savePublicKey(PublicKey publicKey,String str) {
        boolean cc = false;
        try {

            File file = new File(path_program_keyfriends);
            File[] s = file.listFiles();
            for (int i = 0; i < s.length; i++) {
                if (s[i].equals(str)) cc = false; break;
            }
            if (cc == true){
                // Store Public Key.
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                        publicKey.getEncoded());
                FileOutputStream fos = new FileOutputStream(path_program_keyfriends+str);
                fos.write(x509EncodedKeySpec.getEncoded());
                fos.close();
                return cc;
            }
            else return cc;





        } catch (IOException e) {
            e.printStackTrace();
        }
        return cc;
    }
    public void savePublicKeyOverW(PublicKey publicKey,String str) {

        try {

                // Store Public Key.
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                        publicKey.getEncoded());
                FileOutputStream fos = new FileOutputStream(path_program_keyfriends+str);
                fos.write(x509EncodedKeySpec.getEncoded());
                fos.close();




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void savePublicKeyWithText(String name,String number,String text_pu) {

        try {


            // Read Public Key.
            //File filePublicKey = new File(path_program_keyme+public_name);
            //FileInputStream fis = new FileInputStream(path_program_keyme+public_name);
            byte[] encodedPublicKey = Base64.decode(text_pu);
            //fis.read(encodedPublicKey);
            //fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                    publicKey.getEncoded());
            System.out.println(name+" "+number);
            FileOutputStream fos = new FileOutputStream(path_program_keyfriends+name+"_"+number+".PublicKey");
            fos.write(x509EncodedKeySpec.getEncoded());
            fos.close();





        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
