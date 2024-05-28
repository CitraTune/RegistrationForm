import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationForm extends JFrame {
    private final JTextField nameField;
    private final JTextField emailField;
    private final JComboBox<String> mathBox;
    private final JComboBox<String> englishBox;
    private final JComboBox<String> scienceBox;
    private final JComboBox<String> historyBox;
    private final JComboBox<String> foreignLanguageBox;
    private final JTextArea recentUserArea;
    private final List<User> users;

    public RegistrationForm() {
        users = new ArrayList<>();

        setTitle("User Registration Form");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JLabel mathLabel = new JLabel("Math Class:");
        mathBox = new JComboBox<>(new String[]{"Algebra I", "Geometry", "Algebra II", "Statistics", "Pre-Calculus", "Calculus"});

        JLabel englishLabel = new JLabel("English Class:");
        englishBox = new JComboBox<>(new String[]{"English I", "English II", "English III", "Language & Composition", "English IV", "Literature & Composition"});

        JLabel scienceLabel = new JLabel("Science Class:");
        scienceBox = new JComboBox<>(new String[]{"Biology", "Chemistry", "Physics", "Environmental Science"});

        JLabel historyLabel = new JLabel("History Class:");
        historyBox = new JComboBox<>(new String[]{"World History", "US History", "European History", "Government"});

        JLabel foreignLanguageLabel = new JLabel("Foreign Language Class:");
        foreignLanguageBox = new JComboBox<>(new String[]{"Spanish I", "Spanish II", "French I", "French II", "German I", "German II"});

        JButton registerButton = new JButton("Register");
        JButton loadButton = new JButton("Load Users");

        recentUserArea = new JTextArea(10, 30);
        recentUserArea.setEditable(false);
        recentUserArea.setLineWrap(true);
        recentUserArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(recentUserArea);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String mathClass = (String) mathBox.getSelectedItem();
            String englishClass = (String) englishBox.getSelectedItem();
            String scienceClass = (String) scienceBox.getSelectedItem();
            String historyClass = (String) historyBox.getSelectedItem();
            String foreignLanguageClass = (String) foreignLanguageBox.getSelectedItem();

            try {
                registerUser(name, email, mathClass, englishClass, scienceClass, historyClass, foreignLanguageClass);
                updateRecentUserArea(name, email, mathClass, englishClass, scienceClass, historyClass, foreignLanguageClass);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        loadButton.addActionListener(e -> {
            try {
                loadUsers();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(mathLabel, gbc);
        gbc.gridx = 1;
        panel.add(mathBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(englishLabel, gbc);
        gbc.gridx = 1;
        panel.add(englishBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(scienceLabel, gbc);
        gbc.gridx = 1;
        panel.add(scienceBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(historyLabel, gbc);
        gbc.gridx = 1;
        panel.add(historyBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(foreignLanguageLabel, gbc);
        gbc.gridx = 1;
        panel.add(foreignLanguageBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(registerButton, gbc);
        gbc.gridx = 1;
        panel.add(loadButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panel, BorderLayout.CENTER);
        setVisible(true);
        pack();
    }

    private void registerUser(String name, String email, String mathClass, String englishClass, String scienceClass, String historyClass, String foreignLanguageClass) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true));
        writer.write(name + "," + email + "," + mathClass + "," + englishClass + "," + scienceClass + "," + historyClass + "," + foreignLanguageClass);
        writer.newLine();
        writer.close();
        JOptionPane.showMessageDialog(this, "User registered successfully!");
    }

    private void loadUsers() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("users.csv"));
        String line;
        users.clear();
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 7) {
                User user = new User(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                users.add(user);
            }
        }
        reader.close();

        StringBuilder userList = new StringBuilder("<html><body><h1>Loaded Users and Possible Future Classes</h1>");
        for (User user : users) {
            userList.append("<p><strong>Name:</strong> ").append(user.getName()).append("<br>")
                    .append("<strong>Email:</strong> ").append(user.getEmail()).append("<br>")
                    .append("<strong>Math Class:</strong> ").append(user.getMathClass()).append(" -> ").append(getFutureClasses(user.getMathClass())).append("<br>")
                    .append("<strong>English Class:</strong> ").append(user.getEnglishClass()).append(" -> ").append(getFutureClasses(user.getEnglishClass())).append("<br>")
                    .append("<strong>Science Class:</strong> ").append(user.getScienceClass()).append(" -> ").append(getFutureClasses(user.getScienceClass())).append("<br>")
                    .append("<strong>History Class:</strong> ").append(user.getHistoryClass()).append(" -> ").append(getFutureClasses(user.getHistoryClass())).append("<br>")
                    .append("<strong>Foreign Language Class:</strong> ").append(user.getForeignLanguageClass()).append(" -> ").append(getFutureClasses(user.getForeignLanguageClass())).append("<br></p><hr>");
        }
        userList.append("</body></html>");
        JOptionPane.showMessageDialog(this, userList.toString(), "Loaded Users", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateRecentUserArea(String name, String email, String mathClass, String englishClass, String scienceClass, String historyClass, String foreignLanguageClass) {
        StringBuilder recentUserInfo = new StringBuilder();
        recentUserInfo.append("Name: ").append(name).append("\n")
                .append("Email: ").append(email).append("\n")
                .append("Math Class: ").append(mathClass).append(" -> ").append(getFutureClasses(mathClass)).append("\n")
                .append("English Class: ").append(englishClass).append(" -> ").append(getFutureClasses(englishClass)).append("\n")
                .append("Science Class: ").append(scienceClass).append(" -> ").append(getFutureClasses(scienceClass)).append("\n")
                .append("History Class: ").append(historyClass).append(" -> ").append(getFutureClasses(historyClass)).append("\n")
                .append("Foreign Language Class: ").append(foreignLanguageClass).append(" -> ").append(getFutureClasses(foreignLanguageClass)).append("\n");

        recentUserArea.setText(recentUserInfo.toString());
    }

    private String getFutureClasses(String currentClass) {
        return switch (currentClass) {
            case "Algebra I" -> "Geometry";
            case "Geometry" -> "Algebra II";
            case "Algebra II" -> "Pre-Calculus, Statistics";
            case "Pre-Calculus" -> "Calculus, Statistics";
            case "Calculus" -> "Statistics";
            case "English I" -> "English II";
            case "English II" -> "English III, Language & Composition";
            case "English III", "Language & Composition" -> "English IV, Literature & Composition";
            case "Literature & Composition", "English IV" -> "Advanced Literature";
            case "Biology" -> "Chemistry, Environmental Science";
            case "Chemistry" -> "Physics, Advanced Chemistry";
            case "Physics" -> "Advanced Physics";
            case "Environmental Science" -> "Advanced Environmental Studies";
            case "World History" -> "US History, European History";
            case "US History" -> "Government, Advanced US History";
            case "European History" -> "Advanced European Studies";
            case "Government" -> "Political Science";
            case "Spanish I" -> "Spanish II";
            case "Spanish II" -> "Advanced Spanish";
            case "French I" -> "French II";
            case "French II" -> "Advanced French";
            case "German I" -> "German II";
            case "German II" -> "Advanced German";
            default -> "No further classes available";
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}

