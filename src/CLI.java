import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class CLI {
    public static void openCommandLine() throws IOException {
        Caesar caesar = new Caesar();
        FileService fileService = new FileService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Вітаю тебе, username !\n"
                + "Ця программа дозволить зашифрувати та розшифрувати текстовий файл ключем Цезаря.\n"
                + "Щоб спробувати, введи спочатку команду ENCRYPT - щоб закодувати файл, DECRYPT щоб його розкодувати,\n" +
                "або команду BRUTE_FORCE для того, щоб розкодувати файл з невідомим ключем:");
        String action = scanner.nextLine().toLowerCase();//отримуємо перший аргумент і зберігаємо його у змінну
        String word = "";//слово, яке будемо всталяти у діалогове повідомлення для 2 аргумента
        boolean a = true;
        boolean isKey = true;
        while(a){
            //доки користувач не введе значення правильно буде відкриватись діалогове вікно (див нижче)
            //і ми не отриаємо команду у потрібному форматі
            switch (action) {
                case "encrypt" -> {
                    word = "зашифрувати";
                    a = false;
                }
                case "decrypt" -> {
                    word = "розшифрувати";
                    a = false;
                }
                case "brute_force" -> {
                    word = "взламати";
                    a = false;
                    isKey = false;
                }
                default -> {
                    System.out.println("Невірне значення! Введи уважно, так як у прикладі вище.\n");
                    action = scanner.nextLine().toLowerCase();
                }
            }
        }

        System.out.println("Тепер треба ввести абсолюний шлях до файлу, який ти хочеш " + word + ":");
        String path = scanner.nextLine();//отримуємо другий аргумент

        while(!Paths.get(path).isAbsolute()){//перевірка на правильність введення шляху
            System.out.println("Необхідно ввести абсолюний шлях до файлу, спробуй ще раз:\n");
            path = scanner.nextLine();
        }


        if(isKey){//якщо наша команда не Брутфорс, то відкривається цей код
            System.out.println("Введи ключ (тільки цілочисельне значення):\n");

            String key = scanner.nextLine();//отримуємо третій аргумент, йому ще варто пройти перевірку
            boolean b = true;
            while(b){//зациклюємо введення ключа, доки воно не буде відповідати нашому формату
            if(Integer.parseInt(key)>0){//якщо ключ відповідає нашій умові приступаємо до виклику методів
                switch (action){
                    case "encrypt" -> {fileService.encryptFile(path,Integer.parseInt(key)); b = false;}
                    case "decrypt" -> {fileService.decryptFile(path,Integer.parseInt(key)); b = false;}
                }
            }
            else {
                System.out.println("Необхідно ввести тільки цілочисельне значення:\n");
                key = scanner.nextLine();
            }}
        }
        if(!isKey){//коли вибираємо метод Брутфорс, ключ відсутній і відкриєтья цей шматок коду

            String encrText = fileService.read(Paths.get(path));
            int bfKey = caesar.GetBruteForceKey(encrText);
            fileService.decryptFile(path,bfKey);
        }


    }


}
