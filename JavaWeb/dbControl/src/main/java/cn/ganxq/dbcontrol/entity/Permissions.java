package cn.ganxq.dbcontrol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2024-11-20
 */
@Getter
@Setter
@TableName("permissions")
@ApiModel(value = "Permissions对象", description = "")
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty("归属菜单,前端判断并展示菜单使用,")
    @TableField("menu_code")
    private String menuCode;

    @ApiModelProperty("菜单的中文释义")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty("权限的代码/通配符,对应代码中@RequiresPermissions 的value")
    @TableField("permission_code")
    private String permissionCode;

    @ApiModelProperty("本权限的中文释义")
    @TableField("permission_name")
    private String permissionName;

    @ApiModelProperty("是否本菜单必选权限, 1.必选 2非必选 通常是\"列表\"权限是必选")
    @TableField("required_permission")
    private Boolean requiredPermission;
}
