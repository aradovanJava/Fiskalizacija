package hr.fiskalizacija;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CertificateTest {

	@Test
	public void test() {
		Fiscalization fiskal = new Fiscalization("", "FiskalCert", "Mar+ininUr3d");
		int count = PrepareCertificate.convertFromPKCSAndSSLToJKS(fiskal);
		assertTrue(count > 0);
	}

}
