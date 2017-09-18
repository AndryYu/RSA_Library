package com.zyu.wsecx.outter.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import cn.org.bjca.wsecx.core.asn1.ASN1InputStream;
import cn.org.bjca.wsecx.core.asn1.ASN1Sequence;
import cn.org.bjca.wsecx.core.asn1.DERBitString;
import cn.org.bjca.wsecx.core.asn1.DERInteger;
import cn.org.bjca.wsecx.core.asn1.DERObject;
import cn.org.bjca.wsecx.core.asn1.x509.AlgorithmIdentifier;
import cn.org.bjca.wsecx.core.asn1.x509.X509CertificateStructure;
import cn.org.bjca.wsecx.outter.encoder.Base64;

/**
 * *************************************************************************
 * <pre></pre>
 *
 * @文件名称: X509Cert.java
 * @包 路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 * @类描述: 证书解析组装
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-12-4 上午3:00:03
 * @修改记录：
 * -----------------------------------------------------------------------------------------------
 *          时间            |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 *                      |                 |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
public class X509Cert {
    public static String CerType = "X.509";

    /**
     * 通过二进制流解析证书格式
     *
     * @param certB byte[]
     * @return X509Certificate
     */
    public static X509Certificate x509CertByCertBin(byte[] certB) {

        X509Certificate x509Cert = null;

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CerType);
            ByteArrayInputStream bais = new ByteArrayInputStream(certB);

            x509Cert = (X509Certificate) certificateFactory.generateCertificate(bais);
            bais.close();

        } catch (Exception e) {

        }
        return x509Cert;
    }

    /**
     * 通过asn1解析证书格式
     *
     * @param certBase64 String
     * @return X509Certificate
     */
    public static X509Certificate x509CertByCertBin(String certBase64) {
        X509Certificate x509Cert = null;
        InputStream inputstream = null;
        try {

            CertificateFactory certificateFactory = CertificateFactory.getInstance(CerType);

            byte[] derCertB = Base64.decode(certBase64);

            inputstream = new ByteArrayInputStream(derCertB, 0, derCertB.length);

            x509Cert = (X509Certificate) certificateFactory.generateCertificate(inputstream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputstream != null) {
                    inputstream.close();
                }
            } catch (IOException ex) {
            }
        }
        return x509Cert;
    }

    /**
     * 通过asn1解析证书格式
     *
     * @param certBase64 String
     * @return X509Certificate
     */
    public static byte[] publicKeyByCertBin(String certBase64) {

        try {
            X509Certificate cert = x509CertByCertBin(certBase64);
            PublicKey key = cert.getPublicKey();
            return key.getEncoded();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取证书密钥长度
     * <p>certKeyLength</p>
     *
     * @param certBase64
     * @return
     * @Description:
     */
    public static int certKeyLength(String certBase64) {

        if (certBase64 == null) {
            return -1;
        }

        try {
            X509Certificate cert = x509CertByCertBin(certBase64);

            if (cert == null) {
                return -1;
            }

            ByteArrayInputStream bIn = new ByteArrayInputStream(cert.getPublicKey().getEncoded());
            ASN1InputStream dIn = new ASN1InputStream(bIn);

            ASN1Sequence seqPubkeyInfo = (ASN1Sequence) dIn.readObject();
            Enumeration seqPubkeyInfoEnum = seqPubkeyInfo.getObjects();

            AlgorithmIdentifier.getInstance(seqPubkeyInfoEnum.nextElement());

            DERBitString pubkeyBit = DERBitString.getInstance(seqPubkeyInfoEnum.nextElement());

            ASN1InputStream aIn = new ASN1InputStream(pubkeyBit.getBytes());
            ASN1Sequence pubkeySeq = (ASN1Sequence) aIn.readObject();
            Enumeration pubkeyEnum = pubkeySeq.getObjects();
            return DERInteger.getInstance(pubkeyEnum.nextElement()).getPositiveValue().bitLength();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 构造CRL列表
     *
     * @param certB byte[]
     * @return X509CRL
     */
    public static X509CRL crlBin2x509CRL(byte[] certB) {
        X509CRL x509Crl = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CerType);
            ByteArrayInputStream bais = new ByteArrayInputStream(certB);

            x509Crl = (X509CRL) certificateFactory.generateCRL(bais);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return x509Crl;
    }

    /**
     * 构造CRL对象
     *
     * @param certBase64 byte[]
     * @return X509CRL
     */
    public static X509CRL crlBin2x509CRL(String certBase64) {
        X509CRL x509Crl = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CerType);

            byte[] derCertB = Base64.decode(certBase64);
            ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(derCertB);
            x509Crl = (X509CRL) certificateFactory.generateCRL(bytearrayinputstream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return x509Crl;
    }

    /**
     * 通过流构造证书对象
     *
     * @param InputStream byte[]
     * @return X509Certificate
     */
    public static X509Certificate x509CertByCertStream(InputStream input) {

        X509Certificate x509Cert = null;

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CerType);

            x509Cert = (X509Certificate) certificateFactory.generateCertificate(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return x509Cert;
    }

    public static X509CertificateStructure x509CertStructure(String base64Cert) {
        X509CertificateStructure p7CertStrc = null;
        try {

            byte[] certbuf = Base64.decode(base64Cert);
            p7CertStrc = new X509CertificateStructure((ASN1Sequence) getDerObject(certbuf));

        } catch (Exception e) {
//			Log.e("x509CertStructure==", e.getMessage());
            e.printStackTrace();
            return null;
        }
        return p7CertStrc;
    }

    public static X509CertificateStructure x509CertStructure(byte[] cert) {
        X509CertificateStructure p7CertStrc = null;
        try {
            p7CertStrc = new X509CertificateStructure((ASN1Sequence) getDerObject(cert));
        } catch (Exception e) {
//			Log.e("x509CertStructure==", e.getMessage());
            e.printStackTrace();
            return null;
        }
        return p7CertStrc;
    }

    public static DERObject getDerObject(byte[] input) throws IOException {
        ByteArrayInputStream bai = new ByteArrayInputStream(input);
        ASN1InputStream ains = new ASN1InputStream(bai);
        return ains.readObject();
    }

}
