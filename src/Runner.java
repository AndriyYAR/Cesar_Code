import java.io.IOException;
import java.nio.file.Paths;


public class Runner {

    public void run(String[] args) throws IOException{

        if(args.length == 3){//перевіряємо чи присутні всі три аргумента і відкриваємо цей шматок коду
            FileService fileService = new FileService();
            Caesar caesar = new Caesar();

            switch (args[0]){
                case "ENCRYPT":
                    fileService.encryptFile(args[1],Integer.parseInt(args[2]));
                    break;
                case "DECRYPT":
                    fileService.decryptFile(args[1],Integer.parseInt(args[2]));
                    break;
                case "BRUTE_FORCE": {
                    String encrText = fileService.read(Paths.get(args[1]));
                    int bfKey = caesar.GetBruteForceKey(encrText);
                    fileService.decryptFile(args[1],bfKey);
                    break;
                }

                default:
                    System.out.println("Аргументи введені невірно.");
                    CLI.openCommandLine();
            }

        }
        else CLI.openCommandLine();//якщо аргументи не введені або введені невірно відкриваємо інтерфейс взаємодії з користувачем
    }
}
