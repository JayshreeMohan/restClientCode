package com.proj.restClientCode;

import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Supplier;

@Component
public class RestCallExecutor<R> {

    private String url;
    private String requestJson;
    private String applicationId;
    private HttpHeaders httpHeaders;
    private HttpMethod httpMethod;
    private Map<String, ?> uriVariables;
    private Class<R> responseType;
    private String source;
    private String target;
    private RestTemplate restTemplateInstance;

    private static String CLASS_NAME = "RestClientExecutor";


    public RestCallExecutor setSource(String source) {
        this.source = source;
        return this;
    }


    public RestCallExecutor setTarget(String target) {
        this.target = target;
        return this;
    }

    public RestCallExecutor setUrl(String url) {
        this.url = url;
        return this;
    }

    public RestCallExecutor setRequestJson(String requestJson) {
        this.requestJson = requestJson;
        return this;
    }

    public RestCallExecutor setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }


    public RestCallExecutor setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public RestCallExecutor setHttpHeaders(Supplier func) {
        this.httpHeaders = (HttpHeaders) func.get();
        return this;
    }

    public RestCallExecutor setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public RestCallExecutor setUriVariables(Map<String, ?> uriVariables) {
        this.uriVariables = uriVariables;
        return this;
    }

    public RestCallExecutor setResponseType(Class<R> responseType) {
        this.responseType = responseType;
        return this;
    }

    public RestCallExecutor withRestTemplateInstance(RestTemplate restTemplateInstance) {
        this.restTemplateInstance = restTemplateInstance;
        return this;
    }


    public R execute() {
        boolean exceptionOccurred = false;
        Long startTime = null;
        Long endTime = null;
        String status = "FAILURE";
        ResponseEntity responseEntity = null;
        String failureBody = null;
        try {


            HttpEntity entity = null;
            if (StringUtils.isBlank(requestJson)) {
                entity = new HttpEntity<>(httpHeaders);
            } else {
                entity = new HttpEntity<>(requestJson, httpHeaders);
            }

            startTime = System.currentTimeMillis();
            if (uriVariables != null) {
                responseEntity = restTemplateInstance.exchange(url, httpMethod, entity, responseType, uriVariables);
            } else {
                responseEntity = restTemplateInstance.exchange(url, httpMethod, entity, responseType);
            }
            endTime = System.currentTimeMillis();
            status = "SUCCESS";

            return (R) responseEntity.getBody();
        }
        catch(Exception e) {
            endTime = System.currentTimeMillis();
            failureBody = e.getMessage();
            throw e;
        } finally {
//
//            TypedRemoteApiTrackingVO remoteApiTrackingBean = new TypedRemoteApiTrackingVO();
//            remoteApiTrackingBean.setApplicationId(Long.parseLong(applicationId));
//            remoteApiTrackingBean.setRequestPayload(requestJson == null ? null : requestJson);
//            remoteApiTrackingBean.setRequestTimeStamp(startTime);
//            remoteApiTrackingBean.setResponsePayload(responseEntity == null ? null : responseEntity.getBody().toString());
//            remoteApiTrackingBean.setResponseTimeStamp(endTime);
//            remoteApiTrackingBean.setSource(source);
//            remoteApiTrackingBean.setTarget(target);
//            remoteApiTrackingBean.setStatus(status);
//            remoteApiTrackingBean.setTargetApi(url);
//            remoteApiTrackingBean.setError(failureBody);
//            remoteApiTrackingBean.setCorrelationId(customDefaultHeaders.getCmptcorrid());
//
//            remoteApiTracking.saveRemoteRequestResponse(remoteApiTrackingBean);
        }
    }

}
