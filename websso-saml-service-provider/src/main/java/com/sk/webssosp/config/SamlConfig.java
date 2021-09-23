package com.sk.webssosp.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.saml.SAMLBootstrap;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.parser.ParserPoolHolder;
import org.springframework.security.saml.processor.HTTPArtifactBinding;
import org.springframework.security.saml.processor.HTTPPAOS11Binding;
import org.springframework.security.saml.processor.HTTPPostBinding;
import org.springframework.security.saml.processor.HTTPRedirectDeflateBinding;
import org.springframework.security.saml.processor.HTTPSOAP11Binding;
import org.springframework.security.saml.processor.SAMLBinding;
import org.springframework.security.saml.processor.SAMLProcessorImpl;
import org.springframework.security.saml.storage.EmptyStorageFactory;
import org.springframework.security.saml.storage.SAMLMessageStorageFactory;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.ArtifactResolutionProfile;
import org.springframework.security.saml.websso.ArtifactResolutionProfileImpl;
import org.springframework.security.saml.websso.SingleLogoutProfile;
import org.springframework.security.saml.websso.SingleLogoutProfileImpl;
import org.springframework.security.saml.websso.WebSSOProfile;
import org.springframework.security.saml.websso.WebSSOProfileConsumer;
import org.springframework.security.saml.websso.WebSSOProfileConsumerHoKImpl;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;
import org.springframework.security.saml.websso.WebSSOProfileECPImpl;
import org.springframework.security.saml.websso.WebSSOProfileImpl;
import org.springframework.security.saml.websso.WebSSOProfileOptions;

/**
 * @author Sandeep Kumar
 */
@Configuration 
public class SamlConfig implements InitializingBean, DisposableBean {
    
    @Value("${sp.metadata.entityId}") 
    private String entityId;
    
    @Value("${key.key-store}") 
    private Resource keyStore;
    
    @Value("${key.key-store-password}") 
    private String secret;
    
    @Value("${key.key-alias}") 
    private String alias;
    
    @Autowired
    private AppConfig appConfig;
    
    private Timer backgroundTaskTimer;
    private MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager;
    
    public void init() {
        this.backgroundTaskTimer = new Timer(true);
        this.multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
    }
    
    public void shutdown() {
        this.backgroundTaskTimer.purge();
        this.backgroundTaskTimer.cancel();
        this.multiThreadedHttpConnectionManager.shutdown();
    }
    
    @Bean 
    public VelocityEngine velocityEngine() {
        return VelocityFactory.getEngine();
    }
    
    @Bean(initMethod = "initialize") 
    public StaticBasicParserPool parserPool() {
        return new StaticBasicParserPool();
    }
    
    @Bean(name = "parserPoolHolder") 
    public ParserPoolHolder parserPoolHolder() {
        return new ParserPoolHolder();
    }
    
    @Bean 
    public HttpClient httpClient() {
        return new HttpClient(this.multiThreadedHttpConnectionManager);
    }
    
    private ArtifactResolutionProfile artifactResolutionProfile() {
        final ArtifactResolutionProfileImpl artifactResolutionProfile = new ArtifactResolutionProfileImpl(httpClient());
        artifactResolutionProfile.setProcessor(new SAMLProcessorImpl(soapBinding()));
        return artifactResolutionProfile;
    }
    
    @Bean 
    public HTTPArtifactBinding artifactBinding(ParserPool parserPool, VelocityEngine velocityEngine) {
        return new HTTPArtifactBinding(parserPool, velocityEngine, artifactResolutionProfile());
    }
    
    @Bean 
    public HTTPSOAP11Binding soapBinding() {
        return new HTTPSOAP11Binding(parserPool());
    }
    
    @Bean 
    public HTTPPostBinding httpPostBinding() {
        return new HTTPPostBinding(parserPool(), velocityEngine());
    }
    
    @Bean 
    public HTTPRedirectDeflateBinding httpRedirectDeflateBinding() {
        return new HTTPRedirectDeflateBinding(parserPool());
    }
    
    @Bean 
    public HTTPSOAP11Binding httpSOAP11Binding() {
        return new HTTPSOAP11Binding(parserPool());
    }
    
    @Bean 
    public HTTPPAOS11Binding httpPAOS11Binding() {
        return new HTTPPAOS11Binding(parserPool());
    }
    
    @Bean 
    public SAMLProcessorImpl processor() {
        Collection<SAMLBinding> bindings = new ArrayList<>();
        bindings.add(httpRedirectDeflateBinding());
        bindings.add(httpPostBinding());
        bindings.add(artifactBinding(parserPool(), velocityEngine()));
        bindings.add(httpSOAP11Binding());
        bindings.add(httpPAOS11Binding());
        return new SAMLProcessorImpl(bindings);
    }
    
    @Bean 
    public SAMLContextProviderImpl contextProvider() {
        SAMLMessageStorageFactory emptyStorageFactory = new EmptyStorageFactory();
        SAMLContextProviderImpl samlContextProvider = new SAMLContextProviderImpl();
        samlContextProvider.setStorageFactory(emptyStorageFactory);
        return samlContextProvider;
    }
    
    @Bean 
    public static SAMLBootstrap sAMLBootstrap() {
        return new SAMLBootstrap();
    }
    
