package com.microservices.user_service.practice;

import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Maps {

    /**
     * You can use a class as a key in a HashMap in Java.
     * Any object, including class objects, can be used as a key in a HashMap as long as it implements the hashCode() and equals() methods appropriately.
     * For class objects, this is inherently managed by the Class class, which overrides these methods to provide the necessary functionality.
     */
    public static void main(String[] args) {
        // Create a HashMap with Class as the key and String as the value
        Map<Class<?>, String> map = new HashMap<>();

        // Put some class keys and their corresponding values into the map
        map.put(String.class, "This is a string class");
        map.put(Integer.class, "This is an integer class");
        map.put(Double.class, "This is a double class");

        // Retrieve and print values based on class keys
        System.out.println(map.get(String.class));   // Output: This is a string class
        System.out.println(map.get(Integer.class));  // Output: This is an integer class
        System.out.println(map.get(Double.class));   // Output: This is a double class

        // Check if the map contains a specific class key
        if (map.containsKey(Boolean.class)) {
            System.out.println("The map contains a Boolean class key.");
        } else {
            System.out.println("The map does not contain a Boolean class key.");
        }

        List<Books> books = Arrays.asList(
                new Books(1, "CHarles", "Book1"),
                new Books(2, "CHarles", "Book2"),
                new Books(3, "Rahul", "Book3"),
                new Books(4, "Jack", "Book4")
        );

        System.out.println(books);

        Map<String, List<String>> map1 = books.stream()
                .collect(Collectors.groupingBy(Books::getAuthor, Collectors.mapping(Books::getBook, Collectors.toList())));
        System.out.println(map1);

        Map<String, List<Books>> map2 =
                books.stream()
                        .collect(Collectors.groupingBy(Books::getAuthor));

        System.out.println(map2);

        Map<String, List<Books>> map3 = null;//
        books.stream()
                .collect(Collectors.groupingBy(Books::getAuthor))
                .entrySet().stream().filter(a -> a.getValue().size() > 1).forEach(System.out::println);

        List<Map.Entry<String, List<Books>>> result = books.stream()
                .collect(Collectors.groupingBy(Books::getAuthor))
                .entrySet().stream()
                .filter(a -> a.getValue().size() > 1)
                .collect(Collectors.toList());

        System.out.println(result);
    }

    @Data
    static class Books {

        int id;
        String author;
        String book;

        public Books(int id, String author, String book) {
            this.id = id;
            this.author = author;
            this.book = book;
        }
    }

}
