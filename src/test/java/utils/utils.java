package utils;

import java.util.List;

public class utils {

    public static void assertDataType(List<Object> listData, String dataType) {
        for (Object data : listData) {
            switch (dataType.toLowerCase()) {
                case "integer" -> assertIsInteger(data);
                case "string" -> assertIsString(data);
                default -> throw new IllegalStateException("Unexpected value: " + dataType);
            }
        }
    }

    private static void assertIsInteger(Object data) {
        if (!(data instanceof Integer)) {
            assertionError(errorMessageDataType(data));
        }
    }

    private static void assertIsString(Object data) {
        if (!(data instanceof String)) {
            assertionError(errorMessageDataType(data));
        }
    }

    private static String errorMessageDataType(Object data) {
        return String.format("Actual data type is %s!", data.getClass().getSimpleName());
    }

    private static void assertionError(String message) {
        throw new AssertionError(message);
    }
}
