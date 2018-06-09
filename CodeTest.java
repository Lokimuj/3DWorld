import java.util.Objects;

public class CodeTest {
    public static void main(String[] args) {
        int[] ar = new int[]{1,2,3};
        System.out.println(Objects.hash(1,2,3)==Objects.hash(ar));
    }
}
