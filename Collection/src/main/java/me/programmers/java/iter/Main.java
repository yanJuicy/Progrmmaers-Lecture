package me.programmers.java.iter;

import me.programmers.java.collection.MyCollection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MyIterator<String> iter = new MyCollection<String>(Arrays.asList("A", "BD", "CXX", "DDSA"))
                .iterator();

        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
