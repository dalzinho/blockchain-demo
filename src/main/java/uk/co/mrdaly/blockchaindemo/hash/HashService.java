package uk.co.mrdaly.blockchaindemo.hash;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
public class HashService {

    public String calculateHash(String previousHash,
                                LocalDateTime timeStamp,
                                int nonce,
                                String data) {
        String hashInput = previousHash
                + timeStamp.toEpochSecond(ZoneOffset.UTC)
                + nonce
                + data;

        final byte[] hashBytes = hash(hashInput);
        return stringify(hashBytes);

    }

    private byte[] hash(String input) {
        MessageDigest md;
        byte[] bytes = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            log.error("An error occurred while hashing {}", input, e);
        }

        return bytes;
    }

    private String stringify(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
