package com.showfew.rx.retrofit;

import java.util.ArrayList;

/**
 * @author steven
 * @Date 2017/6/12 18:15
 */
public class AllCity {
    private String error_code;
    private String reason;
    private String resultcode;
    private ArrayList<City> result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public ArrayList<City> getResult() {
        return result;
    }

    public void setResult(ArrayList<City> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AllCity{" +
            "error_code='" + error_code + '\'' +
            ", reason='" + reason + '\'' +
            ", resultcode='" + resultcode + '\'' +
            ", result=" + result +
            '}';
    }
}
