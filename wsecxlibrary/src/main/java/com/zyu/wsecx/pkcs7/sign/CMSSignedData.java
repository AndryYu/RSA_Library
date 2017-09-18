package com.zyu.wsecx.pkcs7.sign;

import com.zyu.wsecx.asn1.ASN1EncodableVector;
import com.zyu.wsecx.asn1.ASN1InputStream;
import com.zyu.wsecx.asn1.ASN1OctetString;
import com.zyu.wsecx.asn1.ASN1Sequence;
import com.zyu.wsecx.asn1.ASN1Set;
import com.zyu.wsecx.asn1.BERSequence;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DERSet;
import com.zyu.wsecx.asn1.cms.ContentInfo;
import com.zyu.wsecx.asn1.cms.SignedData;
import com.zyu.wsecx.asn1.cms.SignerInfo;
import com.zyu.wsecx.pkcs7.CMSException;
import com.zyu.wsecx.pkcs7.SignerInformationStore;
import com.zyu.wsecx.x509.NoSuchStoreException;
import com.zyu.wsecx.x509.X509Store;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * *************************************************************************
 * <pre></pre>
 *
 * @文件名称: CMSSignedData.java
 * @包 路   径：  cn.org.bjca.wsecx.core.pkcs7.sign
 * @版权所有：北京数字认证股份有限公司 (C) 2016
 * @类描述: P7签名包封装
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2016-8-23 下午3:15:43
 * @修改记录： -----------------------------------------------------------------------------------------------
 * 时间                      |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 * |                 |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
public class CMSSignedData {
    private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;

    SignedData signedData;
    ContentInfo contentInfo;
    CMSProcessable signedContent;
    CertStore certStore;
    SignerInformationStore signerInfoStore;
    X509Store attributeStore;
    X509Store certificateStore;
    X509Store crlStore;
    private Map hashes;

    private CMSSignedData(
            CMSSignedData c) {
        this.signedData = c.signedData;
        this.contentInfo = c.contentInfo;
        this.signedContent = c.signedContent;
        this.certStore = c.certStore;
        this.signerInfoStore = c.signerInfoStore;
    }

    public CMSSignedData(
            byte[] sigBlock)
            throws CMSException {
        this(CMSUtils.readContentInfo(sigBlock));
    }

    public CMSSignedData(
            CMSProcessable signedContent,
            byte[] sigBlock)
            throws CMSException {
        this(signedContent, CMSUtils.readContentInfo(sigBlock));
    }


