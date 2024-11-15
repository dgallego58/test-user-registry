package co.com.dgallego58.infrastructure.security.filter.jwt;

public class SchematicJwtException extends RuntimeException {

    public SchematicJwtException(String message) {
        super(message);
    }

    public static class NullAuthException extends SchematicJwtException {
        public NullAuthException(String message) {
            super(message);
        }
    }

    public static class UnableToLocateSecret extends SchematicJwtException {
        public UnableToLocateSecret(String message) {
            super(message);
        }
    }

    public static class InvalidJwt extends SchematicJwtException {
        public InvalidJwt(String message) {
            super(message);
        }
    }

    public static class InvalidSecret extends SchematicJwtException {
        public InvalidSecret(String message) {
            super(message);
        }
    }

}
