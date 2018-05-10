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


}