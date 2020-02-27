package com.bizwink.FileUpload;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Created by petersong on 16-7-15.
 */
public class SingleFileUpload extends FileUploadBase  {
    private FileItem fileItem;

    /**
     *
     * @param request
     * @throws UnsupportedEncodingException
     */
    public void parseRequest(HttpServletRequest request) throws UnsupportedEncodingException {

        DiskFileItemFactory factory =new DiskFileItemFactory();

        factory.setSizeThreshold(sizeThreshold);

        if (repository !=null)
            factory.setRepository(repository);

        ServletFileUpload upload =new ServletFileUpload(factory);

        upload.setHeaderEncoding(encoding);

        try{
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String value = item.getString(encoding);
                    parameters.put(fieldName, value);
                }else{
                    if (!super.isValidFile(item)) {
                        continue;
                    }
                    if (fileItem ==null)  fileItem = item;
                }
            }

        }catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件, 调用该方法之前必须先调用 parseRequest(HttpServletRequest request)
     * @param fileName 完整文件路径
     * @throws Exception
     */
    public void upload(String fileName) throws Exception {
        File file =new File(fileName);
        uploadFile(file);
    }

    /**
     * 上传文件, 调用该方法之前必须先调用 parseRequest(HttpServletRequest request)
     * @param parent 存储的目录
     * @throws Exception
     */
    public void upload(File parent) throws Exception {
        if (fileItem ==null) return;

        String name = fileItem.getName();
        File file =new File(parent, name);
        uploadFile(file);
    }

    private void uploadFile(File file) throws Exception{
        if (fileItem ==null) return;

        long fileSize = fileItem.getSize();
        if (sizeMax >-1&& fileSize >super.sizeMax){
            String message = String.format("the request was rejected because its size (%1$s) exceeds the configured maximum (%2$s)", fileSize, super.sizeMax);
            throw new org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException(message, fileSize, super.sizeMax);
        }

        String name = fileItem.getName();
        fileItem.write(file);
    }

    /**
     * 获取文件信息
     * 必须先调用 parseRequest(HttpServletRequest request)
     * @return
     */
    public FileItem getFileItem() {
        return fileItem;
    }
}
