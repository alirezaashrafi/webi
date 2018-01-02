package com.ashrafi.webi.classes;

/**
 * Created by AlirezaAshrafi on 1/2/2018.
 */

final class HttpCode {
    protected String httpCode(int code){
        String res = "";
        switch (code){
            case  202: res= "HTTP_ACCEPTED           ";break;
            case  502: res= "HTTP_BAD_GATEWAY        ";break;
            case  405: res= "HTTP_BAD_METHOD         ";break;
            case  400: res= "HTTP_BAD_REQUEST        ";break;
            case  408: res= "HTTP_CLIENT_TIMEOUT     ";break;
            case  409: res= "HTTP_CONFLICT           ";break;
            case  201: res= "HTTP_CREATED            ";break;
            case  413: res= "HTTP_ENTITY_TOO_LARGE   ";break;
            case  403: res= "HTTP_FORBIDDEN          ";break;
            case  504: res= "HTTP_GATEWAY_TIMEOUT    ";break;
            case  410: res= "HTTP_GONE               ";break;
            case  411: res= "HTTP_LENGTH_REQUIRED    ";break;
            case  301: res= "HTTP_MOVED_PERM         ";break;
            case  302: res= "HTTP_MOVED_TEMP         ";break;
            case  300: res= "HTTP_MULT_CHOICE        ";break;
            case  406: res= "HTTP_NOT_ACCEPTABLE     ";break;
            case  203: res= "HTTP_NOT_AUTHORITATIVE  ";break;
            case  404: res= "HTTP_NOT_FOUND          ";break;
            case  501: res= "HTTP_NOT_IMPLEMENTED    ";break;
            case  304: res= "HTTP_NOT_MODIFIED       ";break;
            case  204: res= "HTTP_NO_CONTENT         ";break;
            case  200: res= "HTTP_OK                 ";break;
            case  206: res= "HTTP_PARTIAL            ";break;
            case  402: res= "HTTP_PAYMENT_REQUIRED   ";break;
            case  412: res= "HTTP_PRECON_FAILED      ";break;
            case  407: res= "HTTP_PROXY_AUTH         ";break;
            case  414: res= "HTTP_REQ_TOO_LONG       ";break;
            case  205: res= "HTTP_RESET              ";break;
            case  303: res= "HTTP_SEE_OTHER          ";break;
            case  500: res= "HTTP_SERVER_ERROR       ";break;
            case  401: res= "HTTP_UNAUTHORIZED       ";break;
            case  503: res= "HTTP_UNAVAILABLE        ";break;
            case  415: res= "HTTP_UNSUPPORTED_TYPE   ";break;
            case  305: res= "HTTP_USE_PROXY          ";break;
            case  505: res= "HTTP_VERSION            ";break;
        }
        return res;
    }


}
