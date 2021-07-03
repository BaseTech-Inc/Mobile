package com.example.tupa_mobile.SettingsPage;

public class SelectorSettings {

    private String name;
    private boolean checked;

    public SelectorSettings(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
