package com.zyu.wsecx.outter.stamp;
/***************************************************************************
 * <pre></pre>
 * @文件名称:  StampUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2016
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2016-6-14 下午4:48:51
 *
 *
 *
typedef struct bjca_eseal_t { 
char	type[2];//"ES"文件标识 
DWORD	dwID;//印章在系统中的标识;由服务器系统自动生成，采用时间 

char	picFormat[4];//图片格式：4位文件扩展名，例 GIF 
DWORD	dwPicSize;//印章图片文件大小 
DWORD	dwTimeMake;//印章制作日间 

DWORD	dwValidTimeStart;//印章有效期起始日期 
DWORD	dwValidTimeEnd;//印章有效期终止日期 

BYTE	signType;//签章授权信息，是否允许在线签章，1=在线,2=离线 3=永久在线 4=永久离线	离线签章有有效期，不在有效期内必须在线签章 
int	 iSignCountLimit;//签章次数限制 
DWORD	dwSOLTimeStart;//离线签章起始时间，在线签章时=0 
DWORD	dwSOLTimeEnd;//离线签章终止时间，在线签章时=0 
BYTE	verifyType;//验证授权信息，在线/离线验证，1=在线，2=离线 3=永久在线 4=永久离线 
DWORD	dwVOLTimeStart;//离线验证起始时间，在线签章时=0 
DWORD	dwVOLTimeEnd;//离线验证终止时间，在线签章时=0 


char	certOIDExt[30];//数字证书唯一身份标识 
char	sealName[40];//印章名称，由管理员填写 
char	verifyURL[100];//印章验证URL 

char	chReserved[100];//预留空间 

} bjca_eseal_t; 

typedef struct bjca_eseal_signinfo_t { 
DWORD	dwSignatureLength;//签名值长度 
DWORD	dwCertLength;//印章制作管理员证书 
} bjca_eseal_signinfo_t; 
//印章文件的格式：bjca_eseal_t + 图片 + bjca_eseal_signinfo_t + 管理员证书 + 管理员证书对前两项base64/hash值的签名 
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |                 |                           |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 ***************************************************************************/


/**
 */
public class StampUtil {

}
