package com.example.privateapp.services;

import com.example.shared.crypto.CryptoService;
import com.google.common.io.Resources;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;

@Service
public class DecryptService {

	private PrivateKey cachedPrivateKey = null;

	public String decrypt(final byte[] encryptedCardData) throws Exception {
		if (cachedPrivateKey == null) {
			cachedPrivateKey = CryptoService.getPrivateKey(getPrivateKeyFromResources());
		}
		final byte[] decrypted = CryptoService.decrypt(cachedPrivateKey, encryptedCardData);
		return new String(decrypted);
	}


	public String getPrivateKeyFromResources() throws IOException {
		URL url = Resources.getResource("keys/private_key.pem");
		return Resources.toString(url, StandardCharsets.UTF_8);
	}
}
