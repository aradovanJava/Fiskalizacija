package hr.fiskalizacija;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CertificateTest {

	@Test
	public void test() {
		Fiscalization fiskal = new Fiscalization("", "naziv_cert", "password_cert");
		int count = PrepareCertificate.convertFromPKCSAndSSLToJKS(fiskal);
		assertTrue(count > 0);
	}

}
