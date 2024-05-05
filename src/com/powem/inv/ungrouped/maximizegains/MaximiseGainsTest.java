package com.powem.inv.ungrouped.maximizegains;

public class MaximiseGainsTest {

  public static void tests() {
    // TEST
    int[] knowledgeCycleNuggets = new int[]{8, 1, 3, 6};
    assert MaximiseGains.maxKnowledgeGain(knowledgeCycleNuggets) == 5;
    // TEST_END

    // TEST
    assert MaximiseGains.maxKnowledgeGain(new int[]{}) == 0;
    // TEST_END

    // TEST
    int[] knowledgeCycleNuggetsAscending = new int[]{1, 2, 3, 4};
    assert MaximiseGains.maxKnowledgeGain(knowledgeCycleNuggetsAscending) == 3;
    // TEST_END
  }
}