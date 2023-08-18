package com.example.rainbowend.Service.Index;

import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;

/**
 * Rainbow
 *
 * @DATE:2023/8/14 0014
 */
public interface IndexFileOperationService {
    ResponseResult deleteFileAndFolder(String fileId, String filePath);

    ResponseResult resetFileName(String newFileName, Files files);

    ResponseResult resetFolderName(String newFileName, Files files);
}
