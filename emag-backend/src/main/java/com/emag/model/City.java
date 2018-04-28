package com.emag.model;

import com.emag.exceptions.CityException;

public class City {
    private Long id;
    private String name;

    public City(Long id, String name) throws CityException {
       setId(id);
       setName(name);
    }

    public City() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws CityException {
       if(id>=0){
           this.id=id;
       }
       else throw new CityException("Invalid city id");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws CityException {
        if(name!=null && name.trim().length()>0){
            this.name = name;
        }
        else throw new CityException("Invalid city name");
    }
}
