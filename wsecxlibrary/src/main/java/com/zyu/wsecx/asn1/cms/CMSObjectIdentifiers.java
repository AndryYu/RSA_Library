package com.zyu.wsecx.asn1.cms;


import com.zyu.wsecx.asn1.ASN1ObjectIdentifier;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.pkcs.PKCSObjectIdentifiers;

public interface CMSObjectIdentifiers {
    static final DERObjectIdentifier data = PKCSObjectIdentifiers.data;
    static final DERObjectIdentifier signedData = PKCSObjectIdentifiers.signedData;
    static final DERObjectIdentifier signedDataSM2 = new ASN1ObjectIdentifier("1.2.156.10197.6.1.4.2.2");

    static final DERObjectIdentifier envelopedData = PKCSObjectIdentifiers.envelopedData;
    static final DERObjectIdentifier envelopedDataSM2 = new ASN1ObjectIdentifier("1.2.156.10197.6.1.4.2.3");

    static final DERObjectIdentifier signedAndEnvelopedData = PKCSObjectIdentifiers.signedAndEnvelopedData;
    static final DERObjectIdentifier digestedData = PKCSObjectIdentifiers.digestedData;
    static final DERObjectIdentifier encryptedData = PKCSObjectIdentifiers.encryptedData;
    static final DERObjectIdentifier authenticatedData = PKCSObjectIdentifiers.id_ct_authData;
    static final DERObjectIdentifier compressedData = PKCSObjectIdentifiers.id_ct_compressedData;
    static final DERObjectIdentifier authEnvelopedData = PKCSObjectIdentifiers.id_ct_authEnvelopedData;

}
