package com.plugin.pid;

import com.intellij.openapi.ui.DialogWrapper;
import com.plugin.processes.MyProcess;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class PidDialogWrapper extends DialogWrapper {
    public MyProcess chosenProcess;
    public ArrayList<MyProcess> options;

    private JPanel pidDialogPanel = new JPanel(new BorderLayout());

    public PidDialogWrapper(ArrayList<MyProcess> options) {
        super(true);
        setTitle("Select Process");
        this.options = options;
        init();


        JPanel headerPanel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Choose your process (cancel => default stats):");
        headerPanel.add(label);

        pidDialogPanel.add(headerPanel, BorderLayout.NORTH);

        int layoutSize = options.size();
        JPanel processesPanel = new JPanel(new GridLayout(layoutSize, 2));
        for (int i = 0; i < options.size(); ++i) {
            label = new JLabel(Integer.toString(options.get(i).getPID()));
            processesPanel.add(label);

            JButton button = new JButton(options.get(i).getName());
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    var chosenProcessOptional = options.stream().filter(process -> Objects.equals(process.getName(), button.getText())).findFirst();
                    if (chosenProcessOptional.isPresent()) {
                        chosenProcess = chosenProcessOptional.get();
                        close(OK_EXIT_CODE);
                    } else {
                        throw new NullPointerException("Error. Pid was not found.");
                    }
                }
            });

            processesPanel.add(button);
        }

        pidDialogPanel.add(processesPanel, BorderLayout.CENTER);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return pidDialogPanel;
    }

    @Override
    public Action @NotNull [] createActions() {
        return new Action[]{};
    }
}
