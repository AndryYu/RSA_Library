package com.zyu.wsecx.pkcs7;

import com.zyu.wsecx.asn1.cms.RecipientInfo;

import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.SecretKey;


interface RecipientInfoGenerator {
    /**
     * Generate a RecipientInfo object for the given key.
     *
     * @param key    the <code>SecretKey</code> to encrypt
     * @param random a source of randomness
     * @param prov   the default provider to use
     * @return a <code>RecipientInfo</code> object for the given key
     * @throws GeneralSecurityException
     */
    RecipientInfo generate(SecretKey key, SecureRandom random,
                           Provider prov) throws GeneralSecurityException;
}
