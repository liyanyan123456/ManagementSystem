package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.Student;
import com.wms.entity.StudentDTO;
import com.wms.mapper.StudentMapper;
import com.wms.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Resource
    private StudentMapper studentMapper;
    @Override
    public IPage pageC(IPage<Student> page) {
        return studentMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<Student> page, Wrapper wrapper) {
        return studentMapper.pageCC(page,wrapper);
    }

    @Override
    public IPage<StudentDTO> pageWithSupervisorName(Page<?> page, @Param(Constants.WRAPPER) Wrapper<?> queryWrapper){return studentMapper.pageWithSupervisorName(page,queryWrapper);};

}
