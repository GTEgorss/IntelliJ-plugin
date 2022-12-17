package com.plugin.processes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessesParser {
    String[] cmd;

    public ProcessesParser(String[] cmd) {
        this.cmd = cmd;
    }

    public ArrayList<MyProcess> getProcesses() throws IOException {
        ArrayList<MyProcess> processes = new ArrayList<>();

        Process process = new ProcessBuilder(cmd).start();

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] pidAndName = line.split(" ");

                processes.add(new MyProcess(
                        Integer.parseInt(pidAndName[0]),
                        pidAndName.length == 1 ? "<empty>" : pidAndName[1]));
            }
        }

        return processes;
    }
}
