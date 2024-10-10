package br.com.matheus.vendas.online.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RestRequest {

    private HttpHeaders headers;

    private Object bodyuObject;
    private MultiValueMap<String,Object> body;
    private HttpMethod method;

    public HttpMethod getMethod(){
        return this.method;
    }

    public enum DispositionType {

        INLINE("inline"), ATTACHMENT("attachment"), FORM_DATA("form-data");

        private String type;

        DispositionType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public RestRequest(HttpMethod method, Object body) {
        this.bodyuObject = body;
        this.method = method;
        this.headers = new HttpHeaders();
        this.body = new LinkedMultiValueMap<>();
        
    }

    public void setContentType(@Nullable MediaType mediaType) {
        this.headers.setContentType(mediaType);
    }

    public void setAcceptable(List<MediaType> acceptableMediaTypes) {
       this.headers.setAccept(acceptableMediaTypes);
    }

    public HttpEntity<?> getHttpEntity() {
        if (this.bodyuObject != null && this.bodyuObject instanceof HttpEntity<?>) {
            return (HttpEntity<?>) bodyuObject;
        } else if(!this.body.isEmpty()){
            return new HttpEntity<>(body,getHeaders());
        } else{
            return new HttpEntity<>(bodyuObject,getHeaders());
        }
    }

    private HttpHeaders getHeaders() {
        return headers;
    }

    

}
