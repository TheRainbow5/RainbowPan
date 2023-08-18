package com.example.rainbowend.Controller.Index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Service.Index.IndexFileOperationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Rainbow
 * 文件管理
 *
 * @DATE:2023/8/14 0014
 */
@RestController
@RequestMapping("index")
public class IndexFilesOperationController {
    @Resource
    private IndexFileOperationService indexFileOperationService;

    /**
     * 删除文件
     *
     * @param jsonObject
     */
    @PostMapping("delete")
    public ResponseResult deleteFile(@RequestBody JSONObject jsonObject) {
        //1、解析参数
        String fileId = jsonObject.getString("fileId");  //表id
        String filePath = jsonObject.getString("filePath");  //全路径
        //删除文件
        return indexFileOperationService.deleteFileAndFolder(fileId, filePath);
    }

    /**
     * 文件重命名
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("resetFileName")
    public ResponseResult resetFileName(@RequestBody JSONObject jsonObject) throws IOException {
        //1、解析参数
        String newFileName = jsonObject.getString("newFileName");
        // 将JSON字符串转换为指定类的对象
        String JSONStr = JSON.toJSONString(jsonObject.getJSONObject("colItem"));
        Files files = JSON.parseObject(JSONStr, Files.class);
        //重命名文件夹
        if (files.getFolderType() == 1) {
            return indexFileOperationService.resetFolderName(newFileName, files);
        } else {
            return indexFileOperationService.resetFileName(newFileName, files);
        }

    }

}
