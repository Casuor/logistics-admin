package com.aki.springbootlogisticsadmin.security;

import com.aki.springbootlogisticsadmin.entity.SysUser;
import com.aki.springbootlogisticsadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    SysUserService sysUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码不存在！！！");
        }
        return new UserDetailImpl(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));
    }

    List<GrantedAuthority> getUserAuthority(Long id) {

        // 角色(ROLE_admin)、菜单操作权限 sys:user:list
        String authority = sysUserService.getUserAuthorityInfo(id);  // ROLE_admin,ROLE_normal,sys:user:list,....

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }

}
