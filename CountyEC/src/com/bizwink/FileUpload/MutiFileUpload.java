package com.bizwink.FileUpload;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Created by petersong on 16-7-15.
 */
public class MutiFileUpload  extends FileUploadBase {
    private Map<String, FileItem> files;// 保存上传的文件
    private long filesSize =0; // 所有文件的总大小


    public void parseRequest(HttpServletRequest request)
            throws UnsupportedEncodingException {

        files =new HashMap<String, FileItem>();

        // Create a factory for disk-based file items

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
                    if (super.isValidFile(item)) {
                        continue;
                    }
                    String fieldName = item.getFieldName();
                    files.put(fieldName, item);
                    filesSize += item.getSize();
                }
            }
        }catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件, 调用该方法之前必须先调用 parseRequest(HttpServletRequest request)
     * @param parent 文件存储的目录
     * @throws Exception
     */
    public void upload(File parent) throws Exception {
        if (files.isEmpty())
            return;

        if (sizeMax >-1&& filesSize >super.sizeMax){
            String message = String.format("the request was rejected because its size (%1$s) exceeds the configured maximum (%2$s)", filesSize, super.sizeMax);

            throw new SizeLimitExceededException(message, filesSize, super.sizeMax);
        }

        for (String key : files.keySet()) {
            FileItem item = files.get(key);
            String name = item.getName();

            File file =new File(parent, name);
            item.write(file);
        }
    }

    public Map<String, FileItem> getFiles() {
        return files;
    }
}
