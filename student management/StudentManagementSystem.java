import java.io.*;
import java.util.*;

class Student {
    int id;
    String name;
    String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String toFileString() {
        return id + "," + name + "," + email;
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        return new Student(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }
}

public class StudentManagementSystem {
    static final String FILE_NAME = "students.txt";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Update Student");
            System.out.println("4. Search Student by ID");
            System.out.println("5. Search Student by Name");
            System.out.println("6. View All Students");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> deleteStudent();
                case 3 -> updateStudent();
                case 4 -> searchById();
                case 5 -> searchByName();
                case 6 -> viewAllStudents();
                case 7 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 7);
    }

    private static void addStudent() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            Student student = new Student(id, name, email);
            writer.write(student.toFileString());
            writer.newLine();
            System.out.println("Student added successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter ID of student to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        boolean found = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student.id != id) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                System.out.println(found ? "Student deleted." : "Student not found.");
            } else {
                System.out.println("Failed to update file.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        System.out.print("Enter ID of student to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        boolean found = false;

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student.id == id) {
                    System.out.print("Enter new Name: ");
                    student.name = scanner.nextLine();
                    System.out.print("Enter new Email: ");
                    student.email = scanner.nextLine();
                    found = true;
                }
                writer.write(student.toFileString());
                writer.newLine();
            }

            if (inputFile.delete() && tempFile.renameTo(inputFile)) {
                System.out.println(found ? "Student updated." : "Student not found.");
            } else {
                System.out.println("Failed to update file.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchById() {
        System.out.print("Enter ID to search: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student.id == id) {
                    System.out.println("Student Found: " + line);
                    found = true;
                }
            }
            if (!found) System.out.println("No student with that ID.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student.name.equalsIgnoreCase(name)) {
                    System.out.println("Student Found: " + line);
                    found = true;
                }
            }
            if (!found) System.out.println("No student with that name.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- Student Records ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}

