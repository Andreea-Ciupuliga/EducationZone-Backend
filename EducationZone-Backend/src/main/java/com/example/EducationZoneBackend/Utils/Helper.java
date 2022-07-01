package com.example.EducationZoneBackend.Utils;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;

import java.util.Set;


public class Helper {


    //metoda asta ne intoarce username-ul care e encodat in token
    public static String getKeycloakUser(Authentication authentication) {
        return ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    public static Set<String> getKeycloakRole(Authentication authentication) {
        return ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken().getRealmAccess().getRoles();
    }

}

