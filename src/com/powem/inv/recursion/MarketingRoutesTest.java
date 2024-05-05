package com.powem.inv.recursion;


public class MarketingRoutesTest {

  public static void tests() {
    // TEST
    assert MarketingRoutes.totalRoutes(0) == 1;
    // TEST_END

    // TEST
    assert MarketingRoutes.totalRoutes(3) == 3;
    // TEST_END

    // TEST
    assert MarketingRoutes.totalRoutes(4) == 5;
    // TEST_END
  }
}