    public CMSSignedData(
            CMSProcessable signedContent,
            InputStream sigData)
            throws CMSException {
        this(signedContent, CMSUtils.readContentInfo(new ASN1InputStream(sigData)));
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param sigData P7流
     * @throws CMSException
     */
    public CMSSignedData(
            InputStream sigData)
            throws CMSException {
        this(CMSUtils.readContentInfo(sigData));
    }

    public CMSSignedData(
            CMSProcessable signedContent,
            ContentInfo sigData) {
        this.signedContent = signedContent;
        this.contentInfo = sigData;
        this.signedData = SignedData.getInstance(contentInfo.getContent());
    }


    /**
     * <p>Title: </p>
     * <p>Description:证书链 </p>
     *
     * @param sigData
     */
    public CMSSignedData(
            ContentInfo sigData) {
        this.contentInfo = sigData;
        this.signedData = SignedData.getInstance(contentInfo.getContent());

        //

        if (signedData.getEncapContentInfo().getContent() != null) {
            this.signedContent = new CMSProcessableByteArray(
                    ((ASN1OctetString) (signedData.getEncapContentInfo()
                            .getContent())).getOctets());
        } else {
            this.signedContent = null;
        }
    }


    public int getVersion() {
        return signedData.getVersion().getValue().intValue();
    }

    /**
     * <p>getSignerInfos</p>
     *
     * @return
     * @Description:签名信息集合
     */
    public SignerInformationStore getSignerInfos() {
        if (signerInfoStore == null) {
            ASN1Set s = signedData.getSignerInfos();
            List signerInfos = new ArrayList();

            for (int i = 0; i != s.size(); i++) {
                SignerInfo info = SignerInfo.getInstance(s.getObjectAt(i));
                DERObjectIdentifier contentType = signedData.getEncapContentInfo().getContentType();

                if (hashes == null) {
                    signerInfos.add(new SignerInformation(info, contentType, signedContent, null));
                } else {

                    byte[] hash = (byte[]) hashes.get(info.getDigestAlgorithm().getObjectId().getId());

                    signerInfos.add(new SignerInformation(info, contentType, null, new BaseDigestCalculator(hash)));
                }
            }

            signerInfoStore = new SignerInformationStore(signerInfos);
        }

        return signerInfoStore;
    }


    public X509Store getAttributeCertificates(
            String type)
            throws NoSuchStoreException, CMSException {
        if (attributeStore == null) {
            attributeStore = HELPER.createAttributeStore(type, signedData.getCertificates());
        }

        return attributeStore;
    }


    public X509Store getCertificates(
            String type)
            throws NoSuchStoreException, CMSException {
        if (certificateStore == null) {
            certificateStore = HELPER.createCertificateStore(type, signedData.getCertificates());
        }

        return certificateStore;
    }


    public X509Store getCRLs(
            String type)
            throws NoSuchStoreException, CMSException {
        if (crlStore == null) {
            crlStore = HELPER.createCRLsStore(type, signedData.getCRLs());
        }

        return crlStore;
    }


    public CertStore getCertificatesAndCRLs(
            String type)
            throws NoSuchAlgorithmException, CMSException {
        if (certStore == null) {
            ASN1Set certSet = signedData.getCertificates();
            ASN1Set crlSet = signedData.getCRLs();

            certStore = HELPER.createCertStore(type, certSet, crlSet);
        }

        return certStore;
    }


    public String getSignedContentTypeOID() {
        return signedData.getEncapContentInfo().getContentType().getId();
    }

    public CMSProcessable getSignedContent() {
        return signedContent;
    }

    /**
     * return the ContentInfo
     */
    public ContentInfo getContentInfo() {
        return contentInfo;
    }


    public byte[] getEncoded()
            throws IOException {
        return contentInfo.getEncoded();
    }


    public static CMSSignedData replaceSigners(
            CMSSignedData signedData,
            SignerInformationStore signerInformationStore) {
        //
        // copy
        //
        CMSSignedData cms = new CMSSignedData(signedData);


        cms.signerInfoStore = signerInformationStore;


        ASN1EncodableVector digestAlgs = new ASN1EncodableVector();
        ASN1EncodableVector vec = new ASN1EncodableVector();

        Iterator it = signerInformationStore.getSigners().iterator();
        while (it.hasNext()) {
            SignerInformation signer = (SignerInformation) it.next();
            digestAlgs.add(CMSSignedHelper.INSTANCE.fixAlgID(signer.getDigestAlgorithmID()));
            vec.add(signer.toSignerInfo());
        }

        ASN1Set digests = new DERSet(digestAlgs);
        ASN1Set signers = new DERSet(vec);
        ASN1Sequence sD = (ASN1Sequence) signedData.signedData.getDERObject();

        vec = new ASN1EncodableVector();

        //
        // signers are the last item in the sequence.
        //
        vec.add(sD.getObjectAt(0)); // version
        vec.add(digests);

        for (int i = 2; i != sD.size() - 1; i++) {
            vec.add(sD.getObjectAt(i));
        }

        vec.add(signers);

        cms.signedData = SignedData.getInstance(new BERSequence(vec));


        cms.contentInfo = new ContentInfo(cms.contentInfo.getContentType(), cms.signedData);

        return cms;
    }


    public static CMSSignedData replaceCertificatesAndCRLs(
            CMSSignedData signedData,
            CertStore certsAndCrls)
            throws CMSException {
        //
        // copy
        //
        CMSSignedData cms = new CMSSignedData(signedData);


        cms.certStore = certsAndCrls;


        ASN1Set certs = null;
        ASN1Set crls = null;

        try {
            ASN1Set set = CMSUtils.createBerSetFromList(CMSUtils.getCertificatesFromStore(certsAndCrls));

            if (set.size() != 0) {
                certs = set;
            }
        } catch (CertStoreException e) {
            throw new CMSException("error getting certs from certStore", e);
        }

        try {
            ASN1Set set = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(certsAndCrls));

            if (set.size() != 0) {
                crls = set;
            }
        } catch (CertStoreException e) {
            throw new CMSException("error getting crls from certStore", e);
        }


        cms.signedData = new SignedData(signedData.signedData.getDigestAlgorithms(),
                signedData.signedData.getEncapContentInfo(),
                certs,
                crls,
                signedData.signedData.getSignerInfos());


        cms.contentInfo = new ContentInfo(cms.contentInfo.getContentType(), cms.signedData);

        return cms;
    }
}
