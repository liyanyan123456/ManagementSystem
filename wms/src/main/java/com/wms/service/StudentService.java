package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.StudentDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-13
 */
public interface StudentService extends IService<Student> {
    IPage pageC(IPage<Student> page);

    IPage pageCC(IPage<Student> page, Wrapper wrapper);
    IPage<StudentDTO> pageWithSupervisorName(Page<?> page, @Param(Constants.WRAPPER) Wrapper<?> queryWrapper);


}
