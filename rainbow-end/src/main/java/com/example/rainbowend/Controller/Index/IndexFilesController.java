package com.example.rainbowend.Controller.Index;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Service.Index.IndexFileService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Rainbow
 * 处理文件
 *
 * @DATE:2023/8/10 0010
 */
@RestController
@RequestMapping("file")
public class IndexFilesController {
    @Resource
    private IndexFileService indexFileService;

    /**
     * 创建新文件夹
     * @param jsonObject 包含文件夹相关信息的 JSON 对象
     * @return 创建结果的 ResponseResult 对象
     */
    @PostMapping("addDir")
    public ResponseResult createNewDir(@RequestBody JSONObject jsonObject) {
        // 解析必要参数
        String fileName = jsonObject.getString("fileName");  // 文件名
        String currentDir = jsonObject.getString("currentDir");  // 当前目录
        String email = jsonObject.getString("email");  // 邮箱

        // 生成文件ID
        String fileId = UUID.randomUUID().toString().replace("-", "");

        // 设置默认文件夹名
        if (fileName.equals("")) {
            fileName = "未命名文件夹";
        }

        // 构建父路径
        String filePid = (currentDir.equals("")) ? (email + currentDir) : (email + "/" + currentDir);

        // 构建文件存储路径（前端访问路径）
        String filePath = filePid + "/" + fileName;

        // 获取创建时间和更新时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(new Date());
        String updateTime = createTime;

        // 设置文件夹类型、状态
        int folderType = 1;          // 文件/目录
        String status = "1";   // 状态正常

        // 数据封装
        Files files = new Files();
        files.setFileId(fileId);
        files.setFileName(fileName);
        files.setEmail(email);
        files.setFilePid(filePid);
        files.setFilePath(filePath);
        files.setCreateTime(createTime);
        files.setUpdateTime(updateTime);
        files.setFolderType(folderType);
        files.setStatus(status);

        // 调用服务创建新文件夹
        return indexFileService.createNewDir(files);

    }


    /**
     * 通过POST请求获取指定目录下的所有文件列表，支持分页查询。
     *
     * @param jsonObject 包含请求参数的 JSON 对象，包括 email、absolutePath、currentPage 和 pageSize
     * @return 响应结果，包含文件列表和总数
     */
    @PostMapping("allFiles")
    public ResponseResult getAllFiles(@RequestBody JSONObject jsonObject) {
        // 从 JSON 对象中解析参数
        String email = jsonObject.getString("email");
        String absolutePath = jsonObject.getString("absolutePath");  // 访问全路径
        int currentPage = Integer.parseInt(jsonObject.getString("currentPage"));  // 当前页数
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));   // 页面大小

        // 调用 indexFileService 的 getAllFiles 方法获取文件列表
        return indexFileService.getAllFiles(new Page(currentPage, pageSize), email, absolutePath);
    }

    /**
     * 上传文件
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("uploadFile")
    public ResponseResult uploadNewFile(HttpServletRequest request) {
        //解析必要参数
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        //获取文件
        List<MultipartFile> filesList = mulRequest.getFiles("file");
        MultipartFile multipartFile = filesList.get(0);  //目前仅支持上传单个文件

        //数据封装
        String fileId = UUID.randomUUID().toString().replace("-", "");  //文件id
        String email = mulRequest.getParameter("email");   //邮箱
        String fileName = multipartFile.getOriginalFilename();   //文件名
        String currentDir = mulRequest.getParameter("currentDir");  //当前目录
        String filePid;   //父目录
        if (currentDir.equals("")) {
            filePid = email;   //父目录
        } else {
            filePid = email + "/" + currentDir;   //父目录
        }
        String filePath = filePid + "/" + fileName;      //文件存储路径（前端访问路径）
        String fileSize = formatFileSize(multipartFile.getSize());   //文件大小
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(new Date());   //创建时间
        String updateTime = dateFormat.format(new Date());  //最后更新时间
        int folderType = 0;          //文件/目录
        //判断文件类型
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String fileCategory = categoryFile(suffix);
        String status = "1";   //状态正常

        Files files = new Files();
        files.setFileId(fileId);
        files.setFileName(fileName);
        files.setEmail(email);
        files.setFilePid(filePid);
        files.setFilePath(filePath);
        files.setFileSize(fileSize);
        files.setCreateTime(createTime);
        files.setUpdateTime(updateTime);
        files.setFolderType(folderType);
        files.setFileCategory(fileCategory);
        files.setStatus(status);

        return indexFileService.uploadNewFile(files, multipartFile);


    }


    /**
     * 判断文件类型
     *
     * @param suffix
     * @return
     */
    public String categoryFile(String suffix) {
        //视频
        List<String> videoList = new ArrayList<>();
        videoList.add(".mp4");
        videoList.add(".MP4");
        videoList.add(".avi");
        videoList.add(".AVI");
        videoList.add(".mov");
        videoList.add(".MOV");
        videoList.add(".wmv");
        videoList.add(".WMV");
        videoList.add(".flv");
        videoList.add(".FLV");
        videoList.add(".mkv");
        videoList.add(".MKV");
        videoList.add(".webm");
        videoList.add(".WEBM");
        videoList.add(".3gp");
        videoList.add(".3GP");
        //音频
        List<String> audioList = new ArrayList<>();
        audioList.add(".mp3");
        audioList.add(".MP3");
        audioList.add(".wav");
        audioList.add(".WAV");
        audioList.add(".flac");
        audioList.add(".FLAC");
        audioList.add(".aac");
        audioList.add(".AAC");
        audioList.add(".ogg");
        audioList.add(".OGG");
        audioList.add(".wma");
        audioList.add(".WMA");
        audioList.add(".m4a");
        audioList.add(".M4A");
        audioList.add(".ape");
        audioList.add(".APE");
        audioList.add(".amr");
        audioList.add(".AMR");
        //图片
        List<String> imageList = new ArrayList<>();
        imageList.add(".jpg");
        imageList.add(".JPG");
        imageList.add(".png");
        imageList.add(".PNG");
        imageList.add(".jpeg");
        imageList.add(".JPEG");
        imageList.add(".gif");
        imageList.add(".GIF");
        imageList.add(".bmp");
        imageList.add(".BMP");
        imageList.add(".webp");
        imageList.add(".WEBP");
        imageList.add(".svg");
        imageList.add(".SVG");
        imageList.add(".ico");
        imageList.add(".ICO");
        imageList.add(".psd");
        imageList.add(".PSD");
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

        if (videoList.contains(suffix)) {  //视频
            return "0";
        } else if (audioList.contains(suffix)) {  //音频
            return "1";
        } else if (imageList.contains(suffix)) {   //图片
            return "2";
        } else if (suffix.equals(".pdf")) {
            return "pdf";
        } else if (wordList.contains(suffix)) {
            return "4";
        } else if (excelList.contains(suffix)) {
            return "5";
        } else if (suffix.equals(".txt")) {
            return "6";
        } else if (codeList.contains(suffix)) {
            return "7";
        } else if (compressionList.contains(suffix)) {  //压缩包
            return "8";
        } else {   //其他
            return "9";
        }
    }

    /**
     * 将字节转为KB、MB 或 GB
     *
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
