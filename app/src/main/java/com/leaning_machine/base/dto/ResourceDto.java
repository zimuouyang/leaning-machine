package com.leaning_machine.base.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.leaning_machine.base.dto.BaseDto;

import java.io.Serializable;

public class ResourceDto implements Serializable {
    @Expose
    @SerializedName("id")
    private Long id;

    @Expose
    @SerializedName("resourceName")
    private String resourceName;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("des")
    private String des;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public ResourceDto(String resourceName, String des) {
        this.resourceName = resourceName;
        this.des = des;
    }

    @Override
    public String toString() {
        return "ResourceDto{" +
                "id=" + id +
                ", resourceName='" + resourceName + '\'' +
                ", address='" + address + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
