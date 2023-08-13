package com.example.rainbowend.Service.Index;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rainbowend.Entity.Files;

import java.util.List;

/**
 * Rainbow
 *
 * @DATE:2023/8/10 0010
 */
public interface IndexFileService {
    int saveDir(Files userFile);

    Files existDir(Files files);

    List<Files> getAllFiles(Page page, String filePid);

    List<Files> getAllFilesNum(String filePid);

    Files existFile(Files files);

    int saveFile(Files files);
}
