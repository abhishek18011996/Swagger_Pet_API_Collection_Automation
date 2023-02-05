package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pet_Requests {
    private static final Logger logger= LogManager.getLogger();
    @Test(description = "[Post] Request_Uploading Pet image")
    public void upload_pet_image_parse_response_via_map(){
        RestAssured.baseURI="https://petstore.swagger.io";


        File f=new File("src/main/resources/petskitchenfreesample.jpg");

        RequestSpecification specification=RestAssured.given().formParams("additionalMetadata","").multiPart(f);
        Response response=specification.post("/v2/pet/13/uploadImage");
        Map<String,Object> map_body=response.jsonPath().get("$");
        for(Map.Entry<String,Object> map:map_body.entrySet())
           logger.debug("Key:"+map.getKey()+"  "+"Value:"+map.getValue());
    }
    @Test(description ="[Post] Request uploading a new pet details")
    public void upload_pet_info_pass_body_via_object(){
        Pet_Category petCategory=new Pet_Category(0L,"Golden Retriever");
        Photo_tage photoTage=new Photo_tage(1L,"Play");
        Photo_tage photoTage2=new Photo_tage(2L,"Cry");
        ArrayList<Photo_tage> p=new ArrayList<>();
        p.add(photoTage);
        p.add(photoTage2);
        List<String> tags=new ArrayList<>();
        tags.add("Tag1");
        tags.add("Tags2");
        Pet_info petInfo=new Pet_info(13L,petCategory,p,tags,"Done");
        RestAssured.baseURI="";
        RequestSpecification requestSpecification=RestAssured.given().contentType(ContentType.JSON).body(petInfo);
        Response response=requestSpecification.post("https://petstore.swagger.io/v2/pet");
       logger.debug(response.asPrettyString());


    }
    @Test(description="[Put] update an existing pet")
    public void update_pet_info_pass_body_via_map(){

        Map<String,Object> petCategory=new HashMap<>();
        petCategory.put("id",1L);
        petCategory.put("name","Golden Retriever");
        Map<String,Object> photoTage=new HashMap<>();
        photoTage.put("id",0L);
        photoTage.put("name","Play");
        ArrayList<Map<String,Object>> p=new ArrayList<>();
        p.add(photoTage);
        List<String> tags=new ArrayList<>();
        tags.add("Tag1");
        tags.add("Tags2");
        Map<String,Object> map_body=new HashMap<>();
        map_body.put("id",78L);
        map_body.put("category",petCategory);
        map_body.put("name","Doggie");
        map_body.put("photoUrls",tags);
        map_body.put("tags",p);
        RestAssured.baseURI="";

        RequestSpecification requestSpecification=RestAssured.given().contentType(ContentType.JSON).body(map_body);
        Response response=requestSpecification.put("https://petstore.swagger.io/v2/pet");
       logger.debug(response.asPrettyString());
    }
    @Test(description="[Get] ids of all pet with status as available,parse array object")
    public void get_pet_info_array_parse_body_via_object(){
        RestAssured.baseURI="https://petstore.swagger.io";
        Map<String,String> param_map=new HashMap<>();
        param_map.put("status","available");
        RequestSpecification requestSpecification=RestAssured.given().accept(ContentType.JSON).params(param_map);
        Response response=requestSpecification.get("/v2/pet/findByStatus");
        List<Pet_info> pet_infos=response.jsonPath().getList("", Pet_info.class);
        for(int i=0;i<pet_infos.size();i++)
           logger.debug(pet_infos.get(i).status);
    }
    @Test(description ="[Get] info of the pet with a specific id in xml format,and also traverse response header")
    public void get_pet_info_xm_extract_response(){
        RestAssured.baseURI="https://petstore.swagger.io";

        RequestSpecification requestSpecification=RestAssured.given().accept(ContentType.XML);
        Response response=requestSpecification.get("v2/pet/0");
       logger.debug(response.statusCode());
       logger.debug(response.xmlPath().getString("apiResponse.message"));
        Headers headers=response.getHeaders();
        for(Header header:headers)
           logger.debug("Key"+header.getName()+"   "+"Value"+header.getValue());
    }
    @Test(description ="[Get] info of the pet with a specific id in json,and also traverse response header")
    public void get_pet_info_json_extract_response(){
        RestAssured.baseURI="https://petstore.swagger.io";
        Map<String,Object> map_param=new HashMap<>();
        map_param.put("name","Oliver");
        map_param.put("status","Available");
        RequestSpecification requestSpecification=RestAssured.given().accept(ContentType.JSON).formParams(map_param);
        Response response=requestSpecification.post("v2/pet/0");
       logger.debug(response.statusCode());
        
       logger.debug(response.jsonPath().getString("message"));
        Headers headers=response.getHeaders();
        for(Header header:headers)
           logger.debug("Key"+header.getName()+"   "+"Value"+header.getValue());
    }
    @Test(description = "[Delete] info of the pet with the required details")
    public void delete_pet_info_pet_parse_json(){
        RestAssured.baseURI="https://petstore.swagger.io";
        Map<String,String> map_header=new HashMap<>();
        map_header.put("api_key","hello_world");
        RequestSpecification requestSpecification=RestAssured.given().headers(map_header);
                Response response=requestSpecification.delete("/v2/pet/0");
               logger.debug(response.getStatusCode());
    }
}
