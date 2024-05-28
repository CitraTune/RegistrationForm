import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private String email;
    private String mathClass;
    private String englishClass;
    private String scienceClass;
    private String historyClass;
    private String foreignLanguageClass;

    public User(String name, String email, String mathClass, String englishClass, String scienceClass, String historyClass, String foreignLanguageClass) {
        this.name = name;
        this.email = email;
        this.mathClass = mathClass;
        this.englishClass = englishClass;
        this.scienceClass = scienceClass;
        this.historyClass = historyClass;
        this.foreignLanguageClass = foreignLanguageClass;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMathClass() { return mathClass; }
    public String getEnglishClass() { return englishClass; }
    public String getScienceClass() { return scienceClass; }
    public String getHistoryClass() { return historyClass; }
    public String getForeignLanguageClass() { return foreignLanguageClass; }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "', mathClass='" + mathClass + "', englishClass='" + englishClass + "', scienceClass='" + scienceClass + "', historyClass='" + historyClass + "', foreignLanguageClass='" + foreignLanguageClass + "'}";
    }
}

public class RegistrationForm extends JFrame {
    private JLabel nameLabel, emailLabel, mathLabel, englishLabel, scienceLabel, historyLabel, foreignLanguageLabel;
    private JTextField nameField, emailField;
    private JComboBox<String> mathBox, englishBox, scienceBox, historyBox, foreignLanguageBox;
    private JButton registerButton, loadButton;
    private List<User> users;

    public RegistrationForm() {
        // Initialize the user list
        users = new ArrayList<>();

        // Set up the frame
        setTitle("User Registration Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        mathLabel = new JLabel("Math Class:");
        mathBox = new JComboBox<>(new String[]{"Algebra I", "Geometry", "Algebra II", "Pre-Calculus", "Calculus"});

        englishLabel = new JLabel("English Class:");
        englishBox = new JComboBox<>(new String[]{"English I", "English II", "English III", "English IV"});

        scienceLabel = new JLabel("Science Class:");
        scienceBox = new JComboBox<>(new String[]{"Biology", "Chemistry", "Physics", "Environmental Science"});

        historyLabel = new JLabel("History Class:");
        historyBox = new JComboBox<>(new String[]{"World History", "US History", "European History", "Government"});

        foreignLanguageLabel = new JLabel("Foreign Language Class:");
        foreignLanguageBox = new JComboBox<>(new String[]{"Spanish I", "Spanish II", "French I", "French II", "German I", "German II"});

        registerButton = new JButton("Register");
        loadButton = new JButton("Load Users");

        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get user input
                String name = nameField.getText();
                String email = emailField.getText();
                String mathClass = (String) mathBox.getSelectedItem();
                String englishClass = (String) englishBox.getSelectedItem();
                String scienceClass = (String) scienceBox.getSelectedItem();
                String historyClass = (String) historyBox.getSelectedItem();
                String foreignLanguageClass = (String) foreignLanguageBox.getSelectedItem();

                // Perform registration
                registerUser(name, email, mathClass, englishClass, scienceClass, historyClass, foreignLanguageClass);
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
        add(mathLabel);
        add(mathBox);
        add(englishLabel);
        add(englishBox);
        add(scienceLabel);
        add(scienceBox);
        add(historyLabel);
        add(historyBox);
        add(foreignLanguageLabel);
        add(foreignLanguageBox);
        add(Box.createVerticalStrut(10)); // Add space
        add(registerButton);
        add(loadButton);

        // Set visibility
        setVisible(true);
    }

    private void registerUser(String name, String email, String mathClass, String englishClass, String scienceClass, String historyClass, String foreignLanguageClass) {
        try {
            // Open file in append mode
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true));

            // Write user data to file
            writer.write(name + "," + email + "," + mathClass + "," + englishClass + "," + scienceClass + "," + historyClass + "," + foreignLanguageClass);
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
                if (data.length == 7) {
                    User user = new User(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                    users.add(user);
                }
            }

            reader.close();

            // Display the loaded users and their possible future classes
            StringBuilder userList = new StringBuilder("Loaded Users and Possible Future Classes:\n");
            for (User user : users) {
                userList.append(user).append("\n")
                        .append("Possible Future Math Classes: ").append(getFutureClasses(user.getMathClass())).append("\n")
                        .append("Possible Future English Classes: ").append(getFutureClasses(user.getEnglishClass())).append("\n")
                        .append("Possible Future Science Classes: ").append(getFutureClasses(user.getScienceClass())).append("\n")
                        .append("Possible Future History Classes: ").append(getFutureClasses(user.getHistoryClass())).append("\n")
                        .append("Possible Future Foreign Language Classes: ").append(getFutureClasses(user.getForeignLanguageClass())).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, userList.toString());
        } catch (IOException ex) {
            // Handle file IO exception
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading users!");
        }
    }

    private String getFutureClasses(String currentClass) {
        // Define class progression
        switch (currentClass) {
            case "Algebra I":
                return "Geometry, Algebra II";
            case "Geometry":
                return "Algebra II, Pre-Calculus";
            case "Algebra II":
                return "Pre-Calculus, Calculus";
            case "Pre-Calculus":
                return "Calculus";
            case "Calculus":
                return "Advanced Mathematics";

            case "English I":
                return "English II";
            case "English II":
                return "English III";
            case "English III":
                return "English IV";
            case "English IV":
                return "Advanced Literature";

            case "Biology":
                return "Chemistry, Environmental Science";
            case "Chemistry":
                return "Physics, Advanced Chemistry";
            case "Physics":
                return "Advanced Physics";
            case "Environmental Science":
                return "Advanced Environmental Studies";

            case "World History":
                return "US History, European History";
            case "US History":
                return "Government, Advanced US History";
            case "European History":
                return "Advanced European Studies";
            case "Government":
                return "Political Science";

            case "Spanish I":
                return "Spanish II";
            case "Spanish II":
                return "Advanced Spanish";
            case "French I":
                return "French II";
            case "French II":
                return "Advanced French";
            case "German I":
                return "German II";
            case "German II":
                return "Advanced German";

            default:
                return "No further classes available";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}
