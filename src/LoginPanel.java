import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends Panel implements ActionListener
{
    private TextField usernameField;

    public LoginPanel()
    {
        setBackground(Color.yellow);
        // components:
        Label loginLabel = new Label("Login Panel");
        Label usernameLabel = new Label("Choose username:");
        this.usernameField = new TextField("");
        this.usernameField.setPreferredSize(new Dimension(200, 25));
        Button submitButton = new Button("Submit");
        submitButton.setName("submit");
        submitButton.setActionCommand("LOGIN_USER");
        submitButton.setPreferredSize(new Dimension(150, 30));
        submitButton.addActionListener(this);

        GridBagLayout gb = new GridBagLayout();
        setLayout(gb);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gb.setConstraints(loginLabel, gbc);
        add(loginLabel);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gb.setConstraints(usernameLabel, gbc);
        add(usernameLabel);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gb.setConstraints(this.usernameField, gbc);
        add(this.usernameField);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gb.setConstraints(submitButton, gbc);
        add(submitButton);
    }

    public void actionPerformed(ActionEvent event)
    {
        System.out.println("text field contents");
        System.out.println(this.usernameField.getText());
        System.out.println(event);
    }
}