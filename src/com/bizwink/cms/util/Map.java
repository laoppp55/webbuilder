package com.bizwink.cms.util;

/**
 * Created by IntelliJ IDEA.
 * User: EricDu
 * Date: 2006-9-29
 * Time: 16:00:02
 */
public class Map {

    /**
     * 根据文件扩展名，获得显示的图片名
     * @param ext 文件扩展名
     * @return 对应的图片名
     */
    public String getFileType(String ext) {
        String pictype = "";

        if (ext != null) {
            if (ext.equalsIgnoreCase("pdf"))
                pictype = "pdf.gif";
            else if (ext.equalsIgnoreCase("doc"))
                pictype = "word.gif";
            else if (ext.equalsIgnoreCase("xls"))
                pictype = "excel.gif";
            else if (ext.equalsIgnoreCase("zip") || ext.equalsIgnoreCase("rar") || ext.equalsIgnoreCase("tar"))
                pictype = "zip.gif";
            else if (ext.equalsIgnoreCase("gif"))
                pictype = "gif.gif";
            else if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))
                pictype = "jpg.gif";
            else if (ext.equalsIgnoreCase("html") || ext.equalsIgnoreCase("htm") || ext.equalsIgnoreCase("shtml") ||
                    ext.equalsIgnoreCase("jsp") || ext.equalsIgnoreCase("asp") || ext.equalsIgnoreCase("php") ||
                    ext.equalsIgnoreCase("aspx"))
                pictype = "htm.gif";
            else if (ext.equalsIgnoreCase("ppt"))
                pictype = "ppt.gif";
            else if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("bmp"))
                pictype = "tu.gif";
            else pictype = "wen.gif";
        } else {
            pictype = "wen.gif";
        }
        return pictype;
    }
}
