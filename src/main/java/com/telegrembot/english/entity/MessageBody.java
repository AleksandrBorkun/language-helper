package com.telegrembot.english.entity;

/*
    Это самый обычный класс
    который ничего не делает
    но представляет собой сообщение которое мы бы хотели видеть в итоге
    тут всего 4 поля (переменных)

    word - само слово в оригинале
    translation - перевод слова
    context - оригинал предложения со словом
    contextTranslation - перевод предложения

    так же для удобства создаем конструктор который будет сетать все значения
    в момент создания екземпляра класса
    и get set методы. возможно когда то они будут использоваться
    get - метод который вернет запрашиваемое значение
    set - метод который сетает значение к указанному полю (переменной)

    так же очень нужно переписать (Override) toString метод
    т.к. в нем мы можем указать каким образом экзампляр данного класа будет переведен в строку
 */
public class MessageBody {

    private String word;
    private String translation;
    private String context;
    private String contextTranslation;

    @Override
    public String toString() {
        return "MessageBody{" +
                "\nword = '" + word + '\'' +
                "\ntranslation = '" + translation + '\'' +
                "\ncontext = '" + context + '\'' +
                "\ncontextTranslation = '" + contextTranslation + '\'' +
                '}';
    }

    public MessageBody(String word, String translation, String context, String contextTranslation) {
        this.word = word;
        this.translation = translation;
        this.context = context;
        this.contextTranslation = contextTranslation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContextTranslation() {
        return contextTranslation;
    }

    public void setContextTranslation(String contextTranslation) {
        this.contextTranslation = contextTranslation;
    }
}
