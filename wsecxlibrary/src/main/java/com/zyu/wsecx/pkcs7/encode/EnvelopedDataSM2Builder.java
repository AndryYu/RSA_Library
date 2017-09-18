package com.zyu.wsecx.pkcs7.encode;

import android.util.Base64;

import com.zyu.wsecx.asn1.ASN1EncodableVector;
import com.zyu.wsecx.asn1.ASN1OctetString;
import com.zyu.wsecx.asn1.BERConstructedOctetString;
import com.zyu.wsecx.asn1.DERObjectIdentifier;
import com.zyu.wsecx.asn1.DEROctetString;
import com.zyu.wsecx.asn1.DERSet;
import com.zyu.wsecx.asn1.cms.CMSObjectIdentifiers;
import com.zyu.wsecx.asn1.cms.EncryptedContentInfo;
import com.zyu.wsecx.asn1.cms.EnvelopedData;
import com.zyu.wsecx.asn1.pkcs.ContentInfo;
import com.zyu.wsecx.asn1.x509.AlgorithmIdentifier;
import com.zyu.wsecx.pkcs7.CMSEnvelopedData;
import com.zyu.wsecx.pkcs7.sign.CMSProcessable;

import java.security.SecureRandom;
import java.util.Iterator;



/**
 * *************************************************************************
 * <pre></pre>
 *
 * @文件名称: EnvelopedDataSM2Builder.java
 * @包 路   径：  cn.org.bjca.wsecx.core.pkcs7.encode
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 * @类描述: 国产算法PKCS7封装
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2015-2-9 下午7:19:32
 * @修改记录： -----------------------------------------------------------------------------------------------
 * 时间                      |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 * |                 |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
public class EnvelopedDataSM2Builder extends EnvelopedBuilder {
    private final static int CBC_IV_LEN = 16;

    /**
     * 对称密钥长度
     */
    private final static int KEY_LEN = 16;

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     */

    public EnvelopedDataSM2Builder() {
        // TODO Auto-generated constructor stub
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param rand
     */

    public EnvelopedDataSM2Builder(SecureRandom rand) {
        super(rand);
        // TODO Auto-generated constructor stub
    }

    public CMSEnvelopedData generate(DERObjectIdentifier enID, WSecXAppInterface wsecx, CMSProcessable content, byte[] iv) throws WSecurityEngineException {

        ASN1EncodableVector recipientInfos = new ASN1EncodableVector();
        AlgorithmIdentifier encAlgId = null;
        ASN1OctetString encContent = null;


        byte[] key = wsecx.genRandom(KEY_LEN);

        try {

            byte[] ivs = new byte[CBC_IV_LEN];
            SecureRandom ivRandom = new SecureRandom();
            ivRandom.nextBytes(ivs);

            String encryptionOID = EnvelopedBuilder.SM4_CBC;

            byte[] contentOrg = (byte[]) content.getContent();

            encAlgId = new AlgorithmIdentifier(new DERObjectIdentifier(encryptionOID),

                    new DEROctetString(ivs));

            contentOrg = (byte[]) content.getContent();

            /**
             * 对称密钥加密原文
             */
            String enKey = wsecx.symmEncryptData(key, contentOrg, BJCAWirelessInterface.SM4, BJCAWirelessInterface.CBC, ivs);

            if (enKey == null) {
                throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.PKCS7_ENCODE_ERROR, "对称加密失败");
            }

            byte[] enKeyBy = Base64.decode(enKey);

            encContent = new BERConstructedOctetString(enKeyBy);

            Iterator it = recipientInfoGenerators.iterator();

            /**
             * 加密对称密钥
             */

            while (it.hasNext()) {
                KeyRecipientInfoBuilder recipient = (KeyRecipientInfoBuilder) it.next();

                recipientInfos.add(recipient.generate(key, rand, wsecx));
            }

            EncryptedContentInfo eci = new EncryptedContentInfo(CMSObjectIdentifiers.data, encAlgId, encContent);

            ContentInfo contentInfo = new ContentInfo(enID, new EnvelopedData(null, new DERSet(recipientInfos), eci, null));

            return new CMSEnvelopedData(contentInfo);
        } catch (Exception e) {

            throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.PKCS7_ENCODE_ERROR, e);

        }

    }

}
