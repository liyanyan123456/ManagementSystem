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
@ApiModel(value="Supervisor对象", description="")
public class Supervisor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "primaryId", type = IdType.AUTO)
    private Integer primaryId;

    @ApiModelProperty(value = "工号")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "职称")
    private String title;

    @ApiModelProperty(value = "办公室")
    private String office;

    @ApiModelProperty(value = "学生数量")
    @TableField("studentNum")
    private Integer studentNum;


}
