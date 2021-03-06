package uk.co.mrdaly.blockchaindemo.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Slf4j
public class Block {

    private final String hash;
    private final String previousHash;
    private final String data;
    private final LocalDateTime timeStamp;
    private final int nonce;

    public Block(String previousHash, String data, LocalDateTime timeStamp, String hash, int nonce) {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = timeStamp;
        this.hash = hash;
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Block{" +
                "data='" + data + '\'' +
                '}';
    }
}
