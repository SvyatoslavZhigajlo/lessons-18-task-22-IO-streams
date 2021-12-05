import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        String fis = "F:\\Dobro pozhalovat v Zombiljend.avi";

        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream("lead time.txt"))) {
//            ДЗ 22 Задание 1.b.i - Побайтовое чтение и запись напрямую из/в File[Input/Output]Stream
            Instant start = Instant.now();
            byteWriteReaderFileStream(fis);
            Instant finish = Instant.now();
            printWriter.print(Duration.between(start, finish).toMillis());

//            ДЗ 22 Задание 1.b.ii -Буферное (локальный байтовый массив на 4Кб) чтение и
//            запись напрямую из/в File[Input/Output]Stream
            Instant start1 = Instant.now();
            bufferWriteReaderFileStream(fis);
            Instant finish1 = Instant.now();
            printWriter.println(Duration.between(start1, finish1).toMillis());

//           ДЗ 22 Задание 1.b.iii - Побайтовое чтение и запись через Buffered[Input/Output]Stream
            Instant start2 = Instant.now();
            bufferedData(fis);
            Instant finish2 = Instant.now();
            printWriter.println(Duration.between(start2, finish2).toMillis());

//           ДЗ 22 Задание 1.b.iv - Буферное (локальный байтовый массив на 4Кб) чтение и
//           запись через Buffered[Input/Output]Stream
            Instant start3 = Instant.now();
            bufferedDataBy4096KB(fis);
            Instant finish3 = Instant.now();
            printWriter.println(Duration.between(start3, finish3).toMillis());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            showInfoTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void byteWriteReaderFileStream(String fis) throws IOException {
        File file = new File(fis);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(
                "F:\\IdeaCopy\\DobroPozhalovatVZombiljend.avi");

        while (true) {
            int f = fileInputStream.read();
            if (f == -1) break;
            fileOutputStream.write(f);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    private void bufferWriteReaderFileStream(String fis) throws IOException {
        File file = new File(fis);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(
                "F:\\IdeaCopy\\DobroPozhalovatVZombiljendBuffer.avi");
        int len;
        byte[] buffer = new byte[4096];
        while ((len = fileInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, len);
        }
    }

    private void bufferedData(String fis) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream("F:\\IdeaCopy\\DobroPozhalovatVZombiljendBuffered.avi"));
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(fis))) {
            while (true) {
                int f = bufferedInputStream.read();
                if (f == -1) break;
                bufferedOutputStream.write(f);
            }
        }
    }

    private void bufferedDataBy4096KB(String fis) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream("F:\\IdeaCopy\\DobroPozhalovatVZombiljendBuffered.avi"));
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(fis))) {
            int len;
            byte[] buffer = new byte[4096];
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, len);
            }
        }
    }

    private void showInfoTime() throws IOException {
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("lead time.txt"), StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(reader);
            }
        }
    }
    // самый длительный способ скопировать большого размера это использовать FileInput/Output Stream
    // без применения буферизации, BufferedInput/Output Stream работает с большими обьемами данных быстрее так
    // как под "капотом" уже заложена логика работы через буферизацию, при каждом вызове BufferedInputStream
    // обращается к буферу, а не считывает данные из файла.

}
