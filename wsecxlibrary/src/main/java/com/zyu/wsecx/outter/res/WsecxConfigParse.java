package com.zyu.wsecx.outter.res;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * *************************************************************************
 * <pre></pre>
 * @文件名称:  WsecxConfigParse.java
 * @包   路   径：  cn.org.bjca.wsecx.outter.res
 * @版权所有：北京数字认证股份有限公司 (C) 2014
 *
 * @类描述:  Pull 解析配置文件xml
 * @版本: V1.5
 * @创建人： lizhiming
 * @创建时间：2014-8-9 下午4:40:42
 *
 *
 *
 * @修改记录：
   -----------------------------------------------------------------------------------------------
             时间                      |       修改人            |         修改的方法                       |         修改描述                                                                
   -----------------------------------------------------------------------------------------------
                 |         liyade        |         重构                  |                                       
   ----------------------------------------------------------------------------------------------- 	
 
 **************************************************************************
 */
public class WsecxConfigParse {

	public static WsecxConfig parse(InputStream inStream) throws IOException, XmlPullParserException {

		DefaultAlg defaultAlg = null;
		DeviceDescribe deviceDescribe = null;
		WsecxConfig config = null;

		XmlPullParserFactory pullFactory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = pullFactory.newPullParser();
		parser.setInput(inStream, "UTF-8");
		// 产生第一个事件
		int eventType = parser.getEventType();
		// 只要不是文档结束事件，就一直循环
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 触发开始文档事件
			case XmlPullParser.START_DOCUMENT:

				config = new WsecxConfig();
				break;
			// 触发开始元素事件
			case XmlPullParser.START_TAG:
				String name = parser.getName();

				if ("default".equals(name)) {
					defaultAlg = new DefaultAlg();
				}
				if ("Hash".equals(name)) {
					defaultAlg.setHash(Integer.parseInt(parser.nextText()));
				}
				if ("Sym".equals(name)) {
					defaultAlg.setSymm(Integer.parseInt(parser.nextText()));
				}
				if ("DisSym".equals(name)) {
					defaultAlg.setAsymm(Integer.parseInt(parser.nextText()));
				}
				if ("padding".equals(name)) {
					defaultAlg.setSymmMode(Integer.parseInt(parser.nextText()));
				}
				if ("scanSel".equals(name)) {
					// deviceE.setId(parser.nextText());
					// String device = parser.getAttributeValue(0);
					config.setDefaultDeviceID(parser.nextText());
					// parser.nextTag();

				}
				if ("ids".equals(name)) {
					// deviceE.setId(parser.nextText());
					config.addID(parser.nextText());
				}
				// device
				if ("device".equals(name)) {
					deviceDescribe = new DeviceDescribe();
				}

				if ("id".equals(name)) {
					deviceDescribe.setId(parser.nextText());
				}
				if ("describe".equals(name)) {
					deviceDescribe.setDescribe(parser.nextText());
				}
				if ("provider".equals(name)) {
					deviceDescribe.setProvider(parser.nextText());
				}
				if ("type".equals(name)) {
					deviceDescribe.setType(Integer.parseInt(parser.nextText()));
				}

				break;
			case XmlPullParser.END_TAG:
				if ("default".equals(parser.getName())) {

					config.setDefaultAlg(defaultAlg);
				}
				if ("device".equals(parser.getName())) {
					String id = deviceDescribe.getId();
					if (config.getID(id) != null) {
						config.addDeviceDescribe(deviceDescribe);
					}
				}

				break;
			default:
				break;
			}
			eventType = parser.next();
		}

		return config;
	}

}