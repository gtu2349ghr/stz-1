import java.util.Comparator;

public class Myconpare implements Comparator<student> {

    @Override
    public int compare(student o1, student o2) {
        return o2.getGrade()-o1.getGrade();
    }
}
