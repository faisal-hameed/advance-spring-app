package pk.habsoft.demo.estore.aop.audit;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopAuditor {

    /**
     * Auditing all methods having Auditable annotation.
     *
     * @param auditable
     *            the auditable
     */
    @Before("execution(* pk.habsoft.demo.estore.service.*.*(..)) && @annotation(auditable)")
    public void audit(Auditable auditable) {
    	// AuditDAO.save(log)
        System.out.println("Auditing .. " + auditable);
    }

}
