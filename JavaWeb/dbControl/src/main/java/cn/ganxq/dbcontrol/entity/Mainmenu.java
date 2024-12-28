package cn.ganxq.dbcontrol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@TableName("mainmenu")
@ApiModel(value = "Mainmenu对象", description = "")
public class Mainmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("path")
    private String path;

    @TableField("icon")
    private String icon;
    // Initialize the list to avoid NullPointerException
    private List<Submenu> slist=new ArrayList<>() ;

    // Constructor with parameters


    // No-argument constructor
    public Mainmenu() {
    }
}
