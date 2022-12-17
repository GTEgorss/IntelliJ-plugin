package com.plugin.myplugin;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.plugin.pid.PidDialogWrapper;
import com.plugin.stats.StatsDialogWrapper;
import com.plugin.processes.MyProcess;
import com.plugin.processes.ProcessesParser;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;

public class MyAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            String[] cmd = new String[]{"/bin/bash", "-c", "jps"};

            ArrayList<MyProcess> options = new ProcessesParser(cmd)
                    .getProcesses();

            PidDialogWrapper pidDialogWrapper = new PidDialogWrapper(options);
            pidDialogWrapper.show();

            if (pidDialogWrapper.chosenProcess != null) {
                StatsDialogWrapper statsDialogWrapper = new StatsDialogWrapper();
                statsDialogWrapper.show();

                String topCommand = "top -pid " + pidDialogWrapper.chosenProcess.getPID();

                if (statsDialogWrapper.isOK()) {
                    topCommand = topCommand.concat(statsDialogWrapper.getConfiguration());
                }

                StringSelection stringSelection = new StringSelection(topCommand);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                ActionManager.getInstance().getAction("Terminal.OpenInTerminal").actionPerformed(e);

                cmd = new String[]{"/bin/bash", "-c", "osascript -e 'tell app \"Terminal\"\n" +
                        "do script " +
                        "\"" +
                        topCommand +
                        "\"\n" +
                        "end tell'"};
                new ProcessBuilder(cmd).start();

                Messages.showMessageDialog("Command executed in a terminal window and saved to " +
                        "your clipboard", "Success", Messages.getInformationIcon());
            } else {
                Messages.showMessageDialog("No process was chosen", "Failure", Messages.getErrorIcon());
            }

        } catch (IOException ex) {
            Messages.showMessageDialog(ex.toString(), "IO Exception", Messages.getErrorIcon());
        }
    }

    @Override
    public boolean isDumbAware() {
        return true;
    }
}
