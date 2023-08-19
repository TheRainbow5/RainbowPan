package com.example.rainbowend.Controller.Index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Service.Index.IndexFileOperationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
    @Value("${UserRoot}")
    private String UserRoot;

    /**
     * 删除文件
     *
     * @param jsonObject
     */
    @PostMapping("delete")
    public ResponseResult deleteFile(@RequestBody JSONObject jsonObject) {
        //序列化数据，转为File实体类
        String JSONStr = JSON.toJSONString(jsonObject.getJSONObject("colItem"));
        Files files = JSON.parseObject(JSONStr, Files.class);
        //删除文件
        return indexFileOperationService.deleteFileAndFolder(files);
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
        }
        //重命名文件
        else {
            return indexFileOperationService.resetFileName(newFileName, files);
        }

    }

    /**
     * 文件下载
     *
     * @param jsonObject 文件属性
     * @return
     */
    @PostMapping("download")
    public ResponseEntity downLoadFile(@RequestBody JSONObject jsonObject) {
        // 将JSON字符串转换为指定类的对象
        String JSONStr = JSON.toJSONString(jsonObject.getJSONObject("colItem"));
        Files files = JSON.parseObject(JSONStr, Files.class);
        //判断是下载文件/文件夹
        if (files.getFolderType() == 1) {   //目录
            return indexFileOperationService.downloadDir(files);
        } else {  //文件
            return indexFileOperationService.downloadFile(files);
        }
    }


}
