package com.auto.demo.utils;

import java.util.UUID;

/**
 * 生成UUID工具类
 * @author shanqiang
 * @date 2019/7/18 17:26
 * @version v1.0
 */
public class UUIDUtil {

	/**
	* @Title: getUUID
	* @Description: TODO(生成UUID)
	* @param @return    参数
	* @return String    返回类型
	* @throws
	*/
	public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
   }


}
