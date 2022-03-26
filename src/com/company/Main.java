package com.company;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        GameProgress gameProgress1 = new GameProgress(5, 45, 23, 0.5);
        GameProgress gameProgress2 = new GameProgress(10, 15, 3, 1.89);
        GameProgress gameProgress3 = new GameProgress(100, 1, 53, 23.5);
        saveGame("D://Нетология//Games//savegames//SaveGame1.txt", gameProgress1);
        saveGame("D://Нетология//Games//savegames//SaveGame2.txt", gameProgress2);
        saveGame("D://Нетология//Games//savegames//SaveGame3.txt", gameProgress3);
        ArrayList soursArch = new ArrayList<String>();
        soursArch.add("D://Нетология//Games//savegames//SaveGame1.txt");
        soursArch.add("D://Нетология//Games//savegames//SaveGame2.txt");
        soursArch.add("D://Нетология//Games//savegames//SaveGame3.txt");
        zipFiles("D://Нетология//Games//savegames//zipArch.zip", soursArch);
    }

    public static void saveGame(String path, GameProgress game) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos)) {
            // сохраняем игру в файл
            objectOutputStream.writeObject(game);
            //закрываем поток и освобождаем ресурсы
            objectOutputStream.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String pathArch, List<String> files) {
        try (ZipOutputStream zipArch = new ZipOutputStream(new FileOutputStream(pathArch))) {// путь и имя архива
            for (String fileName : files) {
                try (FileInputStream fis = new FileInputStream(fileName)) {
                    ZipEntry entry = new ZipEntry(fileName);
                    zipArch.putNextEntry(entry);
// считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
// добавляем содержимое к архиву
                    zipArch.write(buffer);
// закрываем текущую запись для новой записи
                    zipArch.closeEntry();
                    fis.close();
                    if (new File(fileName).delete()) {
                        System.out.printf("\n Файл %s удален", fileName);
                    }
                    //    Files.delete(Paths.get(fileName)); а что лучше? такое удаление или new File(fileName).delete()
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

