package cn.ganxq.dbcontrol.service;

import cn.ganxq.dbcontrol.entity.UploadMusic;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ganxq
 * @since 2024-11-29
 */
public interface IUploadMusicService extends IService<UploadMusic> {
    /**
     * 查询所有音乐
     */
    public JSONObject listAllMusic();
}
