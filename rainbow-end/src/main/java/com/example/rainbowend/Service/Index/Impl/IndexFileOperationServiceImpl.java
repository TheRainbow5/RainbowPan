package com.example.rainbowend.Service.Index.Impl;

import com.example.rainbowend.Controller.Index.IndexFilesOperationController;
import com.example.rainbowend.Dao.Index.IndexFileOperationDao;
import com.example.rainbowend.Entity.Files;
import com.example.rainbowend.Entity.ResponseResult;
import com.example.rainbowend.Service.Index.IndexFileOperationService;
import com.example.rainbowend.Utils.FileOperationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Rainbow
 *
 * @DATE:2023/8/14 0014
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IndexFileOperationServiceImpl implements IndexFileOperationService {
    private static final Logger logger = LoggerFactory.getLogger(IndexFilesOperationController.class);
    @Value("${UserRoot}")
    private String UserRoot;
    @Resource
    private IndexFileOperationDao indexFileOperationDao;


    /**
     * 删除文件
     */
    @Override
    public ResponseResult deleteFileAndFolder(Files files) {
        String filePath = files.getFilePath();
        try {
            //1、判断文件是否存在
            Files existingFile = indexFileOperationDao.exist(files.getFileId());
            if (existingFile != null) {   //文件存在

                //2、递归删除数据
                deleteFile(filePath);
                indexFileOperationDao.delete(files);

                //3、递归删除本地文件
                if (deleteLocalFile(UserRoot + filePath)) {
                    return ResponseResult.ok("删除成功");
                }
            }
            throw new IOException("本地文件删除失败");
        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }

    }

    /**
     * 递归删除数据库中数据
     *
     * @param filePath
     */
    private void deleteFile(String filePath) {
        List<Files> subFilesList = indexFileOperationDao.getSubFiles(filePath);
        if (subFilesList != null) {
            for (Files files : subFilesList) {

//                files.setStatus();
                String subFileName = files.getFileName();
                String subFilePath = filePath + "/" + subFileName;
                if (files.getFolderType() == 1) {  //目录
                    deleteFile(subFilePath);
                }
                //删除数据
                indexFileOperationDao.delete(files);
            }
        }
    }

    /**
     * 递归删除本地文件
     *
     * @param filePath
     * @return 是否删除成功
     */
    private boolean deleteLocalFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            FileOperationUtil.deleteFolder(file);
            return true;
        }
        return false;
    }


    /**
     * 文件重命名
     *
     * @param newFileName 新的文件名（不包含后缀）
     * @param files       文件信息
     * @return 响应结果
     */
    @Override
    public ResponseResult resetFileName(String newFileName, Files files) {
        //解析参数
        String suffix = files.getFileName().substring(files.getFileName().lastIndexOf(".")); //获取文件类型后缀
        newFileName = newFileName + suffix;
        try {
            //判读文件是否存在
            Files existingFile = indexFileOperationDao.exist(files.getFileId());
            if (existingFile == null) {
                return ResponseResult.error("文件不存在");
            }
            //判断新文件名称是否存在
            Files existNewFile = indexFileOperationDao.existFile(files.getFilePid(), newFileName);
            if (existNewFile != null) {
                return ResponseResult.error("该文件已经存在");
            }
            //数据格式化
            //新文件名称
            String newFilePath = files.getFilePid() + "/" + newFileName;
            //修改文件名
            int updateResult = indexFileOperationDao.resetFileName(files.getFileId(), newFileName, newFilePath);
            if (updateResult <= 0) {
                return ResponseResult.error("文件重命名失败");
            }
            //修改本地文件名
            String filePid = files.getFilePid();  //当前文件夹
            String oldFilePath = files.getFilePath();   //获取全路径
            File oldFile = new File(UserRoot, oldFilePath);
            File newFile = new File(UserRoot + filePid, newFileName);
            // 执行文件重命名
            if (oldFile.exists() && !newFile.exists()) {
                if (oldFile.renameTo(newFile)) {
                    return ResponseResult.ok("文件重命名成功");
                }
            }
            throw new Exception("文件重命名失败");
        } catch (FileNotFoundException e) {
            return ResponseResult.error("文件不存在");
        } catch (IOException e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("文件操作失败，请联系管理员");
        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }

    /**
     * 文件夹重命名
     *
     * @param newFileName 新文件夹名称和
     * @param files       存储文件属性的对象
     * @return
     */
    @Override
    public ResponseResult resetFolderName(String newFileName, Files files) {
        try {
            //判读文件夹是否存在
            Files existingFile = indexFileOperationDao.exist(files.getFileId());
            if (existingFile == null) {
                return ResponseResult.error("文件夹不存在");
            }
            //判断新文件夹名称是否存在
            Files existNewFile = indexFileOperationDao.existFile(files.getFilePid(), newFileName);
            if (existNewFile != null) {
                return ResponseResult.error("新文件夹不存在");
            }
            /**
             * 核心算法：通过递归实现子文件的修改
             */
            String oldFilePath = files.getFilePath();   //获取旧的全路径
            String newFilePath = files.getFilePid() + "/" + newFileName;  //新的全路径

            List<Files> newFileList = new ArrayList<>();   //存储修改后的子文件对象
            resetFolder(newFileList, newFilePath, oldFilePath);  //递归修改子文件属性
            //遍历列表修改子文件数据
            for (Files item : newFileList) {
                //System.out.println(item);
                int affectedRow = indexFileOperationDao.resetsub(item);
                if (affectedRow <= 0) {
                    throw new Exception("文件修改失败");
                }
            }

            //修改当前文件夹名称
            files.setFileName(newFileName);
            files.setFilePath(files.getFilePid() + "/" + newFileName);
            int affectedRow = indexFileOperationDao.resetDir(files);
            if (affectedRow <= 0) {
                throw new Exception("文件修改失败");
            }
            //修改本地文件名
            String filePid = files.getFilePid();  //当前文件夹
            File oldFile = new File(UserRoot, oldFilePath);
            File newFile = new File(UserRoot + filePid, newFileName);

            if (oldFile.exists() && !newFile.exists()) {
                if (oldFile.renameTo(newFile)) {
                    return ResponseResult.ok("文件重命名成功");
                }
            }
            return ResponseResult.error("文件重命名失败，请联系管理员");
        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseResult.error("发生一些错误，请联系管理员");
        }
    }


    /**
     * 递归修改子文件属性
     *
     * @param newFileList 存储修改后的子文件对象
     * @param newFilePath 新文件全路径
     * @param oldFilePath 旧的全路径
     */
    private void resetFolder(List<Files> newFileList, String newFilePath, String oldFilePath) {
        //根据oldeFilePath查询下一级子文件
        List<Files> subFilesList = indexFileOperationDao.getSubFiles(oldFilePath);
        if (subFilesList != null) {
            for (Files files : subFilesList) {
                String subFileName = files.getFileName();
                String subOldFilePath = oldFilePath + "/" + subFileName;
                String subNewFilePath = newFilePath + "/" + subFileName;

                files.setFilePid(newFilePath);
                files.setFilePath(newFilePath + "/" + files.getFileName());

                if (files.getFolderType() == 1) {   //如果是目录
                    //递归
                    resetFolder(newFileList, subNewFilePath, subOldFilePath);
                }
            }
        }
        //将subList中的元素移动到newFileList
        newFileList.addAll(subFilesList);
    }


    /**
     * 下载文件夹
     *
     * @param files
     * @return
     */
    @Override
    public ResponseEntity downloadDir(Files files) {
        try {
            //判断是否存在该文件
            Files exist = indexFileOperationDao.exist(files.getFileId());
            if (exist == null) {
                return ResponseEntity.notFound().build();  //资源未找到
            }

            ByteArrayOutputStream zipBytes = new ByteArrayOutputStream();
            try (ZipOutputStream zipOut = new ZipOutputStream(zipBytes)) {
                //文件夹对象
                File folderToZip = new File(UserRoot + files.getFilePath());
                //文件压缩
                addFilesToZip(folderToZip, folderToZip.getName(), zipOut);
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipBytes.toByteArray());


        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseEntity.status(0).build();
        }
    }

    /**
     * 压缩文件夹
     *
     * @param file
     * @param parentPath
     * @param zipOut
     * @throws IOException
     */
    private void addFilesToZip(File file, String parentPath, ZipOutputStream zipOut) throws IOException {
        byte[] buffer = new byte[1024];
        if (file.isDirectory()) {
            for (File innerFile : file.listFiles()) {
                addFilesToZip(innerFile, parentPath + File.separator + file.getName(), zipOut);
            }
        } else {
            FileInputStream fis = new FileInputStream(file);
            zipOut.putNextEntry(new ZipEntry(parentPath + File.separator + file.getName()));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zipOut.write(buffer, 0, length);
            }
            fis.close();
        }
    }

    /**
     * 下载文件
     *
     * @param files
     * @return
     */
    @Override
    public ResponseEntity downloadFile(Files files) {
        try {
            //判断是否存在该文件
            Files exist = indexFileOperationDao.exist(files.getFileId());
            if (exist == null) {
                return ResponseEntity.notFound().build();  //资源未找到
            }
            //创建文件流对象
            File file = new File(UserRoot + files.getFilePath());
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            //获取文件输入流
            InputStream inputStream = new FileInputStream(file);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource);

        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseEntity.status(0).build();
        }
    }

    /**
     * 文件预览
     *
     * @param files
     * @return
     */
    @Override
    public ResponseEntity previewFile(Files files) {
        try {
            //判断是否存在该文件
            Files exist = indexFileOperationDao.exist(files.getFileId());
            if (exist == null) {
                return ResponseEntity.notFound().build();  //资源未找到
            }
            //创建文件流对象
            File file = new File(UserRoot + files.getFilePath());
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            //获取文件输入流
            InputStream inputStream = new FileInputStream(file);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource);

        } catch (Exception e) {
            handleExceptionAndRollback(e);
            return ResponseEntity.status(0).build();
        }
    }

    /**
     * 处理异常并执行事务回滚
     *
     * @param e 异常
     */
    private void handleExceptionAndRollback(Exception e) {
        logger.error(e.getMessage());
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
