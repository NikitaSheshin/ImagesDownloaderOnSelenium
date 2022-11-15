package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите запрос: ");
        String request = scanner.nextLine();

        System.out.print("Задайте количество: ");
        int countOfImages = scanner.nextInt();

        Parser parser = Parser.initParser(request);
        parser.downloadImages(countOfImages);
    }
}