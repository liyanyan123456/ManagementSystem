package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Seat;
import com.wms.mapper.SeatMapper;
import com.wms.service.SeatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-28
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements SeatService {
    @Resource
    SeatMapper seatMapper;

    @Override
    public IPage pageC(IPage<Seat> page) {
        return seatMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<Seat> page, Wrapper wrapper) {return seatMapper.pageCC(page,wrapper);}
}
