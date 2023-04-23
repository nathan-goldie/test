import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class FileManager {
   
    public static void main(String[] args) {
        // create a file
        File file = new File("example.txt");
        try {
            file.createNewFile();
            System.out.println("File created successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
       
        // add content to file and save
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("Hello, world!");
            System.out.println("Content added to file successfully.");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
       
        // upload file from container
        File source = new File("example.txt");
        File dest = new File("newfile.txt");
        try {
            InputStream is = new FileInputStream(source);
            OutputStream os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
            System.out.println("File uploaded successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while uploading the file.");
            e.printStackTrace();
        }
       
        // delete file
        if (file.delete()) {
            System.out.println("File deleted successfully!");
        } else {
            System.out.println("An error occurred while deleting the file.");
        }
       
        // rename file
        File newFile = new File("newname.txt");
        if (file.renameTo(newFile)) {
            System.out.println("File renamed successfully!");
        } else {
            System.out.println("An error occurred while renaming the file.");
        }
       
        // move file
        File dir = new File("newdir");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File moveFile = new File(dir.getName() + File.separator + newFile.getName());
        if (newFile.renameTo(moveFile)) {
            System.out.println("File moved successfully!");
        } else {
            System.out.println("An error occurred while moving the file.");
        }
       
        // copy file
        File copyFile = new File("copy.txt");
        try {
            FileInputStream inStream = new FileInputStream(moveFile);
            FileOutputStream outStream = new FileOutputStream(copyFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            System.out.println("File copied successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file.");
            e.printStackTrace();
        }

        // set file deletion time
        Path path = Paths.get(copyFile.getAbsolutePath());
        FileTime deletionTime = FileTime.from(Instant.now().plusSeconds(30*24*60*60)); // 30 days from now
        try {
            Files.setAttribute(path, "basic:deleteOnExit", false);
            Files.setAttribute(path, "basic:lastModifiedTime", deletionTime);
            System.out.println("File deletion time set successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while setting the file deletion time.");
            e.printStackTrace();
        }
    }
}