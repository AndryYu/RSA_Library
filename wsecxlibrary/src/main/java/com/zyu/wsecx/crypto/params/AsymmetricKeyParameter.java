package com.zyu.wsecx.crypto.params;

import cn.org.bjca.wsecx.core.crypto.CipherParameters;

public class AsymmetricKeyParameter implements CipherParameters {
	boolean privateKey;

	public AsymmetricKeyParameter(boolean privateKey) {
		this.privateKey = privateKey;
	}

	public boolean isPrivate() {
		return privateKey;
	}
}
