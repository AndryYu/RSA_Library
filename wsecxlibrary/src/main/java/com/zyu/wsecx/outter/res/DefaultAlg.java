package com.zyu.wsecx.outter.res;

import cn.org.bjca.wsecx.interfaces.BJCAWirelessInterface;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  DefaultEntity.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.res
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述: 中间件默认算法 
 * @版本: V1.5
 * @创建人： lizhiming
 * @创建时间：2014-8-9 下午4:42:31
 *
 *
 *
 *  <default>
        <!-- Hash_SHA_1 = 1  Hash_SHA_256 = 2 Hash_SM3 = 3 -->
       <Hash>1</Hash> 
       <!-- #ALG_RSA_1024 = 1  ALG_SM2_256 = 2  RSA_2048 3-->
        <DisSym>1</DisSym>
        <!-- # 3DES 1 SM4 = 2      DESede SM4-->
        <Sym>1</Sym>
        <!--  ECB 0 CBC = 1-->
        <mode>1</mode>
         <!--  PKCS5Padding 0   NoPadding 1-->
        <padding>0</padding>
     </default>
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 **************************************************************************
 */
public class DefaultAlg {

	/**
	 * 算法标识
	 */
	private int hash = BJCAWirelessInterface.SHA_1;
	/**
	 * 对称算法
	 */
	private int symm = BJCAWirelessInterface.TDES;
	/**
	 * 非对称算法
	 */
	private int nonSymm = BJCAWirelessInterface.RSA_1024;

	//padding
	private int mode = BJCAWirelessInterface.CBC;
	
	private int padding = BJCAWirelessInterface.PKCS5Padding;
	
	

	/**
	 * @return the hash
	 */
	public int getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(int hash) {
		this.hash = hash;
	}

	/**
	 * @return the sym
	 */
	public int getSymm() {
		return symm;
	}

	/**
	 * @param sym the sym to set
	 */
	public void setSymm(int sym) {
		symm = sym;
	}

	/**
	 * @return the disSym
	 */
	public int getAsymm() {
		return nonSymm;
	}

	/**
	 * @param disSym the disSym to set
	 */
	public void setAsymm(int disSym) {
		nonSymm = disSym;
	}

	/**
	 * @return the mode
	 */
	public int getSymmMode() {
		return mode;
	}

	/**
	 * @param padding the padding to set
	 */
	public void setSymmMode(int mode) {
		this.mode = mode;
	}

	public String toString() {
		return "Hash:" + hash + ",Sym:" + symm + "DisSym:" + nonSymm + "mode:" + mode+ "padding:" + padding;
	}

	public int getPadding()
	{
		return padding;
	}

	public void setPadding(int padding)
	{
		this.padding = padding;
	}
	
	
	
}
