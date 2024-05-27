import com.powem.inv.algos.MarketBasketAnalyzer;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MarketBasketAnalyzer analyzer = new MarketBasketAnalyzer();

        List<String> transaction1 = Arrays.asList("Milk", "Bread", "Butter");
        List<String> transaction2 = Arrays.asList("Beer", "Bread");
        List<String> transaction3 = Arrays.asList("Milk", "Diapers", "Beer", "Bread");
        List<String> transaction4 = Arrays.asList("Butter", "Diapers", "Beer");

        analyzer.processTransaction(transaction1);
        analyzer.processTransaction(transaction2);
        analyzer.processTransaction(transaction3);
        analyzer.processTransaction(transaction4);

        List<MarketBasketAnalyzer.AssociationRule> rules = analyzer.getAssociationRules(0.5, 0.6);

        //TEST
        assert !rules.isEmpty();
        //TEST END

        //TEST
        MarketBasketAnalyzer emptyAnalyzer = new MarketBasketAnalyzer();
        rules = emptyAnalyzer.getAssociationRules(0.5, 0.6);

        assert rules.isEmpty();
        //TEST END

        //TEST
        MarketBasketAnalyzer singleAnalyzer = new MarketBasketAnalyzer();
        singleAnalyzer.processTransaction(Arrays.asList("Milk", "Bread"));
        rules = singleAnalyzer.getAssociationRules(0.5, 0.6);

        assert rules.isEmpty();
        //TEST END

        //TEST
        try {
            analyzer.processTransaction(null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        //TEST END

        //TEST
        try {
            analyzer.processTransaction(Arrays.asList());
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        //TEST END

        //TEST
        rules = analyzer.getAssociationRules(1.0, 1.0);
        assert rules.isEmpty();
        //TEST END
    }
}