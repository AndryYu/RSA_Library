package com.zyu.wsecx.pkcs7.sign;

import com.zyu.wsecx.asn1.ASN1EncodableVector;
import com.zyu.wsecx.asn1.ASN1OctetString;
import com.zyu.wsecx.asn1.ASN1Set;
import com.zyu.wsecx.asn1.DERNull;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DEROctetString;
import com.zyu.wsecx.asn1.DERSet;
import com.zyu.wsecx.asn1.cms.AttributeTable;
import com.zyu.wsecx.asn1.cms.CMSObjectIdentifiers;
import com.zyu.wsecx.asn1.cms.ContentInfo;
import com.zyu.wsecx.asn1.cms.SignedData;
import com.zyu.wsecx.asn1.cms.SignerIdentifier;
import com.zyu.wsecx.asn1.cms.SignerInfo;
import com.zyu.wsecx.asn1.x509.AlgorithmIdentifier;
import com.zyu.wsecx.asn1.x509.X509CertificateStructure;
import com.zyu.wsecx.outter.WSecXAppInterface;
import com.zyu.wsecx.outter.encoder.Base64;
import com.zyu.wsecx.pkcs7.CMSAttributeTableGenerator;
import com.zyu.wsecx.pkcs7.CMSException;
import com.zyu.wsecx.pkcs7.DefaultSignedAttributeTableGenerator;
import com.zyu.wsecx.pkcs7.SimpleAttributeTableGenerator;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * *************************************************************************
 * <pre></pre>
 *
 * @文件名称: CMSSignedDataGenerator.java
 * @包 路   径：  cn.org.bjca.wsecx.core.pkcs7.sign
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 * @类描述:
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2015-2-9 下午6:59:44
 * @修改记录： -----------------------------------------------------------------------------------------------
 * 时间                      |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 * |                 |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
public class CMSSignedDataGenerator extends CMSSignedGenerator {
    List signerInfs = new ArrayList();

    private class SignerInf {
        private final SignerIdentifier signerIdentifier;
        private final String digestOID;
        private final String encOID;
        private final CMSAttributeTableGenerator sAttr;
        private final CMSAttributeTableGenerator unsAttr;
        private final AttributeTable baseSignedTable;

        SignerInf(SignerIdentifier signerIdentifier, String digestOID, String encOID, CMSAttributeTableGenerator sAttr, CMSAttributeTableGenerator unsAttr, AttributeTable baseSignedTable) {
            // this.key = key;
            this.signerIdentifier = signerIdentifier;
            this.digestOID = digestOID;
            this.encOID = encOID;
            this.sAttr = sAttr;
            this.unsAttr = unsAttr;
            this.baseSignedTable = baseSignedTable;
        }

        AlgorithmIdentifier getDigestAlgorithmID() {
            return new AlgorithmIdentifier(new DERObjectIdentifier(digestOID), new DERNull());
        }

        /**
         * <p>toSignerInfo</p>
         *
         * @param contentType
         * @param content
         * @param random
         * @param wsecx
         * @param addDefaultAttributes
         * @param isCounterSignature
         * @return
         * @throws IOException
         * @throws SignatureException
         * @throws InvalidKeyException
         * @throws NoSuchAlgorithmException
         * @throws CertificateEncodingException
         * @throws CMSException
         * @Description: 产生签名者信息
         */

        SignerInfo toSignerInfo(DERObjectIdentifier contentType, CMSProcessable content, SecureRandom random, WSecXAppInterface wsecx, boolean isHash, boolean addDefaultAttributes, boolean isCounterSignature)
                throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException, CertificateEncodingException, CMSException {
            AlgorithmIdentifier encAlgId = getEncAlgorithmIdentifier(encOID, null);
            // digestOID
            DERObjectIdentifier digest = new DERObjectIdentifier(digestOID);

            AlgorithmIdentifier algId = new AlgorithmIdentifier(digest, DERNull.INSTANCE);

            ASN1Set signedAttr = null;

            String signStr = wsecx.signData((byte[]) content.getContent(), BJCAWirelessInterface.KEY_SIGN, isHash);

            byte[] sigBytes = Base64.decode(signStr);// sig.sign();

            ASN1Set unsignedAttr = null;

            return new SignerInfo(signerIdentifier, algId, signedAttr, encAlgId, new DEROctetString(sigBytes), unsignedAttr);
        }


        SignerInfo toSignerInfoBySignedData(DERObjectIdentifier contentType, CMSProcessable content, SecureRandom random, boolean addDefaultAttributes, boolean isCounterSignature)
                throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException, CertificateEncodingException, CMSException {
            AlgorithmIdentifier encAlgId = getEncAlgorithmIdentifier(encOID, null);
            // digestOID
            DERObjectIdentifier digest = new DERObjectIdentifier(digestOID);

            AlgorithmIdentifier algId = new AlgorithmIdentifier(digest, DERNull.INSTANCE);

            ASN1Set signedAttr = null;

//			String signStr = wsecx.signData((byte[]) content.getContent(), BJCAWirelessInterface.KEY_SIGN, isHash);

            byte[] sigBytes = (byte[]) content.getContent();//Base64.decode(signStr);// sig.sign();

            ASN1Set unsignedAttr = null;

            return new SignerInfo(signerIdentifier, algId, signedAttr, encAlgId, new DEROctetString(sigBytes), unsignedAttr);
        }


    }

    /**
     * base constructor
     */
    public CMSSignedDataGenerator() {
    }

