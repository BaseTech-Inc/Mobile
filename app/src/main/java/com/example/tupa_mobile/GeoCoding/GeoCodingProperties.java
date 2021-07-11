package com.example.tupa_mobile.GeoCoding;

public class GeoCodingProperties {

    private String id, gid, layer, source, source_id, name, street, postalcode, accuracy, country, country_gid, country_a, macroregion, macroregion_gid, region, region_gid, locality, locality_gid, borough, borough_gid, neighbourhood, neighbourhood_gid, continent, continent_gid, label;
    private int housenumber,index;
    private double confidence, distance;
    private GeoCodingAddendum addendum;

    public String getId() {
        return id;
    }

    public String getGid() {
        return gid;
    }

    public String getLayer() {
        return layer;
    }

    public String getSource() {
        return source;
    }

    public String getSource_id() {
        return source_id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_gid() {
        return country_gid;
    }

    public String getCountry_a() {
        return country_a;
    }

    public String getMacroregion() {
        return macroregion;
    }

    public String getMacroregion_gid() {
        return macroregion_gid;
    }

    public String getRegion() {
        return region;
    }

    public String getRegion_gid() {
        return region_gid;
    }

    public String getLocality() {
        return locality;
    }

    public String getLocality_gid() {
        return locality_gid;
    }

    public String getBorough() {
        return borough;
    }

    public String getBorough_gid() {
        return borough_gid;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getNeighbourhood_gid() {
        return neighbourhood_gid;
    }

    public String getContinent() {
        return continent;
    }

    public String getContinent_gid() {
        return continent_gid;
    }

    public String getLabel() {
        return label;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public int getIndex() {
        return index;
    }

    public double getConfidence() {
        return confidence;
    }

    public double getDistance() {
        return distance;
    }

    public GeoCodingAddendum getAddendum() {
        return addendum;
    }
}
