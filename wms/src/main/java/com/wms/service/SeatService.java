package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Seat;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-28
 */
public interface SeatService extends IService<Seat> {
    IPage pageC(IPage<Seat> page);
    IPage pageCC(IPage<Seat> page,  Wrapper wrapper);
}
