package com.telegrembot.english;

import com.telegrembot.english.enums.Language;
import com.telegrembot.english.parser.MessageBuilder;

public class App {

    public static void main(String[] args) {

        MessageBuilder.
                getInstance().
                build(Language.ENGLISH).
                forEach((message)->{
                    System.out.println("******************************************");
                    System.out.println(message);
                    });

        System.out.println("******************************************");
    }
}
