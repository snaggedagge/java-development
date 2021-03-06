package portalconnector;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import portalconnector.model.PortalConnectorException;
import portalconnector.model.WebsiteDTO;

import javax.net.ssl.HttpsURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class PortalConnector {

    public static final List<String> urlLocations = new ArrayList<>();
    public static final String URL_HTTPS_STANDARD_PORTAL = "https://dkarlsso.com"; // >Domain not existing yet
    public static final String URL_HTTP_STANDARD_PORTAL = "http://dkarlsso.com"; // >Domain not existing yet


    public static final String URI_ADD_WEBSITE = "/webportal/{websiteId}";

    public static final String URI_GET_ALL_WEBSITES = "/webportal/websites";

    private String latestConnectedUrl;

    private final RestTemplate restTemplate = getRestTemplate();

    public PortalConnector() {
        urlLocations.add(URL_HTTP_STANDARD_PORTAL);
        urlLocations.add(URL_HTTPS_STANDARD_PORTAL);
    }

    public PortalConnector(final String... urlPortal) {
        super();
        urlLocations.addAll(Arrays.asList(urlPortal));
    }

    // TODO: Should probably have some kind of hashed clientSecret for this...
    public WebsiteDTO addWebsite(final WebsiteDTO websiteDTO, final boolean httpsActivated, final int port)
            throws PortalConnectorException {
        final String uriAddWebsite = URI_ADD_WEBSITE.replace("{websiteId}", websiteDTO.getWebsiteId());
        final String prefix = httpsActivated ? "https://" : "http://";

        final String portString = port == 80 ? "" : ":" + port;
        if (websiteDTO.getWebsiteLink() == null) {
            websiteDTO.setWebsiteLink(prefix + restTemplate.getForObject("http://bot.whatismyipaddress.com/", String.class) + portString);
        }

        if (websiteDTO.getLocalWebsiteLink() == null) {
            websiteDTO.setLocalWebsiteLink(prefix + getLocalAdress() + portString);
        }

        for (int i=0;i<urlLocations.size(); i++) {
            String location = urlLocations.get(i);

            if (latestConnectedUrl != null) {
                location = latestConnectedUrl;
                i--;
            }

            final ResponseEntity<WebsiteDTO> response =
                    restTemplate.exchange(location + uriAddWebsite, HttpMethod.PUT, new HttpEntity<>(websiteDTO), WebsiteDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                latestConnectedUrl = location;
                return response.getBody();
            }
            latestConnectedUrl = null;
        }

        throw new PortalConnectorException("Could not add website");
    }

    private RestTemplate getRestTemplate() {

        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }

    private String getLocalAdress() {
        String localAdress = null;
        try {
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements()) {
                final Enumeration<InetAddress> addressEnumeration = networkInterfaces.nextElement().getInetAddresses();
                while(addressEnumeration.hasMoreElements()) {
                    final InetAddress inetAddress = addressEnumeration.nextElement();
                    if (inetAddress.getHostAddress().contains("192.")) {
                        localAdress = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (final Exception e) {
            try {
                localAdress = InetAddress.getLocalHost().getHostAddress();
            } catch (final Exception e1) { }
        }
        return localAdress;
    }
}
