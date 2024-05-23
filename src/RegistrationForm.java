import javax.swing.*;
import java.awt.*;

public class RegistrationForm extends JFrame {
    private JLabel nameLabel, emailLabel;
    private JTextField nameField, emailField;
    private JButton registerButton;

    public RegistrationForm() {
        // Set up the frame
        setTitle("User Registration Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        registerButton = new JButton("Register");

        // Set layout
        setLayout(new GridLayout(3, 2));

        // Add components to the frame
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(new JLabel()); // Empty label for spacing
        add(registerButton);

        // Set visibility
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}
