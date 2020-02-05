package com.bizwink.images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//import com.asprise.util.ocr.OCR;

public class PictureRecognition {

    public static void main(String[] args) throws IOException {
        if(("1.4").compareTo(System.getProperty("java.vm.version")) > 0) {
            System.err.println("Warining: \n\nYou need Java version 1.4 or above for ImageIO to run this demo.");
            System.err.println("Your current Java version is: " + System.getProperty("java.vm.version"));
            System.err.println("\nSolutions: \n");
            System.err.println("(1) Download JRE/JDK version 1.4 or above; OR \n");
            System.err.println("(2) Run DemoUI, which can run on your current Java virtual machine.");
            System.err.println("    Double click the 'runDemoUI' to invoke it.\n");
            return;
        }

/*        System.out.println("Welcome to Asprise OCR v4.0 Demo!\n");
        if(args.length < 1) {
            System.err.println("Usage: java Demo PATH_TO_IMAGE [Description]");
            return;
        }*/

        if(args.length >= 2) {
            System.out.println("-----------------------------------------------------------");
            for(int i=1; i<args.length; i++)
                System.out.println(args[i]);
            System.out.println("-----------------------------------------------------------\n");
        }

        //File file = new File(args[0]);
        System.out.println( "hello word" +System.getProperty("java.library.path"));
        File file = new File("E:\\projects\\Asprise-OCR-Java-Windows_XP_32bit-4.0\\11.png");

        System.out.println("Trying to perform OCR on image: " + file.getAbsolutePath());

        //OCR.setLibraryPath("E:/projects/Asprise-OCR-Java-Windows_XP_32bit-4.0/AspriseOCR.dll");


        BufferedImage image = ImageIO.read(file);

  //      String s = new OCR().recognizeEverything(image);
//        System.out.println("\n---- RESULTS: ------- \n" + s);
    }
}
