package pk.habsoft.demo.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pk.habsoft.demo.estore.core.Endpoints;
import pk.habsoft.demo.estore.core.ResponseBuilder;
import pk.habsoft.demo.estore.db.RoleDao;

@RestController
@RequestMapping(value = Endpoints.Common.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommonUtilsController {

    @Autowired
    private RoleDao rolesDao;

    @RequestMapping(method = RequestMethod.GET, value = Endpoints.Common.GET_USER_ROLES)
    public ResponseEntity<?> getUserRoles() {
        return ResponseBuilder.success(rolesDao.findRolesByIsInternal(false));
    }

    @RequestMapping(method = RequestMethod.GET, value = Endpoints.Common.PING_SERVER)
    public ResponseEntity<?> ping() {
        return ResponseBuilder.success("Server is running.");
    }

    /*
     * @RequestMapping(method = RequestMethod.GET, value =
     * RestApi.Common.DOWNLOAD_LATEST_EMPRO, produces =
     * MediaType.APPLICATION_OCTET_STREAM_VALUE) public ResponseEntity<?>
     * downloadEmproClient(@RequestParam(value = "os") EmproOsEnum os) {
     * Resource resource = versionService.downloadEmproClient(os); return
     * ResponseEntity.ok().contentType(MediaType.parseMediaType(
     * "application/octet-stream")) .header(HttpHeaders.CONTENT_DISPOSITION,
     * "attachment; filename=\"" + resource.getFilename() + "\"")
     * .body(resource); }
     */

}