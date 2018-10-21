package pk.habsoft.demo.estore.config;

import java.beans.PropertyEditorSupport;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import pk.habsoft.demo.estore.enums.RoleType;

@ControllerAdvice
public class GlobalControllerAdvice {

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(RoleType.class, new RoleTypeConverter());
    }

    class RoleTypeConverter extends PropertyEditorSupport {

        @Override
        public void setAsText(final String text) {
            setValue(RoleType.fromText(text));
        }

    }

}
