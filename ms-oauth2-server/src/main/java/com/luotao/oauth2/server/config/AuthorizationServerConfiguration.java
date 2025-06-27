package com.luotao.oauth2.server.config;

import com.luotao.oauth2.server.service.UserService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

/**
 * 授权服务配置 (Spring Authorization Server 0.4.0)
 */
@Configuration
public class AuthorizationServerConfiguration {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ClientOAuth2DataConfiguration clientOAuth2DataConfiguration;

    // UserService 实现了 Spring Security 的 UserDetailsService
    @Resource
    private UserService userService; 

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults()); // Enable OpenID Connect 1.0
        http
            .exceptionHandling(exceptions ->
                exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            )
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientOAuth2DataConfiguration.getClientId())
                .clientSecret(passwordEncoder.encode(clientOAuth2DataConfiguration.getSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantTypes(grantTypesConsumer -> {
                    if (clientOAuth2DataConfiguration.getGrantTypes() != null) {
                        for (String grantType : clientOAuth2DataConfiguration.getGrantTypes()) {
                            grantType = grantType.trim();
                            if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equalsIgnoreCase(grantType)) {
                                grantTypesConsumer.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            }
                            if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equalsIgnoreCase(grantType)) {
                                grantTypesConsumer.add(AuthorizationGrantType.REFRESH_TOKEN);
                            }
                            if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equalsIgnoreCase(grantType)) {
                                grantTypesConsumer.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                            }
                            if (AuthorizationGrantType.PASSWORD.getValue().equalsIgnoreCase(grantType)) {
                                grantTypesConsumer.add(AuthorizationGrantType.PASSWORD);
                            }
                        }
                    }
                })
                .redirectUris(urisConsumer -> {
                    String[] redirectUrisFromConfig = clientOAuth2DataConfiguration.getRedirectUris();
                    if (redirectUrisFromConfig != null && redirectUrisFromConfig.length > 0) {
                        for (String uri : redirectUrisFromConfig) {
                            if (uri != null && !uri.trim().isEmpty()) {
                                urisConsumer.add(uri.trim());
                            }
                        }
                    } else {
                        System.err.println("Warning: No redirect URIs configured for client " + clientOAuth2DataConfiguration.getClientId() + ". Using default: http://127.0.0.1/login/oauth2/code/" + clientOAuth2DataConfiguration.getClientId());
                        urisConsumer.add("http://127.0.0.1/login/oauth2/code/" + clientOAuth2DataConfiguration.getClientId());
                    }
                })
                .scopes(scopesConsumer -> {
                     if (clientOAuth2DataConfiguration.getScopes() != null) {
                        for (String scope : clientOAuth2DataConfiguration.getScopes()) {
                            scope = scope.trim();
                            scopesConsumer.add(scope);
                            if (OidcScopes.OPENID.equalsIgnoreCase(scope)) scopesConsumer.add(OidcScopes.OPENID);
                            if (OidcScopes.PROFILE.equalsIgnoreCase(scope)) scopesConsumer.add(OidcScopes.PROFILE);
                        }
                     }
                })
                .tokenSettings(TokenSettings.builder()
                    .accessTokenTimeToLive(Duration.ofSeconds(clientOAuth2DataConfiguration.getTokenValidityTime()))
                    .refreshTokenTimeToLive(Duration.ofSeconds(clientOAuth2DataConfiguration.getRefreshTokenValidityTime()))
                    .build()
                )
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        // TODO: Replace with Redis-backed OAuth2AuthorizationService in production
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        // TODO: Replace with Redis-backed OAuth2AuthorizationConsentService in production if needed
        return new InMemoryOAuth2AuthorizationConsentService();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        // Customize issuer URL and endpoints if needed
        // Example: .issuer("http://auth-server:9000")
        return AuthorizationServerSettings.builder().build();
    }

    // UserDetailsService is already provided by UserService and injected into SecurityConfiguration
    // No need to redefine it here if SecurityConfiguration handles AuthenticationManager correctly.
}