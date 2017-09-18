package com.zyu.wsecx.crypto.params;

import cn.org.bjca.wsecx.core.math.ec.ECPoint;

public class ECPublicKeyParameters extends ECKeyParameters {
    ECPoint Q;

    public ECPublicKeyParameters(
            ECPoint Q,
            ECDomainParameters params) {
        super(false, params);
        this.Q = Q;
    }

    public ECPoint getQ() {
        return Q;
    }
}
