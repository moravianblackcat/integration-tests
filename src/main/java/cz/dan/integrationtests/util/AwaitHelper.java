package cz.dan.integrationtests.util;

import org.awaitility.core.ThrowingRunnable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public final class AwaitHelper {

    private AwaitHelper() {
        throw new UnsupportedOperationException();
    }

    public static void assertMapsAreLenientEqual(int timeoutInSeconds,
                                                 Supplier<Map<String, Object>> supplierOfActualValue,
                                                 Map<String, Object> expectedValue) {
        assertInSeconds(timeoutInSeconds, () -> {
            Map<String, Object> actualValue = supplierOfActualValue.get();
            if (!allExpectedEntriesAreInActual(expectedValue, actualValue)) {
                throw new AssertionError("Maps are not lenient equal.");
            }
        });
    }


    public static void assertNoRows(int timeoutInSeconds, Supplier<Integer> noOfRowsSupplier) {
        assertInSeconds(timeoutInSeconds, () -> assertThat(noOfRowsSupplier.get()).isNotNull().isZero());
    }

    public static void assertRow(int timeoutInSeconds, Supplier<Map<String, Object>> supplierOfActualRow,
                                 Map<String, Object> expectedRow) {
        assertInSeconds(timeoutInSeconds, () -> {
            Map<String, Object> actualRow = supplierOfActualRow.get();
            assertThat(actualRow).isNotNull().containsAllEntriesOf(expectedRow);
        });
    }

    public static void assertRows(int timeoutInSeconds, Supplier<List<Map<String, Object>>> supplierOfActualRows,
                                  List<Map<String, Object>> expectedRows) {
        assertInSeconds(timeoutInSeconds, () -> {
            List<Map<String, Object>> actualRows = supplierOfActualRows.get();
            assertThat(actualRows).isNotNull().containsOnlyOnceElementsOf(expectedRows);
        });
    }

    public static <T> void assertValue(int timeoutInSeconds, Supplier<T> supplierOfActualValue, T expectedValue) {
        assertInSeconds(timeoutInSeconds, () -> {
            T actualValue = supplierOfActualValue.get();
            assertThat(actualValue).isNotNull().isEqualTo(expectedValue);
        });
    }

    public static void assertValueContains(int timeoutInSeconds, Supplier<String> supplierOfActualValue,
                                           String expectedValue) {
        assertInSeconds(timeoutInSeconds, () -> {
            String actualValue = supplierOfActualValue.get();
            assertThat(actualValue).isNotNull().contains(expectedValue);
        });
    }

    private static void assertInSeconds(int timeoutSeconds, ThrowingRunnable throwingRunnable) {
        await().atMost(timeoutSeconds, SECONDS).untilAsserted(throwingRunnable);
    }

    private static boolean allExpectedEntriesAreInActual(Map<String, Object> expectedValue,
                                                         Map<String, Object> actualValue) {
        return expectedValue.entrySet()
                .stream()
                .allMatch(entry -> entryMatch(actualValue, entry));
    }

    private static boolean entryMatch(Map<String, Object> actual, Map.Entry<String, Object> entry) {
        Object actualValue = actual.get(entry.getKey());
        Object expectedValue = entry.getValue();

        if (actualValue == null && expectedValue == null) {
            return true;
        }
        if (actualValue == null || expectedValue == null) {
            return false;
        }

        if (actualValue instanceof List<?> actualList && expectedValue instanceof List<?> expectedList) {
            return actualList.size() == expectedList.size() && actualList.containsAll(expectedList);
        }

        return actualValue.equals(expectedValue);
    }

}
