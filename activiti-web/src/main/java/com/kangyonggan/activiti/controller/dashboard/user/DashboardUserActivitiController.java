package com.kangyonggan.activiti.controller.dashboard.user;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.constants.Status;
import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.dto.ReplyDto;
import com.kangyonggan.activiti.model.DefinitionApply;
import com.kangyonggan.activiti.service.ActivitiService;
import com.kangyonggan.activiti.service.DefinitionApplyService;
import com.kangyonggan.activiti.util.DateUtil;
import com.kangyonggan.activiti.util.FileUpload;
import com.kangyonggan.activiti.util.SerialNoUtil;
import com.kangyonggan.activiti.util.ShiroUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 部署列表
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
     * 部署
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
     * 保存部署
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
            definitionApply.setZipName(zipFile.getOriginalFilename());
            definitionApply.setSerialNo(SerialNoUtil.getDefinitionApplySerialNo());
            definitionApply.setUsername(ShiroUtils.getShiroUser().getUsername());
            definitionApplyService.saveDefinitionApply(definitionApply);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 编辑
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "{id:[\\d]+}/edit", method = RequestMethod.GET)
    @RequiresPermissions("USER_ACTIVITI")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("definitionApply", definitionApplyService.findDefinitionById(id));
        return getPathFormModal();
    }

    /**
     * 更新部署
     *
     * @param definitionApply
     * @param result
     * @param zipFile
     * @return
     * @throws FileUploadException
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("USER_ACTIVITI")
    public Map<String, Object> update(@ModelAttribute("definitionApply") @Valid DefinitionApply definitionApply, BindingResult result,
                                      @RequestParam(value = "zipFile", required = false) MultipartFile zipFile) throws FileUploadException {
        Map<String, Object> resultMap = getResultMap();
        if (!result.hasErrors()) {
            if (zipFile != null && !zipFile.isEmpty()) {
                String zipPath = FileUpload.upload(zipFile, "DA");
                definitionApply.setZipPath(zipPath);
                definitionApply.setZipName(zipFile.getOriginalFilename());
            }
            definitionApplyService.updateDefinitionApply(definitionApply);
        } else {
            setResultMapFailure(resultMap);
        }

        return resultMap;
    }

    /**
     * 审批历史
     *
     * @param serialNo
     * @param model
     * @return
     */
    @RequestMapping(value = "{serialNo:[\\w]+}", method = RequestMethod.GET)
    @RequiresPermissions("USER_ACTIVITI")
    public String edit(@PathVariable("serialNo") String serialNo, Model model) {
        List<ReplyDto> replyDtos = definitionApplyService.findAllReply(serialNo, ShiroUtils.getShiroUser().getUsername());

        model.addAttribute("statuses", Status.values());
        model.addAttribute("replyDtos", replyDtos);
        return getPathDetailModal();
    }

    /**
     * 查看进度
     *
     * @param serialNo
     * @param model
     * @return
     */
    @RequestMapping(value = "{serialNo:[\\w]+}/progress", method = RequestMethod.GET)
    @RequiresPermissions("USER_ACTIVITI")
    public String progress(@PathVariable("serialNo") String serialNo, Model model) {
        HistoricProcessInstance instance = activitiService.findHiProcInstByBussnessKey(serialNo);

        model.addAttribute("instId", instance.getId());
        return getPathRoot() + "/progress";
    }

    /**
     * 流程进度图
     *
     * @param instIn
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "instance/{instIn:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("USER_ACTIVITI")
    public void instance(@PathVariable("instIn") String instIn, HttpServletResponse response) throws Exception {
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //  获取历史流程实例
        HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(instIn).singleResult();

        // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
        List<HistoricActivityInstance> historicActivityInstanceList = processEngine.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(instIn).orderByHistoricActivityInstanceId().asc().list();

        // 已执行的节点ID集合
        List<String> executedActivityIdList = new ArrayList<>();
        for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
            executedActivityIdList.add(activityInstance.getActivityId());
        }

        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(historicProcessInstance.getProcessDefinitionId());

        // 已执行的线集合
        // 获取流程走过的线 (getHighLightedFlows是下面的方法)
        List<String> flowIds = getHighLightedFlows(bpmnModel, historicActivityInstanceList);

        // 获取流程图图像字符流
        ProcessDiagramGenerator pec = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        //配置字体
        InputStream imageStream = pec.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", null, 2.0);

        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        int len;
        byte[] buffer = new byte[1024];
        while ((len = imageStream.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.close();
        imageStream.close();
    }

    /**
     * 获取高亮的节点
     *
     * @param bpmnModel
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();

        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 对历史流程节点进行遍历
            // 得到节点定义的详细信息
            FlowNode activityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());

            // 用以保存后续开始时间相同的节点
            List<FlowNode> sameStartTimeNodes = new ArrayList<>();
            FlowNode sameActivityImpl1 = null;

            // 第一个节点
            HistoricActivityInstance activityI1 = historicActivityInstances.get(i);
            HistoricActivityInstance activityI2;

            for (int k = i + 1; k <= historicActivityInstances.size() - 1; k++) {
                // 后续第1个节点
                activityI2 = historicActivityInstances.get(k);

                //都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                if (activityI1.getActivityType().equals("userTask")
                        && activityI2.getActivityType().equals("userTask")
                        && DateUtil.toFullDate(activityI1.getStartTime()).equals(DateUtil.toFullDate(activityI2.getStartTime()))) {
                } else {
                    //找到紧跟在后面的一个节点
                    sameActivityImpl1 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());
                    break;
                }

            }
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);

                // 如果第一个节点和第二个节点开始时间相同保存
                if (DateUtil.toFullDate(activityImpl1.getStartTime()).equals(DateUtil.toFullDate(activityImpl2.getStartTime()))) {
                    FlowNode sameActivityImpl2 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows();

            // 对所有的线进行遍历
            for (SequenceFlow pvmTransition : pvmTransitions) {
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                FlowNode pvmActivityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(pvmTransition.getTargetRef());
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;

    }

}
