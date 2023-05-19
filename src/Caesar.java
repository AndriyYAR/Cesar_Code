import java.util.ArrayList;

public class Caesar {




    public ArrayList<Character> GetEncryptedArrayList(String text, int cesarKey){
        //спочатку перевіримо чи не перевищує наш ключ розміру алфавіту по якому будемо проходитись
        //якщо більше то зменшужмо його до тих пір, поки він не стане менше
        while(cesarKey>=Constants.ALPHABET_UA.size()){
            cesarKey = cesarKey-Constants.ALPHABET_UA.size();
        }
        char[] textCharArr = text.toCharArray();//конвертуємо наш текст у масив чарів
        //створюємо новий масив у який будемо записувати наш зашифрований текст
        ArrayList<Character> textList = new ArrayList<>();

        int arrLen = Constants.ALPHABET_UA.size();
        int indexLetter;
        int keyIndex;

        for (int i = 0; i < textCharArr.length ; i++) {
            //додає до зашиф. масиву символ, якого немає в алфавіті, у незмінному вигляді
            if(!Constants.ALPHABET_UA.contains(textCharArr[i])){
                textList.add(i,textCharArr[i]);
                continue;
            }
            indexLetter = Constants.ALPHABET_UA.indexOf(textCharArr[i]);
            keyIndex = indexLetter+cesarKey;
            //перевірка чи не перевищує індекс добуваємого символа загальної к-сті символів у алфавіті
            //якщо перевищує то зменшуємо наш ключовий індекс на розмір алфавіту
            if(keyIndex>=arrLen){
                keyIndex = keyIndex-arrLen;
            }
            //до нового масиву додаємо по одному символи, які зміщені на ключ
            textList.add(i,Constants.ALPHABET_UA.get(keyIndex));
        }
        return textList;
    }
    public String GetEncryptedString(String text, int cesarKey){
        //цей метод конвертує отриманий масив з попереднього метода у String за допомогою стрінгбілдера, додаючи по одному символу
        Caesar enc = new Caesar();
        ArrayList<Character> arrNew = enc.GetEncryptedArrayList(text,cesarKey);
        StringBuilder builder = new StringBuilder();
        for(Character oneChar:arrNew){
            builder.append(oneChar);
        }

        return builder.toString();
    }

    public  ArrayList<Character> GetDecryptedArrayList(String text,int cesarKey){
        //цей метод працює аналогічно GetEncryptedArrayList але у зворотньому напрямку
        //логіка обробки ключа і ключового індекса аналогічна, але зворотня
        while(cesarKey>=Constants.ALPHABET_UA.size()){
            cesarKey = cesarKey-Constants.ALPHABET_UA.size();
        }
        char[] textCharArr = text.toCharArray();
        ArrayList<Character> result = new ArrayList<>(text.length());
        int indexLetter;
        int keyIndex;
        int arrLen = Constants.ALPHABET_UA.size();

        for (int i = 0; i < textCharArr.length; i++) {
            if(!Constants.ALPHABET_UA.contains(textCharArr[i])){
                result.add(i,textCharArr[i]);
                continue;
            }
            indexLetter = Constants.ALPHABET_UA.indexOf(textCharArr[i]);
            keyIndex = indexLetter-cesarKey;
            if(keyIndex<0){
                keyIndex = arrLen - (cesarKey-indexLetter);
            }
            result.add(i,Constants.ALPHABET_UA.get(keyIndex));
        }
        return result;
    }

    public String GetDecryptedString(String text, int cesarKey){
        //цей метод конвертує отриманий масив з попереднього метода у String за допомогою стрінгбілдера, додаючи по одному символу
        Caesar dec = new Caesar();
        ArrayList<Character> arrNew = dec.GetDecryptedArrayList(text,cesarKey);
        StringBuilder builder = new StringBuilder();
        for(Character oneChar:arrNew){
            builder.append(oneChar);
        }

        return builder.toString();
    }

    public int GetBruteForceKey(String text){
        int key = 0;
        Caesar caesar = new Caesar();
        //циклом фор проходимось по розшифрованим текстам, змінюючи ключ від 1 до довжини нашого алфавіту (всі можливі варіанти ключа)
        for (int i = 1; i < Constants.ALPHABET_UA.size(); i++) {
            String decryptedText = caesar.GetDecryptedString(text,i);
           int indexSymbol = decryptedText.indexOf(",");//у розшифрованому тексті шукаємо індекс коми
           if(indexSymbol>0){
              String nextSymbol = decryptedText.substring(indexSymbol+1,indexSymbol+2);//вирізаємо наступний символ за комою
              if(nextSymbol.equals(" ")){//перевіряємо чи цей символ відповідає пробілу, якщо це пробіл то ми знайшли необхідний ключ
                  key = i;
                  System.out.println("Шукаємий ключ:" + key);
              }
           }
        }
        return key;//метод повертає лише значення ключа
    }
}




