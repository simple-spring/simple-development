package com.chexin.simple.development.demo.interceptor;

import com.chexin.simple.development.core.Idempotent.IdempotentHandler;
import com.chexin.simple.development.core.handler.ThreadLocalSignInfoHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 描述:
 * 权限控制
 *
 * @author liko
 * @create 2018-10-09 上午9:13
 */
public class ApiSupportInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(ApiSupportInterceptor.class);
    private static final Logger apiUserLogger = LogManager.getLogger("userLogger");

    /**
     * 该方法会在控制器方法前执行，其返回值表示是否中断后续操作。当其返回值为true时，表示继续向下执行
     * 当其返回值为false时，会中断后续的所有操作（包括调用下一个拦截器和控制器类中的方法执行等）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
         return true;

//        IdempotentHandler.fastSetIdempotentModel(httpServletRequest.getRemoteHost(), httpServletRequest.getRequestURI());
//        // 获取签名
//        String sign = httpServletRequest.getHeader("sign");
//        if (StringUtils.isEmpty(sign)) {
//            throw new GlobalException(RES_PARAM_IS_EMPTY, "sign 未找到");
//        }
//        SignInfo signInfo;
//        try {
//            String decryptSign = AESUtils.decrypt(AESUtils.coupon_key, sign);
//            // signInfo获取
//            signInfo = JSON.parseObject(decryptSign, SignInfo.class);
//            if (signInfo == null) {
//                throw new GlobalException(RES_PARAM_IS_EMPTY, "sign 为空");
//            }
//            if (StringUtils.isEmpty(signInfo.getVersionCode())) {
//                throw new GlobalException(RES_PARAM_IS_EMPTY, "sign 版本号为空");
//            }
//            if (StringUtils.isEmpty(signInfo.getDeviceCode())) {
//                throw new GlobalException(RES_PARAM_IS_EMPTY, "sign 设备号为空");
//            }
//            if (StringUtils.isEmpty(signInfo.getPlatform())) {
//                throw new GlobalException(RES_PARAM_IS_EMPTY, "sign 平台标识为空");
//            }
//            String md5Data = DigestUtils.md5Hex(DigestUtils.md5Hex(signInfo.getVersionCode() + signInfo.getPlatform() + signInfo.getDeviceCode() + SignInfo.ENCRYPT_SIGN_VALUE));
//            if (md5Data.equals(signInfo.getEncryptSign()) == false) {
//                throw new GlobalException(RES_PARAM_IS_EMPTY, "sign校验失败");
//            }
//            apiUserLogger.info("sign:" + DateUtils.getCurrentTime() + ":" + JSON.toJSONString(signInfo));
//        } catch (Exception ex) {
//            logger.error(DateUtils.getCurrentTime(), ex);
//            throw new GlobalException(RES_PARAM_INVALID, "sign格式错误");
//        }
//        ThreadLocalSignInfoHandler.setSignInfo(signInfo);
//        String url = httpServletRequest.getRequestURI();
//        // 不需要登录的地址
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod method = (HandlerMethod) handler;
//            NoLogin noLogin = method.getMethodAnnotation(NoLogin.class);
//            if (noLogin != null) {
//                return true;
//            }
//        }
//        if (StringUtils.isEmpty(signInfo.getToken())) {
//            throw new GlobalException(RES_PARAM_IS_EMPTY, "token为空");
//        }
//        // 读取用户信息
//        String encryptToken = signInfo.getToken();
//        apiUserLogger.info(DateUtils.getCurrentTime() + ":" + encryptToken);
//        String token;
//        try {
//            BASE64Decoder decoder = new BASE64Decoder();
//            String baseDecToken = new String(decoder.decodeBuffer(encryptToken), "UTF-8");
//            token = AESUtils.decrypt(AESUtils.coupon_key, baseDecToken);
//        } catch (Exception ex) {
//            throw new GlobalException(RES_DECRYPT_FAIL, "token解密失败");
//        }
//        if (StringUtils.isEmpty(token)) {
//            throw new GlobalException(RES_PARAM_IS_EMPTY, "token不正确");
//        }
//        // 用户状态检测
//        Jedis jedis = null;
//        try {
//            //校验用户是否失效
//            jedis = JedisPoolUtils.getJedis();
//            String memberInfoJson = jedis.get(token);
//            if (StringUtils.isEmpty(memberInfoJson)) {
//                throw new GlobalException(RES_USER_RELOGIN, "登录状态失效,请重新登录");
//            }
//            return true;
//        } finally {
//            JedisPoolUtils.returnRes(jedis);
//        }
    }

    /**
     * 该方法会在控制器方法调用之后，且解析视图之前执行。可以通过此方法对请求域中的模型和视图做出进一步的修改
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 该方法会在整个请求完成，即视图渲染结束之后执行。可以通过此方法实现一些资源清理、记录日志信息等工作。
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        IdempotentHandler.clearIdempotentModel();
        ThreadLocalSignInfoHandler.remove();
    }
}