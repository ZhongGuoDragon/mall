package com.tom.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.tom.domain.WebLog;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
@Aspect
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.tom.controller.*.*(..))||execution(public * com.tom.*.controller.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @AfterReturning(pointcut = "webLog()", returning = "arg")
    public void doAfterReturning(Object arg) throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = attributes.getRequest();
        WebLog webLog = new WebLog();
        Object object=joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            webLog.setDescription(method.getAnnotation(ApiOperation.class).value());
        }
        Object[] args=joinPoint.getArgs();
        String url = httpServletRequest.getRequestURL().toString();
        String bashPath = StrUtil.removeSuffix(url, URLUtil.url(url).getPath());
        webLog.setBasePath(bashPath);
        webLog.setIp(httpServletRequest.getRemoteUser());
        webLog.setMethod(httpServletRequest.getMethod());
        webLog.setParameter(getParameter(method, args));
        int spendTime =(int) (endTime - startTime);
        webLog.setSpendTime(spendTime);
        webLog.setStartTime(startTime);
        webLog.setUri(httpServletRequest.getRequestURI());
        webLog.setUrl(url);
        Map<String, Object> map = new HashMap<>();
        map.put("url", webLog.getUrl());
        map.put("method", webLog.getMethod());
        map.put("parameter", webLog.getParameter());
        map.put("spendtime", webLog.getSpendTime());
        map.put("description", webLog.getDescription());
        LOGGER.info(Markers.appendEntries(map), JSONUtil.parse(webLog).toString());
        return object;
    }


    private Object getParameter(Method method, Object[] objects) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(objects[i]);
            }
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, objects[i]);
            }
        }
        if (argList.isEmpty()) {
            return null;
        }
        if (argList.size() == 1) {
            return argList.get(0);
        }
        return argList;
    }
}
