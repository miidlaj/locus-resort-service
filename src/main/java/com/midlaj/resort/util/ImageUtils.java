package com.midlaj.resort.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ImageUtils {

    public static byte[] compressImage(MultipartFile image, String imageFormat) throws IOException
    {

        InputStream inputStream = image.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        float imageQuality = 0.3f;

        // Create the buffered image
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        // Get image writers
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(imageFormat); // Input your Format Name here


        if (!imageWriters.hasNext())
            throw new IllegalStateException("Writers Not Found!!");

        ImageWriter imageWriter = imageWriters.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

        // Set the compress quality metrics
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(imageQuality);

        // Compress and insert the image into the byte array.
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

        byte[] imageBytes = outputStream.toByteArray();

        // close all streams
        inputStream.close();
        outputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();


        return imageBytes;
    }
}
