package com.Main.settings;

public class SettingModel {

    private int refreshIntervalInSeconds;
    private String countryName;
    private String countryCode;

    public SettingModel() {
        refreshIntervalInSeconds = 200;
        countryName = "Turkey";
        countryCode = "TR";
    }

    public int getRefreshIntervalInSeconds() {
        return refreshIntervalInSeconds;
    }

    public void setRefreshIntervalInSeconds(int refreshIntervalInSeconds) {
        this.refreshIntervalInSeconds = refreshIntervalInSeconds;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
