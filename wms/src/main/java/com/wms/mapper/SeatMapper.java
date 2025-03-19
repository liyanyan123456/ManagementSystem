package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Seat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-28
 */
public interface SeatMapper extends BaseMapper<Seat> {
    IPage<Seat> pageC(IPage<Seat> page);

    IPage<Seat> pageCC(IPage<Seat> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
