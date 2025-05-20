package com.saucedemo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for test helper methods
 */
public class TestUtils {
    /**
     * Generates indices for every nth item in a list
     * @param totalItems Total number of items
     * @param n Every nth item
     * @return List of indices
     */
    public static List<Integer> generateEveryNthIndex(int totalItems, int n) {
        List<Integer> indices = new ArrayList<>();
        for (int i = n - 1; i < totalItems; i += n) {
            indices.add(i);
        }
        return indices;
    }

    /**
     * Verifies that two lists contain the same elements
     * @param expected Expected list
     * @param actual Actual list
     * @return True if the lists contain the same elements, false otherwise
     */
    public static boolean listsContainSameElements(List<?> expected, List<?> actual) {
        if (expected.size() != actual.size()) {
            return false;
        }

        return expected.containsAll(actual) && actual.containsAll(expected);
    }
}