package com.example.car.ConstantsUtility;

public class Constants {
    public static final String PRIVATE_CODE_KEY = "privateCode";

    public static final String PHONE_KEY_WITHOUT_PLUS = "phoneNumber";
    public static final String PHONE_KEY_WITH_PLUS  = "phoneNumberPlus";

    public static final String MODEL_KEY = "model";

    public static final String MATRICULE_KEY = "matricule";

    public static final String MARQUE_KEY = "marque";

    public static final String FILE_NAME = "CarData.txt";


   public static final String DEFAULT_TEXT = "";


    public static final int PERMISSION_CODE = 1000;

    public static final int REQUEST_CHECK_SETTING = 1001;

    public static final String[] messages = {
            "Le code secret est assez court, veuillez entrer au moins 8 caractères",
            "Le code secret doit contenir au moins 2 chiffres",
            "Le code secret doit contenir au moins 2 caractères speciaux",
            "Le code secret doit contenir au moins 2 caractères en majuscule " };

    public static final String DIGIT = "0123456789";

    public static final String SPECIAL_CHAR = "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~";

    public static final String ALPHA_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String ALPHA_LOWER_CASE = "abcdefghijklmnopqrstuvxyz";

    public static final String CAR_FIRESTORE_COLLECTION ="CAR";

    public static final String POSITION_FIRESTORE_COLLECTION = "POSITION";

    public static final int LOCATION_SERVICE_ID = 175;

    public static final String ACTION_START_LOCATION_SERVICE = "startLocationService";

    public static final String ACTION_STOP_LOCATION_SERVICE = "stopLocationService";

    public static final String SMS = "sms";

    public static final String ENABLE_4G= "enable4g";

    public static final String DISABLE_4G= "disable4g";

    public static final int FORM_FRAGMENT1_ID = 1;

    public static final int FORM_FRAGMENT2_ID = 2;

    public static final int FORM_FRAGMENT3_ID = 3;

    public static final int LOCATION_REQUEST_INTERVAL = 5_000;

    public static final int LOCATION_REQUEST_FASTEST_INTERVAL = 2_000;

    public static final String VALID_PHONE_NUMBER = "             Ce numéro de téléphone est valide en ";

    public static final String INVALID_PHONE_NUMBER = "             Ce numéro de téléphone n'est pas valide en ";
}
