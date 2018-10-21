package pk.habsoft.demo.estore.config.interceptors;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("adding request filterr....." + handler);
        // printheaders(request);
        return true;
    }

    private void printheaders(HttpServletRequest req) {
        // TODO Auto-generated method stub
        Enumeration<String> headerNames = req.getHeaderNames();

        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();

            Enumeration<String> headers = req.getHeaders(headerName);
            System.out.println("Header : " + headerName);
            while (headers.hasMoreElements()) {

                String headerValue = headers.nextElement();
                System.out.println(" >> Header value: " + headerValue);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}