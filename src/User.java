public class User {
    //Whole lotta variables
    private final String name;
    private final String email;
    private final String mathClass;
    private final String englishClass;
    private final String scienceClass;
    private final String historyClass;
    private final String foreignLanguageClass;

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