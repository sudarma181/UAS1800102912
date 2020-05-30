package com.va181.sudarma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.va181.sudarma.adapter.ContactAdapter;
import com.va181.sudarma.model.Contact;
import com.va181.sudarma.model.ResponseData;
import com.va181.sudarma.services.ApiClient;
import com.va181.sudarma.services.ApiContact;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ContactAdapter contactAdapter;
    private RecyclerView rvShowContact;
    private ProgressDialog progressDialog;
    private List<Contact> dataContact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openInput = new Intent(getApplicationContext(), InputActivity.class);
                openInput.putExtra("OPERATION", "insert");
                startActivity(openInput);
            }
        });

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Memuat data...");

        rvShowContact = findViewById(R.id.rv_tampil);

        showContactData();
    }

    private void showContactData() {
        progressDialog.show();
        ApiContact api = ApiClient.getRetrofitInstance().create(ApiContact.class);
        Call<ResponseData> call = api.getData();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String value = response.body().getValue();

                if(value.equals("1")) {
                    dataContact = response.body().getResult();
                    contactAdapter = new ContactAdapter(dataContact, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    rvShowContact.setLayoutManager(layoutManager);
                    rvShowContact.setAdapter(contactAdapter);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    private void searchContact(String keyword) {
        ApiContact api = ApiClient.getRetrofitInstance().create(ApiContact.class);
        Call<ResponseData> call = api.searchData(keyword);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String value = response.body().getValue();

                if(value.equals("1")) {
                    dataContact = response.body().getResult();
                    contactAdapter = new ContactAdapter(dataContact, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    rvShowContact.setLayoutManager(layoutManager);
                    rvShowContact.setAdapter(contactAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchData = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView)searchData.getActionView();
        searchView.setQueryHint("Searching Contact");
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showContactData();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.equals("")) {
            showContactData();
        } else {
            searchContact(newText);
        }
        return true;
    }
}
