package edu.ccrm.domain;

//grade is an enum too as it can only have values S A B C D E F NG
public enum Grade{
    S("S", 10.0),
    A("A", 9.0),
    B("B", 8.0),
    C("C", 7.0),
    D("D", 6.0),
    E("E", 5.0),
    F("F", 0.0),
    NOT_GRADED("NG", 0.0);

    private final String letterGrade;
    private final double gradePoints;

    //private constructor so grades cant be added during runtime and making it more secure
    private Grade(String letterGrade, double gradePoints){
        this.letterGrade = letterGrade;
        this.gradePoints = gradePoints;
    }

    public String getLetterGrade(){
        return letterGrade;
    }

    public double getGradePoints(){
        return gradePoints;
    }

    @Override
    public String toString(){
        return this.letterGrade;
    }
}