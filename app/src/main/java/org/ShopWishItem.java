package org;

public class ShopWishItem {

    String shopImg;
    String shopName;
    String shopPrice;
    String shopId;

    String getShopImg(){
        return this.shopImg;
    }

    String getShopName(){
        return this.shopName;
    }

    String getShopPrice(){
        return this.shopPrice;
    }

    String getShopId(){
        return this.shopId;
    }


    ShopWishItem(String shopImg, String shopName, String shopPrice, String shopId){

        this.shopImg=shopImg;
        this.shopName=shopName;
        this.shopPrice=shopPrice;
        this.shopId=shopId;

    }

}