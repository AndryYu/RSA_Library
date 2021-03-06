package com.zyu.wsecx.pkcs;

import com.zyu.wsecx.asn1.ASN1Sequence;
import com.zyu.wsecx.asn1.ASN1Set;
import com.zyu.wsecx.asn1.DERBitString;
import com.zyu.wsecx.asn1.DEREncodable;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DEROutputStream;
import com.zyu.wsecx.asn1.pkcs.CertificationRequest;
import com.zyu.wsecx.asn1.pkcs.CertificationRequestInfo;
import com.zyu.wsecx.asn1.x509.AlgorithmIdentifier;
import com.zyu.wsecx.asn1.x509.SubjectPublicKeyInfo;
import com.zyu.wsecx.asn1.x509.X509Name;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



/**
 * *************************************************************************
 * <pre></pre>
 *
 * @文件名称: WPKCS10CertificationRequestSM2.java
 * @包 路   径：  cn.org.bjca.wsecx.soft.build
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 * @类描述: 同KEY容器结合产生p10请求 SM2
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-12 上午11:50:57
 * @修改记录：
 * -----------------------------------------------------------------------------------------------
 *          时间        |       修改人     |         修改的方法           |         修改描述
 * -----------------------------------------------------------------------------------------------
 *                      |                  |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
public class WPKCS10SM2 extends CertificationRequest {

    /**
     * 算法标识
     */
    private final static String HASH_SIGN = "SM3WITHSM2";

    /**
     * 非对称算法
     */
    private final static String ALG = "SM2";

    /**
     * 集合属性
     */
    private ASN1Set attributes = null;

    public WPKCS10SM2(byte[] bytes) {
        super(toDERSequence(bytes));
    }

    public WPKCS10SM2(ASN1Sequence sequence) {
        super(sequence);
    }

    public void setASN1Set(ASN1Set attributes) {
        this.attributes = attributes;
    }

    /**
     * @param @param  RSAPubKey
     * @param @param  subject
     * @param @param  alias 容器名
     * @param @param  keyType 密钥类型（BJCAWirelessInterface）（1：加密密钥，2：签名密钥）
     * @param @param  hashType 哈希类型（BJCAWirelessInterface）摘要算法类型（1：SHA-1，
     *                2：SHA-256， 3： SM3）
     * @param @param  bacaInterface
     * @param @throws WSecurityEngineException 设定文件
     * @return WPKCS10CertificationRequestRSA 返回类型
     * @throws
     * @Title: WPKCS10CertificationRequestSM2
     * @Description: TODO
     */
    public WPKCS10SM2(byte[] RSAPubKey, String subject, String alias, int keyType, int hashType, BJCAWirelessInterface bacaInterface) throws WSecurityEngineException {

        DERObjectIdentifier sigOID = (DERObjectIdentifier) algorithms.get(HASH_SIGN);

        DERObjectIdentifier pubKeyOID = (DERObjectIdentifier) algorithms.get(ALG);

        if (sigOID == null) {
            throw new IllegalArgumentException("Unknown signature type requested");
        }

        if (subject == null) {
            throw new IllegalArgumentException("subject must not be null");
        }

        if (noParams.contains(sigOID)) {
            this.sigAlgId = new AlgorithmIdentifier(sigOID);
        } else if (params.containsKey(HASH_SIGN)) {
            this.sigAlgId = new AlgorithmIdentifier(sigOID, (DEREncodable) params.get(HASH_SIGN));
        } else {
            this.sigAlgId = new AlgorithmIdentifier(sigOID, null);
        }

        this.pubKeyAlgId = new AlgorithmIdentifier(pubKeyOID, null);
        byte[] signedData = null;
        DEROutputStream dOut = null;
        try {
            SubjectPublicKeyInfo subPubKeyInfo = new SubjectPublicKeyInfo(this.pubKeyAlgId, RSAPubKey);

            this.reqInfo = new CertificationRequestInfo(new X509Name(subject), subPubKeyInfo, attributes);

            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            dOut = new DEROutputStream(bOut);
            dOut.writeObject(reqInfo);

            byte[] toSign = bOut.toByteArray();
            dOut.close();

            signedData = bacaInterface.signData(alias, keyType, hashType, toSign, false);
        } catch (Exception e) {
            throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.PKCS10_REQUEST_ERROR, e);
        } finally {
            try {
                dOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.sigBits = new DERBitString(signedData);
    }

    /**
     * return a DER encoded byte array representing this object
     */
    public byte[] getEncoded() {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        DEROutputStream dOut = new DEROutputStream(bOut);

        try {
            dOut.writeObject(this);
            byte ret[] = bOut.toByteArray();
            dOut.close();

            return ret;
        } catch (IOException e) {
            throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.PKCS10_REQUEST_ERROR, e);
        } finally {
            try {
                dOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
