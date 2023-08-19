package com.example.rainbowend.Service.Index;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
public interface IndexFileService {
    ResponseResult createNewDir(Files files);

    ResponseResult getAllFiles(Page page, String email, String absolutePath);

    ResponseResult uploadNewFile(Files files, MultipartFile multipartFile);

    ResponseResult getAllFilesByFileName(Page page, String fileName, String email, String absolutePath);
}
