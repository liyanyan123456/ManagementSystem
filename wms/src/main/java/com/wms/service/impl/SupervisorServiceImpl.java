package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Supervisor;
import com.wms.mapper.SupervisorMapper;
import com.wms.service.SupervisorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-13
 */
@Service
public class SupervisorServiceImpl extends ServiceImpl<SupervisorMapper, Supervisor> implements SupervisorService {
    @Resource
    SupervisorMapper supervisorMapper;

    @Override
    public IPage pageC(IPage<Supervisor> page) {
        return supervisorMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<Supervisor> page, Wrapper wrapper) {return supervisorMapper.pageCC(page,wrapper);}


}
