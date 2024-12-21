package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Supervisor;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-13
 */
public interface SupervisorService extends IService<Supervisor> {
    IPage pageC(IPage<Supervisor> page);
    IPage pageCC(IPage<Supervisor> page,  Wrapper wrapper);


}
