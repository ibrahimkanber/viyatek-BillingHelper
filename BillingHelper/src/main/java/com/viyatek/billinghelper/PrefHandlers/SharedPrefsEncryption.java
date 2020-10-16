package com.viyatek.billinghelper.PrefHandlers;

import java.util.Random;

public class SharedPrefsEncryption {
    /**
     * Created by mert on 03.11.2017.
     */

        public static final String TAG = "MYTAG";
        public static final int START_CHAR = 37;
        public static final int END_CHAR = 38;
    public SharedPrefsEncryption() {
    }

        public String encrypt(String text)
        {
            Random rand = new Random();
            int  key = rand.nextInt(5);

            String encryptedText = "";

            //Put the key at the beginning of the string
            encryptedText = encryptedText + (char) START_CHAR;//%
            encryptedText = encryptedText + getRandomChar();
            encryptedText = encryptedText + key;

            for(int i =0; i<text.length(); i++)
            {
                encryptedText = encryptedText + getRandomChar();
                encryptedText = encryptedText + (char) ((int)text.charAt(i) + key);
                encryptedText = encryptedText + getRandomChar();
            }

            //Put the ending
            encryptedText = encryptedText + (char) END_CHAR;//&

            return encryptedText;

        }

        public String getRandomChar()
        {
            Random rand = new Random();
            int  num = rand.nextInt((122-48)+1) + 48;//numbers, signs and letters in ascii

            String s = String.valueOf((char) num);

            return s;
        }


        public String resolveEncrypt(String encryptedText)
        {
            String normalText = "";

            boolean canStart = false;
            boolean atStart = true;
            int key = 0;
            for(int i=0; i<encryptedText.length(); i++)
            {
                if(encryptedText.charAt(i) == (char) START_CHAR)
                {canStart = true;}

                if(canStart == true)
                {
                    if(atStart == true)
                    {
                        key = Character.getNumericValue(encryptedText.charAt(i+2));
                        //Log.i(TAG, "Key: " + key);
                        i = i + 3;//Dummy char ve key için iki indeks ilerlet
                        atStart = false;
                    }

                    if(encryptedText.charAt(i) == END_CHAR)
                    {break;}

                    i++;
                    normalText = normalText + (char)((int) encryptedText.charAt(i) - key);
                    i++;

                }
            }

            return normalText;

        }
    }

