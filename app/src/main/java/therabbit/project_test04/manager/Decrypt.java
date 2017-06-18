package therabbit.project_test04.manager;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.encoders.Base64;
import org.spongycastle.util.encoders.DecoderException;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by root on 8/29/16.
 */
public class Decrypt {
    public String Decrypt(IESParameterSpec paems, PrivateKey privateKey, String en_text){
        Cipher cipher;
        String decrypt = "";
        //en_text = en_text.substring(3);
        Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
        byte[] en_byte = Base64.decode(en_text);
        try {
            cipher = Cipher.getInstance("ECIESwithAES","SC");
            cipher.init(Cipher.DECRYPT_MODE,privateKey,paems,new SecureRandom());
            byte[] out_de = cipher.doFinal(en_byte,0,en_byte.length);
            decrypt = new String(out_de);
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
            return "pad block corrupted";
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        catch (DataLengthException e){
            e.printStackTrace();
            return "Data Incomplete";
        }
        catch (DecoderException e){
            return "Decoder Exception";
        }

        return decrypt;
    }
}
