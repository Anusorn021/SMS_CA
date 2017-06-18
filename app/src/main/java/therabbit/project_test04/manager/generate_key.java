package therabbit.project_test04.manager;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by root on 8/27/16.
 */
public class generate_key{

    private String path_program = Environment.getExternalStorageDirectory() +File.separator+ "SMS_Secure_CA/keyme";

    public KeyPair genkey(int size){
        KeyPairGenerator genkey;
        KeyPair keyPair = null;
        SecureRandom secureRandom = null;
        ECGenParameterSpec genParameterSpec;
        try {
            genkey = KeyPairGenerator.getInstance("EC");
            genParameterSpec = new ECGenParameterSpec("secp"+size+"r1");
            genkey.initialize(genParameterSpec,secureRandom);
            keyPair = genkey.generateKeyPair();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    public void saveKey(PublicKey publicKey,PrivateKey privateKey,String str) {
        try {

            // Store Public Key.
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                    publicKey.getEncoded());
            FileOutputStream fos = new FileOutputStream(path_program+"/"+str +".PublicKey");
            fos.write(x509EncodedKeySpec.getEncoded());
            fos.close();


            // Store Private Key.
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                    privateKey.getEncoded());
            fos = new FileOutputStream(path_program+"/"+str +".PrivateKey");
            fos.write(pkcs8EncodedKeySpec.getEncoded());
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
