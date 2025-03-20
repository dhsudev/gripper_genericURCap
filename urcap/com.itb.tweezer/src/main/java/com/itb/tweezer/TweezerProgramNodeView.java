package com.itb.tweezer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.Style;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;
import com.ur.urcap.api.contribution.ContributionProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeView;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;


public class TweezerProgramNodeView implements SwingProgramNodeView<TweezerProgramNodeContribution> {

    private static final String WIDTH_TXT = "Width";
    private static final String WIDTH_UNIT_TXT = "mm";
    private static final String WIDTH_FORMAT = "%1$.0f";

    private final Style style;
    private JTextField jTextField;
    private JLabel previewTitle;
    private JLabel previewMessage;


    public TweezerProgramNodeView(Style style) {
        this.style = style;
    }

    @Override
    public void buildUp(JPanel jPanel, final ContributionProvider<TweezerProgramNodeContribution> provider) {
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(createInfo());
        jPanel.add(createVerticalSpacing(3));
        jPanel.add(createInput(provider));
        jPanel.add(createVerticalSpacing(6));
    }

    private Box createInfo() {
        Box infoBox = Box.createHorizontalBox();
        infoBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoBox.add(new JLabel("This program will communicate with the tweezer."));
        return infoBox;
    }

    private Box createInput(final ContributionProvider<TweezerProgramNodeContribution> provider) {
        Box inputBox = Box.createHorizontalBox();
        inputBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputBox.add(new JLabel(WIDTH_TXT));
        inputBox.add(createHorizontalSpacing());

        jTextField = new JTextField();
        jTextField.setFocusable(false);
        jTextField.setPreferredSize(new Dimension(10,0));
        jTextField.setMaximumSize(jTextField.getPreferredSize());

        jTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                KeyboardTextInput keyboardInput = provider.get().getKeyboardForTextField();
                keyboardInput.show(jTextField, provider.get().getCallbackForTextField());
            }
        });

        inputBox.add(jTextField);
        return inputBox;
    }

    private Component createVerticalSpacing(int height) {
        return Box.createRigidArea(new Dimension(0, height));
    }

    private Component createHorizontalSpacing() {
        return Box.createRigidArea(new Dimension(10, 0));
    }
}
