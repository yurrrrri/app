package com.ll.stage1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");
        while(true){
            System.out.print("명령) ");
            String order = sc.next();
            if(order.equals("종료")) break;
        }
    }
}