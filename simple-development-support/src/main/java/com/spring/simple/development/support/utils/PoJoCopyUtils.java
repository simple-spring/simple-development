package com.spring.simple.development.support.utils;

import org.dozer.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author liko wang
 */
public class PoJoCopyUtils {
	
	/**
	 * 单个对象属性复制
	 * 
	 * @param mapper
	 * @param orig
	 * @param clazz
	 * @return
	 */
    public static <T> T objectCopy(Mapper mapper, Object orig, Class<T> clazz) {
    	 if (orig == null) {
             return null;
         }
         T dest = mapper.map(orig, clazz);
         return dest;
    }
    
    /**
     * list集合对象属性复制
     * 
     * @param mapper
     * @param sourceList 数据源
     * @param destinationClass 目标对象class
     * @return
     */
	public static <E, T> List<T> listCopy(Mapper mapper,Collection sourceList, Class<T> destinationClass) {
    	if (sourceList == null) {
			return null;
		}
		if (CollectionUtils.isEmpty(sourceList)) {
			return new ArrayList<T>();
		}

		List<T> destinationList = new ArrayList<T>();
		for (Iterator<E> i = sourceList.iterator(); i.hasNext();) {
			Object sourceObject = i.next();
			T destinationObject = mapper.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
    }

}
