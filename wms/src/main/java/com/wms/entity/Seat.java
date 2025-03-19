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
 * @since 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Seat对象", description="")
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "机位名称")
    private String name;

    @ApiModelProperty(value = "机房")
    @TableField("labId")
    private Integer labId;

    @ApiModelProperty(value = "占用历史")
    private String history;

    @ApiModelProperty(value = "状态")
    private Integer state;

    @ApiModelProperty(value = "学生学号")
    @TableField("studentId")
    private String studentId;

    @ApiModelProperty(value = "导师工号")
    @TableField("supervisorId")
    private String supervisorId;


}
