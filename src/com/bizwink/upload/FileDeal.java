package com.bizwink.upload;

/**
 * tool of file and directory operation
 * @author Sunny Peng
 */

import java.io.*;

public class FileDeal
{
    /**
     *   This class moves an input file to output file
     *
     *   @param:String input file to move from
     *   @param:String output file
     *
     */
    public static void move (String input, String output) throws Exception
    {
        File inputFile = new File(input);
        int posi = output.lastIndexOf(java.io.File.separator);
        String tDir = output.substring(0,posi + 1);
        File tFileDir = new File(tDir);
        if (!tFileDir.exists()) tFileDir.mkdirs();

        try
        {
            File outputFile = new File(output);
            inputFile.renameTo(outputFile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     *  This class copies an input file to output file
     *
     *  @param:String input file to copy from
     *  @param:String output file
     */
    public static boolean copy(String input, String output,int delflag) throws Exception
    {
        int BUFSIZE = 65536;
        int posi = output.lastIndexOf(java.io.File.separator);
        String tDir = output.substring(0,posi);
        File tFileDir = new File(tDir);
        if (!tFileDir.exists()) tFileDir.mkdirs();
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);

        try {
            int s;
            byte[] buf = new byte[BUFSIZE];
            while ((s = fis.read(buf)) > -1 )
            {
                fos.write(buf, 0, s);
            }
        } catch (Exception ex){
            throw new Exception("makehome"+ex.getMessage());
        } finally {
            fis.close();
            fos.close();
            if (delflag == 1)
            {
                File sFile = new File(input);
                sFile.delete();
            }
        }
        return true;
    }

    public static void deleteFile(String filename) {
        File sFile = new File(filename);
        sFile.delete();
    }

    public static void makehome(String home) throws Exception
    {
        File homedir = new File(home);
        if (!homedir.exists())
        {
            try
            {
                homedir.mkdirs();
            }
            catch (Exception ex)
            {
                throw new Exception("Can not mkdir :"+ home+" Maybe include special charactor!");
            }
        }
    }

    /**
     *  This class copies an input files of a directory to another directory not include subdir
     *
     *  @param:String sourcedir   the directory to copy from such as:/home/bqlr/images
     *  @param:String destdir      the target directory
     */
    public static void CopyDir(String sourcedir,String destdir) throws Exception
    {
        File dest = new File(destdir);
        File source = new File(sourcedir);

        String [] files= source.list();
        try
        {
            makehome(destdir);
        }
        catch (Exception ex)
        {
            throw new Exception("CopyDir:"+ex.getMessage());
        }

        for (int i = 0; i < files.length; i++)
        {
            String sourcefile = source+File.separator+files[i];
            String  destfile = dest+File.separator+files[i];
            File temp = new File(sourcefile);
            if (temp.isFile())
            {
                try
                {
                    copy(sourcefile,destfile,1);
                }
                catch (Exception ex)
                {
                    throw new Exception("CopyDir:"+ex.getMessage());
                }
            }
        }
    }

    //delflag=0 不删除  delflag=1删除
    public static void CopyDir(String sourcedir,String destdir,int delflag) throws Exception
    {
        File dest = new File(destdir);
        File source = new File(sourcedir);

        String [] files= source.list();
        try
        {
            makehome(destdir);
        }
        catch (Exception ex)
        {
            throw new Exception("CopyDir:"+ex.getMessage());
        }

        for (int i = 0; i < files.length; i++)
        {
            String sourcefile = source+File.separator+files[i];
            String  destfile = dest+File.separator+files[i];
            File temp = new File(sourcefile);
            if (temp.isFile())
            {
                try
                {
                    copy(sourcefile, destfile, delflag);
                }
                catch (Exception ex)
                {
                    throw new Exception("CopyDir:"+ex.getMessage());
                }
            }
        }
    }

    /**
     *  This class del a directory recursively,that means delete all files and directorys.
     *
     *  @param:File directory      the directory that will be deleted.
     */
    public static void recursiveRemoveDir(File directory) throws Exception
    {
        if (!directory.exists())
        {
            throw new IOException(directory.toString()+" do not exist!");
        }

        String[] filelist = directory.list();
        File tmpFile = null;
        for (int i = 0; i < filelist.length; i++)
        {
            tmpFile = new File(directory.getAbsolutePath(),filelist[i]);
            if (tmpFile.isDirectory())
            {
                recursiveRemoveDir(tmpFile);
            }
            else if (tmpFile.isFile())
            {
                try
                {
                    tmpFile.delete();
                }
                catch (Exception ex)
                {
                    throw new Exception(tmpFile.toString()+" can not be deleted "+ex.getMessage());
                }
            }
        }

        try
        {
            directory.delete();
        }
        catch (Exception ex)
        {
            throw new Exception(directory.toString()+" can not be deleted "+ex.getMessage());
        }
        finally
        {
            filelist = null;
        }
    }
}