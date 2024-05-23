package com.powem.inv.collections;
//Day 5: University Course Registration System
//Objective:
//Learn to use Java's HashMap and HashSet to manage complex relationships and enforce rules such as
// course capacity and prerequisites.
//
//You are designing a system for a university to manage course registrations. Each course has a unique code, a
//set number of maximum enrollments (capacity), and may have prerequisites.
//
//Create a Java class CourseRegistrationSystem that manages course enrollments using collections. Implement features
//to register a student for a course, check if a course is full, and verify prerequisites are met before registration.
//Include comprehensive error handling for cases like invalid course codes, courses being full, or prerequisites not being met.
//
//create a method called addCourse(Course course) which adds a course to the system
//
//create a method called registerStudent(String studentId, String courseCode) which performs validations before registering
// the student to a course


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CourseRegistrationSystem {

  private static class Course {
    private String code;
    private int capacity;
    private Set<String> prerequisites;
    private Set<String> enrolledStudents;

    public Course(String code, int capacity, Set<String> prerequisites) {
      this.code = code;
      this.capacity = capacity;
      this.prerequisites = ConcurrentHashMap.newKeySet();
      this.prerequisites.addAll(prerequisites);
      this.enrolledStudents = ConcurrentHashMap.newKeySet();
    }

    public boolean enrollStudent(String studentId) {
      if (enrolledStudents.size() >= capacity) {
        throw new IllegalStateException("Course is full");
      }
      return enrolledStudents.add(studentId);
    }

    public Set<String> getPrerequisites() {
      return prerequisites;
    }
  }

  private ConcurrentMap<String, Course> courses;
  private ConcurrentMap<String, Set<String>> studentCourses;

  public CourseRegistrationSystem() {
    courses = new ConcurrentHashMap<>();
    studentCourses = new ConcurrentHashMap<>();
  }

  public void addCourse(String code, int capacity, Set<String> prerequisites) {
    if (courses.putIfAbsent(code, new Course(code, capacity, prerequisites)) != null) {
      throw new IllegalStateException("Course already exists");
    }
  }

  public boolean registerStudent(String studentId, String courseCode) {
    Course course = courses.get(courseCode);
    if (course == null) {
      throw new IllegalArgumentException("Course does not exist");
    }

    Set<String> completedCourses = studentCourses.computeIfAbsent(studentId, k -> ConcurrentHashMap.newKeySet());
    if (!completedCourses.containsAll(course.getPrerequisites())) {
      throw new IllegalStateException("Prerequisite courses not completed");
    }

    if (course.enrollStudent(studentId)) {
      completedCourses.add(courseCode);
      return true;
    }
    return false;
  }
//  public static class Course {
//    private String code;
//    private int capacity;
//    private Set<String> prerequisites;
//    private int currentEnrollment;
//
//    public Course(String code, int capacity, Set<String> prerequisites) {
//      this.code = code;
//      this.capacity = capacity;
//      this.prerequisites = prerequisites;
//      this.currentEnrollment = 0;
//    }
//
//    public boolean isFull() {
//      return currentEnrollment >= capacity;
//    }
//
//    public void enrollStudent() {
//      if (!isFull()) {
//        currentEnrollment++;
//      } else {
//        throw new IllegalStateException("Course is full");
//      }
//    }
//
//    public String getCode() {
//      return code;
//    }
//
//    public Set<String> getPrerequisites() {
//      return new HashSet<>(prerequisites);
//    }
//  }
//
//  private Map<String, Course> courses;
//  private Map<String, Set<String>> studentCourses;
//
//  public CourseRegistrationSystem() {
//    courses = new HashMap<>();
//    studentCourses = new HashMap<>();
//  }
//
//  public void addCourse(Course course) {
//    if (course == null || course.getCode() == null || course.getCode().isEmpty()) {
//      throw new IllegalArgumentException("Invalid course details");
//    }
//    courses.put(course.getCode(), course);
//  }
//
//  public boolean registerStudent(String studentId, String courseCode) {
//    if (studentId == null || studentId.isEmpty()) {
//      throw new IllegalArgumentException("Invalid student ID");
//    }
//    if (!courses.containsKey(courseCode)) {
//      throw new IllegalArgumentException("Course does not exist");
//    }
//
//    Course course = courses.get(courseCode);
//    Set<String> completedCourses = studentCourses.getOrDefault(studentId, new HashSet<>());
//
//    if (course.isFull()) {
//      throw new IllegalStateException("Course is full");
//    }
//
//    if (!completedCourses.containsAll(course.getPrerequisites())) {
//      throw new IllegalStateException("Prerequisite courses not completed");
//    }
//
//    course.enrollStudent();
//    completedCourses.add(courseCode);
//    studentCourses.put(studentId, completedCourses);
//    return true;
//  }
}

//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//public class Main {
//  public static void main(String[] args) {
//    // TEST
//    CourseRegistrationSystem system = new CourseRegistrationSystem();
//
//    Set<String> prerequisites = new HashSet<>(Arrays.asList("101", "102"));
//    system.addCourse(new CourseRegistrationSystem.Course("201", 2, prerequisites));
//    system.addCourse(new CourseRegistrationSystem.Course("101", 30, new HashSet<>()));
//    system.addCourse(new CourseRegistrationSystem.Course("102", 30, new HashSet<>()));
//
//    system.registerStudent("student01", "101");
//    system.registerStudent("student01", "102");
//
//    assert system.registerStudent("student01", "201");
//    // TEST_END
//
//    // TEST
//    system.registerStudent("student02", "101");
//    system.registerStudent("student02", "102");
//    system.registerStudent("student02", "201");
//    try {
//      system.registerStudent("student03", "201");
//      assert false;
//    } catch (IllegalStateException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.registerStudent("student01", "999");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.registerStudent("", "101");
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.addCourse(null);
//      assert false;
//    } catch (IllegalArgumentException e) {
//      assert true;
//    }
//    // TEST_END
//
//    // TEST
//    try {
//      system.registerStudent("student04", "201");
//      assert false;
//    } catch (IllegalStateException e) {
//      assert true;
//    }
//    // TEST_END
//  }
//}
