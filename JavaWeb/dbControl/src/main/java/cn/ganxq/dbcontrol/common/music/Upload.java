package cn.ganxq.dbcontrol.common.music;

import cn.ganxq.dbcontrol.entity.UploadMusic;
import cn.ganxq.dbcontrol.model.QueryInfo;
import cn.ganxq.dbcontrol.service.IUploadMusicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/upload")
public class Upload {

    private static final String TARGET_DIRECTORY = "C:/project/db/music";
    private static final Logger logger = Logger.getLogger(Upload.class.getName());
    private static final String PUBLIC_PATH = "/music_storage/";
    @Autowired
    private IUploadMusicService uploadMusicService; // 注入 IUploadMusicService

    @PostMapping("/music")
    public ResponseEntity<String> uploadMusicFile(@RequestParam("file") MultipartFile file,
            @RequestParam("musicName") String musicName,
            @RequestParam("uploadUser") String uploadUser) {

        // 检查文件是否为空
        if (file.isEmpty()) {
            logger.warning("No file provided.");
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        // 获取文件名并校验类型
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".mp3")) {
            logger.warning("Invalid file type. Only .mp3 files are supported.");
            return ResponseEntity.badRequest().body("Invalid file type. Only .mp3 files are supported.");
        }

        // 创建用户文件夹
        File userDir = new File(TARGET_DIRECTORY, uploadUser);
        if (!userDir.exists()) {
            if (!userDir.mkdirs()) {
                logger.warning("Failed to create user directory: " + userDir.getAbsolutePath());
                return ResponseEntity.status(500).body("Failed to create user directory.");
            }
            logger.info("Created user directory: " + userDir.getAbsolutePath());
        }

        // 处理文件名冲突
        File targetFile = new File(userDir, fileName);
        // 1. 查询数据库中是否已经有该音乐文件记录
        List<UploadMusic> existingMusic = uploadMusicService.list(new QueryWrapper<UploadMusic>()
                .eq("music_name", musicName)
                .eq("upload_user", uploadUser));

        if (existingMusic != null && !existingMusic.isEmpty()) {
            // 如果数据库中已经存在该记录，返回上传失败
            logger.warning("Music file already uploaded by the user: " + musicName);
            return ResponseEntity.status(400).body("You have already uploaded this music file.");
        }

