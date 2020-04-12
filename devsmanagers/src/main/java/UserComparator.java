import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    public int compare(User u1, User u2) {
        if (u1.getKPI().equals(u2.getKPI())) {
            return 0;
        }
        if (u1.getKPI() > u2.getKPI()) {
            return 1;
        }
        else {
            return -1;
        }
    }
}