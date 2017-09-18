package com.zyu.wsecx.crypto.generators;

import com.zyu.wsecx.crypto.AsymmetricCipherKeyPair;
import com.zyu.wsecx.crypto.AsymmetricCipherKeyPairGenerator;
import com.zyu.wsecx.crypto.KeyGenerationParameters;
import com.zyu.wsecx.crypto.params.ECDomainParameters;
import com.zyu.wsecx.crypto.params.ECKeyGenerationParameters;
import com.zyu.wsecx.crypto.params.ECPrivateKeyParameters;
import com.zyu.wsecx.crypto.params.ECPublicKeyParameters;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.spec.ECPoint;


public class ECKeyPairGenerator implements AsymmetricCipherKeyPairGenerator, ECConstants {
    ECDomainParameters params;
    SecureRandom random;

    public void init(
            KeyGenerationParameters param) {
        ECKeyGenerationParameters ecP = (ECKeyGenerationParameters) param;

        this.random = ecP.getRandom();
        this.params = ecP.getDomainParameters();
    }

    /**
     * Given the domain parameters this routine generates an EC key
     * pair in accordance with X9.62 section 5.2.1 pages 26, 27.
     */
    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger n = params.getN();
        int nBitLength = n.bitLength();
        BigInteger d;

        do {
            d = new BigInteger(nBitLength, random);
        }
        while (d.equals(ZERO) || (d.compareTo(n) >= 0));

        ECPoint Q = params.getG().multiply(d);


        return new AsymmetricCipherKeyPair(
                new ECPublicKeyParameters(Q, params),
                new ECPrivateKeyParameters(d, params));
    }
}