    /**
     * constructor allowing specific source of randomness
     *
     * @param rand instance of SecureRandom to use
     */
    public CMSSignedDataGenerator(SecureRandom rand) {
        super(rand);
    }

    /**
     *
     * <p>addSigner</p>
     * @Description:
     * @param cert
     * @param digestOID
     * @param signedAttr
     * @param unsignedAttr
     * @throws IllegalArgumentException
     */
    // public void addSigner(X509Certificate cert, String enOID,String
    // digestOID, AttributeTable signedAttr, AttributeTable unsignedAttr) throws
    // IllegalArgumentException
    // {
    // addSigner(cert, ENCRYPTION_RSA, digestOID, signedAttr, unsignedAttr);
    //
    // }

    // public void addSigner(X509CertificateStructure cert,String enOID, String
    // digestOID, AttributeTable signedAttr, AttributeTable unsignedAttr) throws
    // IllegalArgumentException
    // {
    // addSigner(cert, ENCRYPTION_RSA, digestOID, signedAttr, unsignedAttr);
    //
    // }

    /**
     * <p>addSigner</p>
     *
     * @param cert
     * @param encryptionOID
     * @param digestOID
     * @param signedAttr
     * @param unsignedAttr
     * @throws IllegalArgumentException
     * @Description:
     */
    public void addSigner(X509Certificate cert, String encryptionOID, String digestOID, AttributeTable signedAttr, AttributeTable unsignedAttr) throws IllegalArgumentException {
        signerInfs.add(new SignerInf(getSignerIdentifier(cert), digestOID, encryptionOID, new DefaultSignedAttributeTableGenerator(signedAttr), new SimpleAttributeTableGenerator(unsignedAttr),
                signedAttr));
    }

    public void addSigner(X509CertificateStructure cert, String encryptionOID, String digestOID, AttributeTable signedAttr, AttributeTable unsignedAttr) throws IllegalArgumentException {
        signerInfs.add(new SignerInf(getSignerIdentifier(cert), digestOID, encryptionOID, new DefaultSignedAttributeTableGenerator(signedAttr), new SimpleAttributeTableGenerator(unsignedAttr),
                signedAttr));
    }

    /**
     * <p>generate</p>
     *
     * @param eContentType
     * @param content
     * @param encapsulate
     * @param wsecx
     * @param addDefaultAttributes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws CMSException
     * @Description: 生成CMSSignedData数据类型
     */
    public CMSSignedData generate(DERObjectIdentifier signID, String eContentType, CMSProcessable content, boolean encapsulate, boolean isHash, WSecXAppInterface wsecx, boolean addDefaultAttributes, boolean isSigned) throws NoSuchAlgorithmException,
            CMSException {

        ASN1EncodableVector digestAlgs = new ASN1EncodableVector();
        ASN1EncodableVector signerInfos = new ASN1EncodableVector();

        _digests.clear();

        boolean isCounterSignature = (eContentType == null);

        DERObjectIdentifier contentTypeOID = isCounterSignature ? CMSObjectIdentifiers.data : new DERObjectIdentifier(eContentType);

        Iterator it = signerInfs.iterator();

        /**
         * 添加签名者信息
         */
        while (it != null && it.hasNext()) {
            SignerInf signer = (SignerInf) it.next();

            try {
                digestAlgs.add(signer.getDigestAlgorithmID());

                if (isSigned) {
                    signerInfos.add(signer.toSignerInfoBySignedData(contentTypeOID, content, rand, addDefaultAttributes, isCounterSignature));
                } else {
                    signerInfos.add(signer.toSignerInfo(contentTypeOID, content, rand, wsecx, isHash, addDefaultAttributes, isCounterSignature));
                }


            } catch (IOException e) {
                throw new CMSException("encoding error.", e);
            } catch (InvalidKeyException e) {
                throw new CMSException("key inappropriate for signature.", e);
            } catch (SignatureException e) {
                throw new CMSException("error creating signature.", e);
            } catch (CertificateEncodingException e) {
                throw new CMSException("error creating sid.", e);
            }
        }

        ASN1Set certificates = null;

        if (_certs.size() != 0) {
            certificates = CMSUtils.createBerSetFromList(_certs);
        }
        //
        ASN1Set certrevlist = null;

        if (_crls.size() != 0) {
            certrevlist = CMSUtils.createBerSetFromList(_crls);
        }

        ASN1OctetString octs = null;
        if (encapsulate) {
            octs = new DEROctetString((byte[]) content.getContent());
        }

        ContentInfo encInfo = new ContentInfo(contentTypeOID, octs);

        SignedData sd = new SignedData(new DERSet(digestAlgs), encInfo, certificates, null, new DERSet(signerInfos));


        ContentInfo contentInfo = new ContentInfo(signID, sd);

        CMSSignedData ret = new CMSSignedData(content, contentInfo);

        return ret;
    }

    /**
     * <p>generate</p>
     *
     * @param content
     * @param encapsulate
     * @param wsecx
     * @param addDefaultAttributes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws Exception
     * @Description: 生成CMSSignedData数据
     */
    public CMSSignedData generate(DERObjectIdentifier signID, CMSProcessable content, boolean encapsulate, boolean isHash, WSecXAppInterface wsecx, boolean addDefaultAttributes, boolean isSigned) throws NoSuchAlgorithmException, NoSuchProviderException,
            Exception {
        return this.generate(signID, DATA, content, encapsulate, isHash, wsecx, addDefaultAttributes, isSigned);
    }

}
