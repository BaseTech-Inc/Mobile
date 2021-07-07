package com.example.tupa_mobile.Address;

public class Address {

    private String address, region;
    private int type;

    public Address(String address, String region, int type) {
        this.address = address;
        this.region = region;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public String getRegion() {
        return region;
    }

    public int getType() {
        return type;
    }
}
