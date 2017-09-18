package com.zyu.wsecx.pkcs7.sign;

import java.security.NoSuchAlgorithmException;

interface DigestCalculator {
    byte[] getDigest()
            throws NoSuchAlgorithmException;
}
