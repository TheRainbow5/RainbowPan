package com.example.rainbowend.Service.Index;

import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import org.springframework.http.ResponseEntity;

/**
 * Rainbow
 *
 * @DATE:2023/8/14 0014
 */
public interface IndexFileOperationService {
    ResponseResult deleteFileAndFolder(Files files);

    ResponseResult resetFileName(String newFileName, Files files);

    ResponseResult resetFolderName(String newFileName, Files files);

    ResponseEntity downloadDir(Files files);

    ResponseEntity downloadFile(Files files);

    ResponseEntity previewFile(Files files);
}
