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
 * @since 2024-12-03
 */
@Getter
@Setter
@TableName("upload_music")
@ApiModel(value = "UploadMusic对象", description = "")
public class UploadMusic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("music_name")
    private String musicName;

    @TableField("upload_user")
    private String uploadUser;

    @TableField("storage_location")
    private String storageLocation;
}
