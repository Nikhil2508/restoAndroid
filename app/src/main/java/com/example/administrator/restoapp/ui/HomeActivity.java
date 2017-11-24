package com.example.administrator.restoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.restoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_mains)
    TextView tvMains;
    @BindView(R.id.tv_sides)
    TextView tvSides;
    @BindView(R.id.tv_beverages)
    TextView tvBeverages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.tv_mains)
    public void onTvMainsClicked() {

        startActivity(new Intent(HomeActivity.this,ProductCategoriesActivity.class).putExtra("category_id",1));

    }

    @OnClick(R.id.tv_sides)
    public void onTvSidesClicked() {


        startActivity(new Intent(HomeActivity.this,ProductCategoriesActivity.class).putExtra("category_id",2));

    }

    @OnClick(R.id.tv_beverages)
    public void onTvBeveragesClicked() {

        startActivity(new Intent(HomeActivity.this,ProductCategoriesActivity.class).putExtra("category_id",3));

    }
}
