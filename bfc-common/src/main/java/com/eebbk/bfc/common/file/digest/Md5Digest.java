package com.eebbk.bfc.common.file.digest;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Simon on 2016/10/12.
 */

public class Md5Digest extends Digest {

    private MessageDigest md;

    public Md5Digest() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] digest(String str) {
        md.update( str.getBytes() );
        return md.digest();
    }

    @Override
    public byte[] digest(File file) {
        return new byte[0];
    }

    @Override
    public byte[] digest(byte[] data) {
        return new byte[0];
    }

    @Override
    public byte[] digest(InputStream is) {
        return new byte[0];
    }
}
