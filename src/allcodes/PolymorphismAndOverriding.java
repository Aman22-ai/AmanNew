package allcodes;
class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}


class Employee extends Person {
    protected double salary;

    public Employee(String name, int age, double salary) {
        super(name, age);
        this.salary = salary;
    }

    
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Salary: $" + salary);
    }
}


class Manager extends Employee {
    private String department;

    public Manager(String name, int age, double salary, String department) {
        super(name, age, salary);
        this.department = department;
    }

    
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Department: " + department);
    }
}


public class PolymorphismAndOverriding {
    public static void main(String[] args) {
        Person p = new Person("Aman Kumar", 20);
        Employee e = new Employee("Ankit", 35, 40);
        Manager m = new Manager("Mehak", 40, 20, "IT");

       
        Person[] people = {p, e, m};

        for (Person person : people) {
            System.out.println("Details");
            person.displayDetails(); 
        }
    }
}
