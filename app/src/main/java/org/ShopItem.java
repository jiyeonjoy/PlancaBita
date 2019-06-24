package org;

public class ShopItem {

    String ID;
    String Name;
    String Link;
    String Price;

    String getID(){
        return this.ID;
    }

    String getName(){
        return this.Name;
    }

    String getLink(){
        return this.Link;
    }

    String getPrice(){
        return this.Price;
    }


    ShopItem(String ID, String Name, String Link, String Price){

        this.ID=ID;
        this.Name=Name;
        this.Link=Link;
        this.Price=Price;


    }
}
