package org;

public class ShopOrderItem {

    String shopDate;
    String shopName;
    String shopPrice;
    String shopReturn;
    String order_id;

    String getShopDate(){
        return this.shopDate;
    }

    String getShopName(){
        return this.shopName;
    }

    String getShopPrice(){
        return this.shopPrice;
    }

    String getShopReturn(){
        return this.shopReturn;
    }

    String getOrder_id(){
        return this.order_id;
    }


    ShopOrderItem(String shopDate, String shopName, String shopPrice, String shopReturn, String order_id){

        this.shopDate=shopDate;
        this.shopName=shopName;
        this.shopPrice=shopPrice;
        this.shopReturn=shopReturn;
        this.order_id=order_id;

    }

}