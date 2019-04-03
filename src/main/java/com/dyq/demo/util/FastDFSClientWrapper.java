package com.dyq.demo.util;

import com.github.tobato.fastdfs.domain.FileInfo;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FastDFSClientWrapper {

    @Autowired
    private FastFileStorageClient storageClient;

    public byte[] downloadFile(String fileUrl){
        String path = fileUrl.split("http://118.89.163.211:81/")[1];
        System.out.println("path:"+path);
        return storageClient.downloadFile("picture", path, new DownloadCallback<byte[]>() {
            @Override
            public byte[] recv(InputStream ins){
                byte[] b = null;
                try{
                    int len = ins.available();
                    b = new byte[len];
                    ins.read(b);
                }catch (IOException e){
                    System.out.println("下载文件异常："+e);
                }finally {
                    try{
                        ins.close();
                    }catch (IOException e){
                        System.out.println("用户取消下载，关闭流："+e);
                    }
                }
                return b;
            }
        });
    }

    public String uploadFile(MultipartFile file) throws IOException {
        //System.out.println(storageClient);
        StorePath storePath = storageClient.uploadFile((InputStream)file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return getResAccessUrl(storePath);
    }

    // 封装文件完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        //String fileUrl = "http://118.89.163.211:81" + "/" + storePath.getFullPath(); 会加上组
        String fileUrl = "http://118.89.163.211:81" + "/" + storePath.getPath();
        System.out.println("Group:"+storePath.getGroup()+",path:"+storePath.getPath());
        System.out.println("fileUrl:"+fileUrl);
        return fileUrl;
    }

    /**
     * 传图片并同时生成一个缩略图 "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     *
     * @param file
     *            文件对象
     * @return 文件访问地址
     * @throws IOException
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 删除文件
     *
     * @param fileUrl
     *            文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        System.out.println("dfs删除文件："+fileUrl);
        try {
            /*StorePath storePath = StorePath.praseFromUrl(fileUrl);
            System.out.println("storePath:"+storePath);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());*/
            String path = fileUrl.split("http://118.89.163.211:81/")[1];
            System.out.println("path:"+path);
            FileInfo fileInfo = storageClient.queryFileInfo("picture",path);
            System.out.println("fileInfo:"+fileInfo);
            storageClient.deleteFile("picture", path);
            //@param filePath 文件路径(groupName/path)
            //storageClient.deleteFile(path);
        } catch (FdfsUnsupportStorePathException e) {
            //删除失败：com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException: 解析文件路径错误,被解析路径url没有group,当前解析路径为http://118.89.163.211:81/M00/00/00/rBEABlsV_xWAc2iZAAAXaU7Mr6E826.jpg
            System.out.println("storePath異常："+e);
        }catch (Exception e){
            //删除失败：com.github.tobato.fastdfs.exception.FdfsServerException: 错误码：2，错误信息：找不到节点或文件
            System.out.println("删除失败："+e);
        }
    }
}