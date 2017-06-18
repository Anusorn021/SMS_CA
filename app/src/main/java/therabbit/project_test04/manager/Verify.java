package therabbit.project_test04.manager;

import org.spongycastle.util.encoders.DecoderException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by root on 8/29/16.
 */
public class Verify {
    String tt = "";
    public void Verify(String text,PublicKey publicKey){


        try {

            String[] str = text.split("#S#");
            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initVerify(publicKey);

            str[0] = str[0].substring(3);
            signature.update(str[0].getBytes("UTF-8"));
            byte[] temp = org.spongycastle.util.encoders.Base64.decode(str[1]);
            boolean result = signature.verify(temp);
            System.out.println(result);
            if (result)this.tt = str[0];
            else  this.tt = "Verify Invalid";
            System.out.println("Valid: " +result);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("1");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            System.out.println("2");
            System.err.println(e);

        } catch (SignatureException e) {
            e.printStackTrace();
            System.out.println("3");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("4");

        }
        catch (DecoderException e){
            this.tt = "Signature invalid";
            e.printStackTrace();
        }
    }
    public String getText(){
        return tt;
    }
}
