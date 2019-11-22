package com.example.passwordlayout;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class SystemPassword {

    private String dict_lang;
    private String dict_num;
    private String dict_spec;

    private boolean isUppercase;
    private boolean isNumbers;
    private boolean isSpecial;


    public SystemPassword(String dict_lang, String dict_num, String dict_spec)
    {
        this.dict_lang = dict_lang;
        this.dict_num = dict_num;
        this.dict_spec = dict_spec;

        this.isUppercase = false;
        this.isNumbers = false;
        this.isSpecial = false;
    }

    public String getGeneratePassword (byte length)
    {
        StringBuilder dict_password = new StringBuilder(dict_lang);

        byte valueCondition = 1;
        if (isUppercase){
            dict_password.append(dict_lang.toUpperCase()); valueCondition++;}
        if (isNumbers){
            dict_password.append(dict_num); valueCondition++;}
        if (isSpecial){
            dict_password.append(dict_spec); valueCondition++;}

        char[] password = new char[length];

        for (int i = 0; i < length; i++)
        {
            password[i] = dict_password.charAt(getRandom(dict_password.length()));
        }

        Set<Integer> s = new LinkedHashSet<>();
        while(s.size() != valueCondition){
            s.add(getRandom(length));
        }

        Iterator<Integer> inter = s.iterator();
        password[inter.next()] = dict_lang.charAt(getRandom(dict_lang.length()));
        if (isUppercase)
            password[inter.next()] = Character.toUpperCase(dict_lang.charAt(getRandom(dict_lang.length())));
        if (isNumbers)
            password[inter.next()] = dict_num.charAt(getRandom(dict_num.length()));
        if (isSpecial)
            password[inter.next()] = dict_spec.charAt(getRandom(dict_spec.length()));

        return String.valueOf(password);
    }


    public int getRandom(int upperRange){
        Random random = new Random();
        return random.nextInt(upperRange);
    }

    public byte getValueSafety(CharSequence source)
    {
        return (byte) (source.length()<=10 ? source.length() : 10);
    }


    public void setUppercase(boolean uppercase) {
        isUppercase = uppercase;
    }

    public void setNumbers(boolean numbers) {
        isNumbers = numbers;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }


}