package com.example.nick.medminder.data;



/**
 * Created by zichenghe on 2018-04-03.
 */

public class ViewReminderProvider {
    private String medicationname;
    private String Date;
    private String Time;
    private String repeat;
    private String repeatNo;
    private String repatType;
    private String Active;

    public String getMedicationname() {
        return medicationname;
    }

    public void setMedicationname(String medicationname) {
        this.medicationname = medicationname;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getRepeatNo() {
        return repeatNo;
    }

    public void setRepeatNo(String repeatNo) {
        this.repeatNo = repeatNo;
    }

    public String getRepatType() {
        return repatType;
    }

    public void setRepatType(String repatType) {
        this.repatType = repatType;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public ViewReminderProvider(String medicationname, String Date, String Time, String repeat,
                                String repeatNo, String repatType, String Active){

        this.medicationname = medicationname;
        this.Date = Date;
        this.Time = Time;
        this.repeat = repeat;
        this.repeatNo = repeatNo;
        this.repatType = repatType;
        this.Active = Active;
    }
}
