package com.example.shirodemo.utils;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /*在shiroconfig配置类中新增凭证比较器*/
    @Bean
    public SimpleCredentialsMatcher CredentialsMatcher() {
        HashedCredentialsMatcher hct = new HashedCredentialsMatcher();
        // 加密算法的名称
        hct.setHashAlgorithmName("MD5");
        // 配置加密的次数
        hct.setHashIterations(1000);
        // 是否存储为16进制，false则存储为base64
        hct.setStoredCredentialsHexEncoded(false);
        return hct;
    }

    /*生成自定义ream对象*/
    @Bean
    public UserRealm getUserRealm(){
        UserRealm userRealm=new UserRealm();//创建自定义的realm对象

        userRealm.setCredentialsMatcher(CredentialsMatcher()); //设置凭证比较器
        return userRealm;
    }

    /**
     * 创建DefaultWebSecurityManager
     * 里面主要定义了登录，创建subject，登出等操作
     */
    @Bean
    public SecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(getUserRealm());//关联realm
        return securityManager;
    }

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean webfileter(){
        //设置安全管理器
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());

        //添加Shiro内置过滤器
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *    常用的过滤器：
         *       anon: 无需认证（登录）可以访问
         *       authc: 必须认证才可以访问
         *       user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterChainMap=new LinkedHashMap<>();
        filterChainMap.put("/h-ui/**","anon"); //静态资源
        filterChainMap.put("/h-ui.admin/**","anon"); //静态资源
        filterChainMap.put("/image/**","anon"); //静态资源
        filterChainMap.put("/lib/**","anon"); //静态资源
        filterChainMap.put("/release/**","anon"); //静态资源
        filterChainMap.put("/logout","logout"); //退出过滤器
        filterChainMap.put("/login","anon"); //登录，可匿名访问
        filterChainMap.put("/adminLogin","anon"); //验证登录，可匿名访问
        filterChainMap.put("/goindex","roles[admin]"); //角色验证
        filterChainMap.put("/**","authc"); //其他所有请求，需要权限
        shiroFilterFactoryBean.setLoginUrl("/gologin"); //设置登录页
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth"); //设置未授权提示页面
        //shiroFilterFactoryBean.setSuccessUrl("goindex"); //设置验证登录成功跳转页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }
}
