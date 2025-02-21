//package com.aleksgolds.spring.web.core.controllers.endpoints;
//
//import com.aleksgolds.spring.web.core.services.ProductsService;
//import com.aleksgolds.spring.web.core.soap.products.GetAllProductsRequest;
//import com.aleksgolds.spring.web.core.soap.products.GetAllProductsResponse;
//import com.aleksgolds.spring.web.core.soap.products.GetProductByIdRequest;
//import com.aleksgolds.spring.web.core.soap.products.GetProductByIdResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.ws.server.endpoint.annotation.Endpoint;
//import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
//import org.springframework.ws.server.endpoint.annotation.RequestPayload;
//import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
//
//@Endpoint
//@RequiredArgsConstructor
//public class ProductEndpoint {
//    private static final String NAMESPACE_URI = "http://www.geekbrains.com/spring/web/soap/products";
//    private final ProductsService productsService;
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
//    @ResponsePayload
//    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
//        GetProductByIdResponse response = new GetProductByIdResponse();
//        response.setProduct(productsService.getProductById(request.getId()));
//        return response;
//    }
//
//
//
//
//    /**
//    Пример запроса: POST http://localhost:8189/app/web
//    Header -> Content-Type: text/xml
//
//     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.com/spring/web/soap/products">
//     <soapenv:Header/>
//     <soapenv:Body>
//     <f:getAllProductsRequest/>
//     </soapenv:Body>
//     </soapenv:Envelope>
//
//     */
//
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
//    @ResponsePayload
//    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
//        GetAllProductsResponse response = new GetAllProductsResponse();
//        productsService.getAllProduct().forEach(response.getProducts()::add);
//        return response;
//    }
//
//        /*
//         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.com/spring/web/soap/products">
//     <soapenv:Header/>
//     <soapenv:Body>
//     <f:getProductByIdRequest>
//        <f:id>1</f:id>
//    </f:getProductByIdRequest>
//     </soapenv:Body>
//     </soapenv:Envelope>
//
//     */
//}
