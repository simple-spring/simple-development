package com.spring.simple.development.demo.support;

import com.github.pagehelper.PageInfo;
import com.spring.simple.development.core.component.mvc.page.ReqPageDTO;
import com.spring.simple.development.core.component.mvc.page.ResPageDTO;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.PoJoCopyUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

/**
 * @author liko.wang
 * @Date 2020/1/9/009 12:32
 * @Description //TODO
 **/
@Component
public class BaseSupport {

    private static final Logger logger = LogManager.getLogger(BaseSupport.class);

    @Autowired
    private Mapper mapper;

    protected Mapper getMapper() {
        return this.mapper;
    }

    public <T> T objectCopy(Object orig, Class<T> clazz) {
        return PoJoCopyUtils.objectCopy(mapper, orig, clazz);
    }

    public <E, T> List<T> listCopy(Collection origList, Class<T> destinationClass) {
        return PoJoCopyUtils.listCopy(mapper, origList, destinationClass);
    }

    public <T> ResPageDTO<T> pageCopy(PageInfo pageInfo, Class<T> destinationClass) {
        if (pageInfo == null) {
            return null;
        }
        ResPageDTO<T> resPageDTO = new ResPageDTO<T>();
        resPageDTO.setPageNum(pageInfo.getPageNum());
        resPageDTO.setPageSize(pageInfo.getPageSize());
        resPageDTO.setTotalCount(pageInfo.getTotal());
        resPageDTO.setList(listCopy(pageInfo.getList(), destinationClass));
        return resPageDTO;
    }

    /**
     * 设置已存在对象全量信息
     *
     * @param o1 已存在的对象
     * @param o2 set到对象的实体
     * @throws Exception
     */
    public void copyObject(Object o1, Object o2) throws Exception {
        try {
            Class<?> aClass1 = o1.getClass();
            Method[] declaredMethods1 = aClass1.getDeclaredMethods();

            Class<?> aClass2 = o2.getClass();
            Method[] declaredMethods2 = aClass2.getDeclaredMethods();

            Map<String, Method> methodHashMap = new HashMap<>();
            for (Method method : declaredMethods2) {
                if (method.getName().contains("get")) {
                    methodHashMap.put(method.getName(), method);
                }
            }
            for (Method method : declaredMethods1) {
                if (method.getName().equals("setCreateTime") || method.getName().equals("setCreator") || method.getName().equals("setCreatorId") || method.getName().equals("setId")) {
                    continue;
                }
                if (method.getName().contains("set")) {
                    String getName = "get" + method.getName().substring(3, method.getName().length());
                    if (methodHashMap.containsKey(getName)) {
                        Method getMethod = methodHashMap.get(getName);
                        Object invoke = getMethod.invoke(o2);
                        method.invoke(o1, invoke);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("基础信息设置异常");
        }
    }

    /**
     * 设置已存在对象全量信息
     *
     * @param o1 目标对象
     * @param o2 源对象
     * @throws Exception
     */
    public void copyObjectNoNull(Object o1, Object o2) throws Exception {
        try {
            Class<?> aClass1 = o1.getClass();
            Method[] declaredMethods1 = aClass1.getDeclaredMethods();

            Class<?> aClass2 = o2.getClass();
            Method[] declaredMethods2 = aClass2.getDeclaredMethods();

            Map<String, Method> methodHashMap = new HashMap<>();
            for (Method method : declaredMethods2) {
                if (method.getName().contains("get")) {
                    methodHashMap.put(method.getName(), method);
                }
            }
            for (Method method : declaredMethods1) {
                if (method.getName().equals("setCreateTime") || method.getName().equals("setCreator") || method.getName().equals("setCreatorId")) {
                    continue;
                }
                if (method.getName().contains("set")) {
                    String getName = "get" + method.getName().substring(3, method.getName().length());
                    if (methodHashMap.containsKey(getName)) {
                        Method getMethod = methodHashMap.get(getName);
                        Object invoke = getMethod.invoke(o2);
                        if (invoke == null) {
                            continue;
                        }
                        method.invoke(o1, invoke);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("基础信息设置异常");
        }
    }

    /**
     * 设置已存在对象全量信息,如果属性已存在则不覆盖
     *
     * @param o1 set目标对象
     * @param o2 get源对象
     * @throws Exception
     */
    public void copyObjectNoExistNull(Object o1, Object o2) throws Exception {
        try {
            Class<?> aClass1 = o1.getClass();
            Method[] declaredMethods1 = aClass1.getDeclaredMethods();

            Class<?> aClass2 = o2.getClass();
            Method[] declaredMethods2 = aClass2.getDeclaredMethods();

            Map<String, Method> methodHashMap1 = new HashMap<>();
            for (Method method : declaredMethods1) {
                if (method.getName().contains("get")) {
                    methodHashMap1.put(method.getName(), method);
                }
            }
            Map<String, Method> methodHashMap2 = new HashMap<>();
            for (Method method : declaredMethods2) {
                if (method.getName().contains("get")) {
                    methodHashMap2.put(method.getName(), method);
                }
            }
            for (Method method : declaredMethods1) {
                if (method.getName().contains("set")) {
                    String getName = "get" + method.getName().substring(3, method.getName().length());
                    if (methodHashMap1.containsKey(getName)) {
                        Method getMethod = methodHashMap1.get(getName);
                        Object invoke = getMethod.invoke(o1);
                        if (invoke != null) {
                            continue;
                        }
                    }
                    Method getMethod2 = methodHashMap2.get(getName);
                    if (getMethod2 != null) {
                        Object invoke2 = getMethod2.invoke(o2);
                        if (invoke2 == null) {
                            continue;
                        }
                        method.invoke(o1, invoke2);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw new Exception("基础信息设置异常");
        }
    }


    /**
     * 基本校验
     *
     * @param reqBody
     */
    public void checkParam(ReqBody reqBody, Boolean isPage, String... strings) {
        if (reqBody == null) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "服务器未收到信息");
        }

        if (strings != null && strings.length > 0) {
            if (reqBody.getParamsMap() == null) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数paramsMap为空");
            }
            for (String str : strings) {
                if (reqBody.getParamsMap().get(str) == null) {
                    throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数" + str + "为空");
                }
                if (StringUtils.isEmpty(reqBody.getParamsMap().get(str).toString())) {
                    throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数" + str + "为空");
                }
            }
        }
        if (isPage == true) {
            if (reqBody.getPage() == null) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "分页参数为空");
            }
            ReqPageDTO page = reqBody.getPage();
            if (page.getStartPage() == null) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "分页参数startPage为空");
            }
            if (page.getPageSize() == null) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "分页参数pageSize为空");
            }
        }
    }
}