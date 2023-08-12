package com.example.rainbowend.Controller.Index;

import com.alibaba.fastjson.JSONObject;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.UserFile;
import com.example.rainbowend.Service.Index.IndexFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Rainbow
 * 处理文件
 * @DATE:2023/8/10 0010
 */
@RestController
@RequestMapping("file")
public class IndexFilesController {
    private static final Logger logger = LoggerFactory.getLogger(IndexFilesController.class);
    @Resource
    private IndexFileService indexFileService;
    @Value("${UserRoot}")
    private String UserRoot;
    /**
     * 创建新文件夹
     * 1、解析参数
     * 2、获取文件夹路径
     * 3、路径入库
     * @param jsonObject
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("newDir")
    public ResponseResult createNewDir(@RequestBody JSONObject jsonObject){
        Map<String, Object> map = new HashMap<>();
        try{
            //1、解析参数
            String email=jsonObject.getString("email");
            String dirName=jsonObject.getString("dirName");
            //2、路径入库
            String dir=email+"/"+ dirName;   //前端文件访问路径
            UserFile userFile = new UserFile(email,dir,null);
            UserFile userFile2=indexFileService.existDir(userFile);  //判断文件夹是否存在

            File file = new File(UserRoot+email, dirName);  //本地存储路径
            map.put("dir",dir);
            if(userFile2!=null){
                //判断本地是否已经存在同名文件夹
                if(!file.exists()){
                    file.mkdir();  //创建文件夹
                    return ResponseResult.ok("文件夹创建成功",map);
                }else{
                    return ResponseResult.error("文件夹已经存在",'1');
                }
            }else{
                //入库
                int i=indexFileService.saveDir(userFile);
                if(i>0){
                    //3、本地创建文件夹
                    file.mkdir();  //创建文件夹
                    return ResponseResult.ok("文件夹创建成功",map);
                }else{
                    return ResponseResult.error("文件夹路径入库失败");
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseResult.error("文件夹已经存在");
        }
    }


    /**
     * 创建新文件
     * 1、解析参数
     * 2、获取文件夹路径
     * 3、路径入库
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("newFile")
    public ResponseResult getNewFile(HttpServletRequest request){
        System.out.println("创建新文件夹");
        Map<String, Object> map = new HashMap<>();
        try{
            //1、解析参数
            MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
                //获取字段参数
            String email = mulRequest.getParameter("email");   //邮箱
            System.out.println(email);
                //获取多个文件
            List<MultipartFile> filesList = mulRequest.getFiles("file");
            System.out.println(filesList.size());
            MultipartFile multipartFile = filesList.get(0);

//            //2、路径入库
//            String dir=email+"/"+newDir;   //前端文件访问路径
//            UserFile userFile = new UserFile(email,dir,null);
//            UserFile userFile2=indexFileService.existDir(userFile);
//
//            File file = new File(UserRoot+email,newDir);  //本地存储路径
//            map.put("dir",dir);
//            if(userFile2!=null){
//                //判断本地是否已经存在同名文件夹
//                if(!file.exists()){
//                    file.mkdir();  //创建文件夹
//                    return ResponseResult.ok("文件夹创建成功",map);
//                }else{
//                    return ResponseResult.error("文件夹已经存在",'1');
//                }
//            }else{
//                //入库
//                int i=indexFileService.saveDir(userFile);
//                if(i>0){
//                    //3、本地创建文件夹
//                    file.mkdir();  //创建文件夹
//                    return ResponseResult.ok("文件夹创建成功",map);
//                }else{
//                    return ResponseResult.error("文件夹路径入库失败");
//                }
//            }
            return ResponseResult.ok("文件夹创建成功",map);
        }catch(Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseResult.error("文件夹已经存在");
        }
    }
}
