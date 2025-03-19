package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Lab;
import com.wms.mapper.LabMapper;
import com.wms.service.LabService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-20
 */
@Service
public class LabServiceImpl extends ServiceImpl<LabMapper, Lab> implements LabService {
    @Resource
    LabMapper labMapper;

    @Override
    public IPage pageC(IPage<Lab> page) {
        return labMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<Lab> page, Wrapper wrapper) {return labMapper.pageCC(page,wrapper);}
}
