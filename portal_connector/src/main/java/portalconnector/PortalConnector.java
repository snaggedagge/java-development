package portalconnector;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import portalconnector.model.PortalConnectorException;
import portalconnector.model.WebsiteDTO;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class PortalConnector {

    public static final String URL_STANDARD_PORTAL = "https://www.dkarlsso.ax"; // >Domain not existing yet

    public static final String URI_ADD_WEBSITE = "/rest/addWebsite";

    public static final String URI_GET_ALL_WEBSITES = "/rest/getAllWebsites";

    private final String portalURL;

    private final RestTemplate restTemplate = getRestTemplate();

    public PortalConnector() {
        portalURL = URL_STANDARD_PORTAL;
    }

    public PortalConnector(final String urlPortal) {
        portalURL = urlPortal;
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    public WebsiteDTO addWebsite(final WebsiteDTO websiteDTO) throws PortalConnectorException {
        final HttpEntity<WebsiteDTO> request = new HttpEntity<>(websiteDTO);

        try {
            return restTemplate.postForObject(portalURL + URI_ADD_WEBSITE,request,WebsiteDTO.class);
        } catch (Exception e) {
            throw new PortalConnectorException("Could not add website", e);
        }
    }

    private RestTemplate getRestTemplate() {
        CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }
}
