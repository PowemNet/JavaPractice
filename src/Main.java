import com.powem.inv.algos.LiteraryInfluenceNetwork;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LiteraryInfluenceNetwork network = new LiteraryInfluenceNetwork();
        network.addAuthor("1", "George Orwell");
        network.addAuthor("2", "Aldous Huxley");

        //TEST
        assert network.getAuthors().containsKey("1");
        //TEST_END

        //TEST
        assert "George Orwell".equals(network.getAuthors().get("1").getName());
        //TEST_END

        network.addInfluence("1", "2", 1949);

        //TEST
        assert network.getInfluenceGraph().containsKey("1") && network.getInfluenceGraph().get("1")
            .containsKey("2");
        //TEST_END

        //TEST
        assert network.getInfluenceGraph().get("1").get("2") == 1949;
        //TEST_END

        network.addAuthor("3", "Ray Bradbury");
        network.addInfluence("2", "3", 1953);

        //TEST
        List<String> path = network.getInfluencePath("1", "3");
        assert path.size() == 3 && "George Orwell".equals(path.get(0)) && "Ray Bradbury".equals(
            path.get(2));
        //TEST_END

        //TEST
        try {
            network.addAuthor(null, "George Orwell");
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        //TEST_END

        //TEST
        try {
            network.addInfluence("1", "999", 1949);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        //TEST_END

        //TEST
        try {
            network.getInfluencePath("1", "");
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        //TEST_END
    }
}