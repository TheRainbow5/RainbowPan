package com.example.rainbowend.Controller.Index;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Entity.Files;
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
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

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
    @PostMapping("addDir")
    public ResponseResult createNewFile(@RequestBody JSONObject jsonObject){
        Map<String, Object> map = new HashMap<>();
        try{
            //1、解析参数
            String fileId = UUID.randomUUID().toString().replace("-","");  //文件id
            String fileName=jsonObject.getString("fileName");  //文件名
            String email=jsonObject.getString("email");
            String filePid;      //父路径
            if(jsonObject.getString("currentDir").equals("")){
                filePid=email+jsonObject.getString("currentDir");
            }else{
                filePid=email+"/"+jsonObject.getString("currentDir");
            }
            String filePath;      //文件存储路径（前端访问路径）
            filePath=filePid+"/"+fileName;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime =  dateFormat.format(new Date());   //创建时间
            String updateTime =  dateFormat.format(new Date());  //最后更新时间
            int folderType=1;          //文件/目录
            String status="1";   //状态正常

            //2、数据封装
            Files files = new Files();
            files.setFileId(fileId);
            files.setFileName(fileName);
            files.setEmail(email);
            files.setFilePid(filePid);
            files.setFilePath(filePath);
            files.setCreateTime(createTime);
            files.setUpdateTime(updateTime );
            files.setFolderType(folderType);
            files.setStatus(status);
            //3、数据入库
            map.put("filePath",filePath);
            map.put("filePid",filePid);
            File file = new File(UserRoot+filePid , fileName);  //本地存储路径
            Files userFile=indexFileService.existDir(files);  //判断文件夹是否存在
            System.out.println(userFile);
            if(userFile!=null){
                //判断本地是否已经存在同名文件夹
                file.mkdir();
                if(file.exists()){
                    return ResponseResult.error("文件夹已经存在",map);  //返回存储路径
                }else{
                    throw new RuntimeException();
                }
            }else{
                //入库
                int i=indexFileService.saveDir(files);
                if(i>0){
                    //3、本地创建文件夹
                    file.mkdir();  //创建文件夹
                    if(!file.exists()){
                        throw new RuntimeException();
                    }else{
                        return ResponseResult.ok("文件夹创建成功",map);  //返回存储路径
                    }
                }else{
                    return ResponseResult.error("文件夹路径入库失败");
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseResult.error("发生一些错误，请联管理员");
        }
    }


    /**
     * 获取路径下所有文件
     * @return
     */
    @PostMapping("allFiles")
    public ResponseResult getAllFiles(@RequestBody JSONObject jsonObject){
        Map<String, Object> map = new HashMap<>();
        try{
            //1、解析参数
            String email=jsonObject.getString("email");
            String currentDir=jsonObject.getString("currentDir");  //当前目录
            int currentPage= Integer.parseInt(jsonObject.getString("currentPage"));  //当前页数
            int pageSize= Integer.parseInt(jsonObject.getString("pageSize"));   //页面带线啊哦
            String filePid;
            if(currentDir.equals("")){
                filePid=email;   //当前目录
            }else{
                filePid=email+"/"+currentDir;   //当前目录
            }
            //2、查询所有子文件
                //查询一共有多少条
            List<Files>  filesNum=indexFileService.getAllFilesNum(filePid);
            int fileNum=filesNum.size();
                //分页查询
            List<Files>  filesList=indexFileService.getAllFiles(new Page(currentPage,pageSize),filePid);
            map.put("fileList",filesList);
            map.put("totalNum",fileNum);
            if(fileNum==0){
                return ResponseResult.error("获取失败",map);
            }else{
                //对list进行特殊排序
                Comparator<Files> comparator = Comparator.comparingInt(Files::getFolderType).reversed();
                Collections.sort(filesList, comparator);
                System.out.println(filesList);
                return ResponseResult.ok("获取成功",map);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }
    /**
     * 上传文件
     * 1、解析参数
     * 2、获取文件夹路径
     * 3、路径入库
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("uploadFile")
    public ResponseResult getNewFile(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        try{
            MultipartHttpServletRequest mulRequest=(MultipartHttpServletRequest) request;
            //1、解析参数
                //获取多个文件
            List<MultipartFile> filesList = mulRequest.getFiles("file");
                //通过for循环实现处理多个文件
            System.out.println("传入文件个数:"+filesList.size());
            MultipartFile multipartFile = filesList.get(0);
                //参数
            String fileId = UUID.randomUUID().toString().replace("-","");  //文件id
            String email = mulRequest.getParameter("email");   //邮箱
            String fileName=multipartFile.getOriginalFilename();   //文件名
            String currentDir=mulRequest.getParameter("currentDir");  //当前目录
            String filePid;   //父目录
            if(currentDir.equals("")){
                filePid=email;   //父目录
            }else{
                filePid=email+"/"+currentDir;   //父目录
            }
            String filePath=filePid+"/"+fileName;      //文件存储路径（前端访问路径）
            String fileSize= formatFileSize(multipartFile.getSize());   //文件大小
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime =  dateFormat.format(new Date());   //创建时间
            String updateTime =  dateFormat.format(new Date());  //最后更新时间
            int folderType=0;          //文件/目录
            //判断文件类型
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            String fileCategory = categoryFile(suffix);
            String status="1";   //状态正常

            //2、数据封装
            Files files = new Files();
            files.setFileId(fileId);
            files.setFileName(fileName);
            files.setEmail(email);
            files.setFilePid(filePid);
            files.setFilePath(filePath);
            files.setFileSize(fileSize);
            files.setCreateTime(createTime);
            files.setUpdateTime(updateTime );
            files.setFolderType(folderType);
            files.setFileCategory(fileCategory);
            files.setStatus(status);

            map.put("filePath",filePath);  //返回文件访问路径
            map.put("filePid",filePid);   //返回文件父路径
            map.put("folderType",folderType);  //返回文件类型
            //3、数据存储
            File file = new File(UserRoot+filePid , fileName);  //本地存储路径
            Files userFile=indexFileService.existFile(files);  //判断文件夹是否存在
            if(userFile!=null){
                //判断本地是否已经存在同名文件
                multipartFile.transferTo(file);
                if(file.exists()){
                    return ResponseResult.error("文件夹已经存在",map);  //返回存储路径
                }else{
                    throw new RuntimeException();
                }
            }else{
                //入库
                int i=indexFileService.saveFile(files);
                if(i>0){
                    //3、本地创建文件夹
                    multipartFile.transferTo(file);
                    if(!file.exists()){
                        throw new RuntimeException();
                    }else{
                        return ResponseResult.ok("文件夹创建成功",map);  //返回存储路径
                    }
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
     * 判断文件类型
     * @param suffix
     * @return
     */
    public String categoryFile(String suffix){
        //视频
        List<String> videoList = new ArrayList<>();
        videoList.add(".mp4");
        videoList.add(".avi");
        videoList.add(".mov");
        videoList.add(".wmv");
        videoList.add(".mkv");
        videoList.add(".flv");
        videoList.add(".webm");
        videoList.add(".m4v");
        videoList.add(".3gp");
        videoList.add(".mpeg");
        //音频
        List<String> audioList = new ArrayList<>();
        audioList.add(".mp3");
        audioList.add(".wav");
        audioList.add(".flac");
        audioList.add(".aac");
        audioList.add(".ogg");
        audioList.add(".wma");
        audioList.add(".m4a");
        audioList.add(".ape");
        audioList.add(".mp2");
        audioList.add(".mid");
        //图片
        List<String> imageList = new ArrayList<>();
        imageList.add(".jpg");
        imageList.add(".jpeg");
        imageList.add(".png");
        imageList.add(".gif");
        imageList.add(".bmp");
        imageList.add(".tiff");
        imageList.add(".tif");
        imageList.add(".webp");
        imageList.add(".svg");
        imageList.add(".ico");
        imageList.add(".psd");
        //word文档
        List<String> wordList = new ArrayList<>();
        wordList.add(".doc");
        wordList.add(".docx");
        wordList.add(".dot");
        wordList.add(".dotx");
        wordList.add(".docm");
        wordList.add(".dotm");
        //excel
        List<String> excelList = new ArrayList<>();
        excelList.add(".xls");
        excelList.add(".xlsx");
        excelList.add(".xlsm");
        excelList.add(".xlsb");
        excelList.add(".xlt");
        excelList.add(".xltx");
        excelList.add(".xltm");
        excelList.add(".csv");
        //代码
        List<String> codeList = new ArrayList<>();
        codeList.add(".java");
        codeList.add(".cpp");
        codeList.add(".cxx");
        codeList.add(".c");
        codeList.add(".py");
        codeList.add(".js");
        codeList.add(".html");
        codeList.add(".css");
        codeList.add(".php");
        codeList.add(".rb");
        codeList.add(".swift");
        codeList.add(".go");
        codeList.add(".pl");
        codeList.add(".lua");
        codeList.add(".ts");
        codeList.add(".scss");
        codeList.add(".dart");
        codeList.add(".jsx");
        codeList.add(".vue");
        codeList.add(".sql");
        codeList.add(".xml");
        //压缩包
        List<String> compressionList = new ArrayList<>();
        compressionList.add(".zip");
        compressionList.add(".rar");
        compressionList.add(".7z");
        compressionList.add(".tar");
        compressionList.add(".gz");
        compressionList.add(".bz2");
        compressionList.add(".xz");
        compressionList.add(".tar.gz");
        compressionList.add(".tgz");
        compressionList.add(".tar.bz2");
        compressionList.add(".tar.xz");

        if(videoList.contains(suffix)){  //视频
            return "0";
        } else if (audioList.contains(suffix) ) {  //音频
            return "1";
        }else if(imageList.contains(suffix)){   //图片
            return "2";
        } else if (suffix.equals(".pdf")) {
            return "pdf";
        } else if(wordList.contains(suffix)){
            return "4";
        }else if(excelList.contains(suffix)){
            return "5";
        }else if(suffix.equals(".txt")){
            return "6";
        }else if(codeList.contains(suffix)){
            return "7";
        }else if(compressionList.contains(suffix)){  //压缩包
            return "8";
        }
        else{   //其他
            return "9";
        }
    }

    /**
     * 将字节转为KB、MB 或 GB
     * @param bytes
     * @return
     */
    public String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return (bytes / 1024) + " KB";
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", (float) bytes / (1024 * 1024));
        } else {
            return String.format("%.2f GB", (float) bytes / (1024 * 1024 * 1024));
        }
    }

}
