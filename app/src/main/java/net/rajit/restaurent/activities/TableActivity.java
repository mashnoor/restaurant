package net.rajit.restaurent.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import net.rajit.restaurent.R;
import net.rajit.restaurent.utils.Datas;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableActivity extends AppCompatActivity {


    @BindView(R.id.tableList)
    Spinner tableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
    }
    public void goCategory(View v)
    {
        String tableCode = tableList.getSelectedItem().toString();
        Datas.setTableCode(this, tableCode);
        startActivity(new Intent(this, CategoryActivity.class));
    }

}
