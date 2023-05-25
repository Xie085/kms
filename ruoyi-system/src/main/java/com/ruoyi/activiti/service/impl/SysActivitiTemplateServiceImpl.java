package com.ruoyi.activiti.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.activiti.domain.SysActivitiTemplate;
import com.ruoyi.activiti.dto.SysActivitiApprove;
import com.ruoyi.activiti.dto.SysActivitiTemplateDto;
import com.ruoyi.common.utils.DateUtils;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activiti.mapper.SysActivitiTemplateMapper;
import com.ruoyi.activiti.service.ISysActivitiTemplateService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 流程模板Service业务层处理
 *
 * @author ruoyi
 * @date 2022-09-30
 */
@Service
public class SysActivitiTemplateServiceImpl implements ISysActivitiTemplateService {
    @Autowired
    private SysActivitiTemplateMapper sysActivitiTemplateMapper;

    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;

    /**
     * 查询流程模板
     *
     * @param id 流程模板主键
     * @return 流程模板
     */
    @Override
    public SysActivitiTemplate selectSysActivitiTemplateById(Long id) {
        return sysActivitiTemplateMapper.selectSysActivitiTemplateById(id);
    }

    /**
     * 查询流程模板列表
     *
     * @param sysActivitiTemplate 流程模板
     * @return 流程模板
     */
    @Override
    public List<SysActivitiTemplate> selectSysActivitiTemplateList(SysActivitiTemplate sysActivitiTemplate) {
        return sysActivitiTemplateMapper.selectSysActivitiTemplateList(sysActivitiTemplate);
    }

    /**
     * 新增流程模板
     *
     * @param sysActivitiTemplateDto 流程模板
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSysActivitiTemplate(SysActivitiTemplateDto sysActivitiTemplateDto) {
        sysActivitiTemplateDto.setCreateTime(DateUtils.getNowDate());

        //1、保存流程模板
        SysActivitiTemplate sysActivitiTemplate = new SysActivitiTemplate();
        BeanUtils.copyProperties(sysActivitiTemplateDto, sysActivitiTemplate);
        sysActivitiTemplateMapper.insertSysActivitiTemplate(sysActivitiTemplate);
        Long templateId = sysActivitiTemplate.getId();
        String templateName = sysActivitiTemplate.getName();
        String xml = sysActivitiTemplateDto.getProcessDefXml();

        ///2、部署流程
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addString("key_" + templateId + ".bpmn20.xml", xml).name(templateName).deploy();

        //3、更新流程模板信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        sysActivitiTemplate.setProcessDefId(processDefinition.getId());
        sysActivitiTemplate.setProcessDeploymentId(deployment.getId());
        sysActivitiTemplate.setProcessKey(processDefinition.getKey());
        sysActivitiTemplateMapper.updateSysActivitiTemplate(sysActivitiTemplate);
    }

    /**
     * 修改流程模板
     *
     * @param sysActivitiTemplate 流程模板
     * @return 结果
     */
    @Override
    public int updateSysActivitiTemplate(SysActivitiTemplate sysActivitiTemplate) {
        sysActivitiTemplate.setUpdateTime(DateUtils.getNowDate());
        return sysActivitiTemplateMapper.updateSysActivitiTemplate(sysActivitiTemplate);
    }

    /**
     * 批量删除流程模板
     *
     * @param ids 需要删除的流程模板主键
     * @return 结果
     */
    @Override
    public int deleteSysActivitiTemplateByIds(Long[] ids) {
        return sysActivitiTemplateMapper.deleteSysActivitiTemplateByIds(ids);
    }

    /**
     * 删除流程模板信息
     *
     * @param id 流程模板主键
     * @return 结果
     */
    @Override
    public int deleteSysActivitiTemplateById(Long id) {
        return sysActivitiTemplateMapper.deleteSysActivitiTemplateById(id);
    }

    public ProcessInstance startProcessInstanceByKey(String key) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID=getId:" + processInstance.getId());
        System.out.println("当前活动ID:" + processInstance.getActivityId());
        System.out.println("流程实例ID=getProcessInstanceId:" + processInstance.getProcessInstanceId());
        return processInstance;
    }

    public List<Map<String, Object>> findMyTaskList(String processInstanceId, String assignee) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                //.processDefinitionKey(key)
                .processInstanceId(processInstanceId)
                .taskAssignee(assignee)
                .list();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Task task : taskList) {
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务Name:" + task.getName());
            System.out.println("提交人:" + task.getAssignee());
            Map<String, Object> map = new HashMap<>();
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("taskId", task.getId());
            map.put("taskName", task.getName());
            map.put("assignee", task.getAssignee());
            list.add(map);
        }
        return list;
    }

    public void completeTask(SysActivitiApprove sysActivitiApprove) {
        String processInstanceId = sysActivitiApprove.getProcessInstanceId();
        String assignee = sysActivitiApprove.getAssignee();
        String comment = sysActivitiApprove.getComment();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                //.processDefinitionKey(key)
                .processInstanceId(processInstanceId)
                .taskAssignee(assignee)
                .singleResult();

        //taskService.complete(task.getId());
        Map<String, Object> variables = new HashMap();
        variables.put("comment", comment);
        taskService.addComment(task.getId(), processInstanceId, comment);
        taskService.complete(task.getId(), variables);
    }

    public List<Map<String, Object>> findHistory(String processInstanceId) {
        List<Map<String, Object>> rtnList = new ArrayList<>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        instanceQuery.processInstanceId(processInstanceId);
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance> list = instanceQuery.list();

        for (HistoricActivityInstance hi : list) {
            if (hi.getEndTime() != null) {
                List<Comment> comments = taskService.getTaskComments(hi.getTaskId(), "comment");
                System.out.println(hi.getActivityId());
                System.out.println(hi.getActivityName());
                System.out.println(hi.getProcessDefinitionId());
                System.out.println(hi.getProcessInstanceId());
                System.out.println("============================");
                Map<String, Object> map = new HashMap<>();
                map.put("fdEndTime", hi.getEndTime());
                if ("StartEvent".equals(hi.getActivityName())) {
                    map.put("fdActivityName", "开始节点");
                    map.put("fdAssignee", "系统");
                } else if ("EndEvent".equals(hi.getActivityName())) {
                    map.put("fdActivityName", "结束节点");
                    map.put("fdAssignee", "系统");
                } else {
                    map.put("fdActivityName", hi.getActivityName());
                    map.put("fdAssignee", hi.getAssignee());
                }
                if (CollUtil.isNotEmpty(comments)) {
                    map.put("comments", comments.get(0).getFullMessage());
                }
                rtnList.add(map);
            }
        }

        return rtnList;
    }
}
