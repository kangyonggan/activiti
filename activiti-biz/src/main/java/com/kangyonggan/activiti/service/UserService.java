package com.kangyonggan.activiti.service;


import com.kangyonggan.activiti.model.User;

import java.util.List;

/**
 * @author kangyonggan
 * @date 3/22/18
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 按条件搜索用户
     *
     * @param pageNum
     * @param username
     * @param realname
     * @return
     */
    List<User> searchUsers(int pageNum, String username, String realname);

    /**
     * 保存用户
     *
     * @param user
     */
    void saveUserWithDefaultRole(User user);

    /**
     * 根据用户名更新用户信息
     *
     * @param user
     */
    void updateUserByUsername(User user);

    /**
     * 更新用户密码
     *
     * @param user
     */
    void updateUserPassword(User user);

    /**
     * 更新用户角色
     *
     * @param username
     * @param roleCodes
     */
    void updateUserRoles(String username, String roleCodes);

    /**
     * 删除用户
     *
     * @param username
     */
    void deleteUserByUsername(String username);

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return
     */
    boolean existsUsername(String username);
}
