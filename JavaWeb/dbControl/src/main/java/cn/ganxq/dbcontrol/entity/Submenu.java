package cn.ganxq.dbcontrol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ganxq
 * @since 2024-12-02
 */
@Getter
@Setter
@TableName("submenu")
@ApiModel(value = "Submenu对象", description = "")
public class Submenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("path")
    private String path;

    @TableField("mid")
    private Integer mid;

    @TableField("icon")
    private String icon;
}
