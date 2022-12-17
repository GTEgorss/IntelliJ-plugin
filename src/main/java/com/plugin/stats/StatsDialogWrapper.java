package com.plugin.stats;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StatsDialogWrapper extends DialogWrapper {
    private JPanel statsDialogPanel = new JPanel(new BorderLayout());
    private TopConfiguration topConfiguration;

    String statisticsHelp = "pid:     Process ID\n" +
            "\n" +
            "             command:\n" +
            "                     Command name.\n" +
            "\n" +
            "             cpu:     CPU usage.  (default).\n" +
            "\n" +
            "             cpu_me:  CPU time charged to me by other processes.\n" +
            "\n" +
            "             cpu_others:\n" +
            "                     CPU time charged to other processes by me.\n" +
            "\n" +
            "             csw:     The number of context switches.\n" +
            "\n" +
            "             time:    Execution time.\n" +
            "\n" +
            "             threads:\n" +
            "                     alias: th\n" +
            "                     Number of threads (total/running).\n" +
            "\n" +
            "             ports:   alias: prt\n" +
            "                     Number of Mach ports.\n" +
            "\n" +
            "             mregion:\n" +
            "                     alias: mreg, reg\n" +
            "                     Number of memory regions.\n" +
            "\n" +
            "             mem:     Physical memory footprint of the process.\n" +
            "\n" +
            "             rprvt:   Resident private address space size.\n" +
            "\n" +
            "             purg:    Purgeable memory size.\n" +
            "\n" +
            "             vsize:   Total memory size." +
            "\n" +
            "\n" +
            "             vprvt:   Private address space size.\n" +
            "\n" +
            "             kprvt:   Private kernel memory size.\n" +
            "\n" +
            "             kshrd:   Shared kernel memory size.\n" +
            "\n" +
            "             pgrp:    Process group ID.\n" +
            "\n" +
            "             ppid:    Parent process ID.\n" +
            "\n" +
            "             state:   alias: pstate\n" +
            "                     Process state.\n" +
            "                     One of \"zombie\", \"running\", \"stuck\" (i.e. uninterruptible sleep),  \"sleeping\", \"idle\", \"stopped\", \"halted\", or \"unknown\".\n" +
            "\n" +
            "             uid:     User ID.\n" +
            "\n" +
            "             wq:      alias: #wq, workqueue\n" +
            "                     The workqueue total/running.\n" +
            "\n" +
            "             faults:  alias: fault\n" +
            "                     The number of page faults.\n" +
            "\n" +
            "             cow:     alias: cow_faults\n" +
            "                     The copy-on-write faults.\n" +
            "\n" +
            "             user:    alias: username\n" +
            "                     Username.\n" +
            "             msgsent:\n" +
            "                     Total number of Mach messages sent.\n" +
            "\n" +
            "             msgrecv:\n" +
            "                     Total number of Mach messages received.\n" +
            "\n" +
            "             sysbsd:  Total BSD syscalls.\n" +
            "\n" +
            "             sysmach:\n" +
            "                     Total Mach syscalls.\n" +
            "\n" +
            "             pageins:\n" +
            "                     Total pageins.\n" +
            "\n" +
            "             boosts:  The number of boosts held by the process.  This is followed by the number of times the process has transitioned from unboosted to boosted\n" +
            "                     in brackets.  An asterisk before the value indicates that the process was able to send boosts at some point since the previous update.\n" +
            "                     For more information about boosts, see xpc_transaction_begin(3).\n" +
            "\n" +
            "             instrs:  The number of instructions retired by the process in both user space and the kernel.\n" +
            "\n" +
            "             cycles:  The number of cycles spent executing instructions in the process in both user space and the kernel.";

    public StatsDialogWrapper() throws IOException {
        super(true);
        setTitle("Select statistics");
        init();

        topConfiguration = new TopConfiguration();

        JPanel helpPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton helpButton = new JButton("?");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Messages.showMessageDialog(statisticsHelp, "Statistics", Messages.getInformationIcon());
            }
        });
        helpPanel.add(helpButton);

        statsDialogPanel.add(helpPanel, BorderLayout.NORTH);


        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Choose statistics:");
        headerPanel.add(label);

        statsDialogPanel.add(headerPanel, BorderLayout.CENTER);


        var layoutSize = topConfiguration.getSize() / 3;
        JPanel checkBoxPanel = new JPanel(new GridLayout(layoutSize, 3));
        for (String key : topConfiguration.getKeys()) {
            JCheckBox checkBox = new JCheckBox(key, topConfiguration.getValue(key));
            checkBox.addItemListener(new MyItemListener(key, topConfiguration));
            checkBoxPanel.add(checkBox);
        }

        statsDialogPanel.add(checkBoxPanel, BorderLayout.SOUTH);
    }

    public String getConfiguration() {
        return topConfiguration.getConfiguration();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return statsDialogPanel;
    }
}
