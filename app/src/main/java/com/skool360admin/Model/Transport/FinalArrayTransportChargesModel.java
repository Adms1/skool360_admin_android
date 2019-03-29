package com.skool360admin.Model.Transport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 11/23/2017.
 */

public class FinalArrayTransportChargesModel {
    @SerializedName("Km")
    @Expose
    private String km;
    @SerializedName("Term 1")
    @Expose
    private Double term1;
    @SerializedName("Term 2")
    @Expose
    private Double term2;
    @SerializedName("Route")
    @Expose
    private String route;
    @SerializedName("RouteID")
    @Expose
    private Integer routeID;
    @SerializedName("PickupPointDetail")
    @Expose
    private List<PickupPointDetailModel> pickupPointDetail = new ArrayList<PickupPointDetailModel>();
    @SerializedName("VehicleName")
    @Expose
    private String vehicleName;
    @SerializedName("VehicleId")
    @Expose
    private Integer vehicleId;
    @SerializedName("Registration No.")
    @Expose
    private String registrationNo;
    @SerializedName("YearMFG")
    @Expose
    private String yearMFG;
    @SerializedName("Passng Capacity")
    @Expose
    private Integer passngCapacity;
    @SerializedName("Vehicle")
    @Expose
    private String vehicle;
    @SerializedName("VehicleIDID")
    @Expose
    private Integer vehicleIDID;
    @SerializedName("RouteNm")
    @Expose
    private String routeNm;

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public Double getTerm1() {
        return term1;
    }

    public void setTerm1(Double term1) {
        this.term1 = term1;
    }

    public Double getTerm2() {
        return term2;
    }

    public void setTerm2(Double term2) {
        this.term2 = term2;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public List<PickupPointDetailModel> getPickupPointDetail() {
        return pickupPointDetail;
    }

    public void setPickupPointDetail(List<PickupPointDetailModel> pickupPointDetail) {
        this.pickupPointDetail = pickupPointDetail;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getYearMFG() {
        return yearMFG;
    }

    public void setYearMFG(String yearMFG) {
        this.yearMFG = yearMFG;
    }

    public Integer getPassngCapacity() {
        return passngCapacity;
    }

    public void setPassngCapacity(Integer passngCapacity) {
        this.passngCapacity = passngCapacity;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getVehicleIDID() {
        return vehicleIDID;
    }

    public void setVehicleIDID(Integer vehicleIDID) {
        this.vehicleIDID = vehicleIDID;
    }

    public String getRouteNm() {
        return routeNm;
    }

    public void setRouteNm(String routeNm) {
        this.routeNm = routeNm;
    }
}
