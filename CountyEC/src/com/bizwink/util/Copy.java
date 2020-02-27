package com.bizwink.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Copy {
    public int copyMoreFiles(List sourceFiles, String destRootDir) {
        if (sourceFiles == null) {
            return -1;
        }
        int fileNum = sourceFiles.size();
        if (fileNum < 1) {
            return -1;
        }
        int retCode = 0;
        for (int i = 0; i < fileNum; i++) {
            String temp = (String) sourceFiles.get(i);
            String filePath = temp.substring(0, temp.lastIndexOf(","));
            String fileDir = temp.substring(temp.lastIndexOf(",") + 1);
            retCode = copyFile(filePath, destRootDir, fileDir);
        }
        return retCode;
    }

    public int copyFile(String sourceFile, String destRootDir, String fileDir) {
        destRootDir = StringUtil.replace(destRootDir, "/", File.separator);
        destRootDir = StringUtil.replace(destRootDir, "\\", File.separator);
        fileDir = StringUtil.replace(fileDir, "/", File.separator);
        fileDir = StringUtil.replace(fileDir, "\\", File.separator);
        sourceFile = StringUtil.replace(sourceFile, "/", File.separator);
        sourceFile = StringUtil.replace(sourceFile, "\\", File.separator);

        File pubFile = new File(sourceFile);
        if (!pubFile.exists()) {
            return -1;
        }

        int pos = destRootDir.lastIndexOf(File.separator);
        if (pos == destRootDir.length()) destRootDir = destRootDir.substring(0, pos - 1);
        File dir = new File(destRootDir + fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileInputStream source = null;
        FileOutputStream destination = null;

        //从包含完整路径的源文件名中分离出文件名
        int posi = sourceFile.lastIndexOf(File.separator);
        String filename = sourceFile.substring(posi + 1);
        try {
            source = new FileInputStream(sourceFile);
            destination = new FileOutputStream(destRootDir + fileDir + filename);
            byte[] buffer = new byte[1024];
            int bytes_read;

            while (true) {
                bytes_read = source.read(buffer);
                if (bytes_read == -1) break;
                destination.write(buffer, 0, bytes_read);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex);
            return -40;
        } finally {
            if (source != null)
                try {
                    source.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (destination != null)
                try {
                    destination.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return 0;
    }

    public int copyMoreFiles(List sourceFiles, String fileDir, String destRootDir) {
        if (sourceFiles == null) {
            return -1;
        }
        int fileNum = sourceFiles.size();
        if (fileNum < 1) {
            return -1;
        }
        int retCode = 0;
        for (int i = 0; i < fileNum; i++) {
            String sfilename = (String) sourceFiles.get(i);
            retCode = copyFile(sfilename, destRootDir, fileDir);
        }
        return retCode;
    }
}