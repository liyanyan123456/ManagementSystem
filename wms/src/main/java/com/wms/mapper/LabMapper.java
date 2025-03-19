package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Lab;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-20
 */
public interface LabMapper extends BaseMapper<Lab> {
    IPage<Lab> pageC(IPage<Lab> page);

    IPage<Lab> pageCC(IPage<Lab> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
