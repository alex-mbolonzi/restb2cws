/*
    Author: Alex Mbolonzi

 */
package ke.co.mmm.b2c.business;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.vodafone.mm.gen.api_v1.mminterface.client.MpesaB2cJob;

import ke.co.am.ISOIntegrator.AmIsoJob;
//import ke.co.mmm.b2c.B2CResource;
import ke.co.ars.dao.TransferB2CDAO;
import ke.co.mmm.b2c.entity.Response;
import ke.co.ars.entity.StatusCode;
import ke.co.ars.entity.TrxResponse;

import ke.co.abc.ISOIntegrator.AbcIsoJob;

public class Card2Mobile {
    
    /* Get actual class name to be printed on */
    static Logger log = Logger.getLogger(Card2Mobile.class.getName());
    
    public Card2Mobile() {
        
      //PropertiesConfigurator is used to configure logger from properties file
        
        PropertyConfigurator.configure("/opt/log4j/B2CWebserviceLog4j.properties");
    }

    public Response card2Mobile(String MNO,String countryCode, 
            String transactionID, String sourceMobile, String destMobile,
            String amount, int sourceID, String source, String beneficiary_name) {

        log.info("card2Mobile() : " + MNO);
        
        Response response = new Response();
        
//        String responseString = null;
       
        String host = null;
        
//        StringTokenizer stringTokenizer = null;
        
        String status = null;
        
        String responseDesc = null;
        
        TrxResponse c2mTransactionResponse = null;
        
            switch (MNO) {

                case "ATKE":

                    host = "SRV" + MNO + "01";

                    switch(source.toUpperCase()){
                    
                    	case "PCI CLOUD":
                    	
                    		AmIsoJob isoJob = new AmIsoJob();
                    
                    		c2mTransactionResponse = new TrxResponse();
                    		
                    		c2mTransactionResponse = isoJob.card2Mobile(host,destMobile,sourceMobile,
                            amount,transactionID,sourceID,source,beneficiary_name);
                    		
//                    		responseString = "00" + "$" + "AUTHORISED" + "$" + "";
                    		
                    	break;
                    	
                    	case "POAPAYKE":
                        	
                        	AbcIsoJob abcIsoJob = new AbcIsoJob();
                        	
                        	c2mTransactionResponse = new TrxResponse();
                        	
                        	c2mTransactionResponse = abcIsoJob.card2Mobile(host,destMobile,sourceMobile,
                                    amount,transactionID,sourceID,source,beneficiary_name);
                        break;
                    	
                    }
//                    stringTokenizer = new StringTokenizer(responseString, "$");
                    
//                    status = stringTokenizer.nextElement().toString().trim();
                    
                    
                    switch (c2mTransactionResponse.getStatusCode()) {
                        
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
                    response.setResponseDescription(c2mTransactionResponse.getStatusDescription());
                    response.setResponseData(c2mTransactionResponse.getTransactionData());
                    response.setTransactionID(transactionID);
                    
                    break;
                    
                    case "SAFKE":

                    host = "SRV" + MNO + "01";

                    
                    switch(source.toUpperCase()){
                    
                    case "PCI CLOUD":
                    	
                    	MpesaB2cJob mpesab2cJob = new MpesaB2cJob();
                        
                    	c2mTransactionResponse = new TrxResponse();
                    	
                    	c2mTransactionResponse = mpesab2cJob.card2Mobile(host,destMobile,sourceMobile,
                                amount,transactionID,sourceID,source,beneficiary_name);
                    	break;
                    	
                    case "POAPAYKE":
                    	
                    	AbcIsoJob abcIsoJob = new AbcIsoJob();
                    	
                    	c2mTransactionResponse = new TrxResponse();
                    	
                    	c2mTransactionResponse = abcIsoJob.card2Mobile(host,destMobile,sourceMobile,
                                amount,transactionID,sourceID,source,beneficiary_name);
                    	break;
                    }
                    

//                    stringTokenizer = new StringTokenizer(responseString, "$");
//                    
//                    status = stringTokenizer.nextElement().toString().trim();
//                    
//                    responseDesc = stringTokenizer.nextElement().toString().trim();
//                    
                    switch (c2mTransactionResponse.getStatusCode()) {
                        
                        case 0:        
                            
                            StatusCode checkTrxStatus = new StatusCode();
                            
                            checkTrxStatus = getTransactionStatus(transactionID,sourceID);
                            
                            responseDesc = checkTrxStatus.getStatusDescription();
                            
                            int validationStatus = checkTrxStatus.getStatusCode();
                            
                            log.info("validation Status : " + validationStatus + " ResponseDesc : " + responseDesc);
                            
                            switch(validationStatus) {
   
                                case 0 :
                                    
                                    status = "00";
                                    break;
                                    
                                case 14 :
                                    
                                    status = "M0002";
                                    break;
                                    
                                default :
                                    
                                    status = "M0005";
                                    break;
                            }
//                            status = "00";
                        break;
                        
                        default:                            
                            status = "M0007";                            
                        break;
                    }
                    
                    response.setResponseStatus(status);
                    response.setResponseDescription(responseDesc);
//                    response.setResponseData(stringTokenizer.nextElement().toString().trim());
                    response.setTransactionID(transactionID);
                    
                    break;   
            }


        return response;
    }
    
    public StatusCode getTransactionStatus(String transactionID, int sourceID) {
        
//        List<StatusCode> trxStatusList = new ArrayList<StatusCode>();
        
        StatusCode trxStatus = new StatusCode();
        
//        int waitTime = 10000;
        
//        try {
            
//            Thread.sleep(waitTime);
            
            TransferB2CDAO transferDAO = new TransferB2CDAO();
            
            trxStatus = transferDAO.findTransactionStatus(transactionID,sourceID,3);
            
//            log.info("getMpesaValidationStatus() : List size" + trxStatusList.size());
            
//            if(trxStatusList.size() > 0) {
//                
//                trxStatus = trxStatusList.get(0);
//                
//            }
            
            log.info("getMpesaValidationStatus() : " + trxStatus.getStatusCode());
            
            if(trxStatus.getStatusCode() == 100) {
                
                trxStatus.setStatusCode(0);
                trxStatus.setStatusDescription("Processing request.");
            }

//        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
//            log.error("Exception: ",e.fillInStackTrace());
            
//        }
        return trxStatus;
    }
}
