package com.zyu.wsecx.outter;

import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;
import cn.org.bjca.wsecx.outter.res.ContainerConfig;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WSecXAppInterface.java
 * @包   路   径：  cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述: 证书应用接口 
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-12 下午2:54:16
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 **************************************************************************
 */
public interface WSecXAppInterface {

	/**
	 * 产生指定长度的随机数
	 * 
	 * @param len
	 *            待产生的随机数长度
	 * @return 产生的随机数的Base64编码 备注：由于进行了Base64编码，故返回值字符串长度大于制定长度
	 */
	public byte[] genRandom(int len) throws WSecurityEngineException;

	/**
	 * 数据签名
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param inData 原文
	 * @param @param keyType 密钥类型 1 为加密密钥 2为签名密钥
	 * @param @return String
	 * @param @throws WSecurityEngineException
	 * @return String
	 * @throws
	 */
	public String signData(byte[] inData, int keyType, boolean isHash) throws WSecurityEngineException;

	public String signData(byte[] inData, int keyType, int hashType, boolean isHash) throws WSecurityEngineException;

	/**
	 * 验签
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param base64EncodeCert 证书base64
	 * @param @param inData 原文
	 * @param @param signValue 签名值
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */
	public boolean verifySignedData(String base64EncodeCert, byte[] inData, byte[] signValue, boolean isHash) throws WSecurityEngineException;

	public boolean verifySignedData(String base64EncodeCert, byte[] inData, byte[] signValue, int hashType, boolean isHash) throws WSecurityEngineException;

	/**
	 * 获得指定密钥容器的证书
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param containerName
	 * @param @param keyType
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecXAppInterface
	 * @throws
	 */
	public String getCert(String containerName, int keyType) throws WSecurityEngineException;

	/**
	 * 获得指定密钥容器的公钥
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param 获得指定密钥容器的公钥
	 * @param @param keyType
	 * @param @return
	 * @param @throws WSecXAppException
	 * @return WSecXAppInterface
	 * @throws
	 */
	public String getPubKey(String containerName, int keyType) throws WSecurityEngineException;

	/**
	 * 公钥加密
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param base64EncodeCert
	 * @param @param inData
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return byte[]
	 * @throws
	 */

	public byte[] pubKeyEncrypt(String base64EncodeCert, byte[] inData) throws WSecurityEngineException;

	/**
	 * 公钥加密
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param base64EncodeCert
	 * @param @param inData
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return byte[]
	 * @throws
	 */

	public byte[] pubKeyEncrypt(int keyType, byte[] inData) throws WSecurityEngineException;

	/**
	 * 
	 * @Title: WSecXAppInterface
	 * @Description:私钥解密
	 * @param @param containerName
	 * @param @param keyType
	 * @param @param encryptedData
	 * @param @return
	 * @param @throws WSecXAppException 设定文件
	 * @return WSecXAppInterface 返回类型
	 * @throws
	 */
	// public byte[] priKeyDecrypt(String containerName, int keyType,
	// byte[] encryptedData) throws WSecXContainerException;

	/**
	 * 私钥解密
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param inData
	 * @param @param keyType
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecXAppInterface
	 * @throws
	 */
	public byte[] priKeyDecrypt(byte[] inData, int keyType) throws WSecurityEngineException;

	/**
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: 数据对称解密
	 * @param @param plainText
	 * @param @param Key
	 * @param @param alg
	 * @param @return
	 * @param @throws WSecXAppException 设定文件
	 * @return WSecXAppInterface 返回类型
	 * @throws
	 */
	// public byte[] symEncrypt(byte[] plainText, byte[] Key, String alg)
	// throws WSecXContainerException;
	/**
	 * 数据对称解密
	 * 
	 * @Title: 数据对称解密
	 * @Description: TODO
	 * @param @param key
	 * @param @param inData
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecXAppInterface
	 * @throws
	 */

	public String symmEncryptData(byte[] key, byte[] inData) throws WSecurityEngineException;

