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
 * @since 2025-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Lab对象", description="")
public class Lab implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "机房名称")
    private String name;

    @ApiModelProperty(value = "大楼名称")
    private String building;

    @ApiModelProperty(value = "楼层")
    private Integer floor;

    @ApiModelProperty(value = "机位数量")
    @TableField("seatNum")
    private Integer seatNum;



}
