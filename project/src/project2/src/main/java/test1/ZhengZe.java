package test1;

import java.util.Scanner;

public class ZhengZe {
    public static void main(String[] args) {
        while(true){
            Scanner scanner = new Scanner(System.in);
            String a= scanner.next();
            System.out.println(a);
            System.out.println(a.matches("[A-Z].*|[0-9-_a-z]{0,8}"));
        }

    }
}
