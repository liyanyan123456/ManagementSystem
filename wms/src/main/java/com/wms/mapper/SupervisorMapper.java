package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Supervisor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-13
 */
public interface SupervisorMapper extends BaseMapper<Supervisor> {
    IPage<Supervisor> pageC(IPage<Supervisor> page);

    IPage<Supervisor> pageCC(IPage<Supervisor> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
