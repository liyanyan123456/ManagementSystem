package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.StudentDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-13
 */
public interface StudentMapper extends BaseMapper<Student> {
    IPage<Student> pageC(IPage<Student> page);

    IPage<Student> pageCC(IPage<Student> page, @Param(Constants.WRAPPER) Wrapper wrapper);
    @Select("SELECT s.*, t.name AS supervisorName " +
            "FROM student s " +
            "LEFT JOIN supervisor t ON s.supervisorId = t.id " +
            "${ew.customSqlSegment}")
    IPage<StudentDTO> pageWithSupervisorName(Page<?> page, @Param(Constants.WRAPPER) Wrapper<?> queryWrapper);


}
