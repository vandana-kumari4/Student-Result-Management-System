import java.io.*;
import java.util.*;

// Serializable class to store student data
class Student implements Serializable {
    private int rollNo;
    private String name;
    private double marks;

    public Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public double getMarks() {
        return marks;
    }

    // Grade calculation based on marks
    public String getGrade() {
        if (marks >= 90) return "A+";
        else if (marks >= 75) return "A";
        else if (marks >= 60) return "B";
        else if (marks >= 45) return "C";
        else return "Fail";
    }

    @Override
    public String toString() {
        return String.format("Roll No: %d | Name: %s | Marks: %.2f | Grade: %s",
                rollNo, name, marks, getGrade());
    }
}

// Main class
public class StudentResultSystem {

    private static final String FILE_NAME = "students.dat";
    private static ArrayList<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== Student Result Management System ====");
            System.out.println("1. Add Student Record");
            System.out.println("2. View All Records");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Delete Student Record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            while (!sc.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                sc.next();
            }
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> displayAll();
                case 3 -> searchStudent(sc);
                case 4 -> deleteStudent(sc);
                case 5 -> {
                    saveData();
                    System.out.println("💾 Exiting... Data saved successfully.");
                }
                default -> System.out.println("⚠️ Invalid choice! Please try again.");
            }

        } while (choice != 5);

        sc.close();
    }

    // Add a new student
    private static void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        studentList.add(new Student(roll, name, marks));
        System.out.println("✅ Student record added successfully!");
    }

    // Display all student records
    private static void displayAll() {
        if (studentList.isEmpty()) {
            System.out.println("📭 No records found.");
            return;
        }
        System.out.println("\n---- All Student Records ----");
        for (Student s : studentList) {
            System.out.println(s);
        }
    }

    // Search student by roll number
    private static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        int roll = sc.nextInt();

        for (Student s : studentList) {
            if (s.getRollNo() == roll) {
                System.out.println("🔍 Record Found: " + s);
                return;
            }
        }
        System.out.println("❌ Student not found!");
    }

    // Delete a student record
    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to delete: ");
        int roll = sc.nextInt();

        Iterator<Student> it = studentList.iterator();
        while (it.hasNext()) {
            if (it.next().getRollNo() == roll) {
                it.remove();
                System.out.println("🗑️ Student record deleted successfully.");
                return;
            }
        }
        System.out.println("❌ Record not found!");
    }

    // Save data to file
    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            System.out.println("⚠️ Error saving data: " + e.getMessage());
        }
    }

    // Load data from file
    @SuppressWarnings("unchecked")
    private static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            studentList = (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            studentList = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("⚠️ Error loading data: " + e.getMessage());
        }
    }
}
