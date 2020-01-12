package com.example.feedme;

import java.util.ArrayList;
import java.util.Random;

public class GoodsItem{
    public int id;
    public int typeId;
    public int rating;
    public String name;
    public String typeName;
    public double price;
    public int count;
    public int img;

    public GoodsItem(int id, double price, String name, int typeId, String typeName,int img) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        rating = new Random().nextInt(5)+1;
        this.img = img;
    }

    private static ArrayList<GoodsItem> goodsList;
    private static ArrayList<GoodsItem> typeList;

    private static void initData(){
        goodsList = new ArrayList<>();
        typeList = new ArrayList<>();
        String typeName = null;
        String typeFood = null;
        GoodsItem item = null;
        int img = 0;
        for(int i=1;i<=3;i++){
            for(int j=1;j<=4;j++){
                switch (i){
                    case 1:
                        typeName = "Grade";
                        break;
                    case 2:
                        typeName = "Nationality";
                        break;
                    case 3:
                        typeName = "Categories";
                        break;
                }
                switch (j){
                    case 1:
                        typeFood = "Extreme Pizza";
                        img = R.drawable.grade1_pizza;
                        break;
                    case 2:
                        typeFood = "Beef Burger";
                        img = R.drawable.grade2_burger;
                        break;
                    case 3:
                        typeFood = "Spaghetti";
                        img = R.drawable.grade3_spaghetti;
                        break;
                    case 4:
                        typeFood = "Fried Chicken";
                        img = R.drawable.grade4_fried_chicken;
                        break;
                }
                item = new GoodsItem(100*i+j,Math.random()*10000,typeFood,i,typeName,img);
                goodsList.add(item);
            }
            typeList.add(item);
        }
    }

    public static ArrayList<GoodsItem> getGoodsList(){
        if(goodsList==null){
            initData();
        }
        return goodsList;
    }
    public static ArrayList<GoodsItem> getTypeList(){
        if(typeList==null){
            initData();
        }
        return typeList;
    }
}
