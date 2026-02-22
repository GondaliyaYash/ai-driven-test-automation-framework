package utils;

/**
 * TestData — Central repository for all test data constants.
 * Update credentials and card details to match your test account.
 */
public class TestData {

    // ---- Registered Test Account ----
    // Using the account verified working in the case study PDF
    public static final String VALID_EMAIL    = "priya.patel.qa02@gmail.com";
    public static final String VALID_PASSWORD = "Qa@2026";
    public static final String VALID_NAME     = "Priya Patel";

    // ---- Invalid Credentials ----
    public static final String INVALID_EMAIL    = "invalid_user@notexist.com";
    public static final String INVALID_PASSWORD = "WrongPass999";
    public static final String EMPTY_STRING     = "";

    // ---- New User Registration ----
    // Use a unique email each run (timestamp suffix avoids duplicates)
    public static String getUniqueEmail() {
        return "testae_" + System.currentTimeMillis() + "@mailnull.com";
    }
    public static final String NEW_USER_PASSWORD  = "NewPass@1234";
    public static final String NEW_USER_FIRSTNAME = "Jane";
    public static final String NEW_USER_LASTNAME  = "Doe";
    public static final String NEW_USER_ADDRESS   = "123 Test Street, Apt 4B";
    public static final String NEW_USER_COUNTRY   = "United States";
    public static final String NEW_USER_STATE     = "California";
    public static final String NEW_USER_CITY      = "Los Angeles";
    public static final String NEW_USER_ZIP       = "90001";
    public static final String NEW_USER_MOBILE    = "9876543210";
    public static final String NEW_USER_DOB_DAY   = "15";
    public static final String NEW_USER_DOB_MONTH = "March";
    public static final String NEW_USER_DOB_YEAR  = "1995";

    // ---- Payment Details (test card) ----
    public static final String CARD_NAME    = "Test User";
    public static final String CARD_NUMBER  = "4111111111111111";
    public static final String CARD_CVC     = "123";
    public static final String CARD_MONTH   = "12";
    public static final String CARD_YEAR    = "2027";

    // ---- Invalid Payment Details ----
    public static final String INVALID_CARD_NUMBER = "9999999999999999";
    public static final String INVALID_CVC         = "00";
    public static final String EXPIRED_CARD_YEAR   = "2020";

    // ---- Product Search Terms ----
    public static final String SEARCH_TERM_VALID   = "Blue Top";
    public static final String SEARCH_TERM_INVALID = "XYZXYZXYZNOTEXIST";

    // ---- Subscription Email ----
    public static final String SUBSCRIPTION_EMAIL         = "subscriber@mailnull.com";
    public static final String INVALID_SUBSCRIPTION_EMAIL = "not-an-email";

    // ---- Order Comment ----
    public static final String ORDER_COMMENT = "Please deliver between 9am - 5pm. Handle with care.";

    // ---- URLs ----
    public static final String BASE_URL       = "https://automationexercise.com";
    public static final String PRODUCTS_URL   = BASE_URL + "/products";
    public static final String CART_URL       = BASE_URL + "/view_cart";
    public static final String LOGIN_URL      = BASE_URL + "/login";
    public static final String CHECKOUT_URL   = BASE_URL + "/checkout";
    public static final String PAYMENT_URL    = BASE_URL + "/payment";
}
