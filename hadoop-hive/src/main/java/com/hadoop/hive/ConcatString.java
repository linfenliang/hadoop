/*
 * 版权信息：北京汉方三陆零科技有限公司</br>
 * Copyright ©2014-2015. All rights reserved.  京ICP备15000045号
 */
package com.hadoop.hive;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/****************************************************************************
 * com.hadoop.hive ConcatString.java Created on 2015年11月20日
 * 
 * @Author: linfenliang
 * @Description: Hive自定义函数，详见<a href='http://blog.163.com/linfenliang@126/blog/static/1278571952015102083216973/'>Hive自定义函数</>
 * @Version: 1.0
 ***************************************************************************/
public class ConcatString extends UDF{
	public Text evaluate(Text t1,Text t2) {
		return new Text(t1.toString()+"*****"+t2.toString());
	}
}
