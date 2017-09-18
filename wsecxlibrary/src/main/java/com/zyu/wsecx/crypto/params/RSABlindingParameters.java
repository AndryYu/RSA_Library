package com.zyu.wsecx.crypto.params;

import cn.org.bjca.wsecx.core.crypto.CipherParameters;
import cn.org.bjca.wsecx.core.math.BigInteger;


public class RSABlindingParameters implements CipherParameters {
	private RSAKeyParameters publicKey;

	private BigInteger blindingFactor;

	public RSABlindingParameters(RSAKeyParameters publicKey,
			BigInteger blindingFactor) {
		if (publicKey instanceof RSAPrivateCrtKeyParameters) {
			throw new IllegalArgumentException(
					"RSA parameters should be for a public key");
		}

		this.publicKey = publicKey;
		this.blindingFactor = blindingFactor;
	}

	public RSAKeyParameters getPublicKey() {
		return publicKey;
	}

	public BigInteger getBlindingFactor() {
		return blindingFactor;
	}
}
