/**   
 * @Title: WSecXDevicePinInterface.java 
 * @Package cn.org.bjca.wsecx 
 * @Description: TODO
 * @author liyade
 * @date 2014-1-27 下午1:20:07 
 * @version V1.0   
 */
package com.zyu.wsecx.outter;

import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WSecXDevicePinInterface.java
 * @包   路   径：  cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  用户pin码管理接口
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-12 下午2:55:38
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
public interface WSecXDevicePinInterface {
	/**
	 * 修改用户pin码
	 * 
	 * @Title: WSecXDevicePinInterface
	 * @Description: TODO
	 * @param @param oldUserPin
	 * @param @param newUserPin
	 * @param @return boolean
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */
	public int modifyUserPin(String oldUserPin, String newUserPin) throws WSecurityEngineException;

	/**
	 * 修改管理员密码
	 * 
	 * @Title: WSecXDevicePinInterface
	 * @Description: TODO
	 * @param @param oldAdminPin 
	 * @param @param newAdminPin
	 * @param @return boolean
	 * @return boolean
	 * @throws
	 */
	public int modifyAdminPin(String oldAdminPin, String newAdminPin);

	/**
	 * 用户pin码解锁
	 * 
	 * @Title: WSecXDevicePinInterface
	 * @Description: TODO
	 * @param @param adminPin 管理员pin码
	 * @param @param userPin
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return WSecXDevicePinInterface
	 * @throws
	 */

	public int unLockDevice(String adminPin, String userPin) throws WSecurityEngineException;

	/**
	 * 用户KEY容器初始化，密钥对和证书都将删除
	 * 
	 * @Title: WSecXDevicePinInterface
	 * @Description: TODO
	 * @param @param superPin
	 * @param @param adminPin
	 * @param @param userPin
	 * @param @param retryNum 锁KEY次数
	 * @param @return boolean
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */

	public int initDevice(String superPin, String adminPin, String userPin, int retryNum) throws WSecurityEngineException;

	/**
	 * 删除key容器别名下的证书和密钥对
	 * 
	 * @Title: WSecXDevicePinInterface
	 * @Description: TODO
	 * @param @param alias
	 * @param @return
	 * @param @throws WSecurityEngineException
	 * @return boolean
	 * @throws
	 */

	public int deleteContainer(String alias) throws WSecurityEngineException;
}
