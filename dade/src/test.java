import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.TreeSet;

public class test {
    public static void main(String[] args) {
        Myconpare myconpare = new Myconpare();
        TreeSet<student> students = new TreeSet<>(myconpare);
         students.add(new student("张三",13));
        students.add(new student("李四",51));
        students.add(new student("王麻子",7));
        System.out.println(students);

    }
}
