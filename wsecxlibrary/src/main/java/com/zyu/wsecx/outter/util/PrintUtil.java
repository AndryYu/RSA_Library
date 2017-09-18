package com.zyu.wsecx.outter.util;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  PrintUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  二进制打印处理
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-8-14 下午12:42:40
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
public class PrintUtil {
	
	public  static String toHexString(byte[] data) {
		byte temp;
		int n;
		String str = "";
		for (int i = 1; i <= data.length; i++) {
			temp = data[i-1];
			n = (int) ((temp & 0xf0) >> 4);
			str += IntToHex(n);
			n = (int) ((temp & 0x0f));
			str += IntToHex(n);
			str += " ";
			if (i % 16 == 0) {
				str += "\n";
			}
		}

		return str;
	}
	
//	public  static void printWithHex(byte[] data) {
//		System.out.println(toHexString(data));
//	}
//
//	public static void logEWithHex(String tag ,byte[] data)
//	{
//		Log.e(tag, toHexString(data));
//	}
//	
	public static String IntToHex(int n) {
		if (n > 15 || n < 0) {
			return "";
		} else if ((n >= 0) && (n <= 9)) {
			return "" + n;
		} else {
			switch (n) {
			case 10: {
				return "A";
			}
			case 11: {
				return "B";
			}
			case 12: {
				return "C";
			}
			case 13: {
				return "D";
			}
			case 14: {
				return "E";
			}
			case 15: {
				return "F";
			}
			default:
				return "";
			}
		}
	}
}
