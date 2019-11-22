package com.example.passwordlayout;

import java.util.HashMap;
import java.util.Map;

public class SystemConvert {

    private final Map<Character, Character> dictConvert;


    public SystemConvert(String firstLang, String secondLang) {

        if(firstLang.length() != secondLang.length())
            throw new IllegalArgumentException();

        this.dictConvert = new HashMap<>(firstLang.length());

        for (int i = 0; i < firstLang.length(); i++)
            dictConvert.put(firstLang.charAt(i), secondLang.charAt(i));
    }

    public String convert (CharSequence source)
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < source.length(); i++)
        {
            Character symbol = dictConvert.get(Character.toLowerCase(source.charAt(i)));

            if (symbol != null)
                result.append(Character.isUpperCase(source.charAt(i)) ? Character.toUpperCase(symbol) : symbol);
            else
                result.append(source.charAt(i));
        }

        return result.toString();
    }

}