import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "access.log";  // путь к файлу.
        int totalLines = 0;         // общее количество строк
        int minLength = Integer.MAX_VALUE;  // длина самой короткой строки
        int maxLength = 0;         // длина самой длинной строки
        File file = new File(path);

        try (FileReader fileReader = new FileReader(path);
             BufferedReader reader = new BufferedReader(fileReader)) {
            if (!file.exists()) {
                throw new RuntimeException("Файл не существует: " + path);
            }
            if (!file.isFile()) {
                throw new RuntimeException("Путь ведет не к файлу: " + path);
            }
            String line;
            while ((line = reader.readLine()) != null) {
                int length = line.length();

                if (length > 1024) { // Проверка на слишком длинную строку
                    throw new LineTooLongException("Файл содержит строку с количеством символов " + length + " (количество превышает максимально допустимые 1024 символа). " + "Строка #" + (totalLines + 1));
                }
                totalLines++;

                if (length > maxLength) {
                    maxLength = length;
                }
                if (length < minLength) {
                    minLength = length;
                }
            }
            if (totalLines == 0) {
                minLength = 0;
            }

            System.out.println("Общее количество строк в файле: " + totalLines);
            System.out.println("Длина самой короткой строки: " + minLength);
            System.out.println("Длина самой длинной строки: " + maxLength);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}