/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otros;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author PC-HP
 */
public class utilidades {

	public static String hashPasswordSha256(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		/*
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());
		byte[] b = md.digest();
		StringBuilder sb = new StringBuilder();
		for (byte b1 : b) {
			sb.append(Integer.toHexString(b1 & 0xff));
		}
		return sb.toString();
		 */
		String hash = DatatypeConverter.printHexBinary(
						MessageDigest.getInstance("SHA-256").digest(password.getBytes("UTF-8")));
		return hash.toLowerCase();
	}
}
