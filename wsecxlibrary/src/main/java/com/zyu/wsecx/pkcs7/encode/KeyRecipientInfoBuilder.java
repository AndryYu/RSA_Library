package com.zyu.wsecx.pkcs7.encode;

import android.util.Base64;

import com.zyu.wsecx.asn1.ASN1Object;
import com.zyu.wsecx.asn1.ASN1OctetString;
import com.zyu.wsecx.asn1.DEROctetString;
import com.zyu.wsecx.asn1.cms.IssuerAndSerialNumber;
import com.zyu.wsecx.asn1.cms.KeyTransRecipientInfo;
import com.zyu.wsecx.asn1.cms.RecipientIdentifier;
import com.zyu.wsecx.asn1.cms.RecipientInfo;
import com.zyu.wsecx.asn1.x509.AlgorithmIdentifier;
import com.zyu.wsecx.asn1.x509.SubjectPublicKeyInfo;
import com.zyu.wsecx.asn1.x509.TBSCertificateStructure;
import com.zyu.wsecx.asn1.x509.X509CertificateStructure;
import com.zyu.wsecx.pkcs7.sign.CMSUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;



/**
 * *************************************************************************
 * <pre></pre>
 *
 * @文件名称: KeyRecipientInfoBuilder.java
 * @包 路   径：  cn.org.bjca.wsecx.core.pkcs7.encode
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 * @类描述: 加密密钥公钥保护
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2015-2-2 下午3:58:54
 * @修改记录： -----------------------------------------------------------------------------------------------
 * 时间                      |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 * |                 |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
class KeyRecipientInfoBuilder {

    private TBSCertificateStructure recipientTBSCert;
    //	private PublicKey recipientPublicKey;
    private ASN1OctetString subjectKeyIdentifier;
    private X509CertificateStructure cert;

    // Derived fields
    private SubjectPublicKeyInfo info;

    private X509Certificate recipientCert;

    KeyRecipientInfoBuilder() {
    }

    void setRecipientCert(X509CertificateStructure recipientCert) {
        this.cert = recipientCert;
        this.recipientTBSCert = recipientCert.getTBSCertificate();

        this.info = recipientCert.getSubjectPublicKeyInfo();

    }

    void setRecipientCert(X509Certificate recipientCert) {
        try {
            this.recipientCert = recipientCert;
            this.recipientTBSCert = CMSUtils.getTBSCertificateStructure(recipientCert);
        } catch (CertificateEncodingException e) {
            throw new IllegalArgumentException("can't extract TBS structure from this cert");
        }

//		this.recipientPublicKey = recipientCert.getPublicKey();
        this.info = recipientTBSCert.getSubjectPublicKeyInfo();

    }

    void setRecipientPublicKey(PublicKey recipientPublicKey) {
//		this.recipientPublicKey = recipientPublicKey;

        try {
            info = SubjectPublicKeyInfo.getInstance(ASN1Object.fromByteArray(recipientPublicKey.getEncoded()));
        } catch (IOException e) {
            throw new IllegalArgumentException("can't extract key algorithm from this key");
        }
    }

    void setSubjectKeyIdentifier(ASN1OctetString subjectKeyIdentifier) {
        this.subjectKeyIdentifier = subjectKeyIdentifier;
    }


    public RecipientInfo generate(byte[] key, SecureRandom random, WSecXAppInterface wsecx) throws GeneralSecurityException {
        AlgorithmIdentifier keyEncAlg = info.getAlgorithmId();

        ASN1OctetString encKey = null;

        byte[] keyEn = null;

        try {
            // RSA/ECB/PKCS1Padding

            if (recipientCert != null) {
                String base64EncodeCert = Base64.encode(recipientCert.getEncoded());
                keyEn = wsecx.pubKeyEncrypt(base64EncodeCert, key);
            } else {
                String base64EncodeCert = Base64.encode(cert.getEncoded());
                keyEn = wsecx.pubKeyEncrypt(base64EncodeCert, key);
            }


        } catch (Exception e) // some providers do not
        {

            throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.PKCS7_ENCODE_ERROR, e);
        }


        encKey = new DEROctetString(keyEn);

        RecipientIdentifier recipId;
        if (recipientTBSCert != null) {
            IssuerAndSerialNumber issuerAndSerial = new IssuerAndSerialNumber(recipientTBSCert.getIssuer(), recipientTBSCert.getSerialNumber().getValue());
            recipId = new RecipientIdentifier(issuerAndSerial);
        } else {
            recipId = new RecipientIdentifier(subjectKeyIdentifier);
        }

        return new RecipientInfo(new KeyTransRecipientInfo(recipId, keyEncAlg, encKey));
    }
}
