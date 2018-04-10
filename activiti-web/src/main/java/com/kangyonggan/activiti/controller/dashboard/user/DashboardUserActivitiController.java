package com.kangyonggan.activiti.controller.dashboard.user;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.constants.Status;
import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.model.DefinitionApply;
import com.kangyonggan.activiti.service.DefinitionApplyService;
import com.kangyonggan.activiti.util.FileUpload;
import com.kangyonggan.activiti.util.PropertiesUtil;
import com.kangyonggan.activiti.util.SerialNoUtil;
import com.kangyonggan.activiti.util.ShiroUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @date 4/10/18
 */
@Controller
@RequestMapping("dashboard/user/activiti")
public class DashboardUserActivitiController extends BaseController {

    @Autowired
    private DefinitionApplyService definitionApplyService;

    /**
     * 流程中心
     *
     * @param pageNum
     * @param serialNo
     * @param remark
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("USER_ACTIVITI")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "serialNo", required = false, defaultValue = "") String serialNo,
                       @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
                       @RequestParam(value = "status", required = false, defaultValue = "") String status,
                       Model model) {
        List<DefinitionApply> definitionApplies = definitionApplyService.searchDefinitionApplies(pageNum, AppConstants.PAGE_SIZE, ShiroUtils.getShiroUser().getUsername(), serialNo, remark, status);
        PageInfo<DefinitionApply> page = new PageInfo<>(definitionApplies);

        model.addAttribute("statuses", Status.values());
        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 申请
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @RequiresPermissions("USER_ACTIVITI")
    public String create(Model model) {
        model.addAttribute("definitionApply", new DefinitionApply());
        return getPathFormModal();
    }

    /**
     * 保存申请
     *
     * @param definitionApply
     * @param result
     * @param zipFile
     * @return
     * @throws FileUploadException
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("USER_ACTIVITI")
    public Map<String, Object> save(@ModelAttribute("definitionApply") @Valid DefinitionApply definitionApply, BindingResult result,
                                    @RequestParam("zipFile") MultipartFile zipFile) throws FileUploadException {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            String zipPath = FileUpload.upload(zipFile, "DA");
            definitionApply.setZipPath(zipPath);
            definitionApply.setSerialNo(SerialNoUtil.getDefinitionApplySerialNo());
            definitionApply.setUsername(ShiroUtils.getShiroUser().getUsername());
            definitionApplyService.saveDefinitionApply(definitionApply, PropertiesUtil.getProperties(AppConstants.FILE_PATH_ROOT));
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }
}
