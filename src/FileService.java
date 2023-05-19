import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {
    public String read(Path path) throws IOException {

        return new String(Files.readAllBytes(path));
    }

    public void write(Path path, String text) throws IOException{

        Files.writeString(path,text);
    }

    public Path addFileNameAnnotation(Path filePath, String annotation){
        StringBuilder fileName  = new StringBuilder(filePath.getFileName().toString());
        Path dir = filePath.getParent();

        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
            fileName.insert(fileName.lastIndexOf("."),annotation);
            return dir.resolve(Path.of(fileName.toString()));
        }
        return null;
    }
    public void encryptFile(String path, int key) throws IOException{
        FileService fileService = new FileService();
        Caesar caesar = new Caesar();
        String text =  fileService.read(Path.of(path));
        String nText = caesar.GetEncryptedString(text,key);
        Path newPath = fileService.addFileNameAnnotation(Path.of(path),"[ENCRYPTED]");
        fileService.write(Path.of(String.valueOf(newPath)),nText);
    }

    public void decryptFile(String path, int key) throws IOException{
        FileService fileService = new FileService();
        Caesar caesar = new Caesar();
        String encText = fileService.read(Path.of(path));
        String decText = caesar.GetDecryptedString(encText,key);
        Path decPath = fileService.addFileNameAnnotation(Path.of(path),"[DECRYPTED]");
        fileService.write(decPath,decText);
    }
}
