package com.telegrembot.english.entity;

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
