package com.zyu.wsecx.outter.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  FileUtil.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.util
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  
 * @版本: V1.5
 * @创建人： liyade
 * @创建时间：2014-10-20 下午9:12:58
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
public class FileUtil {

	/**

	 *
	 * @param path
	 */
	public static void DeleteFile(String path) {
		File fileName = new File(path);
		if (fileName.exists()) {
			fileName.delete();
		}
	}

	/**

	 *
	 * @param path
	 * @param filename
	 * @param content
	 */
	public static void savefile(String path, String filename, String content) {

		PrintWriter pw = null;
		try {
			File temp = new File(path);

			if (!temp.isDirectory()) {
				temp.mkdir();

			}

			pw = new PrintWriter(new FileOutputStream(path + filename));
			pw.println(content);
			pw.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}

	} // function

	/**

	 * @param filePath
	 * @param content
	 */
	public static boolean savefile(String filePath, String content, boolean isAppend) {

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(filePath, isAppend));
			pw.println(content);
			pw.flush();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		return true;

	} // function

	/**
	 * @param filePath String
	 * @param bytes byte[]
	 * @param isAppend boolean 
	 * @return boolean
	 */
	public static boolean savefile(String filePath, byte[] bytes, boolean isAppend) {

		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filePath, isAppend));
			out.write(bytes);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return true;

	} // function

	/**
	 * @param filepath String 
	 * @return byte[]
	 */
	public static byte[] getBytesByFile(String filepath) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		BufferedInputStream buffer = null;
		try {
			FileInputStream in = new FileInputStream(filepath);
			buffer = new BufferedInputStream(in);

			byte b[] = new byte[1024];
			int len = buffer.read(b);
			while (len != -1) {
				bytes.write(b, 0, len);
				len = buffer.read(b);
			}
			return bytes.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				buffer.close();
				bytes.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		}

	} // function

	/**
	 * @param filepath 
	 * @return String
	 */
	public static String getStrByFile(String filepath) {
		StringBuffer str = new StringBuffer(4096);
		BufferedReader buffer = null;
		FileReader in = null;
		try {
			in = new FileReader(filepath);
			buffer = new BufferedReader(in);
			String strBuf = buffer.readLine();

			while (strBuf != null) {
				str.append(strBuf);
				strBuf = buffer.readLine();
			}

			return str.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				buffer.close();
				in.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		}

	} // function

	/**
	 * @param filePath String
	 * @return boolean
	 */
	public static boolean isExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

} // class
