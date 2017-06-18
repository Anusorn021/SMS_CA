package therabbit.project_test04.manager;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by root on 8/29/16.
 */
public class Sing {
    public String Signature(String text,PrivateKey privateKey ){
        byte[] bytetext = null;
        String string = null;
        try {

            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initSign(privateKey);
            signature.update(text.getBytes("UTF-8"));
            bytetext = signature.sign();
            string = org.spongycastle.util.encoders.Base64.toBase64String(bytetext);
            //string = new Base64().encodeToString(bytetext);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }
}
