import java.io.*;

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
    }
    // share file with specific user
    public static void shareFile(File file, String user, boolean readAccess, boolean writeAccess) {
        try {
            // get the file's current ACL
            Path path = Paths.get(file.getAbsolutePath());
            AclFileAttributeView aclView = Files.getFileAttributeView(path, AclFileAttributeView.class);
            List<AclEntry> acl = aclView.getAcl();
            
            // create a new ACL entry for the user
            UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            UserPrincipal principal = lookupService.lookupPrincipalByName(user);
            AclEntry.Builder builder = AclEntry.newBuilder(principal);
            builder.setType(AclEntryType.ALLOW);
            builder.setPermissions(readAccess ? EnumSet.of(AclEntryPermission.READ) : EnumSet.noneOf(AclEntryPermission.class));
            builder.setPermissions(writeAccess ? EnumSet.of(AclEntryPermission.WRITE) : EnumSet.noneOf(AclEntryPermission.class));
            AclEntry entry = builder.build();
            
            // add the new entry to the ACL and update the file's attributes
            acl.add(entry);
            aclView.setAcl(acl);
            System.out.println("File shared with user " + user + " successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while sharing the file.");
            e.printStackTrace();
        }
    }
    
    // revoke file sharing with specific user
    public static void revokeFileSharing(File file, String user) {
        try {
            // get the file's current ACL
            Path path = Paths.get(file.getAbsolutePath());
            AclFileAttributeView aclView = Files.getFileAttributeView(path, AclFileAttributeView.class);
            List<AclEntry> acl = aclView.getAcl();
            
            // remove any existing ACL entries for the user
            UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            UserPrincipal principal = lookupService.lookupPrincipalByName(user);
            Iterator<AclEntry> iter = acl.iterator();
            while (iter.hasNext()) {
                AclEntry entry = iter.next();
                if (entry.principal().equals(principal)) {
                    iter.remove();
                }
            }
            
            // update the file's attributes
            aclView.setAcl(acl);
            System.out.println("File sharing with user " + user + " revoked successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while revoking file sharing.");
            e.printStackTrace();
        }
    }
}
