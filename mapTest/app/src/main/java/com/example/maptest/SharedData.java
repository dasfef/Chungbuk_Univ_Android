package com.example.maptest;

public class SharedData {
    private static SharedData instance = null;

    private String name;
    private String addr;
    private String content;

    private SharedData() {}

    public static SharedData getInstance() {
        if(instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
