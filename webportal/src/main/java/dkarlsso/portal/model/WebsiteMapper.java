package dkarlsso.portal.model;

import portalconnector.model.WebsiteDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebsiteMapper {


    public static WebsiteDTO map(final WebsiteDAO dao) {
        final WebsiteDTO dto = new WebsiteDTO();
        dto.setImageBase64(dao.getImageBase64());
        dto.setWebsiteDescription(dao.getWebsiteDescription());
        dto.setWebsiteLink(dao.getWebsiteLink());
        dto.setWebsiteName(dao.getWebsiteName());
        dto.setPermission(dao.getPermission());
        return dto;
    }

    public static WebsiteDAO map(final WebsiteDTO dto) {
        final WebsiteDAO dao = new WebsiteDAO();
        dao.setImageBase64(dto.getImageBase64());
        dao.setWebsiteDescription(dto.getWebsiteDescription());
        dao.setWebsiteLink(dto.getWebsiteLink());
        dao.setWebsiteName(dto.getWebsiteName());
        dao.setPermission(dto.getPermission());
        dao.setDateSinceLastConnection(new Date());
        return dao;
    }

    public static List<WebsiteDTO> mapDaos(final List<WebsiteDAO> list) {
        final List<WebsiteDTO> daoList= new ArrayList<>();

        for(WebsiteDAO dto : list) {
            daoList.add(map(dto));
        }
        return daoList;
    }

    public static List<WebsiteDAO> mapDtos(final List<WebsiteDTO> list) {
        final List<WebsiteDAO> daoList= new ArrayList<>();

        for(WebsiteDTO dto : list) {
            daoList.add(map(dto));
        }
        return daoList;
    }

}
