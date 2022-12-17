package com.plugin.processes;

public class MyProcess {
    private int PID;
    private String name;

    public MyProcess(int PID, String name) {
        this.PID = PID;
        this.name = name;
    }

    public int getPID() {
        return PID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Process{" +
                "PID=" + PID +
                ", name='" + name + '\'' +
                '}';
    }
}
