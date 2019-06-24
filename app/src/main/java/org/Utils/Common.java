package org.Utils;

import org.Model.Category;
import org.Retrofit.IDrinkShopAPI;
import org.Retrofit.RetrofitClient;

public class Common {
    private static final String BASE_URL = "http://ec2-15-164-102-182.ap-northeast-2.compute.amazonaws.com/planb/";

    public static Category currentCategory=null;

    public static int shopId = 0;

    public static IDrinkShopAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }
}
