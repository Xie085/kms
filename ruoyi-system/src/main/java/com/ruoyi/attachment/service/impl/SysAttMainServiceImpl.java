package com.ruoyi.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.attachment.domain.SysAttFile;
import com.ruoyi.attachment.domain.SysAttMain;
import com.ruoyi.attachment.enums.AttKeyEnums;
import com.ruoyi.attachment.mapper.SysAttFileMapper;
import com.ruoyi.attachment.mapper.SysAttMainMapper;
import com.ruoyi.attachment.service.ISysAttMainService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenzh
 * @Date 1/4/22 2:08 PM
 */
@Log4j2
@Service
public class SysAttMainServiceImpl implements ISysAttMainService {
    @Autowired
    private SysAttFileMapper sysAttFileMapper;
    @Autowired
    private SysAttMainMapper sysAttMainMapper;

    @Override
    public List<SysAttFile> findConverted(Long kmsId) {
        log.info("预览附件;当前知识ID为:[{}]", () -> kmsId);
        SysAttMain sysAttMain = new SysAttMain();
        sysAttMain.setModelId(kmsId);
        sysAttMain.setModelName(AttKeyEnums.KMS.getModel());
        List<SysAttFile> convertedList = sysAttFileMapper.selectAttConvertedList(sysAttMain);
        if (CollectionUtil.isEmpty(convertedList)) {
            return new ArrayList<>();
        }
        return convertedList;
    }

    @Override
    public void deleteNoUsedAttAndFile() {
        List<Long> ids = sysAttMainMapper.findNoUsedAttId();
        if (!CollectionUtil.isEmpty(ids)) {
            sysAttMainMapper.deleteByIds(ids.toArray(new Long[]{}));
        }
    }
}
