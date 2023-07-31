package gui;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class Learn {
    private enum Peoples {
        A(1),
        B(2);

        Peoples(int age) {

        }
    }

    public static void main(String[] args) {
        String a = repeatString(1, "asdfes32");

        System.out.println(a);
    }

    public static String repeatString(int count, String string) {
        StringBuilder repeatedString = new StringBuilder();
        for (int i = 0; i < count; i++) {
            repeatedString.append(string);
        }

        return repeatedString.toString();
    }
}
