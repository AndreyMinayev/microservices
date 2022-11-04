package com.example.shared.crypto;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.crypto.Cipher;
import javax.validation.ValidationException;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

public class CryptoService {

	private static final String ALGORITHM = "RSA";

	public static byte[] encrypt(final PublicKey publicKey, final byte[] inputData) throws Exception {
		final PublicKey key = KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(publicKey.getEncoded()));

		final Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(inputData);
	}

	public static byte[] decrypt(final PrivateKey privateKey, final byte[] inputData) throws Exception {
		// todo: rewrite decryption so the key size would not matter
		final PrivateKey key = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(privateKey.getEncoded()));

		final Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(inputData);
	}

	public static PrivateKey getPrivateKey(final String keyString) throws Exception{
		final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(prepareKey(keyString));
		final KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public static PublicKey getPublicKey(final String keyString) throws Exception {
		final X509EncodedKeySpec spec = new X509EncodedKeySpec(prepareKey(keyString));
		final KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	private static byte[] prepareKey(final String keyString){
		try (Reader reader = new StringReader(keyString);
			 PemReader pemReader = new PemReader(reader)) {
			PemObject pemObject = pemReader.readPemObject();
			return pemObject.getContent();
		} catch (Throwable t) {
			throw new ValidationException("Key is null or invalid format. Key: "+ keyString);
		}
	}
}
