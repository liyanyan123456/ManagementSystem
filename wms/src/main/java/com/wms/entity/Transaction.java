package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * @since 2024-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Transaction对象", description="")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField("createTime")
    private LocalDate createTime;

    @ApiModelProperty(value = "处理时间")
    @TableField("handleTime")
    private LocalDate handleTime;

    @ApiModelProperty(value = "事务类型")
    private String type;

    @ApiModelProperty(value = "报修类型")
    private String repair;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "批注")
    private String postil;

    @ApiModelProperty(value = "学生学号")
    @TableField("studentId")
    private String studentId;

    @ApiModelProperty(value = "机位")
    @TableField("seatId")
    private Integer seatId;


}
