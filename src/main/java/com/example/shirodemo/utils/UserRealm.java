package com.example.shirodemo.utils;
import com.example.shirodemo.entity.User;
import com.example.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;


/**
 * 自定义Realm
 * （1）AuthenticatingRealm：shiro中的用于进行认证的领域，实现doGetAuthentcationInfo方法实现用户登录时的认证逻辑；
 * （2）AuthorizingRealm：shiro中用于授权的领域，实现doGetAuthrozitionInfo方法实现用户的授权逻辑，
 * AuthorizingRealm继承了AuthenticatingRealm，所以在实际使用中主要用到的就是这个AuthorizingRealm类；
 * （3）AuthenticatingRealm、AuthorizingRealm这两个类都是shiro中提供了一些线程的realm接口
 * （4）在与spring整合项目中，shiro的SecurityManager会自动调用这两个方法，从而实现认证和授权，
 * 可以结合shiro的CacheManager将认证和授权信息保存在缓存中，这样可以提高系统的处理效率。
 */
public class UserRealm extends AuthorizingRealm {
    @Resource
    private UserService usersSerivce;

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名  token中的用户信息是登录时候传进来的
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        String adminphone=token.getUsername();
//      String password=new String(token.getPassword());
        //String adminphone=(String)authenticationToken.getPrincipal(); //也可获得authenticationToken中的username
        //String password=(String)authenticationToken.getCredentials(); //也可获得authenticationToken中的password
        User user=usersSerivce.findByPhone(adminphone);//查询是否存在该用户
        if(null==user){
            //用户名不存在
            return null;//shiro底层会抛出UnKnowAccountException异常
        }

        //2.判断密码
        //第二个字段是user.getPassword()，注意这里是指从数据库中获取的password。第三个字段是用户的盐。第四个字段是realm，即当前realm的名称。
        //这块对比逻辑是先对比username，但是username肯定是相等的，所以真正对比的是password。
        //从这里传入的password（这里是从数据库获取的）和token（filter中登录时生成的）中的password做对比，如果相同就允许登录，
        // 不相同就抛出IncorrectCredentialsException异常。
        //如果认证不通过，就不会执行下面的授权方法了
        return new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getUsersalt()),getName());

        // SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user,user.getPassword(),user.getUserPhone());
    }

    /**
     * 执行授权逻辑,doGetAuthorizationInfo方法可能会执行多次，权限判断次数多少，就会执行多少次
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        /*//添加资源的授权字符串
        info.addStringPermission("user:add");*/

        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user=null;
        Object object=subject.getPrincipal();
        if(object instanceof User){
            user=(User)object;
        }
        Set<String> set=new HashSet<>();
        if(user!=null){
            //到数据库查询当前登录用户的授权字符串
            //String permissionStr=
            //添加查询到的资源的授权字符串
            //info.addStringPermission(permissionStr);
            set.add(user.getUsertype()); // 查询角色权限
            info.setRoles(set);
        }
        return info;
    }
}

