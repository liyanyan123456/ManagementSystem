package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liyanyan
 * @since 2024-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "primaryId", type = IdType.AUTO)
    private Integer primaryId;

    @ApiModelProperty(value = "学号")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年级")
    private String grade;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "导师工号")
    @TableField("supervisorId")
    private String supervisorId;

    @ApiModelProperty(value = "机房")
    @TableField("labId")
    private Integer labId;

    @ApiModelProperty(value = "机位")
    @TableField("seatId")
    private Integer seatId;


}
