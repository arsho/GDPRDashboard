
package gdprdashboard;
import gdprdashboard.User;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class FileService implements ServiceInterface{
    Scanner sc = new Scanner(System.in);
    private Scanner fileScanner;
    
    private PolicyService policyService;
    private UserService userService;
    
    public FileService(){
        this.policyService = new PolicyService();
        this.userService = new UserService();
    }
    
    public void processPolicyData(){
        File policyFile = this.getPolicyDataFile();
        this.loadPolicyData(policyFile);
    }
    
    public void processUserData(){
        File userFile = this.getUserDataFile();
        this.loadUserData(userFile);
    }
    
    
    public void loadPolicyData(File file) {
        String errorMessageData = "Data in input file is not correct. Try again.";
        String errorMessageLineContent = "Each line must have 2 Strings. Try again.";
        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    System.out.println(errorMessageLineContent);
                }
                this.policyService.createPolicy(parts[0], parts[1]);
            }
            fileScanner.close();
        } catch (Exception ex) {
            System.out.println(errorMessageData);
        }
    }

    public void loadUserData(File file) {
        String errorMessageData = "Data in input file is not correct. Try again.";
        String errorMessageLineContent = "Each line must have 3 Strings. Try again.";
        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println(errorMessageLineContent);
                }
                this.userService.createUser(parts[0], parts[1], parts[2]);
            }
            fileScanner.close();
        } catch (Exception ex) {
            System.out.println(errorMessageData);
        }
    }

    public File getInputFile(String promptMessage, String defaultFile) {
        String errorMessage = "File not found, try again.";
        File file = null;
        String filePath = "";
        while (true) {
            System.out.print(promptMessage);
            filePath = sc.nextLine().trim();
            try {
                if (filePath.equals("")) {
                    filePath = defaultFile;
                }
                file = new File(filePath);
                if (file.exists()) {
                    System.out.println("Imported " + filePath);
                    return file;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (Exception ex) {
                System.out.println(errorMessage);
            }
        }
    }

    public File getUserDataFile() {
        String promptMessage = "Enter path for user data file or for default file press Enter: ";
        String defaultFile = "src/gdprdashboard/data/user-data.txt";
        return getInputFile(promptMessage, defaultFile);
    }

    public File getPolicyDataFile() {
        String promptMessage = "Enter path for user data file or for default file press Enter: ";
        String defaultFile = "src/gdprdashboard/data/policy-data.txt";
        return getInputFile(promptMessage, defaultFile);
    }
}
