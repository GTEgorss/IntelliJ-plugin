package com.plugin.myplugin;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class MyDialogWrapper extends DialogWrapper {
    public MyProcess chosenProcess;
    public ArrayList<MyProcess> options;

    private JPanel dialogPanel = new JPanel();

    public MyDialogWrapper(ArrayList<MyProcess> options) {
        super(true);
        setTitle("Select Process");
        this.options = options;
        init();

        int layoutSize = options.size() + 1;
        int xOffset = 25;
        int yOffset = 2;
        int width = 150;
        int height = 50;

        dialogPanel.setLayout(new GridLayout(layoutSize, 2));

        JLabel label = new JLabel("Choose your fighter:");
        label.setBounds(xOffset, yOffset, width, height);
        dialogPanel.add(label);

        label = new JLabel("");
        label.setBounds(xOffset, yOffset, width, height);
        dialogPanel.add(label);


        for (int i = 0; i < options.size(); ++i) {
            label = new JLabel(Integer.toString(options.get(i).getPID()));
            label.setBounds(xOffset, yOffset * (i + 2) + height * (i + 1), width, height);
            dialogPanel.add(label);

            JButton button = new JButton(options.get(i).getName());
            button.setBounds(xOffset, yOffset * (i + 2) + height * (i + 1), width, height);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chosenProcess = options.stream().filter(process -> Objects.equals(process.getName(), button.getText())).findFirst().get();
                }
            });

            dialogPanel.add(button);
        }
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return dialogPanel;
    }

    @Override
    public Action @NotNull [] createActions() {
        return new Action[]{getOKAction()};
    }
}
