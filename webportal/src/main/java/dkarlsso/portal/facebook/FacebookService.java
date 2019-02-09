//package dkarlsso.portal.facebook;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.api.impl.FacebookTemplate;
//import org.springframework.social.facebook.connect.FacebookConnectionFactory;
//import org.springframework.social.oauth2.AccessGrant;
//import org.springframework.social.oauth2.OAuth2Operations;
//import org.springframework.social.oauth2.OAuth2Parameters;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FacebookService {
//
//    @Value("${spring.social.facebook.appId}")
//    String facebookAppId;
//    @Value("${spring.social.facebook.appSecret}")
//    String facebookSecret;
//
//    String accessToken = "";
//
//    public String createFacebookAuthorizationURL(){
//        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
//        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
//        OAuth2Parameters params = new OAuth2Parameters();
//        params.setRedirectUri("https://localhost:8080/facebook");
//        params.setScope("public_profile,email");
//        return oauthOperations.buildAuthorizeUrl(params);
//    }
//
//    public String createFacebookAccessToken(String code) {
//        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
//
//        OAuth2Parameters params = new OAuth2Parameters();
//
//        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8080/facebook", null);
//        accessToken = accessGrant.getAccessToken();
//        return accessToken;
//    }
//
//    public String getName() {
//        Facebook facebook = new FacebookTemplate(accessToken);
//        String[] fields = {"id", "name"};
//        return new String(facebook.fetchObject("me", String.class, fields).getBytes());
//    }
//}