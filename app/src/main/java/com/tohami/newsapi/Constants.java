package com.tohami.newsapi;

public class Constants {
    public class Prefs {
        public static final String PREF_FILE_NAME = "news_prefs" ;
        public static final String APP_LOCALE_KEY = "appLocale";
        public static final String LANGUAGE_IS_SELECTED = "LANGUAGE_IS_SELECTED";
        public static final String LANGUAGE_KEY = "language_key";
    }

    public class DateTime {
        public static final String FORMAT_DEFAULT = "yyyy-MM-dd";
        public static final String FORMAT_ARTICLE = "MMM dd, yyyy";
    }

    public enum Status {
        SUCCESS , FAIL , LOADING
    }

    public class Api {
        public static final String DEFAULT_REQUEST_ERROR = "Server Can not process your request \uD83D\uDE13" ;
        public static final String DEFAULT_NETWORK_ERROR = "Can not submit your request \uD83D\uDE25" ;
    }

    public class News {
        public static final String DEFAULT_AUTHOR_NAME = "Anonymous" ;
    }
}
