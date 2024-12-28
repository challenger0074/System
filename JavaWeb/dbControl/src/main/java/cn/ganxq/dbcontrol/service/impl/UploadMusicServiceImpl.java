package cn.ganxq.dbcontrol.service.impl;

import cn.ganxq.dbcontrol.entity.UploadMusic;
import cn.ganxq.dbcontrol.mapper.UploadMusicMapper;
import cn.ganxq.dbcontrol.service.IUploadMusicService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ganxq
 * @since 2024-11-29
 */
@Service
public class UploadMusicServiceImpl extends ServiceImpl<UploadMusicMapper, UploadMusic> implements IUploadMusicService {

    @Override
    public JSONObject listAllMusic() {
        return null;
    }
}
