package com.plugin.myplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class MyAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            String[] cmd = new String[]{"/bin/bash", "-c", "jps"};

            ArrayList<MyProcess> options = new ProcessesParser(cmd)
                    .getProcesses();

            Thread.sleep(200);

            MyDialogWrapper dialogWrapper = new MyDialogWrapper(options);
            dialogWrapper.show();

            if (dialogWrapper.chosenProcess != null) {
                System.out.println(dialogWrapper.chosenProcess);

                cmd = new String[]{"/bin/bash", "-c", "osascript -e 'tell app \"Terminal\"\n" +
                        "do script \"top -pid " + dialogWrapper.chosenProcess.getPID() + "\"\n" +
                        "end tell'"};
                new ProcessBuilder(cmd).start();

            } else {
                Messages.showMessageDialog("Failure", "No process was chosen", Messages.getQuestionIcon());
            }

        } catch (IOException ex) {
            Messages.showMessageDialog(ex.toString(), "IO Exception", Messages.getErrorIcon());
        } catch (InterruptedException ex) {
            Messages.showMessageDialog(ex.toString(), "Thread Exception", Messages.getErrorIcon());
        }
    }

    @Override
    public boolean isDumbAware() {
        return super.isDumbAware();
    }
}
