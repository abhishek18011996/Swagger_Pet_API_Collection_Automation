package org.example;

import com.google.gson.annotations.SerializedName;

public class Pet_Category {
    @SerializedName("id")
    public Long id;
    @SerializedName("name")
    public String name;
    public Pet_Category(Long id,String name){
        this.id=id;
        this.name=name;
    }
}
