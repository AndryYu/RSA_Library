package com.zyu.wsecx.pkcs7.sign;


import com.zyu.wsecx.outter.util.Arrays;

class BaseDigestCalculator implements DigestCalculator {
    private final byte[] digest;

    BaseDigestCalculator(byte[] digest) {
        this.digest = digest;
    }

    public byte[] getDigest() {
        return Arrays.clone(digest);
    }
}
