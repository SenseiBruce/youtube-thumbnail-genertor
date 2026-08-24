package com.thumbnailgen.service;

/**
 * Advisory YouTube-title length checks. Clients can call this before generate.
 */
public final class TitleValidator {

    public static final int MAX_LENGTH = 100;

    private TitleValidator() {
    }

    public static TitleValidationResult validate(String title) {
        String value = title == null ? "" : title;
        int length = value.length();
        boolean valid = !value.isBlank() && length <= MAX_LENGTH;
        String message = valid ? null : (
                value.isBlank()
                        ? "Title must not be blank"
                        : "Title exceeds " + MAX_LENGTH + " characters"
        );
        return new TitleValidationResult(value, length, MAX_LENGTH, valid, message);
    }

    public static final class TitleValidationResult {
        private final String title;
        private final int length;
        private final int maxLength;
        private final boolean valid;
        private final String message;

        public TitleValidationResult(
                String title,
                int length,
                int maxLength,
                boolean valid,
                String message) {
            this.title = title;
            this.length = length;
            this.maxLength = maxLength;
            this.valid = valid;
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public int getLength() {
            return length;
        }

        public int getMaxLength() {
            return maxLength;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }
    }
}
