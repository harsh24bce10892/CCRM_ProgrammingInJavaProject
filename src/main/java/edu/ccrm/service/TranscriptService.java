package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;

import java.text.DecimalFormat;
import java.util.List;

//generating transcripts
public class TranscriptService{

    //formatter to ensure our GPA is always displayed with two decimal places
    private static final DecimalFormat GPA_FORMAT = new DecimalFormat("0.00");

    //generate complete formatted transcripts
    public String generateTranscript(Student student){
        StringBuilder transcriptBuilder = new StringBuilder();

        transcriptBuilder.append("========================================\n");
        transcriptBuilder.append(" ACADEMIC TRANSCRIPT\n");
        transcriptBuilder.append("========================================\n\n");
        
        transcriptBuilder.append("Student Name: ").append(student.getFullName()).append("\n");
        transcriptBuilder.append("Reg. Number: ").append(student.getRegistrationNumber()).append("\n\n");

        transcriptBuilder.append("--- Enrolled Courses ---\n");
        transcriptBuilder.append(String.format("%-10s | %-30s | %-7s | %-5s\n", "Code", "Title", "Credits", "Grade"));
        transcriptBuilder.append("-----------------------------------------------------------------\n");

        List<Enrollment> enrollments = student.getEnrollments();
        if (enrollments.isEmpty()){
            transcriptBuilder.append("No courses enrolled.\n");
        } else{
            for (Enrollment enrollment : enrollments){
                transcriptBuilder.append(String.format("%-10s | %-30s | %-7d | %-5s\n",
                        enrollment.getCourse().getCode(),
                        enrollment.getCourse().getTitle(),
                        enrollment.getCourse().getCredits(),
                        enrollment.getGrade().getLetterGrade()));
            }
        }

        transcriptBuilder.append("\n-----------------------------------------------------------------\n");
        
        //calculate the GPA and append it to the transcript
        double gpa = calculateGpa(student);
        transcriptBuilder.append("Cumulative GPA: ").append(GPA_FORMAT.format(gpa)).append("\n");
        transcriptBuilder.append("========================================\n");

        return transcriptBuilder.toString();
    }

    //GPA = Sum of (Credit * Grade Points) / Total Credits Attempted (for graded courses)
    private double calculateGpa(Student student){
        List<Enrollment> gradedEnrollments = student.getEnrollments().stream()
                .filter(e -> e.getGrade() != Grade.NOT_GRADED)
                .collect(java.util.stream.Collectors.toList());

        if (gradedEnrollments.isEmpty()){
            return 0.0; //non graded courses, so GPA is 0
        }

        //calculate the total quality points (credit * grade point for each course)
        double totalQualityPoints = gradedEnrollments.stream()
                .mapToDouble(e -> e.getCourse().getCredits() * e.getGrade().getGradePoints())
                .sum();
        
        //calculate the total credits for which grades have been awarded
        int totalCreditsAttempted = gradedEnrollments.stream()
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        
        //avoid division by zero
        if (totalCreditsAttempted == 0){
            return 0.0;
        }

        //return final gpa
        return totalQualityPoints / totalCreditsAttempted;
    }
}