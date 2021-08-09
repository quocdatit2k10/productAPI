package au.xero.product.common;

/**
 * Config constants
 */
public class Constant {

    private Constant() {
    }

    /**
     * Declare constant table product
     */
    public static class product {
        private product() {
        }
        public static final String NOT_FOUND = "product.notFound";
        public static final String NO_DATA = "product.noData";
        public static final String MUST_NUMBER = "product.mustNumber";
        public static final String CREATE = "product.create";
        public static final String UPDATE = "product.update";
        public static final String DELETE = "product.delete";
        public static final String CHECK_FORMAT_UUID = "product.uuid";
    }

    /**
     * Declare constant table product
     */
    public static class productOption {
        private productOption() {
        }

        public static final String NOT_FOUND = "product.option.notFound";
        public static final String NO_DATA = "product.option.noData";
        public static final String MUST_NUMBER = "product.option.mustNumber";
        public static final String CREATE = "product.option.create";
        public static final String UPDATE = "product.option.update";
        public static final String DELETE = "product.option.delete";
        public static final String CHECK_FORMAT_UUID = "product.option.uuid";
    }

    /**
     * Declare constant table user and authentication
     */
    public static class user {
        private user() {
        }

        public static final String AUTHEN_USERNAME = "authentication.username";
        public static final String AUTHEN_PASSWORD = "authentication.password";
        public static final String USER_EXIST = "user.exist";
        public static final String USER_CREATE = "user.create";
        public static final String USER_MAXLEGTH = "user.error.maxlength";
        public static final String USER_CONFIRM_PASSWORD = "user.error.password.confirm";

    }

    /**
     * Declare constant security
     */
    public static class security {
        private security() {
        }

        public static final String SECRET ="SecretKeyToGenJWTs";
        public static final String TOKEN_PREFIX= "Bearer ";
        public static final String HEADER_STRING = "Authorization";
        public static final long EXPIRATION_TIME = 3000_000; //300 seconds

    }


}
