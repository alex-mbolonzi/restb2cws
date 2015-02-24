/*
    Author: Alex Mbolonzi

 */
package ke.co.mmm.b2c.business;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//import com.vodafone.mm.gen.api_v1.mminterface.client.MpesaB2cJob;


//import ke.co.am.ISOIntegrator.AmIsoJob;
//import ke.co.mmm.b2c.B2CResource;
//import ke.co.ars.dao.TransferB2CDAO;
import ke.co.mmm.b2c.entity.Response;
//import ke.co.ars.entity.StatusCode;
import ke.co.ars.entity.TrxResponse;
import ke.co.abc.ISOIntegrator.AbcIsoJob;

public class Card2BankAcc {
    
    /* Get actual class name to be printed on */
    static Logger log = Logger.getLogger(Card2BankAcc.class.getName());
    
    public Card2BankAcc() {
        
      //PropertiesConfigurator is used to configure logger from properties file
        
        PropertyConfigurator.configure("/opt/log4j/B2CWebserviceLog4j.properties");
    }

    public Response card2Acc(String bankCode,String countryCode, 
            String transactionID, String sourceMobile, String creditBankAcc,
            String amount, int sourceID, String source, String beneficiary_name) {

        log.info("card2Acc() : " + bankCode);
        
        Response response = new Response();
        
//        String responseString = null;
       
        String host = null;
        
//        StringTokenizer stringTokenizer = null;
        
        String status = null;
        
//        String responseDesc = null;
        TrxResponse c2bTransactionResponse = null;
        
            switch (bankCode) {

                case "ABCLKENA":

                    host = "SRV" + bankCode + "01";

                    switch(source.toUpperCase()){

                    	case "POAPAYKE":
                        	
                        	AbcIsoJob abcIsoJob = new AbcIsoJob();
                        	
                        	c2bTransactionResponse = new TrxResponse();
                        	
                        	c2bTransactionResponse = abcIsoJob.card2BankAcc(host,creditBankAcc,sourceMobile,
                                    amount,transactionID,sourceID,source,beneficiary_name);
                        break;
                    	
                    }
//                    stringTokenizer = new StringTokenizer(responseString, "$");
//                    
//                    status = stringTokenizer.nextElement().toString().trim();
                    
                    switch (c2bTransactionResponse.getStatusCode()) {
                        
                        case 0:                         
                            status = "00";
                        break;
                        
                        case 7:                         
                            status = "M0002";
                        break;
                        
                        default:                            
                            status = "M0006";                            
                        break;
                    }

                    response.setResponseStatus(status);
                    response.setResponseDescription(c2bTransactionResponse.getStatusDescription());
                    response.setResponseData(c2bTransactionResponse.getTransactionData());
                    response.setTransactionID(transactionID);
                    
                    break;
                      
            }


        return response;
    }
    
//    public TransactionStatus getTransactionStatus(String transactionID, int sourceID) {
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
//            trxStatusList = transferDAO.findStatus(transactionID,sourceID);
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
//            if(trxStatus.getStatusID() < 0) {
//                
//                trxStatus.setStatusID(0);
//                trxStatus.setStatusDescription("Processing request.");
//            }
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
