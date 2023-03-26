public class student {
    public student(String name, Integer grade) {
        this.name = name;
        this.grade = grade;
    }

    private String name;
    private Integer grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }

//    @Override
//    public int compareTo(student o) {
//        return o.grade-this.grade;
//    }
}
