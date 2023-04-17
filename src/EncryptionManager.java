import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

//Not to be implimented yet

public class EncryptionManager {

	private static byte[] transform(byte[] data, int encryptMode, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException {

        PBEKeySpec pbeKeySpec;
        PBEParameterSpec pbeParamSpec;
        SecretKeyFactory keyFac;
        // Salt
        byte[] salt = {
                (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
                (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
        };

        // Iteration count
        int count = 20;

        // Create PBE parameter set
        pbeParamSpec = new PBEParameterSpec(salt, count);

        // Prompt user for encryption password.
        // Collect user password as char array (using the
        // "readPassword" method from above), and convert
        // it into a SecretKey object, using a PBE key
        // factory.
        pbeKeySpec = new PBEKeySpec(password.toCharArray());
        keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

        // Create PBE Cipher
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

        // Initialize PBE Cipher with key and parameters
        pbeCipher.init(encryptMode, pbeKey, pbeParamSpec);

        return pbeCipher.doFinal(data);

}

	public static byte[] encrypt(byte[] data) throws InvalidKeyException {
		try {
			return transform(data, Cipher.ENCRYPT_MODE, App.user.password);
		} catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeySpecException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] decrypt(byte[] data) throws InvalidKeyException{
		try {
			return transform(data, Cipher.DECRYPT_MODE, App.user.password);
		} catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeySpecException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
