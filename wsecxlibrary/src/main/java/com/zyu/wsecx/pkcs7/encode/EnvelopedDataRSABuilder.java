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
 * @文件名称: EnvelopedDataBuilder.java
 * @包 路   径：  cn.org.bjca.wsecx.core.pkcs7
 * @版权所有：北京数字认证股份有限公司 (C) 2015
 * @类描述: RSA加密数字信封发生器
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2015-2-1 下午8:18:04
 * @修改记录： -----------------------------------------------------------------------------------------------
 * 时间                      |       修改人            |         修改的方法                       |         修改描述
 * -----------------------------------------------------------------------------------------------
 * |                 |                           |
 * -----------------------------------------------------------------------------------------------
 * <p>
 * *************************************************************************
 */
public class EnvelopedDataRSABuilder extends EnvelopedBuilder {
    private final static int CBC_IV_LEN = 8;
    /**
     * 对称密钥长度
     */
    private final static int KEY_LEN = 24;

    public EnvelopedDataRSABuilder() {
    }

    public EnvelopedDataRSABuilder(SecureRandom rand) {
        super(rand);
    }

    public CMSEnvelopedData generate(DERObjectIdentifier enID, WSecXAppInterface wsecx, CMSProcessable content, byte[] iv) throws WSecurityEngineException {

        ASN1EncodableVector recipientInfos = new ASN1EncodableVector();
        AlgorithmIdentifier encAlgId = null;
        ASN1OctetString encContent = null;
        byte[] key = wsecx.genRandom(KEY_LEN);

        SecureRandom random = null;
        //
        try {
            /**
             * CBC 矢量值
             */
            if (iv == null) {
                iv = new byte[CBC_IV_LEN];

                random = new SecureRandom();

                random.nextBytes(iv);
            }

            String encryptionOID = EnvelopedBuilder.DES_EDE3_CBC;

            byte[] orgContentg = (byte[]) content.getContent();

            encAlgId = new AlgorithmIdentifier(new DERObjectIdentifier(encryptionOID),

                    new DEROctetString(iv));

            orgContentg = (byte[]) content.getContent();

//			/**
//			 * 对称密钥加密原文
//			 */
//			String enContentBase64 = wsecx.symmEncryptData(key, orgContentg);
//			
            /**
             * 对称密钥加密原文
             */
            String enContentBase64 = wsecx.symmEncryptData(key, orgContentg, BJCAWirelessInterface.TDES, BJCAWirelessInterface.CBC, iv);


            if (enContentBase64 == null) {
                throw new WSecurityEngineException(BJCAWirelessInfo.ErrorInfo.PKCS7_ENCODE_ERROR, "对称加密失败");
            }

            byte[] enContentBin = Base64.decode(enContentBase64);

            encContent = new BERConstructedOctetString(enContentBin);

            Iterator it = recipientInfoGenerators.iterator();

            /**
             * 证书加密对称密钥
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
