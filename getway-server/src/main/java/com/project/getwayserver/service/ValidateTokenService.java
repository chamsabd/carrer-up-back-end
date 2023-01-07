// package com.project.getwayserver.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;

// import com.project.getwayserver.dao.AuthenticationServiceRepository;
// import com.project.getwayserver.model.ConnValidationResponse;

// @Service
// public class ValidateTokenService {

// //    @Autowired
// //    private AuthenticationServiceProxy authenticationServiceProxy;

//     @Autowired
//     private AuthenticationServiceRepository authenticationServiceRepository;


//     public ResponseEntity<ConnValidationResponse> validateAuthenticationToken(String bearerToken) throws Exception {

//         if(Utilities.isTokenValid(bearerToken)) {
//             String token = bearerToken.replace("Bearer ", "");

//             try {
// //                ResponseEntity<ConnValidationResponse> responseEntity = authenticationServiceProxy.validateConnection(token);
// //                if(responseEntity.getStatusCode().is2xxSuccessful()) {
// //                    return responseEntity.getBody();
// //                } else if(responseEntity.getStatusCode().is4xxClientError()) {
// //                    int errorCode = responseEntity.getStatusCode().value();
// //                    if(errorCode==401) {
// //                        throw new BusinessException("", HttpStatus.UNAUTHORIZED, "Unauthorized");
// //                    } else if(errorCode==403) {
// //                        throw new BusinessException("", HttpStatus.FORBIDDEN, "Forbidden");
// //                    }
// //                }

//                 return AuthenticationServiceProxy.validateConnection(bearerToken);

//             } catch (Exception e) {
//                 throw new Exception( "Unauthorized");

//             }
//         }

//         throw new Exception( "Unauthorized");

//     }

// }