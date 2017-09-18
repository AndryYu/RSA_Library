/**   
 * @Title: ContainerConfig.java 
 * @Package cn.org.bjca.res 
 * @Description: TODO
 * @author liyade
 * @date 2014-2-11 上午9:22:47 
 * @version V1.0   
 */
package com.zyu.wsecx.outter.res;

import java.util.Collection;

import cn.org.bjca.wsecx.interfaces.BJCAWirelessInterface;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  ContainerConfig.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.res
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述: 中间件基础配置类  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-9 下午6:38:21
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
public class ContainerConfig {
	
	
	public final static String DEF_ID = "SoftWirelessImpl"; 

	private String containerAlias = null;

	/**
	 * xml 配置描述
	 */
	private WsecxConfig wsecxConfig = null;

	private DeviceDescribe selDeviceDescribe = null;
	
//	private boolean isRefresh = false;

	/**
	 * @return the selDeviceDescribe
	 */
	public DeviceDescribe getSelDeviceDescribe() {
		return selDeviceDescribe;
	}

	/**
	  * @param selDeviceDescribe 要设置的 selDeviceDescribe
	  */

	public void setSelDeviceDescribe(DeviceDescribe selDeviceDescribe) {
		this.selDeviceDescribe = selDeviceDescribe;
	}

	/**
	 * @return the wsecxConfig
	 */
	public WsecxConfig getWsecxConfig() {
		return wsecxConfig;
	}

	/**
	 * 厂商标识
	 */
	private String selDeviceID;
	/**
	 * OTG
	 * 蓝牙
	 */

	private boolean isLogin = false;

	/**
	 * @Title: ContainerConfig
	 * @Description: TODO
	 * @param 设定文件
	 * @return ContainerConfig 返回类型
	 * @throws
	 */

	public ContainerConfig(WsecxConfig wsecxConfig) {
		// TODO Auto-generated constructor stub
		this.wsecxConfig = wsecxConfig;
	}
	
	public ContainerConfig(String provider) {
		// TODO Auto-generated constructor stub
		this.wsecxConfig = new WsecxConfig();
		DeviceDescribe dev = new DeviceDescribe();
		dev.setDeviceID(DEF_ID);
		dev.setId(DEF_ID);
		dev.setDescribe("北京CA软实现");
		dev.setDeviceName(DEF_ID);
		dev.setType(BJCAWirelessInterface.SOFT);
		dev.setProvider(provider);
		
		this.wsecxConfig.addDeviceDescribe(dev);
		
		wsecxConfig.setDefaultAlg(new DefaultAlg());
		
	}

	/**
	 * @return the defaultDevice
	 */
	public DeviceDescribe getDeaultSelDevice() {
		
		return selDeviceDescribe;
	}

	/**
	 * @return the defaultDevice
	 */
	public DeviceDescribe getSelDevice(String id) {
		DeviceDescribe device = this.wsecxConfig.getDeviceDescribe(id);

		if (device != null) {
			selDeviceDescribe = device;

			this.selDeviceID = id;
			return selDeviceDescribe;
		}
		String deviceID = this.wsecxConfig.getDefaultDeviceID();

		selDeviceDescribe = this.wsecxConfig.getDeviceDescribe(deviceID);

		return selDeviceDescribe;

	}

	/**
	 * @return the deviceFlag
	 */
	public String getSelDeviceID() {
		return selDeviceID;
	}

	/**
	 * @param selDeviceDescribe the defaultDevice to set
	 */
	public Collection<DeviceDescribe> getDeviceList() {
		return wsecxConfig.getDeviceList();
	}

	/**
	 * @return the containerAlias
	 */
	public String getContainerAlias() {
		
		return containerAlias;
	}

	/**
	 * @param containerAlias the containerAlias to set
	 */
	public void setContainerAlias(String containerAlias) {
		this.containerAlias = containerAlias;
//		isRefresh = true;
	}

	/**
	 * @return the algHash
	 */
	public int getAlgHash() {
		return wsecxConfig.getDefaultAlg().getHash();
	}

//	/**
//	 * @param algHash the algHash to set
//	 */
//	public void setAlgHash(int algHash) {
//		wsecxConfig.getDefaultAlg().setHash(algHash);
//	}

	/**
	 * @return the algSym
	 */
	public int getSymm() {
		return wsecxConfig.getDefaultAlg().getSymm();
	}

	/**
	 * @param algSym the algSym to set
	 */
	public void setSymm(int algSym) {
		// this.algSym = algSym;
		wsecxConfig.getDefaultAlg().setSymm(algSym);
//		isRefresh = true;
	}

	/**
	 * @return the algDisSym
	 */
	public int getAsymm() {
		
		return wsecxConfig.getDefaultAlg().getAsymm();
		
	}
	
	public int getSymmMode() {
		return wsecxConfig.getDefaultAlg().getSymmMode();
	}

	/**
	 * @param algDisSym the algDisSym to set
	 */
	public void setAsymm(int algDisSym) {
		// this.algDisSym = algDisSym;
		wsecxConfig.getDefaultAlg().setAsymm(algDisSym);
//		isRefresh = true;
	}

	/**
	 * @return the deviceType
	 */
	public int getDeviceType() {
		if (selDeviceDescribe == null) {
			return BJCAWirelessInterface.UNKNOW;
		}

		return selDeviceDescribe.getType();
	}