	/**
	 * 数据对称解密
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: TODO
	 * @param @param key
	 * @param @param inData
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecXAppInterface
	 * @throws
	 */
	public String symmDecryptData(byte[] key, byte[] inData) throws WSecurityEngineException;

	/**
	 * 获得证书基本信息
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: 获得证书基本信息
	 * @param @param base64Cert
	 * @param @param infoType
	 * @param @return
	 * @param @throws WSecXAppException
	 * @return 证书基本信息
	 * @throws
	 */

	public Object getCertInfo(String base64Cert, int infoType) throws WSecurityEngineException;

	/**
	 * 解析证书私有扩展项信息
	 * 
	 * @Title: WSecXAppInterface
	 * @Description: 解析证书私有扩展项信息
	 * @param @param base64Cert
	 * @param @param oid 扩展ID
	 * @param @return
	 * @param @throws WSecXAppException
	 * @return String 证书扩展信息
	 * @throws
	 */
	public byte[] getExtCertInfo(String base64Cert, String oid) throws WSecurityEngineException;

	/**
	 * 
	  * <p>signEleStamp</p>
	  * @Description:移动电子签章
	  * @param originalData 原文
	  * @param stampPic  电子图章（支持图片和公司自定义两种格式）（BASE64）
	  * @param keyType 政务、信天行
	 *            BJCAWirelessInterface.KeyEncrypt 
	 *            BJCAWirelessInterface.KeySign
	  * @param CharSet 字符集
	  * @return
	  * @throws WSecurityEngineException 签章值（和PC网页签章格式相同）
	 */
	public String signEleStamp(String originalData, String stampPic, int keyType, String CharSet) throws WSecurityEngineException;

	/**
	 * 
	  * <p>signEleStamp</p>
	  * @Description:移动电子签章
	  * @param originalData 原文
	  * @param stampPic  电子图章（支持图片和公司自定义两种格式）（BASE64）
	  * @param signTime 签名时间外部来源
	  * @param keyType 政务、信天行
	 *            BJCAWirelessInterface.KeyEncrypt 
	 *            BJCAWirelessInterface.KeySign
	  * @param CharSet 字符集
	  * @return
	  * @throws WSecurityEngineException 签章值（和PC网页签章格式相同）
	 */
	public String signEleStamp(String originalData, String stampPic, long signTime, int keyType, String CharSet) throws WSecurityEngineException;

	/**
	 * 
	  * <p>signEleStamp</p>
	  * @Description: 适用于蓝牙KEY 以提高签章性能
	  * @param originalData 原文
	  * @param stampPic 电子图章（支持图片和公司自定义两种格式）（BASE64）
	  * @param signTime 可以为空
	  * @param keyType
	  *           BJCAWirelessInterface.KeyEncrypt 
	 *            BJCAWirelessInterface.KeySign
	  * @param hashType
	  * 
	  *             public static final int MD5 = 0;
					public static final int SHA_1 = 1;
					public static final int SHA_256 = 2;
					public static final int SM3 = 3;
	  * @param CharSet
	  * @return
	  * @throws WSecurityEngineException
	 */

	public String signEleStamp(String originalData, long signTime, int keyType, int hashType, String CharSet) throws WSecurityEngineException;

	/**
	 * 
	  * <p>signEleStamp</p>
	  * @Description:适用于蓝牙KEY 的外部缓存图章和证书
	  * @param originalData 原文
	  * @param stampPic 图片
	  * @param cert 证书
	  * @param signTime 签名时间
	  * @param CharSet 字符集
	  * @return
	  * @throws WSecurityEngineException
	 */
	public String signEleStamp(String originalData, String stampPic, String cert, long signTime, String CharSet) throws WSecurityEngineException;

	/**
	 *  验证签章,用户传入原文，与从签章值取出的图片组成真正的原文，再用证书验签。
	  * <p>verifyEleStamp</p>
	  * @Description:
	  * @param origianlData  原文
	  * @param eleStamp 签章
	  * @param CharSet 字符集
	  * @return
	  * @throws WSecurityEngineException
	 */
	public boolean verifyEleStamp(String origianlData, String eleStamp, String CharSet) throws WSecurityEngineException;

