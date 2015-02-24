/*
    Author: Alex Mbolonzi

 */
package ke.co.mmm.b2c.business;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//import org.openqa.selenium.example.MpesaValidate;
//import ke.co.am.ISOIntegrator.AmIsoJob;
//import ke.co.ars.dao.MobileSubscriberDAO;
//import ke.co.ars.dao.TransferB2CDAO;
//import ke.co.ars.entity.MobileSubscriber;
import ke.co.mmm.b2c.entity.Response;
import ke.co.ars.entity.StatusCode;
import ke.co.ars.entity.TrxResponse;

import com.vodafone.mm.gen.api_v1.mminterface.client.MpesaB2cJob;

public class ValidateMSISDN {
    
    /* Get actual class name to be printed on */
    static Logger log = Logger.getLogger(ValidateMSISDN.class.getName());

    public ValidateMSISDN() {
    
      //PropertiesConfigurator is used to configure logger from properties file
        
        PropertyConfigurator.configure("/opt/log4j/B2CWebserviceLog4j.properties");
    }
    
    public Response validateMobileNumber(String sourceMSISDN,String MSISDN, String MNO,
            String countryCode, String transactionID, int sourceID, String source) {

        log.info("validateMobileNumber() : " + MNO);

        Response response = new Response();

//        String responseString = null;
        
        String host = null;
        
//        StringTokenizer stringTokenizer = null;
        
        String status = null;
        
        String responseDesc = null;
        
        String beneficiary_name = null;

        TrxResponse validTransactionResponse = null;
        
            switch (MNO) {

                case "SAFKE":

                    host = "SRV" + MNO + "01";
                    
                    MpesaB2cJob mpesab2cJob = new MpesaB2cJob();
                    
                    validTransactionResponse = new TrxResponse();
                    
                    validTransactionResponse = mpesab2cJob.card2Mobile(host,MSISDN,sourceMSISDN,
                            "0","VAL"+transactionID,sourceID,source,beneficiary_name);

//                    stringTokenizer = new StringTokenizer(responseString, "$");
//                    
//                    status = stringTokenizer.nextElement().toString().trim();
//
//                    responseDesc = stringTokenizer.nextElement().toString().trim();
//                    
                    switch (validTransactionResponse.getStatusCode()) {
                        
                        case 0:        
                            
                            StatusCode validationTrxStatus = new StatusCode();
                            
//                            validationTrxStatus = getMpesaValidationStatus(transactionID,sourceID);
                            
                            responseDesc = validationTrxStatus.getStatusDescription();
                            
                            int validationStatus = validationTrxStatus.getStatusCode();
                            
                            log.info("validation Status : " + validationStatus + " ResponseDesc : " + responseDesc);
                            
                            switch(validationStatus) {
                                
                                case 14 :
                                    
                                    status = "M0004";
                                    break;
                                    
                                case 2 :
                                    
                                    status = "00";
                                    break;
                                    
                                default :
                                    
                                    status = "M0007";
                                    break;
                            }

                        break;

                        default:                            
                            status = "M0007";                            
                        break;
                    }

                    response.setResponseStatus(status);
                    response.setResponseDescription(responseDesc);
                    response.setResponseData("");
                    response.setTransactionID(transactionID);

                    break;

                case "ATKE":

                    host = "SRV" + MNO + "01";

//                    String isoResponse = null;
                    
                    status = "00";
                    
                    responseDesc = "AUTHORISED";
                    
                    switch (status) {
                        
                        case "00":                         
                            status = "00";
                        break;
                        
                        case "25":       
                            status = "M0002";                            
                        break;
                        
                        default:                            
                            status = "M0006";                            
                        break;
                    }
                    
                    response.setResponseStatus(status);
                    response.setResponseDescription(responseDesc);
//                    response.setResponseData(stringTokenizer.nextElement().toString().trim());
                    response.setResponseData(null);
                    response.setTransactionID(transactionID);

                    break;
            }


        return response;
    }
    
//    public StatusCode getMpesaValidationStatus(String transactionID, int sourceID) {
//             
//        List<TransactionStatus> trxStatusList = new ArrayList<TransactionStatus>();
//        
//        TransactionStatus trxStatus = new TransactionStatus();
//        
////        int waitTime = 10000;
//        
////        try {
//            
////            Thread.sleep(waitTime);
//            
//            TransferB2CDAO transferDAO = new TransferB2CDAO();
//            
//            trxStatusList = transferDAO.findStatus("VAL"+transactionID,sourceID);
//            
//            log.info("getMpesaValidationStatus() : List size" + trxStatusList.size());
//            
//            if(trxStatusList.size() > 0) {
//                
//                trxStatus = trxStatusList.get(0);
//                
//            }
//            
//            log.info("getMpesaValidationStatus() : " + trxStatus.getStatusID());
//            
////            if(trxStatus.getStatusID() < 0) {
////                
////                getMpesaValidationStatus(transactionID,sourceID);
////            }
//
////        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
////            e.printStackTrace();
////            log.error("Exception: ",e.fillInStackTrace());
//            
////        }
//        return trxStatus;
//    }
}
