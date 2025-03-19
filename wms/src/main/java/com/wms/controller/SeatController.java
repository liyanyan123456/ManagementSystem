package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Seat;
import com.wms.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liyanyan
 * @since 2025-02-28
 */
@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("/list")
    public List<Seat> list(){
        return seatService.list();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Seat seat){
        return seatService.save(seat)?Result.suc():Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Seat seat){
        System.out.println(seat);
        // 检查座位号房是否已经存在
        Seat existingSeat = seatService.lambdaQuery()
                .eq(Seat::getName, seat.getName())
                .ne(Seat::getId, seat.getId()) // 排除当前记录
                .one();

        if (existingSeat != null) {
            return Result.fail().setMsg("座位号已存在，不能重复！");
        }

        return seatService.updateById(seat)?Result.suc():Result.fail();
    }

    @GetMapping("/del")
    public Result del(@RequestParam String id){
        System.out.println("座位ID号："+id);
        // 根据座位ID号查找对应的主键id
        Seat seat = seatService.lambdaQuery()
                .eq(Seat::getId, id)
                .one();
        if (seat == null) {
            return Result.fail().setMsg("未找到对应的座位号记录，座位ID号: " + id);
        }
        System.out.println("seat："+seat);


        // 获取主键id并删除
        boolean removed = seatService.removeById(seat.getId());
        return removed ? Result.suc().setMsg("删除成功") : Result.fail().setMsg("删除失败");

    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        try {
            HashMap param = query.getParam();
            String name = (String) param.get("name");



            Page<Seat> page = new Page();
            page.setCurrent(query.getPageNum());
            page.setSize(query.getPageSize());

            LambdaQueryWrapper<Seat> lambdaQueryWrapper = new LambdaQueryWrapper();
            if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
                lambdaQueryWrapper.like(Seat::getName, name);
            }
//            if (StringUtils.isNotBlank(gender)) {
//                lambdaQueryWrapper.eq(Supervisor::getGender, gender);
//            }

            IPage result = seatService.pageCC(page, lambdaQueryWrapper);

            System.out.println("total==" + result.getTotal());

            return Result.suc(result.getRecords(), result.getTotal());
        }catch (Exception e) {
            e.printStackTrace();
            return Result.fail().setMsg("查询失败" );
        }
    }
}
