import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}

class InsufficientDataException extends Exception {
    public InsufficientDataException(String message) {
        super(message);
    }
}

class Person {
    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;
    private long phoneNumber;
    private char gender;

    public Person(String data) throws InsufficientDataException, InvalidDataFormatException {
        String[] parts = data.split(" ");
        if (parts.length != 6) {
            throw new InsufficientDataException("Data format error: Insufficient data provided.");
        }

        lastName = parts[0];
        firstName = parts[1];
        middleName = parts[2];
        try {
            birthDate = new SimpleDateFormat("dd.MM.yyyy").parse(parts[3]);
        } catch (ParseException e) {
            throw new InvalidDataFormatException("Data format error: Invalid date format. Use dd.mm.yyyy");
        }
        try {
            phoneNumber = Long.parseLong(parts[4]);
        } catch (NumberFormatException e) {
            throw new InvalidDataFormatException("Data format error: Phone number must be a numeric value.");
        }
        if (parts[5].length() != 1 || (parts[5].charAt(0) != 'f' && parts[5].charAt(0) != 'm')) {
            throw new InvalidDataFormatException("Data format error: Gender must be 'f' or 'm'.");
        }
        gender = parts[5].charAt(0);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return String.format("%s %s %s %s %d %c", lastName, firstName, middleName, dateFormat.format(birthDate), phoneNumber, gender);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the following data separated by spaces:");
        System.out.println("Last Name First Name Middle Name Birth Date(DD.MM.YYYY) Phone Number Gender(m/f)");

        try {
            String input = scanner.nextLine();
            Person person = new Person(input);

            // Writing to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(person.getLastName() + ".txt", true))) {
                writer.write(person.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Data has been written to file successfully.");

        } catch (InsufficientDataException | InvalidDataFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
