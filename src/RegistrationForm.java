import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }
}

public class RegistrationForm extends JFrame {
    private JLabel nameLabel, emailLabel;
    private JTextField nameField, emailField;
    private JButton registerButton, loadButton;
    private List<User> users;

    public RegistrationForm() {
        // Initialize the user list
        users = new ArrayList<>();

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
        loadButton = new JButton("Load Users");

        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get user input
                String name = nameField.getText();
                String email = emailField.getText();

                // Perform registration
                registerUser(name, email);
            }
        });

        // Add action listener to the load button
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Load users from the CSV file
                loadUsers();
            }
        });

        // Set layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(Box.createVerticalStrut(10)); // Add space
        add(registerButton);
        add(loadButton);

        // Set visibility
        setVisible(true);
    }

    private void registerUser(String name, String email) {
        try {
            // Open file in append mode
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true));

            // Write user data to file
            writer.write(name + "," + email);
            writer.newLine();

            // Close file
            writer.close();

            // Notify user of successful registration
            JOptionPane.showMessageDialog(this, "User registered successfully!");
        } catch (IOException ex) {
            // Handle file IO exception
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user!");
        }
    }

    private void loadUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
            String line;
            users.clear(); // Clear the existing list

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    User user = new User(data[0], data[1]);
                    users.add(user);
                }
            }

            reader.close();

            // Display the loaded users
            StringBuilder userList = new StringBuilder("Loaded Users:\n");
            for (User user : users) {
                userList.append(user).append("\n");
            }
            JOptionPane.showMessageDialog(this, userList.toString());
        } catch (IOException ex) {
            // Handle file IO exception
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading users!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}