    @Bean 
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }
    
    @Bean 
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        WebSSOProfileConsumer webSSOProfileConsumer = new WebSSOProfileConsumerImpl();
        ((WebSSOProfileConsumerImpl) webSSOProfileConsumer).setResponseSkew(18000);
        ((WebSSOProfileConsumerImpl) webSSOProfileConsumer).setMaxAssertionTime(18000);
        return webSSOProfileConsumer;
    }
    
    @Bean 
    public WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer() {
        return new WebSSOProfileConsumerHoKImpl();
    }
    
    @Bean 
    public WebSSOProfile webSSOprofile() {
        return new WebSSOProfileImpl();
    }
    
    @Bean 
    public WebSSOProfileConsumerHoKImpl hokWebSSOProfile() {
        return new WebSSOProfileConsumerHoKImpl();
    }
    
    @Bean 
    public WebSSOProfileECPImpl ecpprofile() {
        return new WebSSOProfileECPImpl();
    }
    
    @Bean 
    public SingleLogoutProfile logoutprofile() {
        SingleLogoutProfile singleLogoutProfile = new SingleLogoutProfileImpl();
        ((SingleLogoutProfileImpl) singleLogoutProfile).setMaxAssertionTime(18000);
        ((SingleLogoutProfileImpl) singleLogoutProfile).setResponseSkew(18000);
        return singleLogoutProfile;
    }
    
    @Bean 
    public KeyManager keyManager() {
        Map<String, String> passwords = new HashMap<>();
        passwords.put(alias, secret);
        return new JKSKeyManager(keyStore, secret, passwords, alias);
    }
    
    @Bean 
    public ExtendedMetadata extendedMetadata() {
        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        extendedMetadata.setIdpDiscoveryEnabled(false);
        extendedMetadata.setSignMetadata(true);
        extendedMetadata.setSigningAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        extendedMetadata.setEcpEnabled(true);
        extendedMetadata.setSecurityProfile("pkix");
        return extendedMetadata;
    }
    
  /**  
   * This has been commented intentionally 
   * @Bean @Qualifier("idp-http") public ExtendedMetadataDelegate idpHttpExtendedMetadataProvider()
   *     throws MetadataProviderException {
   *     String idpSSOCircleMetadataURL = "https://idp.ssocircle.com/meta-idp.xml";
   *     HTTPMetadataProvider httpMetadataProvider =
   *         new HTTPMetadataProvider(this.backgroundTaskTimer, httpClient(), idpSSOCircleMetadataURL);
   *     httpMetadataProvider.setParserPool(parserPool());
   *     ExtendedMetadataDelegate extendedMetadataDelegate =
   *         new ExtendedMetadataDelegate(httpMetadataProvider, extendedMetadata());
   *     // Signature verification can be disabled by setting value false
   *     extendedMetadataDelegate.setMetadataTrustCheck(true);
   *     // MetadataRequireSignature true will reject metadata unless it's
   *     // digitally signed.
   *     extendedMetadataDelegate.setMetadataRequireSignature(false);
   *     backgroundTaskTimer.purge();
   *     return extendedMetadataDelegate;
   * }
   */
    
    private ExtendedMetadataDelegate idpExtendedMetadataProvider(Resource resoruce)
        throws MetadataProviderException {
        FilesystemMetadataProvider metadataFile = null;
        try {
            metadataFile = new FilesystemMetadataProvider(this.backgroundTaskTimer, resoruce.getFile());
            metadataFile.setParserPool(parserPool());
        } catch (Exception e) {
            throw new MetadataProviderException(e);
        }
        
        ExtendedMetadataDelegate extendedMetadataDelegate =
            new ExtendedMetadataDelegate(metadataFile, extendedMetadata());
        extendedMetadataDelegate.setMetadataTrustCheck(true);
        extendedMetadataDelegate.setMetadataRequireSignature(false);
        backgroundTaskTimer.purge();
        return extendedMetadataDelegate;
    }
    
    @Bean 
    @Qualifier("metadata") 
    public CachingMetadataManager metadata() throws MetadataProviderException {
        List<MetadataProvider> providers = new ArrayList<>();
        for(Resource resource:appConfig.getIdps()) {
            providers.add(idpExtendedMetadataProvider(resource));
        }
        return new CachingMetadataManager(providers);
    }
    
    @Bean 
    public MetadataGenerator metadataGenerator() {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        metadataGenerator.setEntityId(entityId);
        metadataGenerator.setExtendedMetadata(extendedMetadata());
        metadataGenerator.setIncludeDiscoveryExtension(false);
        metadataGenerator.setKeyManager(keyManager());
        return metadataGenerator;
    }
    
    @Bean 
    public WebSSOProfileOptions defaultWebSSOProfileOptions() {
        WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();
        webSSOProfileOptions.setIncludeScoping(false);
        return webSSOProfileOptions;
    }
    
    @Bean 
    public SAMLEntryPoint samlEntryPoint() {
        SAMLEntryPoint samlEntryPoint = new SAMLEntryPoint();
        samlEntryPoint.setDefaultProfileOptions(defaultWebSSOProfileOptions());
        return samlEntryPoint;
    }
    
    @Override 
    public void afterPropertiesSet() throws Exception {
        init();
    }
    
    @Override 
    public void destroy() throws Exception {
        shutdown();
    }
}
