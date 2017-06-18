package therabbit.project_test04.manager;

import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.encoders.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by root on 8/27/16.
 */
public class Encrypt {

    public String Encrypt(IESParameterSpec parms, PublicKey publicKey, String text){
        Cipher cipher;
        String encrypt = "";
        Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
        try {
            cipher = Cipher.getInstance("ECIESwithAES","SC");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey,parms,new SecureRandom());
            byte[] out_en = cipher.doFinal(text.getBytes(),0,text.getBytes().length);
            encrypt = Base64.toBase64String(out_en);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return "@E$"+encrypt;
    }
}
