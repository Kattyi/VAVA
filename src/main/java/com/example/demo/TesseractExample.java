package com.example.demo;

import java.io.File;
import net.sourceforge.tess4j.*;

public class TesseractExample {

    public String converter(String path) {

        File imageFile = new File(path);

        ITesseract instance = new Tesseract();

        instance.setDatapath(".");

        try {
            String result = instance.doOCR(imageFile);
            return result;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

//    public static void main(String[] args) {
//
//        System.out.println("hello");
//
//        File imageFile = new File("/Users/KatCake/Desktop/demo/upload-dir/test.png");
//        /**
//         * JNA Interface Mapping
//         **/
//        ITesseract instance = new Tesseract();
//
//        /**
//         * You either set your own tessdata folder with your custom language pack or
//         * use LoadLibs to load the default tessdata folder for you.
//         **/
//        instance.setDatapath(".");
//
//        try {
//            String result = instance.doOCR(imageFile);
//            System.out.println(result);
//        } catch (TesseractException e) {
//            System.err.println(e.getMessage());
//        }
//    }
}