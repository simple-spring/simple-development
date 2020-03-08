#### 标注拦截器
- 1.在项目结构interceptor包下的类标注@interceptor
- 2.并且继承HandlerInterceptor 
- 3.则项目启动自动注册拦截器
#### 代码样例
```java
@SimpleInterceptor
public class ApiSupportInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //获取请求的url
        String url = httpServletRequest.getRequestURI();
        // 不需要登录的地址
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            NoLogin noLogin = method.getMethodAnnotation(NoLogin.class);
            if (noLogin != null) {
                return true;
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
    }
}
```