package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {
    WebDriver browser;
    String requestName;
    static String pathToSave = "A:\\5семестр\\Практика\\ImagesFinder\\images\\";

    private Parser(String requestName){
        System.setProperty
                ("webdriver.chrome.driver", "A:\\5семестр\\JavaLabs\\SeleniumParser\\driver\\chromedriver.exe");
        browser = new ChromeDriver();
        this.requestName = requestName;
    }

    public void downloadImages(int countOfImages){
        openBrowser();

        inputRequest();
        sleep(1000);
        clickFindButton();

        sleep(1000);
        clickToImagePage();
        int j = 2;
        sleep(1000);

        doubleClickToFirstImage();

        for(int i = 1; i <= countOfImages; ++i){
            if (isImage(i)) {
                clickToCurrentImage(i);

                sleep(5000);

                String s = getImageAddress(j++);
                System.out.println(s);

                downloadImage(i, s);
            }
        }

        closeBrowser();
    }

    private void inputRequest(){
        browser.findElement
                (By.xpath
                        ("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"))
                .sendKeys(requestName);
    }

    private void clickFindButton(){
        browser.findElement
                (By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[1]"))
                .click();
    }

    private void sleep(int timeInMilliseconds){
        try {
            Thread.sleep(timeInMilliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void clickToImagePage(){
        browser.findElement(
                By.xpath("//*[@id=\"hdtb-msb\"]/div[1]/div/div[2]/a")).click();
    }

    private void doubleClickToFirstImage(){
        browser.findElement(By.xpath("" +
                "/html/body/div[2]/c-wiz/div[3]/div[1]/div/div/div/div/div[1]/div[1]/span/div[1]/div[1]/div[1]/a[1]"
        )).click();
        browser.findElement(By.xpath("" +
                "/html/body/div[2]/c-wiz/div[3]/div[1]/div/div/div/div/div[1]/div[1]/span/div[1]/div[1]/div[1]/a[1]"
        )).click();
    }

    private Boolean isImage(int number){
        return number % 25 != 0;
    }

    private void clickToCurrentImage(int number){
        browser.findElement(By.xpath("" +
                "/html/body/div[2]/c-wiz/div[3]/div[1]/div/div/div/div/div[1]/div[1]/span/div[1]/div[1]/div[" +
                number +
                "]/a[1]"
        )).click();
    }

    private void downloadImage(int number, String url){
        if (!url.equals("")){
            try {
                URL connection = new URL(url);
                HttpURLConnection urlConnection;
                urlConnection = (HttpURLConnection) connection.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream loader = urlConnection.getInputStream();

                File file = new File
                        (pathToSave + number + ".jpg");
                OutputStream writer = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int currentPoint = loader.read(buffer);
                while (currentPoint > 0) {
                    writer.write(buffer, 0, currentPoint);
                    currentPoint = loader.read(buffer);
                }
                writer.flush();
                writer.close();
                loader.close();
            } catch (Exception e) {
                System.out.println("Ошибка соединения: " + e);
            }
        }
    }

    private String getImageAddress(int number){
        return browser.findElement(By.cssSelector(
                "#Sva75c > div:nth-child(" +
                        number +
                        ") > div > div > div.pxAole > div.tvh9oe.BIB1wf > c-wiz > div.nIWXKc.JgfpDb.cZEg1e > div.OUZ5W > " +
                        "div.zjoqD > div.qdnLaf.isv-id.b0vFpe > div > a > img"
        )).getAttribute("src");
    }

    public static Parser initParser(String nameOfChapter){
        return new Parser(nameOfChapter);
    }

    private void openBrowser() {
        browser.get("https://google.com");
    }

    private void closeBrowser(){
        browser.quit();
    }
}
