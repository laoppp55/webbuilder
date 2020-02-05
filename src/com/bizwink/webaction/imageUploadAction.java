package com.bizwink.webaction;

import com.bizwink.util.ImageUploadUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ckeditor图片上传
 *
 * @Title imageUpload
 * @param : request
 * @param : response
 */

public class imageUploadAction {
    @RequestMapping("imageUpload")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response) {
        String DirectoryName = "upload/";
        try {
            ImageUploadUtil.ckeditor(request, response, DirectoryName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
