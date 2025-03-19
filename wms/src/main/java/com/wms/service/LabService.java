package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Lab;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-20
 */
public interface LabService extends IService<Lab> {
    IPage pageC(IPage<Lab> page);
    IPage pageCC(IPage<Lab> page,  Wrapper wrapper);
}
