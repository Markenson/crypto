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
		
		/*String chavePublica = ""
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
		String chavePublica = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoD0+fj7mGGPCFRMdSAmUSmWqNTD1j6Z9KBT8Wq6NhLlkDn+f1itubEVNrh4tX7IbJYZUtK2oSvRPDdwhtgYtyQNRMpk02ARq1tjIA8f2NZA1Gx0Ftw3Tv1sQEFSLLGkfZady4AoMgJAYIxl1xHI2Ipw6q/+0b85X/TvJZ3fGpM+LjTl++zEtSrZftvNqp+iHwkBm7k2moVBb6av+1K95NPXrAAX41qB8aa6duoeU92WJ499sIHRdqgOaFCoJRJIhRmZAPdXMTRlShhdFSAc495GfBeoexpXBRmx0xZsmhR7jNDs6xsLcqOMzpQJq6CFD85wzn5wjSjzJyoCXA4dcQwIDAQAB-----END PUBLIC KEY-----";
		String chavePrivada = "-----BEGIN PRIVATE KEY-----MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgPT5+PuYYY8IVEx1ICZRKZao1MPWPpn0oFPxaro2EuWQOf5/WK25sRU2uHi1fshslhlS0rahK9E8N3CG2Bi3JA1EymTTYBGrW2MgDx/Y1kDUbHQW3DdO/WxAQVIssaR9lp3LgCgyAkBgjGXXEcjYinDqr/7Rvzlf9O8lnd8akz4uNOX77MS1Ktl+282qn6IfCQGbuTaahUFvpq/7Ur3k09esABfjWoHxprp26h5T3ZYnj32wgdF2qA5oUKglEkiFGZkA91cxNGVKGF0VIBzj3kZ8F6h7GlcFGbHTFmyaFHuM0OzrGwtyo4zOlAmroIUPznDOfnCNKPMnKgJcDh1xDAgMBAAECggEAGc7YuoXwPUwOkve0fEkUBIosncs4Nwi+Q68NmKALKa30+uN8n4ajOaD33+v7AYddbppyGMpCFBXBMx7vTF02Zpe4LjQVb21WtSp5HH109C+OaMLbNskX/6FFDGbxe2z695O+oW2SPJVOAz6IhI6jQB51fK+pz2V72ndp+tYvEkAck09V6kEXs87sP1sjYdJp0iqB0eKIKpDb7qAVEh3X4kKmQbP+szTMcIsrI1q29fi0JjcS1Ba73PJFc/nF9OLlFCg9LoBi78tvml2fEpmh1W+98BZz0l9ciqyTD5OyUOh7a16XGAWuciMBLMiCk+t25VuNn4G8MOC+jjKIykUJ8QKBgQDSx1dZkA6ZmFltZl9ZoGyPf05c+LyFGv8pI+aTeu7cTLBZztUQyr6z0A0Vuv5JRxK/5uyD3CP56eve6QJlhXDWzeciyea2/TqcaidpXGPV79gMxQOB1WNfjwJAHYluerU23Y89NhZAFNQ15Pn8wHzOplkGNqX+4betyVgcMKDETQKBgQDCnhxCYv5w2O0z798rLuR6JbRStYvXsApA/1TkYpc4i5h79DqMpcnUgAd3gnEyUcrzy1yoc/EPTwVzY7MYyt00gRD9Tr11cnInGesinv9T+8UyFfq+z9gjSbszkc15ISi3IHELSzHISXP7WJvnpxVktXmw2j0+XEYQoZcp0KQqzwKBgQDBHl9L8g9ObyXQdHF8Xt3YhB26VTP1CI0slnYWPhZHxgP15OmWnwwnF8JFXLTLUtE8/klJbxOPUOIJtJe7iI2gYbsaRr6afl2LHj/J6xqV01CyMnhKJscLsK2xLN7UWJ1cDZfFz56HTA466vaYu4weko8SgFOKuihelBGkknFaKQKBgHXUgqmTBBhQznS2k3iywHRP5bh54HwrQloN/Vj1dDcxFZZezSAgtG0rQtoRqPVdXjJy+tumrtmk71jokSO+l2VVi3LCPKUiMIkKAToJ5+7yLXdgpVrnviXHW0cizLO85sjRJIBA+5gXGNih0mrT13kNAuuXhqL9/RHOtBYGE48zAoGBALBE5BxQ0AyC79vpKbXJDO6ck+qG3jPfV2YpFThNbi220j7eCXOQEDaF90q1MZ2ARD2MSCBrNYqGLL9AfKsGOKd7C14OnEU5EVEBK4W8eYgYrOYrcssVndkY0UEznh4afaBWcpiGQ4bev7RP8jHKO3aDJ37hFsHSQGF44Cg9Q+7V-----END PRIVATE KEY-----";
		
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
		String b64Enc = Base64.encodeBytes(encryptedData,Base64.DONT_BREAK_LINES); 
		System.out.println("Encriptada:" + b64Enc);
		
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] decryptedData = cipher.doFinal(Base64.decode(b64Enc));

		assertTrue(senha.equals(new String(decryptedData)));

		String fromWeb = "NjQsMTQyLDE0MywxMTMsMTk3LDIwLDE1OCw3NywxNDEsMTQ3LDIwNyw2OCwxMTEsNzEsMjUxLDk4LDE1NSwxNDksOTksNiwxMjAsMTY1LDg1LDY0LDE0Miw4LDE0MywxNzcsMjIyLDIzMCwxMjYsMjM4LDQyLDQsMTA0LDE3MiwxNTcsMTI2LDE3NCwxNDcsODMsNTEsMjM5LDE0OSwyMTgsMjMwLDgyLDE3MywxODAsNTcsMjM3LDE5MCwyMzIsMTQzLDEyNCwyMzIsMTQ0LDIwMSwyLDE1Myw5OSwzMCwxNzEsODIsMjA1LDIxNyw2NSwxMTYsNjQsMTIxLDUzLDE3MSwyNDgsMTcwLDIxLDcwLDQ0LDE2MiwxMjQsMTUwLDE2LDIxMSwxMSwyMTgsMTE3LDg0LDU1LDEwLDE2MiwxNzUsMjE2LDMsNTgsMTg4LDI1MSw4Myw5NiwzNiw1NiwyMzUsNzYsNDQsMzMsMTQ5LDUxLDEyNywxMzksMTY1LDIwNiwxNDksMTc4LDIsOTQsMyw5OCwxOTcsMjQwLDI1NSw5OSwxNDMsNzAsMTc0LDIzMiw0OCwyMjYsMTk5LDEzMiwxMjEsMTU5LDY1LDE2OCwxOSwxLDE3NywyNTUsMTAsODEsNjUsMjgsMjksMjA4LDEwLDg2LDIyMywxNDUsODksMTUsMjIwLDc2LDEyNiwxNTEsODYsMywxODgsMjMsNjEsMjIzLDEwMiwxNDEsNzQsMTg2LDEwLDkxLDIzNyw5MywyMDMsMTgsNzgsMTI0LDE4NSwxMzIsOTcsNjgsMjQxLDI0NCwxMjAsMjQ5LDE1NCw1Nyw1OCwxNzQsNTgsODgsMjM2LDc3LDI1MCwxNjEsMTI1LDE4MywxNzUsMjUxLDE1LDE1Niw0Niw0NSwxMTksMjQ2LDE4NiwzMiwzMyw3MywxMywyNTQsMTcxLDY4LDIzNywyNDYsMTQ4LDEwOSwyMDEsODgsMTA1LDIxNiwxOTksMjA4LDM2LDgwLDAsMTQyLDE4MywzMiwyMTksNCwxMiwxMzksMTIyLDI0NCwxODMsNTEsOCwyOSwxMSw5MCw0NCwyMzEsMTI4LDkxLDE1MiwyMDEsMTI2LDkxLDIxMSwyNDUsMTE3LDEwMSwyMDQsMjU1LDEyLDE5NSw5OSwxNDksMTQ5LDE2NSwxMDMsMjksMTk3";
		byte[] decriptFromWeb = cipher.doFinal(Base64.decode(fromWeb));
		assertTrue("teste123".equals(new String(decriptFromWeb)));

		

	}

	
}
