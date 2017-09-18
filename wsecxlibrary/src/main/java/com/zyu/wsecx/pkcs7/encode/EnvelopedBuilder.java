package com.zyu.wsecx.pkcs7.encode;

import com.zyu.wsecx.asn1.ASN1Object;
import com.zyu.wsecx.asn1.DEREncodable;
import com.zyu.wsecx.asn1.DERNull;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DEROctetString;
import com.zyu.wsecx.asn1.nist.NISTObjectIdentifiers;
import com.zyu.wsecx.asn1.pkcs.PKCSObjectIdentifiers;
import com.zyu.wsecx.asn1.x509.AlgorithmIdentifier;
import com.zyu.wsecx.asn1.x509.X509CertificateStructure;
import com.zyu.wsecx.asn1.x9.X9ObjectIdentifiers;
import com.zyu.wsecx.pkcs7.CMSEnvelopedData;
import com.zyu.wsecx.pkcs7.CMSException;
import com.zyu.wsecx.pkcs7.sign.CMSProcessable;

import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;



/**
 * *************************************************************************
 * <pre></pre>
 *
 * @文件名称: EnvelopedBuilder.java
 * @包 路   径：  cn.org.bjca.wsecx.core.pkcs7.encode
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 * @类描述:
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2015-2-1 下午8:50:01
 * @修改记录： -----------------------------------------------------------------------------------------------
 * 时间                      |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 * |                 |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
public abstract class EnvelopedBuilder {
    public static final String DES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC.getId();

    public static final String SM4_CBC = "1.2.156.10197.1.104";
    public static final String RC2_CBC = PKCSObjectIdentifiers.RC2_CBC.getId();
    public static final String IDEA_CBC = "1.3.6.1.4.1.188.7.1.1.2";
    public static final String CAST5_CBC = "1.2.840.113533.7.66.10";
    public static final String AES128_CBC = NISTObjectIdentifiers.id_aes128_CBC.getId();
    public static final String AES192_CBC = NISTObjectIdentifiers.id_aes192_CBC.getId();
    public static final String AES256_CBC = NISTObjectIdentifiers.id_aes256_CBC.getId();

    public static final String DES_EDE3_WRAP = PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId();
    public static final String AES128_WRAP = NISTObjectIdentifiers.id_aes128_wrap.getId();
    public static final String AES192_WRAP = NISTObjectIdentifiers.id_aes192_wrap.getId();
    public static final String AES256_WRAP = NISTObjectIdentifiers.id_aes256_wrap.getId();

    public static final String ECDH_SHA1KDF = X9ObjectIdentifiers.dhSinglePass_stdDH_sha1kdf_scheme.getId();
    public static final String ECMQV_SHA1KDF = X9ObjectIdentifiers.mqvSinglePass_sha1kdf_scheme.getId();

    final List recipientInfoGenerators = new ArrayList();
    final SecureRandom rand;

    /**
     * base constructor
     */
    public EnvelopedBuilder() {
        this(new SecureRandom());
    }

    /**
     * constructor allowing specific source of randomness
     *
     * @param rand instance of SecureRandom to use
     */
    public EnvelopedBuilder(SecureRandom rand) {
        this.rand = rand;
    }

    public void addKeyTransRecipient(X509Certificate cert) throws IllegalArgumentException {
        KeyRecipientInfoBuilder ktrig = new KeyRecipientInfoBuilder();
        ktrig.setRecipientCert(cert);
        recipientInfoGenerators.add(ktrig);
    }

    public void addKeyTransRecipient(String certStr) throws IllegalArgumentException {

        X509Certificate cert = X509Cert.x509CertByCertBin(certStr);
        KeyRecipientInfoBuilder ktrig = new KeyRecipientInfoBuilder();
        ktrig.setRecipientCert(cert);
        recipientInfoGenerators.add(ktrig);
    }

    public void addKeyTransRecipientSM2(String certStr) throws IllegalArgumentException {

        X509CertificateStructure cert = X509Cert.x509CertStructure(certStr);
        KeyRecipientInfoBuilder ktrig = new KeyRecipientInfoBuilder();
        ktrig.setRecipientCert(cert);
        recipientInfoGenerators.add(ktrig);
    }

    public void addKeyTransRecipient(PublicKey key, byte[] subKeyId) throws IllegalArgumentException {
        KeyRecipientInfoBuilder ktrig = new KeyRecipientInfoBuilder();
        ktrig.setRecipientPublicKey(key);
        ktrig.setSubjectKeyIdentifier(new DEROctetString(subKeyId));

        recipientInfoGenerators.add(ktrig);
    }

    protected AlgorithmIdentifier getAlgorithmIdentifier(String encryptionOID, AlgorithmParameters params) throws IOException {
        DEREncodable asn1Params;
        if (params != null) {
            asn1Params = ASN1Object.fromByteArray(params.getEncoded("ASN.1"));

        } else {
            asn1Params = DERNull.INSTANCE;
        }

        return new AlgorithmIdentifier(new DERObjectIdentifier(encryptionOID), asn1Params);
    }

    protected AlgorithmParameters generateParameters(String encryptionOID, SecretKey encKey) throws CMSException {
        try {
            AlgorithmParameterGenerator pGen = AlgorithmParameterGenerator.getInstance(encryptionOID);

            return pGen.generateParameters();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public abstract CMSEnvelopedData generate(DERObjectIdentifier enID, WSecXAppInterface wsecx, CMSProcessable content, byte[] iv);
}
