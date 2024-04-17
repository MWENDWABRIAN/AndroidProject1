package com.example.barbercustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Services extends AppCompatActivity {
SearchView searchView;
RecyclerView recyclerView;
ArrayList<ModelClass> arrayList= new ArrayList<>();
ArrayList<ModelClass> searchList;
String[] serviceList= new String[]{"Beard Trim","Facial Massage","Hair Coloring","Haircuts","Hairstyling",
                                      "Hot towel treatment","Mustache trim","Scalp treatment","Eyebrow Shaping"
                                      ,"Beard oil treatment"};

String[] serviceNum= new String[]{"service 1","service 2","service 3","service 4","service 5",
                                  "service 6","service 7","service 8","service 9","service 10"};
int[] imgList= new int[]{R.drawable.beardtrim,R.drawable.facialmassage,R.drawable.haircoloring,
                          R.drawable.haircuts,R.drawable.hairstyling,R.drawable.hottoweltreatment,
                  R.drawable.mustachetrim,R.drawable.scalptreatment,R.drawable.eyebrow,R.drawable.beardtreatment};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        searchView= findViewById(R.id.searchview);
        recyclerView= findViewById(R.id.recyclerview);

        for (int i=0; i<serviceList.length;i++){
            ModelClass modelClass= new ModelClass();
            modelClass.setServiceName(serviceList[i]);
            modelClass.setServiceNum(serviceNum[i]);
            modelClass.setImg(imgList[i]);
            arrayList.add(modelClass);
        }

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Services.this);
        recyclerView.setLayoutManager(layoutManager);

        ServiceAdapter serviceAdapter= new ServiceAdapter(Services.this,arrayList);
        recyclerView.setAdapter(serviceAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList= new ArrayList<>();

                if (query.length()>0){
                    for (int i=0; i<arrayList.size();i++){
                        if (arrayList.get(i).getServiceName().toUpperCase().contains(query.toUpperCase()) || arrayList.get(i).getServiceNum().toUpperCase().contains(query.toUpperCase())){
                            ModelClass modelClass= new ModelClass();
                            modelClass.setServiceName(arrayList.get(i).getServiceName());
                            modelClass.setServiceNum(arrayList.get(i).getServiceNum());
                            modelClass.setImg(arrayList.get(i).getImg());
                            searchList.add(modelClass);
                        }
                    }

                    RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Services.this);
                    recyclerView.setLayoutManager(layoutManager);

                    ServiceAdapter serviceAdapter= new ServiceAdapter(Services.this,searchList);
                    recyclerView.setAdapter(serviceAdapter);
                }else {

                    RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Services.this);
                    recyclerView.setLayoutManager(layoutManager);

                    ServiceAdapter serviceAdapter= new ServiceAdapter(Services.this,arrayList);
                    recyclerView.setAdapter(serviceAdapter);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList= new ArrayList<>();

                if (newText.length()>0){
                    for (int i=0; i<arrayList.size();i++){
                        if (arrayList.get(i).getServiceName().toUpperCase().contains(newText.toUpperCase()) || arrayList.get(i).getServiceNum().toUpperCase().contains(newText.toUpperCase())){
                            ModelClass modelClass= new ModelClass();
                            modelClass.setServiceName(arrayList.get(i).getServiceName());
                            modelClass.setServiceNum(arrayList.get(i).getServiceNum());
                            modelClass.setImg(arrayList.get(i).getImg());
                            searchList.add(modelClass);
                        }
                    }

                    RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Services.this);
                    recyclerView.setLayoutManager(layoutManager);

                    ServiceAdapter serviceAdapter= new ServiceAdapter(Services.this,searchList);
                    recyclerView.setAdapter(serviceAdapter);
                }else {

                    RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(Services.this);
                    recyclerView.setLayoutManager(layoutManager);

                    ServiceAdapter serviceAdapter= new ServiceAdapter(Services.this,arrayList);
                    recyclerView.setAdapter(serviceAdapter);

                }
                return false;
            }
        });
    }
}
