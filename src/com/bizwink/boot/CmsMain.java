package com.bizwink.boot;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.bizwink.boot.util.*;

/**
 * This class is a commadnlineinterface for the opencms. It can be used to test
 * the opencms, and for the initial setup. It uses the OpenCms-Object.
 *
 * @author Andreas Schouten
 * @author Anders Fugmann
 * @version $Revision: 1.1.1.1 $ $Date: 2006/07/18 08:31:16 $
 */
public class CmsMain {


    private static final int C_MODE_ECMASCRIPT = 1;
    private static final int C_MODE_CLASSIC = 0;

    /**
     * Main entry point when started via the command line.
     *
     * @param args Array of parameters passed to the application
     * via the command line.
     */
    public static void main(String[] args) {
        boolean wrongUsage = false;

        String base = null;
        String script = null;
        String cmdLineMode = null;

        int mode = C_MODE_CLASSIC;

        if(args.length > 2) {

            wrongUsage = true;
        } else {
            for(int i=0; i < args.length; i++) {
                String arg = args[i];
                if(arg.startsWith("-base=") ) {
                    base = arg.substring(6);
                } else if(arg.startsWith("-script=") ) {
                    script = arg.substring(8);
                } else if(arg.startsWith("-mode=") ) {
                    cmdLineMode = arg.substring(6);
                } else {
                    System.out.println("wrong usage!");
                    wrongUsage = true;
                }
            }
        }
        if(wrongUsage) {
            usage();
        } else {
            FileInputStream stream = null;
            if(script != null) {
                try {
                    stream = new FileInputStream(script);
                } catch (IOException exc) {
                    System.out.println("wrong script-file " + script + " using stdin instead");
                }
            }
            if(cmdLineMode!=null){
                if(cmdLineMode.equals("ecmascript"))mode=C_MODE_ECMASCRIPT;
                if(cmdLineMode.equals("es"))mode=C_MODE_ECMASCRIPT;
                if(cmdLineMode.equals("classic"))mode=C_MODE_CLASSIC;
            }

            if(stream == null) {
                // no script-file use input-stream
                stream = new FileInputStream(FileDescriptor.in);
            }

            begin(stream, base, mode);
        }
    }

    /**
     * Main entry point when started via the OpenCms setup wizard.
     *
     * @param file file containing the setup commands (cmssetup.txt)
     * @param base OpenCms base folder
     */
    public static void startSetup(String file, String base)  {
        try {
            begin(new FileInputStream(new File(file)),base,C_MODE_CLASSIC);
        }
        catch (FileNotFoundException  e)  {
            e.printStackTrace();
        }
    }

    /**
     * Used to launch the OpenCms command line interface (CmsShell)
     */
    private static void begin(FileInputStream fis, String base, int mode)  {
        String classname = "com.opencms.core.CmsShell";
        if(base == null || "".equals(base)) {
            System.out.println("No OpenCms home folder given. Trying to guess...");
            base = searchBaseFolder(System.getProperty("user.dir"));
            if(base == null || "".equals(base)) {
                System.err.println("-----------------------------------------------------------------------");
                System.err.println("OpenCms base folder could not be guessed.");
                System.err.println("");
                System.err.println("Please start the OpenCms command line interface from the directory");
                System.err.println("containing the \"opencms.properties\" and the \"oclib\" folder or pass the");
                System.err.println("OpenCms home folder as argument.");
                System.err.println("-----------------------------------------------------------------------");
                return;
            }
        }
        base = CmsBase.setBasePath(base);
        try {

            Class c = Class.forName(classname);
            // Now we have to look for the constructor
            Object o = c.newInstance();

            Class classArgs[] = {fis.getClass()};

            // the "classic" mode
            if(mode==C_MODE_CLASSIC){
                Method m = c.getMethod("commands", classArgs);

                Object objArgs[] = {fis};
                m.invoke(o, objArgs);
            }

            // the "ecmascript" mode
            if(mode==C_MODE_ECMASCRIPT){
                Method m = c.getMethod("ecmacommands", classArgs);

                Object objArgs[] = {fis};
                m.invoke(o, objArgs);
            }
        } catch(InvocationTargetException e) {
            Throwable t = e.getTargetException();
            t.printStackTrace();
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }


    public static String searchBaseFolder(String startFolder) {

        File currentDir = null;
        String base = null;
        File father = null;
        File grandFather = null;

        // Get a file obkect of the current folder
        if(startFolder != null && !"".equals(startFolder)) {
            currentDir = new File(startFolder);
        }

        // Get father and grand father
        if(currentDir != null && currentDir.exists()) {
            father = currentDir.getParentFile();
        }
        if(father != null && father.exists()) {
            grandFather = father.getParentFile();
        }

        if(currentDir != null) {
            base = downSearchBaseFolder(currentDir);
        }
        if(base == null && grandFather != null) {
            base = downSearchBaseFolder(grandFather);
        }
        if(base == null && father != null) {
            base = downSearchBaseFolder(father);
        }
        return base;
    }

    private static String downSearchBaseFolder(File currentDir) {
        if(isBaseFolder(currentDir)) {
            return currentDir.getAbsolutePath();
        } else {
            if(currentDir.exists() && currentDir.isDirectory()) {
                File webinfDir = new File(currentDir, "WEB-INF");
                if(isBaseFolder(webinfDir)) {
                    return webinfDir.getAbsolutePath();
                }
            }
        }
        return null;
    }

    private static boolean isBaseFolder(File currentDir) {
        if(currentDir.exists() && currentDir.isDirectory()) {
            File f1 = new File(currentDir.getAbsolutePath() + File.separator + CmsBase.getPropertiesPath(false));
            File f2 = new File(currentDir, "ocsetup");
            return (f1.exists() && f1.isFile() && f2.exists() && f2.isDirectory());
        }
        return false;
    }

    /**
     * Gives the usage-information to the user.
     */
    private static void usage() {
        System.out.println("Usage: java com.opencms.core.CmsMain [-base=<basepath>] [-script=<scriptfile>] [-mode=[<ecmascript><es>/<classic>]]");
    }
}