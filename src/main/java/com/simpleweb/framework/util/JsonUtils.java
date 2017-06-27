package com.simpleweb.framework.util;

import com.alibaba.fastjson.JSON;

public class JsonUtils {

	/**
	 * 将对象转换为json
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {

		String jsonString = JSON.toJSONString(object);

		return jsonString;
	}

	/**
	 * 将 json 转化为对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		T object = JSON.parseObject(json, clazz);
		return object;
	}

}
