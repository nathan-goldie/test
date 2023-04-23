/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zipeg;

import java.io.File;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;



/**
 *
 * @author pedro
 */

 

public class ZipOutputStreamExample {
    public static void main(String[] args) throws ZipException 
    {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setCompressionLevel(CompressionLevel.MAXIMUM);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        try{
            ZipFile zipFile = new ZipFile("/home/ntu-user/filename.zip", "password".toCharArray());
            zipFile.addFolder(new File("/home/ntu-user/folder_to_add"), zipParameters);
            zipFile.addFile(new File("/home/ntu-user/folder_to_add/aFile.txt"), zipParameters);
            zipFile.addFile(new File("/home/ntu-user/folder_to_add/bFile.txt"), zipParameters);
            System.out.println("File zipped with success");
        }
        catch(Exception e) 
        {
            e.printStackTrace();
            
        }
        try{
            ZipFile zipFile = new ZipFile("/home/ntu-user/filename.zip", "password".toCharArray());
            zipFile.extractAll("/home/ntu-user/destination_directory");
            System.out.println("File unzipped with success");
        }
        catch(Exception e) 
        {
            e.printStackTrace();
            
        }
            
    }
}
