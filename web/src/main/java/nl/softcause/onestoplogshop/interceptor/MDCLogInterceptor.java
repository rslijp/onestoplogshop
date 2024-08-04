package nl.softcause.onestoplogshop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
//@AllArgsConstructor
public class MDCLogInterceptor implements HandlerInterceptor {

    public MDCLogInterceptor(@Value(value = "${info.version}") String version,
                             @Value(value = "${info.nodeId}") String nodeId,
                             @Value(value = "${spring.application.name}") String applicationName) {
        this.version = version;
        this.nodeId = nodeId;
        this.applicationName = applicationName;
    }

    private final String version;

    private final String nodeId;

    private final String applicationName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put("version", version);
        MDC.put("nodeId", nodeId);
        MDC.put("applicationName", applicationName);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable
            Exception ex) {
        MDC.clear();
    }
}
