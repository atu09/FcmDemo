package com.atirek.alm.fcmdemo.PojoClass;

/**
 * Created by Alm on 7/5/2016.
 */
public class PersonRow {
    //name and address string
    private String name;
    private String address;

    public PersonRow() {
      /*Blank default constructor essential for Firebase*/
    }

    public PersonRow(String name, String address) {
        this.name = name;
        this.address = address;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
