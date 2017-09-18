package com.zyu.wsecx.outter;

import java.util.Vector;

import cn.org.bjca.wsecx.interfaces.WSecurityEngineException;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WSecXDataInterface.java
 * @包   路   径：  cn.org.bjca.wsecx.outter
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:   数据存储接口
 * 				可以存储电子签章、证书密钥以外的数据
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-12 下午2:55:05
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
public interface WSecXDataInterface {

	/**
	 * 写数据
	 * 
	 * @param id
	 *            （文件标识）
	 * @param data
	 *            文件数据
	 * @return 成功返回当前写入长度（单位：字节）， 失败返回 -1
	 */
	public int writeData(String id, byte[] data);

	/**
	 * 读文件
	 * 
	 * @param fileName
	 *           （文件标识）
	 * @return 返回文件数据（无当前文件返回NULL）
	 * @throws WSecurityEngineException 
	 */
	public byte[] readData(String id) throws WSecurityEngineException;

	/**
	 * 删除文件
	 * 
	 * @param id
	 *            （文件标识）
	 * @return 成功返回1，无当前文件返回0，失败返回-1
	 */
	public int deleteData(String id);

	/**
	 * 获取文件别名列表
	 * 
	 * @return 文件名列表Vector<String>
	 */
	public Vector<String> findDataList() throws WSecurityEngineException;;

}
