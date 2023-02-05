package org.example;

import java.util.List;

public class Pet_info {

public Long id;
public Pet_Category category;
public List<Photo_tage> tags;
public List<String> photoUrls;
public String status;
public Pet_info(Long id,Pet_Category petCategory,List<Photo_tage>photoTages,List<String> tags,String status ){
    this.id=id;
    this.category=petCategory;
    this.tags=photoTages;
    this.photoUrls=tags;
    this.status=status;
}
}
