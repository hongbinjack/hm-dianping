package com.hmdp.config;

import com.hmdp.utils.LoginInterceptor;
import com.hmdp.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 登录拦截器
         */
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        //上面一行的方法排除了一些路径请求不用拦截，可以直接通过。
                        "/shop/**",  //放行所有以shop打头的请求
                        "/shop-type/**",//同上
                        "/upload/**",
                        "/voucher/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/login"
                ).order(1);//这个拦截器拦截部分请求


        /**
         * 刷新拦截器
         */
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
       //上面这行代码的这个拦截器拦截所有请求

        //order中数字越小，该拦截器越先执行
    }
}
