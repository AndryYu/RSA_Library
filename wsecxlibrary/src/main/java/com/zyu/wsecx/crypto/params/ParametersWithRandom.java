package com.zyu.wsecx.crypto.params;

import cn.org.bjca.wsecx.core.crypto.CipherParameters;
import cn.org.bjca.wsecx.core.math.SecureRandom;


public class ParametersWithRandom implements CipherParameters {
	private SecureRandom random;

	private CipherParameters parameters;

	public ParametersWithRandom(CipherParameters parameters, SecureRandom random) {
		this.random = random;
		this.parameters = parameters;
	}

	public ParametersWithRandom(CipherParameters parameters) {
		this(parameters, new SecureRandom());
	}

	public SecureRandom getRandom() {
		return random;
	}

	public CipherParameters getParameters() {
		return parameters;
	}
}
