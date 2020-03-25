package dkarlsso.portal.domain.model;

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
        dto.setLocalWebsiteLink(dao.getLocalWebsiteLink());
        dto.setWebsiteId(dao.getWebsiteId());
        dto.setHasLogin(dao.isHasLogin());
        dto.setInfoLink(dao.getInfoLink());
        return dto;
    }

    public static WebsiteDAO map(final WebsiteDTO dto) {
        final WebsiteDAO dao = new WebsiteDAO();
        dao.setWebsiteId(dto.getWebsiteId());
        dao.setImageBase64(dto.getImageBase64());
        dao.setWebsiteDescription(dto.getWebsiteDescription());
        dao.setWebsiteLink(dto.getWebsiteLink());
        dao.setWebsiteName(dto.getWebsiteName());
        dao.setPermission(dto.getPermission());
        dao.setLocalWebsiteLink(dto.getLocalWebsiteLink());
        dao.setDateSinceLastConnection(new Date());
        dao.setHasLogin(dto.isHasLogin());
        dao.setInfoLink(dto.getInfoLink());
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
