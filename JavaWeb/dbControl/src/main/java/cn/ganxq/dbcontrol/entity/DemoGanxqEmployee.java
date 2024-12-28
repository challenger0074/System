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
 * @since 2024-08-26
 */
@Getter
@Setter
@TableName("demo_ganxq_employee")
@ApiModel(value = "DemoGanxqEmployee对象", description = "")
public class DemoGanxqEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("position")
    private String position;

    @TableField("contact_info")
    private String contactInfo;

    @TableField("gender")
    private String gender;

    @ApiModelProperty("入职时间")
    @TableField("start_date")
    private LocalDateTime startDate;

    @TableField("state")
    private Integer state;
}
