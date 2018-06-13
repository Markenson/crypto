package br.com.markenson.crypt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.jboss.security.Base64Utils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.picketlink.common.util.Base64;

import com.mysql.jdbc.util.Base64Decoder;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JsJavaCryptTest {
	
	
	@Test
	public void test015CriptografarSenha() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		String senha = "teste";

		/*
		String chavePublica = ""
+ "-----BEGIN PUBLIC KEY-----"
+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoD0+fj7mGGPCFRMdSAmU"
+ "SmWqNTD1j6Z9KBT8Wq6NhLlkDn+f1itubEVNrh4tX7IbJYZUtK2oSvRPDdwhtgYt"
+ "yQNRMpk02ARq1tjIA8f2NZA1Gx0Ftw3Tv1sQEFSLLGkfZady4AoMgJAYIxl1xHI2"
+ "Ipw6q/+0b85X/TvJZ3fGpM+LjTl++zEtSrZftvNqp+iHwkBm7k2moVBb6av+1K95"
+ "NPXrAAX41qB8aa6duoeU92WJ499sIHRdqgOaFCoJRJIhRmZAPdXMTRlShhdFSAc4"
+ "95GfBeoexpXBRmx0xZsmhR7jNDs6xsLcqOMzpQJq6CFD85wzn5wjSjzJyoCXA4dc"
+ "QwIDAQAB"
+ "-----END PUBLIC KEY-----";
		
		String chavePrivada = ""
+ "-----BEGIN PRIVATE KEY-----"
+ "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgPT5+PuYYY8IV"
+ "Ex1ICZRKZao1MPWPpn0oFPxaro2EuWQOf5/WK25sRU2uHi1fshslhlS0rahK9E8N"
+ "3CG2Bi3JA1EymTTYBGrW2MgDx/Y1kDUbHQW3DdO/WxAQVIssaR9lp3LgCgyAkBgj"
+ "GXXEcjYinDqr/7Rvzlf9O8lnd8akz4uNOX77MS1Ktl+282qn6IfCQGbuTaahUFvp"
+ "q/7Ur3k09esABfjWoHxprp26h5T3ZYnj32wgdF2qA5oUKglEkiFGZkA91cxNGVKG"
+ "F0VIBzj3kZ8F6h7GlcFGbHTFmyaFHuM0OzrGwtyo4zOlAmroIUPznDOfnCNKPMnK"
+ "gJcDh1xDAgMBAAECggEAGc7YuoXwPUwOkve0fEkUBIosncs4Nwi+Q68NmKALKa30"
+ "+uN8n4ajOaD33+v7AYddbppyGMpCFBXBMx7vTF02Zpe4LjQVb21WtSp5HH109C+O"
+ "aMLbNskX/6FFDGbxe2z695O+oW2SPJVOAz6IhI6jQB51fK+pz2V72ndp+tYvEkAc"
+ "k09V6kEXs87sP1sjYdJp0iqB0eKIKpDb7qAVEh3X4kKmQbP+szTMcIsrI1q29fi0"
+ "JjcS1Ba73PJFc/nF9OLlFCg9LoBi78tvml2fEpmh1W+98BZz0l9ciqyTD5OyUOh7"
+ "a16XGAWuciMBLMiCk+t25VuNn4G8MOC+jjKIykUJ8QKBgQDSx1dZkA6ZmFltZl9Z"
+ "oGyPf05c+LyFGv8pI+aTeu7cTLBZztUQyr6z0A0Vuv5JRxK/5uyD3CP56eve6QJl"
+ "hXDWzeciyea2/TqcaidpXGPV79gMxQOB1WNfjwJAHYluerU23Y89NhZAFNQ15Pn8"
+ "wHzOplkGNqX+4betyVgcMKDETQKBgQDCnhxCYv5w2O0z798rLuR6JbRStYvXsApA"
+ "/1TkYpc4i5h79DqMpcnUgAd3gnEyUcrzy1yoc/EPTwVzY7MYyt00gRD9Tr11cnIn"
+ "Gesinv9T+8UyFfq+z9gjSbszkc15ISi3IHELSzHISXP7WJvnpxVktXmw2j0+XEYQ"
+ "oZcp0KQqzwKBgQDBHl9L8g9ObyXQdHF8Xt3YhB26VTP1CI0slnYWPhZHxgP15OmW"
+ "nwwnF8JFXLTLUtE8/klJbxOPUOIJtJe7iI2gYbsaRr6afl2LHj/J6xqV01CyMnhK"
+ "JscLsK2xLN7UWJ1cDZfFz56HTA466vaYu4weko8SgFOKuihelBGkknFaKQKBgHXU"
+ "gqmTBBhQznS2k3iywHRP5bh54HwrQloN/Vj1dDcxFZZezSAgtG0rQtoRqPVdXjJy"
+ "+tumrtmk71jokSO+l2VVi3LCPKUiMIkKAToJ5+7yLXdgpVrnviXHW0cizLO85sjR"
+ "JIBA+5gXGNih0mrT13kNAuuXhqL9/RHOtBYGE48zAoGBALBE5BxQ0AyC79vpKbXJ"
+ "DO6ck+qG3jPfV2YpFThNbi220j7eCXOQEDaF90q1MZ2ARD2MSCBrNYqGLL9AfKsG"
+ "OKd7C14OnEU5EVEBK4W8eYgYrOYrcssVndkY0UEznh4afaBWcpiGQ4bev7RP8jHK"
+ "O3aDJ37hFsHSQGF44Cg9Q+7V"
+ "-----END PRIVATE KEY-----";
		
*/
		String[]  keys = createKeys();
		String chavePublica = keys[PUB_K];
		String chavePrivada = keys[PRIV_K];

        String privateKeyContent = chavePrivada.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        String publicKeyContent = chavePublica.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");;

		
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA1ANDMGF1PADDING");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKeyContent));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.decode(publicKeyContent));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] encryptedData = cipher.doFinal(senha.getBytes());
		System.out.println("Encriptada:" + Base64.encodeBytes(encryptedData));
		
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] decryptedData = cipher.doFinal(encryptedData);

		assertTrue(senha.equals(new String(decryptedData)));

		String fromWeb = "MTAwLDE3OSwxNSw4MCwxOTUsNjksODYsMTgyLDEwMSwyMTAsMTU1LDk1LDc2LDg2LDM2LDEyNiwyMTAsNzksMTUyLDEzNSwxNDIsMTg1LDE1NCwyMzUsMTUsMTc3LDgzLDIxOCwyNTQsMjE2LDIxOCwxNzgsMTE5LDEzNCw0OCwxMDksNDcsMjQ0LDIyLDExNywxMDEsNDgsMjM4LDU1LDEyOSw5NiwyMTYsNjUsNzgsMTc5LDE1OCwyMzgsMTQzLDIwMSwxODUsMjI2LDY3LDE1MSw1NCwxMyw1MCw0OCwyMTcsODAsMTI0LDMyLDIwLDEyNywxMDcsMTMzLDExOCwxNjEsMTc1LDE3NCwyMTYsMzcsMTE5LDExNSwzOSwyNDcsMTcxLDkwLDI1NSwyNDMsNCwxMzksMTgyLDEyMSw2OCw3NCw5MCwxMSwzNiw2NSwyMTcsNTksNjMsNTcsMTY0LDU3LDEwMywwLDE3NCwxODYsMTA2LDE5MiwxNTMsMTU4LDQsMjE0LDE4Niw3MSwxMDgsMTI0LDEyMSwxNjEsMjAsMTQ2LDI1MCw2MiwyMDUsMTEyLDE2LDExNSwyMjEsMjQwLDgzLDI1MiwxOTEsMzcsNTAsMjQsNDUsODMsNjgsMTQsMTc5LDIwLDc1LDEwLDI0LDcxLDEyLDEyNSwzOCwxMDksNjYsNTcsNzUsMTI0LDEwNiwxNjAsMjM0LDExOCwxMzMsOCw4NSwyMywxNCwxNzQsMTQ0LDEwOSwyMjksMjUwLDUyLDI0NSwyNSw0LDIzMCw5NCwxODMsMjAzLDE1LDE1NCwxMjcsNzksNDUsMywxOTcsNDksMTQ2LDE5MCw2MCwxNjIsMjQ1LDEwNiwxNzAsMTE4LDE3OSw4OCw0NSwxMzMsMjAwLDE5MiwxNDYsMTkyLDIzNiwwLDE5MSw4NCwxNjEsMTc3LDExNCw1NywzNywxMjcsMTYsMTgwLDE4NCwzMCwyMzQsMjAsMTk3LDQzLDI1NSwyMTAsMTc5LDY2LDExNCwyNTUsMTI3LDg1LDEyMywyMTIsMjQ1LDg3LDI0MSw0OCwyNTAsOTksNjAsMTYwLDQwLDIyMSwyMywyNDMsMTU0LDE4NiwxODEsMjIwLDIzNCwyMTgsMjIwLDgzLDkxLDcwLDI1Miw3Myw5NywxOTAsMjQwLDEyNCw3LDE5MiwxNjUsOTk=";
		byte[] decriptFromWeb = cipher.doFinal(Base64.decode(fromWeb));
		assertTrue("teste123".equals(new String(decriptFromWeb)));

		

	}

	private String[] createKeys() {
		String[] keys = new String[2];
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			Key pub = kp.getPublic();
			Key pvt = kp.getPrivate();
			
			StringBuffer sbPvt = new StringBuffer();
			sbPvt.append("-----BEGIN PRIVATE KEY-----\n");
			sbPvt.append(Base64.encodeBytes(pvt.getEncoded()));
			sbPvt.append("\n-----END PRIVATE KEY-----\n");
			
			StringBuffer sbPub = new StringBuffer();
			sbPub.append("-----BEGIN PUBLIC KEY-----\n");
			sbPub.append(Base64.encodeBytes(pub.getEncoded()));
			sbPub.append("\n-----END PUBLIC KEY-----\n");

			keys[PRIV_K] = sbPvt.toString();
			keys[PUB_K] = sbPub.toString();
			
			System.out.println("Private:\n" + keys[PRIV_K]);
			System.out.println("Public:\n" + keys[PUB_K]);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keys;
	}

	
}
