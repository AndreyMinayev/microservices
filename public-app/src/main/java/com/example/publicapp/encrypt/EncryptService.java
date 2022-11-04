package com.example.publicapp.encrypt;

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
import java.security.PublicKey;

@Service
public class EncryptService {

	private PublicKey cachedPublicKey = null;

	public byte[] encrypt(final String message) throws Exception {
		if (cachedPublicKey == null) {
			cachedPublicKey = CryptoService.getPublicKey(getPublicKeyFromResources());
		}
		return CryptoService.encrypt(cachedPublicKey, message.getBytes(StandardCharsets.UTF_8));
	}

	private String getPublicKeyFromResources() throws IOException {
		URL url = Resources.getResource("keys/public_key.pem");
		return Resources.toString(url, StandardCharsets.UTF_8);
	}
}
