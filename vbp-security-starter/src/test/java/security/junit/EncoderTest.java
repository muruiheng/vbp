package security.junit;

import org.junit.Test;

import com.sgai.vbp.security.encrypt.AESEncryptors;
import com.sgai.vbp.security.encrypt.TextEncryptor;

public class EncoderTest {

	@Test
	public void testAes() {
		
		TextEncryptor queryableText = AESEncryptors.queryableText("admin123", "1243");
		
		System.out.println(queryableText.encrypt("124"));
	}
}
