package com.zyu.wsecx.outter;

import android.content.pm.PackageInfo;

import cn.org.bjca.wsecx.core.impl.Copyright;
import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;
import cn.org.bjca.wsecx.outter.res.ContainerConfig;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WSecXCounterSignerInterface.java
 * @包   路   径：  cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述: 证书附属签名的应用接口 
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
public interface WSecXCounterSignerInterface {
	/**
	 * 
	  * <p>hasCounterSignature</p>
	  * @Description:是否包含附属签名
	  * @param info android提取的应用包名对应的应用包
	  * @return
	  * @throws WSecurityEngineException
	 */
	public boolean hasCounterSignature(PackageInfo info) throws WSecurityEngineException;

	/**
	 * 
	  * <p>verifySignature</p>
	  * @Description:附属签名验证
	  * @param info android提取的应用包名对应的应用包
	  * @return
	  * @throws WSecurityEngineException
	 */
	public boolean verifySignature(PackageInfo info) throws WSecurityEngineException;

	/**
	 * 
	  * <p>setConfig</p>
	  * @Description:算法配置
	  * @param config
	 */
	public void setConfig(ContainerConfig config);

	/**
	 * 
	  * <p>getConfig</p>
	  * @Description:算法配置
	  * @return
	 */
	public ContainerConfig getConfig();
	
	/**
	 * 
	  * <p>hasCounterSignature</p>
	  * @Description:是否包含附属签名
	  * @param info android提取的应用包名对应的应用包
	  * @return
	  * @throws WSecurityEngineException
	 */
	public Copyright hasCopyrightSignature(PackageInfo info) throws WSecurityEngineException;
	
	public Copyright hasCopyrightSignature(String pakPath) throws WSecurityEngineException;

	/**
	 * 
	  * <p>verifySignature</p>
	  * @Description:附属签名验证
	  * @param info android提取的应用包名对应的应用包
	  * @return
	  * @throws WSecurityEngineException
	 */
	public boolean verifyCopyrightSignature(PackageInfo info) throws WSecurityEngineException;
}