	/**
	 * @return the isLogin
	 */
	public boolean isLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin the isLogin to set
	 */
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
//		isRefresh = true;
	}
	
	/**
	 * 
	  * <p>setSignAlg</p>
	  * @Description:
	  * SM3WITHSM2
		SHA1WITHRSA
		SHA256WITHRSA
		
		asymLen:
		BJCAWirelessInterface.RSA_1024
		
	  * @param signAlg
	 */
	public void setSignAlg(String signAlg,String asymm)
	{
		if(signAlg == null)
		{
			return;
		}
		DefaultAlg alg = wsecxConfig.getDefaultAlg();
		
		if(signAlg.equalsIgnoreCase("SHA1WITHRSA"))
		{
			
			alg.setHash(BJCAWirelessInterface.SHA_1);
			alg.setSymm(BJCAWirelessInterface.TDES);
			alg.setSymmMode(BJCAWirelessInterface.ECB);
			
			
		}
		else if(signAlg.equalsIgnoreCase("SHA256WITHRSA"))
		{
			
			alg.setHash(BJCAWirelessInterface.SHA_256);
			alg.setSymm(BJCAWirelessInterface.TDES);
			alg.setSymmMode(BJCAWirelessInterface.ECB);
			
		}
		else if(signAlg.equalsIgnoreCase("SM3WITHSM2"))
		{
			
			alg.setAsymm(BJCAWirelessInterface.SM2_256);
			alg.setSymm(BJCAWirelessInterface.SM4);
			alg.setSymmMode(BJCAWirelessInterface.CBC);
			
		}else
		{
			return;
		}
		
		
		
		if(asymm.equalsIgnoreCase("RSA-1024"))
		{	
			alg.setAsymm(BJCAWirelessInterface.RSA_1024);
		}
		else if(asymm.equalsIgnoreCase("RSA-2048"))
		{
			alg.setAsymm(BJCAWirelessInterface.RSA_2048);
		}
		else if(asymm.equalsIgnoreCase("SM2-256"))
		{
			alg.setAsymm(BJCAWirelessInterface.SM2_256);
		}else
		{
			return;
		}
		
		wsecxConfig.setDefaultAlg(alg);
		
//		isRefresh = true;
	}
	
	/**
	 * 
	  * <p>setSignAlg</p>
	  * @Description:
	  * RSA-1024
		RSA-2048
		SM2-256
		
		
		
		
		BJCAWirelessInterface.SHA_1
		BJCAWirelessInterface.SHA_256
	  * @param signAlg
	 */
	public void setAsymmAlg(String asymm,int hashAlg)
	{
		if(asymm == null)
		{
			return;
		}
		
		DefaultAlg alg = wsecxConfig.getDefaultAlg();
		if(asymm.equalsIgnoreCase("RSA-1024"))
		{	
			alg.setAsymm(BJCAWirelessInterface.RSA_1024);
			alg.setHash(hashAlg);
			alg.setSymm(BJCAWirelessInterface.TDES);
			alg.setSymmMode(BJCAWirelessInterface.ECB);//24
			
			
		}
		else if(asymm.equalsIgnoreCase("RSA-2048"))
		{
		
			alg.setAsymm(BJCAWirelessInterface.RSA_2048);
			alg.setHash(hashAlg);
			alg.setSymm(BJCAWirelessInterface.TDES);
			alg.setSymmMode(BJCAWirelessInterface.ECB);
		
			
		}
		else if(asymm.equalsIgnoreCase("SM2-256"))
		{
			
			alg.setAsymm(BJCAWirelessInterface.SM2_256);
			alg.setHash(hashAlg);
			alg.setSymm(BJCAWirelessInterface.SM4);
			alg.setSymmMode(BJCAWirelessInterface.CBC);
			
		}else
		{
			return;
		}
		wsecxConfig.setDefaultAlg(alg);
		
//		isRefresh = true;
	}
	
	
	
	/**
	 * 
	  * <p>setAlg</p>
	  * @Description: 老算法
	  * SM3WITHSM2
		SHA1WITHRSA
	  * @param signAlg
	 */
	public void setAlg(String asymm)
	{
		//BJCAWirelessInterface.SM3
		if(asymm == null)
		{
			return;
		}
		
		DefaultAlg alg = wsecxConfig.getDefaultAlg();
		if(asymm.equalsIgnoreCase("RSA-1024"))
		{
			
			alg.setAsymm(BJCAWirelessInterface.RSA_1024);
//			alg.setHash(sha);
			alg.setSymm(BJCAWirelessInterface.TDES);
			alg.setSymmMode(BJCAWirelessInterface.ECB);
//			alg.setPadding(pading);
			
			
		}
		else if(asymm.equalsIgnoreCase("RSA-2048"))
		{
			alg.setAsymm(BJCAWirelessInterface.RSA_2048);
//			alg.setHash(sha);
			alg.setSymm(BJCAWirelessInterface.TDES);
			alg.setSymmMode(BJCAWirelessInterface.ECB);
			
		}
		else if(asymm.equalsIgnoreCase("SM2-256"))
		{
			alg.setAsymm(BJCAWirelessInterface.SM2_256);
//			alg.setHash(sha);
			alg.setSymm(BJCAWirelessInterface.SM4);
			alg.setSymmMode(BJCAWirelessInterface.CBC);
//			alg.setPadding(pading);
		}else
		{
			return;
		}
		wsecxConfig.setDefaultAlg(alg);
		
//		isRefresh = true;
	}

//	/**
//	 * @return the isRefresh
//	 */
//	public boolean isRefresh() {
//		return isRefresh;
//	}
//
//	/**
//	  * @param isRefresh 要设置的 isRefresh
//	  */
//	
//	public void setRefresh(boolean isRefresh) {
//		this.isRefresh = isRefresh;
//	}
	
	
	

}
