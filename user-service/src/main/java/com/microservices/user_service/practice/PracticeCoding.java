package com.microservices.user_service.practice;

import java.util.Arrays;
import java.util.Scanner;

public class PracticeCoding {

    private String firstName;
    private String lastName;

    public PracticeCoding(){

    }

    public PracticeCoding(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFullName(String s1, String s2) {
        return s1 + " " + s2;
        //return "Albert" + " " + "Einstein";
    }

    void makeSound() {
        IO.println("Practice Coding");
    }

    static void main() {


        int numm = 1234;
       // int newNum = numm++;
        System.out.println(numm++);
        System.out.println(++numm);
        int rev =0;
        while (numm >0){
            int digit = numm % 10;
            rev = (rev * 10) + digit;
            numm /=10;
        }
        System.out.println(rev);

        Scanner sc = new Scanner(System.in);

        // Read first name and last name from input
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();

        // Create a Customer object
        PracticeCoding c = new PracticeCoding(s1, s2);

        System.out.println(c.getFullName(s1,s2));

        // Print the full name
        System.out.println(c.getFullName());

        sc.close(); // Close the scanner to avoid resource leaks

        try {
            int a = 100 / 0;
            IO.println("Try Block");
        } catch (ArithmeticException | NullPointerException e) {
            IO.println("Catched Exception");
        } finally {
            IO.println("Finally");
        }

        /*
         * Implicit type conversion
         * */
        int num = 10;
        double bigNum = num;
        System.out.println(num + " " + bigNum);

        /*
         * Explicit type conversion
         * */

        double bgNUm1 = 11;
        int num1 = (int) bgNUm1;
        System.out.println(num1 + " " + bgNUm1);

        /*final int x =10;
        x = 20;
        System.out.println(x);*/

        String str = "StRinG";
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (char s : chars) {
            if (Character.isUpperCase(s)) {
                sb.append(Character.toLowerCase(s));
            } else
                sb.append(Character.toUpperCase(s));
        }
        System.out.println(new String(sb));

        Dog dog = new Dog();
        dog.

                makeSound();
        dog.

                makeSound();

       /* PracticeCoding dog1 = new Dog();
        dog1.makeSound();*/

        countDigitOccurence();

        reverseArray();

        checkAnagram();
    }

    private static void checkAnagram() {
        String str1 ="act";
        String str2 ="cat";

        char[] char1 = str1.toCharArray();
        char[] char2 = str2.toCharArray();

        Arrays.sort(char1);
        Arrays.sort(char2);

        if(Arrays.equals(char1,char2)){
            IO.println("Anagram");
        }else{
            IO.println("Not Anagram");
        }
    }

    private static void reverseArray() {

        int[] arr = {10, 1, 2, 9, 8};
        int k = 3;
        reverseGivenArray(arr,0,arr.length-1);
        reverseGivenArray(arr,0,k-1);
        reverseGivenArray(arr,k,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void reverseGivenArray(int[] arr, int start, int end) {

        while (start < end){
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    private static void countDigitOccurence() {

        int num = 12223455;
        int n = 2;
        int count = 0;

        while (num > 0) {
            if (num % 10 == n) {
                count++;
            }
            num /= 10;
        }
        System.out.println("Count:- " + count);
    }
}

class Dog extends PracticeCoding {

    void makeSound() {
        super.makeSound();
        IO.println("Dog Sound");
    }
}
