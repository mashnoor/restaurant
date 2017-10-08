package net.rajit.restaurent.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import net.rajit.restaurent.R;
import net.rajit.restaurent.adapters.MenuAdapter;
import net.rajit.restaurent.models.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {


    @BindView(R.id.menuGridView)
    GridView menuGrid;

    MenuAdapter adapter;

    private void showToast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        MenuItem m1 = new MenuItem(0, "0", "Pepsi", "Awesome", "15", "nai", 0);
        MenuItem m2 = new MenuItem(0, "0", "7 UP", "Awesome", "30", "nai", 0);
        MenuItem m3 = new MenuItem(0, "0", "Mountain Dew", "Awesome", "20", "nai", 0);
        MenuItem m4 = new MenuItem(0, "0", "Coca Cola", "Awesome", "15", "nai", 0);
        MenuItem m5 = new MenuItem(0, "0", "Merinda", "Awesome", "15", "nai", 0);
        MenuItem m6 = new MenuItem(0, "0", "Speed", "Awesome", "40", "nai", 0);
        MenuItem[] ms = {m1, m2, m3, m4, m5, m6};
        adapter = new MenuAdapter(getApplicationContext(), ms);
        menuGrid.setAdapter(adapter);
        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder addQuantity = new AlertDialog.Builder(
                        MenuActivity.this);
                LayoutInflater inflater = getLayoutInflater();

                final View workingview = inflater.inflate(
                        R.layout.add_qty, null);
                addQuantity.setView(workingview);
                addQuantity.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("Added to list");
                        dialogInterface.dismiss();
                    }
                });
                addQuantity.show();
            }
        });


    }

}
