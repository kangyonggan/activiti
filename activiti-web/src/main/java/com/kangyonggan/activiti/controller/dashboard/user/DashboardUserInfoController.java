package com.kangyonggan.activiti.controller.dashboard.user;

import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.dto.ShiroUser;
import com.kangyonggan.activiti.model.User;
import com.kangyonggan.activiti.service.UserService;
import com.kangyonggan.activiti.util.ShiroUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author kangyonggan
 * @date 5/8/17
 */
@Controller
@RequestMapping("dashboard/user/info")
public class DashboardUserInfoController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 基本信息
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("USER_INFO")
    public String info(Model model) {
        ShiroUser shiroUser = ShiroUtils.getShiroUser();
        User user = userService.findUserByUsername(shiroUser.getUsername());

        model.addAttribute("user", user);
        return getPathIndex();
    }

    /**
     * 基本信息
     *
     * @param user
     * @param result
     * @return
     * @throws FileUploadException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("USER_INFO")
    public Map<String, Object> info(@ModelAttribute(value = "user") @Valid User user, BindingResult result) {
        Map<String, Object> resultMap = getResultMap();
        ShiroUser shiroUser = ShiroUtils.getShiroUser();

        if (!result.hasErrors()) {
            user.setUsername(shiroUser.getUsername());

            userService.updateUserByUsername(user);
            if (StringUtils.isNotEmpty(user.getPassword())) {
                userService.updateUserPassword(user);
            }

            user = userService.findUserByUsername(shiroUser.getUsername());

            resultMap.put("user", user);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

}
