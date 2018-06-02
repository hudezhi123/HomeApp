package com.hudezhi.freedom.homeapp.person.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by boy on 2017/8/8.
 */

public class FormContentInfo implements Serializable {
    private int _id;
    private String GoStartPlace;
    private String GoStartDate;
    private String GoStartTime;
    private String GoArrivePlace;
    private String GoArriveDate;
    private String GoArriveTime;
    private String GoTools;
    private double GoToolsFees;


    private String BackStartPlace;
    private String BackStartDate;
    private String BackStartTime;
    private String BackArrivePlace;
    private String BackArriveDate;
    private String BackArriveTime;
    private String BackTools;
    private double BackToolsFees;

    private double Bonus;
    private double RoomFees;
    private double OtherFees;

    private String marks;
    private List<String> ReceiptsPath;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGoStartPlace() {
        return GoStartPlace;
    }

    public void setGoStartPlace(String goStartPlace) {
        GoStartPlace = goStartPlace;
    }

    public String getGoStartDate() {
        return GoStartDate;
    }

    public void setGoStartDate(String goStartDate) {
        GoStartDate = goStartDate;
    }

    public String getGoStartTime() {
        return GoStartTime;
    }

    public void setGoStartTime(String goStartTime) {
        GoStartTime = goStartTime;
    }

    public String getGoArrivePlace() {
        return GoArrivePlace;
    }

    public void setGoArrivePlace(String goArrivePlace) {
        GoArrivePlace = goArrivePlace;
    }

    public String getGoArriveDate() {
        return GoArriveDate;
    }

    public void setGoArriveDate(String goArriveDate) {
        GoArriveDate = goArriveDate;
    }

    public String getGoArriveTime() {
        return GoArriveTime;
    }

    public void setGoArriveTime(String goArriveTime) {
        GoArriveTime = goArriveTime;
    }

    public String getGoTools() {
        return GoTools;
    }

    public void setGoTools(String goTools) {
        GoTools = goTools;
    }

    public double getGoToolsFees() {
        return GoToolsFees;
    }

    public void setGoToolsFees(double goToolsFees) {
        GoToolsFees = goToolsFees;
    }

    public String getBackStartPlace() {
        return BackStartPlace;
    }

    public void setBackStartPlace(String backStartPlace) {
        BackStartPlace = backStartPlace;
    }

    public String getBackStartDate() {
        return BackStartDate;
    }

    public void setBackStartDate(String backStartDate) {
        BackStartDate = backStartDate;
    }

    public String getBackStartTime() {
        return BackStartTime;
    }

    public void setBackStartTime(String backStartTime) {
        BackStartTime = backStartTime;
    }

    public String getBackArrivePlace() {
        return BackArrivePlace;
    }

    public void setBackArrivePlace(String backArrivePlace) {
        BackArrivePlace = backArrivePlace;
    }

    public String getBackArriveDate() {
        return BackArriveDate;
    }

    public void setBackArriveDate(String backArriveDate) {
        BackArriveDate = backArriveDate;
    }

    public String getBackArriveTime() {
        return BackArriveTime;
    }

    public void setBackArriveTime(String backArriveTime) {
        BackArriveTime = backArriveTime;
    }

    public String getBackTools() {
        return BackTools;
    }

    public void setBackTools(String backTools) {
        BackTools = backTools;
    }

    public double getBackToolsFees() {
        return BackToolsFees;
    }

    public void setBackToolsFees(double backToolsFees) {
        BackToolsFees = backToolsFees;
    }

    public double getBonus() {
        return Bonus;
    }

    public void setBonus(double bonus) {
        Bonus = bonus;
    }

    public double getRoomFees() {
        return RoomFees;
    }

    public void setRoomFees(double roomFees) {
        RoomFees = roomFees;
    }

    public double getOtherFees() {
        return OtherFees;
    }

    public void setOtherFees(int otherFees) {
        OtherFees = otherFees;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public List<String> getReceiptsPath() {
        return ReceiptsPath;
    }

    public void setReceiptsPath(List<String> receiptsPath) {
        ReceiptsPath = receiptsPath;
    }
}
