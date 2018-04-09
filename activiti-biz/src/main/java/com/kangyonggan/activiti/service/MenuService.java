package com.kangyonggan.activiti.service;

import com.kangyonggan.activiti.model.Menu;

import java.util.List;

/**
 * @author kangyonggan
 * @date 3/22/18
 */
public interface MenuService {

    /**
     * 查找用户菜单
     *
     * @param username
     * @return
     */
    List<Menu> findMenusByUsername(String username);

    /**
     * 查找角色菜单
     *
     * @param code
     * @return
     */
    List<Menu> findMenus4Role(String code);

    /**
     * 校验菜单代码是否存在
     *
     * @param code
     * @return
     */
    boolean existsMenuCode(String code);

    /**
     * 查找所有菜单
     *
     * @return
     */
    List<Menu> findAllMenus();

    /**
     * 保存菜单
     *
     * @param menu
     */
    void saveMenu(Menu menu);

    /**
     * 查找菜单根据ID
     *
     * @param id
     * @return
     */
    Menu findMenuById(Long id);

    /**
     * 根据菜单代码查找菜单
     *
     * @param code
     * @return
     */
    Menu findMenuByCode(String code);

    /**
     * 更新菜单
     *
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 删除菜单，物理删除
     *
     * @param menu
     */
    void deleteMenu(Menu menu);
}
