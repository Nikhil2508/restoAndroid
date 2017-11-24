package com.example.administrator.restoapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {

    @SerializedName("response")
    @Expose
    public List<Response> response = null;

    public class Response {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("category")
        @Expose
        public List<Category> category = null;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("sale_price")
        @Expose
        public String salePrice;
        @SerializedName("mealType")
        @Expose
        public String mealType;

        public class Category {

            @SerializedName("id")
            @Expose
            public Integer id;
            @SerializedName("title")
            @Expose
            public String title;
            @SerializedName("active")
            @Expose
            public Boolean active;

        }

    }

}