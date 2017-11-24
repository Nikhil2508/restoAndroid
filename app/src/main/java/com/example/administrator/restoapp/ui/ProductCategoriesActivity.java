package com.example.administrator.restoapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.restoapp.R;
import com.example.administrator.restoapp.models.ProductModel;
import com.example.administrator.restoapp.utils.RestoApi;
import com.example.administrator.restoapp.utils.SharedPrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductCategoriesActivity extends AppCompatActivity {


    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    private RestoApi webService;
    private static final String TAG = "ProductCategoriesActivi";
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        ButterKnife.bind(this);

        getProducts();

    }

    private void getProducts() {

        Call<ProductModel> getProducts = getWebService().getProducts(SharedPrefUtils.getString(this, "token"), getIntent().getIntExtra("category_id", 0));
        getProducts.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {

                Log.d(TAG, "onResponse: on successss-----> " + response.body());
                ProductModel model = response.body();
                adapter = new ProductAdapter(model.response, ProductCategoriesActivity.this);
                rvProducts.setAdapter(adapter);
                rvProducts.setLayoutManager(new GridLayoutManager(ProductCategoriesActivity.this,2));

            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {

                Log.d(TAG, "onResponse: on error -----> ");

            }

        });

    }


    private RestoApi getWebService() {
        if (webService == null) {
            webService = new Retrofit.Builder().baseUrl(RestoApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                            .create())
                    .build()
                    .create(RestoApi.class);
        }
        return webService;
    }

}
