/**   
 * @Title: WSecXCertContainerInterface.java 
 * @Package cn.org.bjca.wsecx 
 * @Description: TODO
 * @author liyade
 * @date 2014-1-27 下午1:14:54 
 * @version V1.0   
 */
package com.zyu.wsecx.outter;

import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;
import cn.org.bjca.wsecx.outter.res.ContainerConfig;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WSecXCertContainerInterface.java
 * @包   路   径：  cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述: 证书容器管理接口 
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-12 下午2:54:45
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
public interface WSecXCertContainerInterface {
	/**
	 * 产生密钥对
	 * 
	 * @Title: WSecXCertContainerInterface
	 * @Description: TODO
	 * @param @param containerName 容器别名
	 * @param @param algType 算法类型（1：RSA-1024 ，2： SM2-256）
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecXCertContainerInterface
	 * @throws
	 */
	public int generateKeyPair(String containerName, int algType)
			throws WSecurityEngineException;

	/**
	 * 导入证书
	 * 
	 * @Title: WSecXCertContainerInterface
	 * @Description: TODO
	 * @param @param cert
	 * @param @param keyType 密钥类型（1：加密密钥，2：签名密钥）
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */

	public int importCertificate(byte[] cert, int keyType)
			throws WSecurityEngineException;

	/**
	 * @throws WSecurityEngineException 
	 * 目前未实现
	 * 
	 * @Title: WSecXCertContainerInterface
	 * @Description: TODO
	 * @param @param encryptedWrapKey 使用该容器内签名公钥保护的对称算法密钥
	 * @param @param pubKey 加密公钥
	 * @param @param encryptedPriKey 使用对称密钥加密的加密私钥
	 * @param @param algType
	 * @param @return
	 * @return WSecXCertContainerInterface
	 * @throws
	 */

	public int importEncryptionKeyPair(byte[] encryptedWrapKey,
                                       byte[] pubKey, byte[] encryptedPriKey, int algType) throws WSecurityEngineException;


	/**
	 * @throws WSecurityEngineException 
	 * 目前未实现
	 * 
	 * @Title: WSecXCertContainerInterface
	 * @Description: TODO
	 * @param @param pkcs12
	 * @param @param p12Password
	 * @param @param keyType
	 * @param @return 设定文件
	 * @return WSecXCertContainerInterface 返回类型
	 * @throws
	 */

	public int importP12(byte[] pkcs12, byte[] p12Password, int keyType) throws WSecurityEngineException;

	/**
	 * 导出公钥
	 * 
	 * @Title: WSecXCertContainerInterface
	 * @Description: TODO
	 * @param @param containerName
	 * @param @param keyType
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecXCertContainerInterface
	 * @throws
	 */

	public String exportPubKey(String containerName, int keyType)
			throws WSecurityEngineException;
	
	/**
	 * 产生p10请求
	* @Title: WSecXCertContainerInterface
	* @Description: TODO
	* @param @param containerName
	* @param @param dn
	* @param @param algType
	* @param @return
	* @param @throws WSecurityEngineException    
	* @return WSecXCertContainerInterface    
	* @throws
	 */

	public String genP10CertRequest(String containerName, String dn, int algType)
			throws WSecurityEngineException;
	
	
	public void setConfig(ContainerConfig config);

	public ContainerConfig getConfig();
}
