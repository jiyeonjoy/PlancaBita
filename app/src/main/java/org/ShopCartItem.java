package org;

public class ShopCartItem {

    String shopId;
    String shopImg;
    String shopName;
    int shopCount;
    String shopPrice;
    int shopPriceInt;


    String getShopId(){
        return this.shopId;
    }

    String getShopImg(){
        return this.shopImg;
    }

    String getShopName(){
        return this.shopName;
    }

    int getShopCount(){
        return this.shopCount;
    }

    String getShopPrice(){
        return this.shopPrice;
    }

    int getShopPriceInt(){
        return this.shopPriceInt;
    }


    ShopCartItem(String shopImg, String shopName, String shopPrice, int shopCount, String shopId, int shopPriceInt){

        this.shopImg=shopImg;
        this.shopName=shopName;
        this.shopPrice=shopPrice;
        this.shopCount=shopCount;
        this.shopId=shopId;
        this.shopPriceInt=shopPriceInt;

    }

}