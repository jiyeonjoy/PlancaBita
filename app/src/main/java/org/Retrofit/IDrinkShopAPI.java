package org.Retrofit;

import org.Model.Banner;
import org.Model.Category;
import org.Shop;

import java.util.List;


import retrofit2.http.GET;


public interface IDrinkShopAPI {

    @GET("getbanner.php")
    io.reactivex.Observable<List<Banner>> getBanners();

    @GET("getmenu.php")
    io.reactivex.Observable<List<Category>> getMenu();

    @GET("getshop.php")
    io.reactivex.Observable<List<Shop>> getShop();

}

