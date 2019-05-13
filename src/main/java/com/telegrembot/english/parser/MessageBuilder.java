package com.telegrembot.english.parser;

import com.telegrembot.english.entity.MessageBody;
import com.telegrembot.english.enums.Language;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessageBuilder {

    private static final String ENGLISH_RANDOMS = "https://www.randomlists.com/random-words";
    private static final String ITALIAN_RANDOMS = "тут дожна быть ссылка на сайт с italian random words";

    private static final String CONTEXT_URL_BUILDER = "https://context.reverso.net/translation/%s/%s";
    private static final String ENGLISH_CONTEXT = "english-russian";
    private static final String ITALIAN_CONTEXT = "italian-russian";

    //переменная INSTANCE которая хранит в себе объект(экземряр класса) типа MessageBuilder
    private static MessageBuilder INSTANCE;

    /*
        создаем приватный конструктор класса
        таким образом объект данного класса можно создать только внутри данного класа
     */
    private MessageBuilder(){
        super();
    }

    //публичний(обшедоступный) не статический метод(значит требует объект класса чтоб его вызвать)
    //принимает в параметры 'Language type'
    //возвращает тип MessageBody
    public List<MessageBody> build(Language type){
        String contextType;
        List<String> words;
        List<MessageBody> message = new ArrayList<>();

        //switch - оператор условия
        //в зависимости от type что мы передадим выполнится определенный case
        switch (type){
            case ENGLISH:
                words = getEnglishRandomWords();
                contextType = ENGLISH_CONTEXT;
                break;
            case ITALIAN:
                words = getItalianRandomWords();
                contextType = ITALIAN_CONTEXT;
                break;
                default:
                    throw new RuntimeException("Что пошло не так. Типа переданного языка не найден.");
        }

        for(String word: words){
            //String.format - удобная штука
            //можно хранить строку и внутри нее оставлять место для переменных(значений) %s
            //в String.format мы передаем эту строку с местами для переменных и через зяпятую передаем все эти значения что нам нужны
            //к примеру String.format("in the %s or at the %s", "middle", "end") = 'in the middle or at the end'
            String[] contextData = getContextData(String.format(CONTEXT_URL_BUILDER, contextType, word));
            if(contextData!=null){
                MessageBody currentWord = new MessageBody(word, contextData[0], contextData[1], contextData[2]);
                message.add(currentWord);
            }
        }
        return message;
    }

    //публичний(обшедоступный) статический метод (значит не требует объект класса чтоб его вызвать)
    //который проверяет создан ли INSTANCE. Создает если нужно и возвращает.
    //не принимает параметры
    //возвращает объеет типа MessageBuilder
    public static MessageBuilder getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MessageBuilder();
        }
        return INSTANCE;
    }

    //метод который через webdriver подключится к сайту найдет по локатору слова и вернет их в списке
    private List<String> getEnglishRandomWords(){
        List<String> randomWordsList = new ArrayList<String>();
        WebDriver driver = getWebDriver();
        driver.get(ENGLISH_RANDOMS);
        driver.findElements(By.className("rand_large")).forEach(
                (currentElement) -> {
                    randomWordsList.add(currentElement.getText());
                }
        );
        driver.quit();
        return randomWordsList;
    }

    //метод который должен работать по аналогии с 'getEnglishRandomWords' но возвращать итальянские слова
    private List<String> getItalianRandomWords(){
        return new ArrayList<>();//сейчас просто возвращает новый пустой лист.
    }


    //данный метод просто инстанциирует и возвращает обьект типа WebDriver
    //чтоб все работало нужно поместить chromedriver.exe в папку main/resources/
    private static WebDriver getWebDriver(){
        MessageBuilder.class.getResource("chromedriver.exe");
        String path = "src/main/resources/chromedriver.exe";
        System.out.println("путь = " + path);
        System.setProperty("webdriver.chrome.driver", path);
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    //метод используя Jsoup парсит переданную в параметры страницу
    //находит перевод слова + контекст
    //возвращает масив строк
    private String[] getContextData(String url){
        try {
            Document doc = Jsoup.connect(url)
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36")
                    .get();
            //тут по локатору находим перевод слова
            String translation = doc.select(".wide-container .translation").get(0).text();
            //тут по локатору находим пример контекста
            Element fullContext = doc.getElementsByClass("example").get(0);

            //так как fullContext содержит оба языка. нужно строку разделить.
            //для этого смотрим на елемент fullContext в браузере(class = 'example')
            //и находим внутри него обьекти для перевода и оригинала
            //ищем различия в локаторах и сохраняем в соответствуюшие переменные
            String context = fullContext.select(".src").text();
            String contextTranslation = fullContext.select(".trg").text();
            return new String[] {translation, context, contextTranslation};
        } catch (Exception e) {
            System.out.println(String.format("Что-то пошло не так.\nНе удается подключится к %s. \nДанное слово не будет отображено в результатах", url));
            return null;
        }
    }

}
