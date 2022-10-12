package com.gmhis_backk.constant;

/**
 * 
 * @author adjara
 *
 */
public class SecurityConstant {
    public static final long EXPIRATION_TIME = 86_400_000; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String SOCIETY_NAME= "Societé BIBLOS";
    public static final String SOCIETY_ADMINISTRATION = "ERP BIBLOS";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "Vous devez vous authentifier pour acceder a cette page ";
    public static final String ACCESS_DENIED_MESSAGE = "Vous n'etes pas authoriser a acceder a cette page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String ACTION_PERFORM_DENIED_MESSAGE ="Vous n'êtes pas authorisé à effectuer cette action!";
    public static final String[] PUBLIC_URLS = {
    		"/",
    		"/user/login", /*"/user/register",*/ 
    		"/user/image/**",
    		"/user/resetpassword",
    		"/user/request-reset-password",
    		"/application-parameter/**",
    		"/customer/garantee-image/**",
    		"/article/image/**",
    		"/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/socket/info",
            "/socket/**",
            "/webjars/**" };
   // public static final String[] PUBLIC_URLS = { "**" };
}