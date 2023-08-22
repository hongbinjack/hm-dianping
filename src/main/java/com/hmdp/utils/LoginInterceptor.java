package com.hmdp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*  登录过程的拦截器
   这个拦截器是第一层拦截器，只判断是否有用户，其他拦截在RefreshTokenInterceptor拦截中实现
   在config.MvcConfig中添加该拦截器
    }
 */
public class LoginInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;
    public LoginInterceptor() {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.判断是否需要拦截(ThreadLocal中是否有用户）
        if(UserHolder.getUser() == null){
            //没有用户，需要拦截，设置状态码（401表示拦截状态）
            response.setStatus(401);
            //拦截
            return false;
        }
        //有用户，则放行，返回true
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) throws Exception{
        // 移除用户，避免内存泄露
       UserHolder.removeUser();
    }
}
