package com.plugin.stats;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyItemListener implements ItemListener {
    private String stat;
    private TopConfiguration topConfiguration;

    public MyItemListener(String stat, TopConfiguration topConfiguration) {
        this.stat = stat;
        this.topConfiguration = topConfiguration;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                topConfiguration.changeValue(stat, true);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                topConfiguration.changeValue(stat, false);
            } else {
                throw new NullPointerException("Error. Wrong item state");
            }
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