        // 保存文件
        try {
            file.transferTo(targetFile);
            logger.info("File uploaded successfully: " + targetFile.getAbsolutePath());
            // 构建公共路径
            String storageLocation = PUBLIC_PATH + uploadUser + "/" + fileName;
            // Set the storage location in the database (relative path)
            // 插入文件信息到数据库
            UploadMusic uploadMusic = new UploadMusic();
            uploadMusic.setMusicName(musicName);
            uploadMusic.setUploadUser(uploadUser);
            uploadMusic.setStorageLocation(storageLocation);
            boolean saved = uploadMusicService.save(uploadMusic);  // 使用 MyBatis-Plus 插入数据库
            if (saved) {
                logger.info("Music info saved to database successfully.");
            } else {
                logger.warning("Failed to save music info to database.");
            }

            return ResponseEntity.ok("File uploaded and information saved successfully!");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error uploading file: ", e);
            return ResponseEntity.status(500).body("Error uploading file.");
        }
    }

    @DeleteMapping("/music/{musicFile}")
    public ResponseEntity<String> deleteMusicFile(@PathVariable("musicFile") String fileName,
                                                  @RequestParam("uploadUser") String uploadUser) {
        // 校验文件类型
        if (fileName == null || !fileName.toLowerCase().endsWith(".mp3")) {
            logger.warning("Invalid file type. Only .mp3 files are supported.");
            return ResponseEntity.badRequest().body("Invalid file type. Only .mp3 files are supported.");
        }

        // 去除 .mp3 后缀，以便于查询数据库时使用的文件名
        String fileNameWithoutExtension = fileName.substring(0, fileName.length() - 4);

        // 定位到用户文件目录
        File userDir = new File(TARGET_DIRECTORY, uploadUser);
        File targetFile = new File(userDir, fileName);

        // 1. 查询数据库中是否有相关记录，基于 fileNameWithoutExtension 和 uploadUser
        List<UploadMusic> existingMusic = uploadMusicService.list(new QueryWrapper<UploadMusic>()
                .eq("music_name", fileNameWithoutExtension)
                .eq("upload_user", uploadUser));  // MyBatis-Plus查询条件

        if (existingMusic == null) {
            logger.warning("File not found in the database: " + fileNameWithoutExtension);
            return ResponseEntity.status(404).body("File not found in the database.");
        }

        // 2. 删除数据库中的音乐记录
        boolean deletedFromDb = uploadMusicService.remove(new QueryWrapper<UploadMusic>()
                .eq("music_name", fileNameWithoutExtension)
                .eq("upload_user", uploadUser));  // 使用QueryWrapper删除条件
        if (!deletedFromDb) {
            logger.warning("Failed to delete music info from database.");
            return ResponseEntity.status(500).body("Failed to delete music info from database.");
        }
        logger.info("Music info deleted from database successfully.");

        // 3. 删除文件系统中的文件
        if (!targetFile.exists()) {
            logger.warning("File not found in the file system: " + targetFile.getAbsolutePath());
            return ResponseEntity.status(404).body("File not found in the file system.");
        }

        if (targetFile.delete()) {
            logger.info("File deleted successfully: " + targetFile.getAbsolutePath());
            return ResponseEntity.ok("File and database record deleted successfully!");
        } else {
            logger.warning("Failed to delete the file: " + targetFile.getAbsolutePath());
            return ResponseEntity.status(500).body("Failed to delete the file from file system.");
        }
    }
    //分页查询个人上传歌曲
    @GetMapping("/music/list/{uploadUser}")
    public ResponseEntity<Page<UploadMusic>> getMusicList(@PathVariable("uploadUser") String uploadUser, QueryInfo queryInfo) {
        // 1. Create a Page object for pagination
        Page<UploadMusic> page = new Page<>(queryInfo.getPageNum(), queryInfo.getPageSize());

        // 2. Query the database for the music list uploaded by the user with pagination
        Page<UploadMusic> musicPage = uploadMusicService.page(page, new QueryWrapper<UploadMusic>()
                .eq("upload_user", uploadUser)  // MyBatis-Plus query condition
                .like(queryInfo.getQuery() != null && !queryInfo.getQuery().isEmpty(), "music_name", queryInfo.getQuery())  // Optional search query for music name
        );

        // 3. If no music records are found, return an empty list (this is optional, depends on your use case)
        if (musicPage.getRecords().isEmpty()) {
            logger.info("No music found for the user: " + uploadUser);
            return ResponseEntity.ok(musicPage);
        }

        // 4. Return the paginated list of music files
        logger.info("Fetched paginated music list for user: " + uploadUser);
        return ResponseEntity.ok(musicPage);
    }
//分页查询全部歌曲
    @GetMapping("/music/list")
    public ResponseEntity<IPage<UploadMusic>> getMusicList(QueryInfo queryInfo) {
        // 1. Create a Page object for pagination
        Page<UploadMusic> page = new Page<>(queryInfo.getPageNum(), queryInfo.getPageSize());

        // 2. Query the database for the music list with pagination (no user-specific filter)
        IPage<UploadMusic> musicPage = uploadMusicService.page(page, new QueryWrapper<UploadMusic>()
                .like(queryInfo.getQuery() != null && !queryInfo.getQuery().isEmpty(), "music_name", queryInfo.getQuery())  // Optional search query for music name
        );

        // 3. If no music records are found, return an empty list (this is optional, depends on your use case)
        if (musicPage.getRecords().isEmpty()) {
            logger.info("No music found.");
            return ResponseEntity.ok(musicPage);
        }

        // 4. Return the paginated list of music files
        logger.info("Fetched paginated music list.");
        return ResponseEntity.ok(musicPage);
    }
    // 下载音乐文件
    @GetMapping("/music/download/{musicId}")
    public ResponseEntity<FileSystemResource> downloadMusicFile(@PathVariable("musicId") Long musicId) {
        // 查询数据库获取音乐文件信息
        UploadMusic uploadMusic = uploadMusicService.getById(musicId);
        if (uploadMusic == null) {
            logger.warning("Music file not found with ID: " + musicId);
            return ResponseEntity.notFound().build();
        }

        // 构建文件路径
        String filePath = TARGET_DIRECTORY + uploadMusic.getStorageLocation().substring(PUBLIC_PATH.length());
        File file = new File(filePath);

        if (!file.exists()) {
            logger.warning("File not found in the file system: " + file.getAbsolutePath());
            return ResponseEntity.notFound().build();
        }

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uploadMusic.getMusicName() + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(new FileSystemResource(file));
    }



    private String getBaseName(String originalFileName) {
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return originalFileName;
        }
        return originalFileName.substring(0, dotIndex);
    }

    private String getExtension(String originalFileName) {
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return originalFileName.substring(dotIndex);
    }
}
