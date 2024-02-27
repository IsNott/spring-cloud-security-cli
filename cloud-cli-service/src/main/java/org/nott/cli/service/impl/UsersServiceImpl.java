package org.nott.cli.service.impl;


import org.nott.cli.bean.model.Users;
import org.nott.cli.common.exception.BusinessException;
import org.nott.cli.service.mapper.UsersMapper;
import org.nott.cli.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nott
 * @since 2024-02-19
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService, UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersMapper.getUserByUserName(username);
        if(Objects.isNull(users)){
            throw new BusinessException(String.format("user %s not found", username));
        }
        return users;
    }
}
