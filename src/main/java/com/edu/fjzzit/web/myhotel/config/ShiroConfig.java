package com.edu.fjzzit.web.myhotel.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    /**
     * Shiro过滤器工厂管理Bean实体
     * @param securityManager 安全管理器
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Autowired SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager); //设置安全管理器

        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setLoginUrl("/user/need_login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/unauth");

        //使用LinkedHashMap链表，实现顺序存储
        LinkedHashMap<String,String> filterMap=new LinkedHashMap<>();

        //开放对swagger2接口的访问
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");

        filterMap.put("/user/login","anon");//设置登录模块忽视验证
        filterMap.put("/admin/login","anon");//设置登录模块忽视验证
        filterMap.put("/admin/login/images/**","anon");//设置登录模块忽视验证
        filterMap.put("/static/**", "anon");
        filterMap.put("/**","authc");//所有除了登录模块的其他模块需要验证

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 获取安全管理器对象
     * @param myShiroRealm
     * @return
     */
    @Bean
    public SecurityManager securityManager(@Autowired MyShiroRealm myShiroRealm,@Autowired SessionManager sessionManager){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(myShiroRealm);

        return  securityManager;
    }

    /**
     * 获取MyShiroRealm实体，并使用算法加密校验器
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm(CredentialsMatcher credentialsMatcher){
        MyShiroRealm myShiroRealm=new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(credentialsMatcher); //设置算法校验器

        return myShiroRealm;
    }

    /**
     * 加密算法校验器
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME); //加密算法
        hashedCredentialsMatcher.setHashIterations(3); //加密次数

        return hashedCredentialsMatcher;
    }

    @Bean
    public SessionManager sessionManager(){
        MySessionManager sessionManager=new MySessionManager();
        sessionManager.setGlobalSessionTimeout(300000);
        return sessionManager;
    }



    /**
     * 管理Shiro相关Bean的生命周期。
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 扫描上下文寻找所有的Advisor（通知器），将符合条件的Advisor切入对应的Bean。
     * 需要依赖LifecycleBeanPostProcessor。
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 加入Shiro注解的使用，不加这个AOP注解不生效。
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
