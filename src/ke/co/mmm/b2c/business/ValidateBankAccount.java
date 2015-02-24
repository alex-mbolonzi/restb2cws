/*
    Author: Alex Mbolonzi

 */
package ke.co.mmm.b2c.business;

//import java.util.ArrayList;
//import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
//import org.openqa.selenium.example.MpesaValidate;
//import ke.co.am.ISOIntegrator.AmIsoJob;
//import ke.co.ars.dao.MobileSubscriberDAO;
//import ke.co.ars.dao.TransferB2CDAO;
//import ke.co.ars.entity.MobileSubscriber;
import ke.co.mmm.b2c.entity.Response;
//import ke.co.ars.entity.StatusCode;

//import com.vodafone.mm.gen.api_v1.mminterface.client.MpesaB2cJob;

public class ValidateBankAccount {
    
    /* Get actual class name to be printed on */
    static Logger log = Logger.getLogger(ValidateBankAccount.class.getName());

    public ValidateBankAccount() {
    
      //PropertiesConfigurator is used to configure logger from properties file
        
        PropertyConfigurator.configure("/opt/log4j/B2CWebserviceLog4j.properties");
    }
    
    public Response validateMobileNumber(String sourceMSISDN,String destBankAcc, String bankCode,
            String countryCode, String transactionID, int sourceID, String source) {

        log.info("validateBankAccount() : " + bankCode);
        
//        MobileSubscriberDAO subscriberDAO = new MobileSubscriberDAO();

//        List<MobileSubscriber> subscriberDetails = new ArrayList<MobileSubscriber>();

        Response response = new Response();
        // check if number exists in the MM subscriber db

//        subscriberDetails = subscriberDAO.findByMSISDN(MSISDN);

//        MobileSubscriber subscriberValidate = new MobileSubscriber();
        // if no record is found get the details from the mpesa site
//        if (subscriberDetails.isEmpty()) {
        
//        String responseString = null;
        

        
        StringTokenizer stringTokenizer = null;
        
        String status = null;
        
        String responseDesc = null;

            switch (bankCode) {

                case "KCBLKE":
             
                    String host = null;
//                    AmIsoJob isoJob = new AmIsoJob();

                    host = "SRV" + bankCode + "01";

                    String isoResponse = null;
                    
//                    isoResponse = isoJob.kycData(host,sourceMSISDN,destBankAcc,transactionID);

                    stringTokenizer = new StringTokenizer(isoResponse, "$");
                    
                    status = stringTokenizer.nextElement().toString().trim();

                    responseDesc = stringTokenizer.nextElement().toString().trim();
                    
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
                    response.setResponseData(stringTokenizer.nextElement().toString().trim());
                    response.setTransactionID(transactionID);

                    break;
            }


        return response;
    }
    
//    public TransactionStatus getMpesaValidationStatus(String transactionID, int sourceID) {
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
