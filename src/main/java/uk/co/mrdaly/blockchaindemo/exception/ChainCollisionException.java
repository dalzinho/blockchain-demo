package uk.co.mrdaly.blockchaindemo.exception;

public class ChainCollisionException extends RuntimeException {

    public ChainCollisionException(String s) {
        super(s);
    }
}