	/**
	 * 
	  * <p>getEleStamp</p>
	  * @Description: 获得电子签章
	  * @param stampPic 电子签章图片
	  * @return
	  * @throws WSecurityEngineException
	 */
	public String getEleStamp(String stampPic) throws WSecurityEngineException;

	/**
	 * 
	  * <p>getSignEleStamp</p>
	  * @Description: 获得电子签章图片
	  * @param stampPic 电子签章值
	  * @return
	  * @throws WSecurityEngineException
	 */
	public String getSignEleStamp(String eleStamp) throws WSecurityEngineException;

	/**
	 * 
	  * <p>hash</p>
	  * @Description:哈希算法
	  * @param hashType
	  * @param orgs
	  * @return
	  * @throws WSecurityEngineException
	 */

	public String hash(int hashType, String certBase64, byte[]... orgs) throws WSecurityEngineException;

	/**
	 * 
	  * <p>signSignedDataPkcs7</p>
	  * @Description:签名数字信封
	  * @param inData原文
	  * @param attach 是否带原文
	  * @return
	  * @throws WSecurityEngineException
	 */

	public String signSignedDataPkcs7(byte[] inData, boolean attac, boolean isHashh) throws WSecurityEngineException;

	public String signSignedDataPkcs7(byte[] signedDataPk1, String certBy, int hashAlg) throws WSecurityEngineException;

	/**
	 * 
	  * <p>verifySignedDataPkcs7</p>
	  * @Description:（带原文验签）
	  * @param signedValue P7签名值
	  * @return
	  * @throws WSecurityEngineException
	 */
	public boolean verifySignedDataPkcs7(String signedValue) throws WSecurityEngineException;

	/**
	 * 
	  * <p>verifySignedDataPkcs7</p>
	  * @Description:（不带原文验签）
	  * @param signedValue P7签名值
	  * @param orgData 原文
	  * @return
	  * @throws WSecurityEngineException
	 */
	public boolean verifySignedDataPkcs7(String signedValue, byte[] orgData, boolean isHash) throws WSecurityEngineException;

	/**
	  * <p>encodeP7EnvelopedData</p>
	  * @Description:加密数字信封
	  * @param inData 原文
	  * @param certStr 证书的base64
	  * 
	  * @return
	  * @throws WSecurityEngineException
	  */

	public String encodeP7EnvelopedData(byte[] inData, String certStr) throws WSecurityEngineException;

	/**
	 * 
	  * <p>decodeP7EnvelopedData</p>
	  * @Description:解密数字信封
	  * @param containerName 容器别名
	  * @param enCodeData 加密信封
	  * @return
	  * @throws WSecurityEngineException
	 */

	public String decodeP7EnvelopedData(String containerName, String enCodeData) throws WSecurityEngineException;

	/**
	  * <p>symmEncryptData</p>
	  * @Description:
	  * @param key
	  * @param inData
	  * @param symm
	  * @param mode
	  * @param iv
	  * @return
	  * @throws WSecurityEngineException
	  */

	public String symmEncryptData(byte[] key, byte[] inData, int symm, int mode, byte[] iv) throws WSecurityEngineException;

	/**
	  * <p>symmDecryptData</p>
	  * @Description:
	  * @param key
	  * @param inData
	  * @param symm
	  * @param mode
	  * @param iv
	  * @return
	  * @throws WSecurityEngineException
	  */

	public String symmDecryptData(byte[] key, byte[] inData, int symm, int mode, byte[] iv) throws WSecurityEngineException;

	public void setConfig(ContainerConfig config);

	public ContainerConfig getConfig();

	public boolean isEleStampFormat(String stampPic);

	/**
	 * 释放环境
	 * 
	 * @return 成功返回 true ，失败返回false
	 */
	public boolean finalizeEnv();
}
