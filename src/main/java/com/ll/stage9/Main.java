package com.ll.stage9;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");
        int number = 1;
        List<appList> list = new ArrayList<>();

        File file = new File("data.json");
        if(file.isFile()){
            try {
                JSONParser parser = new JSONParser();
                Reader reader = new FileReader("data.json");
                JSONArray arr = (JSONArray)parser.parse(reader);
                for(int i=0; i<arr.size(); i++){
                    JSONObject jsonObject = (JSONObject)arr.get(i);
                    long id = (long)jsonObject.get("id");
                    String content = (String)jsonObject.get("content");
                    String author = (String)jsonObject.get("author");
                    list.add(new appList(id, author, content));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        while(true){
            System.out.print("명령) ");
            String order = sc.next();
            if(order.equals("등록")){
                String space = sc.nextLine();
                System.out.print("명언 : ");
                String saying = sc.nextLine();
                System.out.print("작가 : ");
                String author = sc.next();
                System.out.println(number + "번 명언이 등록되었습니다.");
                list.add(new appList(number, author, saying));
                number++;
            } else if(order.equals("목록")){
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                for(int i=list.size()-1; i>=0; i--){
                    System.out.printf("%d / %s / %s\n", list.get(i).number, list.get(i).author, list.get(i).saying);
                }
            } else if(order.substring(0, 2).equals("삭제")){
                int index = -1;
                try{
                    index = Integer.parseInt(order.substring(6));
                } catch(Exception e){
                    continue;
                }
                boolean flag = false;
                for(int i=0; i<list.size(); i++){
                    if(list.get(i).number == index){
                        list.remove(i);
                        System.out.println(index + "번 명언이 삭제되었습니다.");
                        flag = true;
                    }
                }
                if(flag == false){
                    System.out.println(index + "번 명언은 존재하지 않습니다.");
                }
            } else if(order.substring(0, 2).equals("수정")){
                int index = -1;
                try{
                    index = Integer.parseInt(order.substring(6));
                } catch(Exception e) {
                    continue;
                }
                boolean flag = false;
                for(int i=0; i<list.size(); i++){
                    if(list.get(i).number == index){
                        System.out.println("명언(기존) : "+list.get(i).saying);
                        System.out.print("명언 : ");
                        String space = sc.nextLine();
                        String saying = sc.nextLine();
                        list.get(i).saying = saying;
                        System.out.println("작가(기존) : "+list.get(i).author);
                        System.out.print("작가 : ");
                        String author = sc.next();
                        list.get(i).author = author;
                        flag = true;
                    }
                }
                if(flag == false){
                    System.out.println(index + "번 명언은 존재하지 않습니다.");
                }
            } else if(order.equals("종료")){
                sc.close();
                JSONArray arr = new JSONArray();
                for(int i=0; i<list.size(); i++){
                    JSONObject data = new JSONObject();
                    data.put("id", list.get(i).number);
                    data.put("content", list.get(i).saying);
                    data.put("author", list.get(i).author);
                    arr.add(data);
                }
                try(PrintWriter out = new PrintWriter(new FileWriter("data1.json"))){
                    out.write(arr.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else {
                System.out.println("사용 가능한 명령 : 등록, 목록, 수정, 삭제, 종료");
            }
        }
    }
}