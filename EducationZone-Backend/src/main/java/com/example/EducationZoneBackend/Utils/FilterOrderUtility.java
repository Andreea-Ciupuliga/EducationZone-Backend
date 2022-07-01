package com.example.EducationZoneBackend.Utils;

import org.springframework.core.Ordered;

public class FilterOrderUtility {
    //public static final int LOGGING_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE;
    public static final int CORS_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 1;

}
