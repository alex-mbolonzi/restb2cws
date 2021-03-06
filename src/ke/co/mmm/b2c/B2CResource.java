
package ke.co.mmm.b2c;


import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
//import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

import ke.co.mmm.b2c.business.Card2BankAcc;
import ke.co.mmm.b2c.business.Card2Mobile;
import ke.co.mmm.b2c.business.ValidateMSISDN;
import ke.co.ars.dao.TransferB2CDAO;
//import ke.co.ars.dao.MobileSubscriberDAO;
import ke.co.mmm.b2c.entity.MethodCall;
//import ke.co.ars.entity.MobileSubscriber;
import ke.co.ars.entity.Mpesab2c;
import ke.co.mmm.b2c.entity.Response;
import ke.co.ars.entity.StatusCode;
//import ke.co.mmm.b2c.entity.Validate;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

//import org.openqa.selenium.example.MpesaValidate;
//import ke.co.mmm.b2c.entity.MethodCall;

@XmlRootElement
@Path("/B2C/")
public class B2CResource {
    
    /* Get actual class name to be printed on */
    static Logger log = Logger.getLogger(B2CResource.class.getName());
    
    public B2CResource() {
  
        //PropertiesConfigurator is used to configure logger from properties file
    
        PropertyConfigurator.configure("/opt/log4j/B2CWebserviceLog4j.properties");
    }
    
    TransferB2CDAO B2Cdao = new TransferB2CDAO();
   
    
//    @GET @Path("findByDate")
//    @Produces({MediaType.APPLICATION_XML })
//    public List<Mpesab2c> findByDate(@QueryParam("startDate") String startDate, 
//            @QueryParam("startDate") String endDate) {
//        return B2Cdao.findByDate(startDate,endDate);
//    }
    
//    @GET @Path("findStatus")
//    @Produces({MediaType.APPLICATION_XML })
//    public List<Mpesab2c> findStatus(@QueryParam("MSISDN") String MSISDN) {
//        return B2Cdao.findStatus(MSISDN);
//    }
    
//    @GET @Path("findReceipt")
//    @Produces({MediaType.APPLICATION_XML })
//    public List<Mpesab2c> findReceipt(@QueryParam("receiptNumber") String receiptNumber) {
//        return B2Cdao.findByReceiptNumber(receiptNumber);
//    }
    
//    @POST 
//    @Path("sendMoney")
//    @Consumes("application/x-www-form-urlencoded")
////    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//    @Produces({MediaType.APPLICATION_XML })
//    public List<StatusCode> sendMoney(@FormParam("transaction_id") String transactionID,
//            @FormParam("msisdn") String MSISDN,@FormParam("mno") String MNO,
//            @FormParam("country_id") String countryID,  @FormParam("amount") String amount,
//            @FormParam("currency") String currency, @FormParam("tstamp") String tstamp) {
//        
////        List<TransactionStatus> trxStatusList = new ArrayList<TransactionStatus>();
//        
//        Mpesab2c addMpesaB2CTrx = new Mpesab2c();
//        
//        addMpesaB2CTrx.setOrigTrxCode(transactionID);
//        addMpesaB2CTrx.setOrig(MNO);
//        addMpesaB2CTrx.setBusinessNumber(countryID);
//        addMpesaB2CTrx.setOrigTimeStamp(tstamp);
//        addMpesaB2CTrx.setAmount(amount);
//        addMpesaB2CTrx.setSenderName(currency);
//        addMpesaB2CTrx.setCurrency("KES");
//        
////        trxStatusList = B2Cdao.addMpesaC2BTrx(addMpesaC2BTrx);
//        
//        return B2Cdao.addMpesaC2BTrx(addMpesaB2CTrx);
//    }
    
    @POST
    @Path("actionHandler")
//    @Consumes("application/x-www-form-urlencoded")
//    @Produces("text/plain")
    @Consumes("text/xml")
    @Produces({MediaType.TEXT_XML})
//    @Produces("text/xml")
    public MethodCall postHandler(MethodCall call) {
        
//        MethodCall call2 = new MethodCall();
        log.info("postHandler() : " + call);
        
        MethodCall.Params params = call.getParams();
        
        List<MethodCall.Params.Param> paramList = params.getParam();
        
        MethodCall.Params.Param param = paramList.get(0);
        
        MethodCall.Params.Param.Value value = param.getValue();
        
        MethodCall.Params.Param.Value.Struct struct = value.getStruct();
        
        List <MethodCall.Params.Param.Value.Struct.Member> memberList = struct.getMember();
          
        MethodCall.Params.Param param2 = paramList.get(1);
        
        MethodCall.Params.Param.Value valueTwo = param2.getValue();

        List<MethodCall.Params.Param.Value.Struct.Member.Value2.Struct2.Member2> memberList2 = null;
        
        String method = valueTwo.getString();
//        String method = call.getMethodName();
                
        String mobile = null;
        String mno = null;
        String country_code = null;
        String transactionId = null;
        String destMobile = null;
        String amount = null;
        int sourceID = 0;
        String source = null;
        String creditBankAcc = null;
        String bankCode = null;
        String beneficiary_name = null;
        
        int arraySize = memberList.size();
        
        Response response = new Response();
        
        if (arraySize > 1 ) {
            
       
        for (int i=0; i<arraySize; i++){
            
            MethodCall.Params.Param.Value.Struct.Member member = memberList.get(i);
            
            MethodCall.Params.Param.Value.Struct.Member.Value2 value2 = member.getValue();
                 
            switch (member.getName()) {
                
                case "origin_msisdn": mobile = value2.getString();
                     break;      
                case "mno": mno = value2.getString();
                     break;
                case "country_code" : country_code = value2.getString();
                     break;
                case "transaction_id" : transactionId = value2.getString();
                     break;
                case "dest_msisdn" : destMobile = value2.getString();

            switch (method) {
            
                        case "bulkcard2mobile":

                    MethodCall.Params.Param.Value.Struct.Member.Value2.Struct2 struct2 = value2.getStruct();

                    memberList2 = struct2.getMember();
                default:
                    destMobile = value2.getString();
                break;
            }
                     break;
                case "beneficiary_name" :
                	beneficiary_name = value2.getString();
                	break;
                case "amount" : 
                	amount = value2.getString();
                     break;
                case "source_id" : 
                	sourceID = Integer.valueOf(value2.getString());
                     break;
                case "source" : 
                	source = value2.getString();
                     break;
                case "dest_bank_account" : 
                	creditBankAcc = value2.getString();
                     break;
                case "bank_code" : 
                	bankCode = value2.getString();
                     break;
            }
            
         }
        
        log.info("method : " + method);
        
        switch (method) {
            
            case "msisdnValidation": 
                
                ValidateMSISDN validateMSISDN = new ValidateMSISDN();
                
                response = validateMSISDN.validateMobileNumber(mobile,destMobile,mno,
                        country_code,transactionId,sourceID,source);
                
                break;
            
            case "card2mobile":
                
                Card2Mobile b2c = new Card2Mobile();
                                
                response = b2c.card2Mobile(mno,country_code,transactionId,
                        mobile,destMobile,amount,sourceID,source,beneficiary_name);
                
                break;

        case "bulkcard2mobile":

        String bulkDestMobile = null;

        String bulkAmount = null;
        
        int arraySize2 = memberList2.size();

        if (arraySize2 > 1 ) {

            for (int x=0; x<arraySize2; x++){

                MethodCall.Params.Param.Value.Struct.Member.Value2.Struct2.Member2 member2 = memberList2.get(x);

                bulkDestMobile = member2.getName();

                MethodCall.Params.Param.Value.Struct.Member.Value2.Struct2.Member2.Value3 value3 = member2.getValue();

                bulkAmount = value3.getString();

                Card2Mobile b2cBulk = new Card2Mobile();
                                
                        response = b2cBulk.card2Mobile(mno,country_code,transactionId + "B" + x,
                                          mobile,bulkDestMobile,bulkAmount,sourceID,source,beneficiary_name);
            }

        }

        break;
                
            case "card2bankaccount":
                
            	Card2BankAcc c2b = new Card2BankAcc();
            	
            	response = c2b.card2Acc(bankCode, country_code, transactionId, 
            			mobile, creditBankAcc, amount, sourceID, source,beneficiary_name);
                break;
//            case "bankAccountValidation":
//                
//                break;
        }
        
        }
        
//        List<Response> responseArray = new ArrayList<Response>();
//        
//        responseArray.add(response);
        
        List <MethodCall.Params.Param.Value.Struct.Member> memberList1 = new ArrayList<MethodCall.Params.Param.Value.Struct.Member>();

        MethodCall.Params.Param.Value.Struct.Member member1 = new MethodCall.Params.Param.Value.Struct.Member();
                
        MethodCall.Params.Param.Value.Struct.Member.Value2 value3 = new MethodCall.Params.Param.Value.Struct.Member.Value2();

        value3.setString(response.getTransactionID());

        member1.setName("transactionID");

        member1.setValue(value3);

        memberList1.add(member1);

        
        MethodCall.Params.Param.Value.Struct.Member member2 = new MethodCall.Params.Param.Value.Struct.Member();
        
        MethodCall.Params.Param.Value.Struct.Member.Value2 value5 = new MethodCall.Params.Param.Value.Struct.Member.Value2();

        
        value5.setString(response.getResponseStatus());

        member2.setName("response_status");

        member2.setValue(value5);

        memberList1.add(member2);
        
        
        MethodCall.Params.Param.Value.Struct.Member member3 = new MethodCall.Params.Param.Value.Struct.Member();
        
        MethodCall.Params.Param.Value.Struct.Member.Value2 value6 = new MethodCall.Params.Param.Value.Struct.Member.Value2();

        value6.setString(response.getResponseDescription());

        member3.setName("responseDescription");

        member3.setValue(value6);

        memberList1.add(member3);

        
        MethodCall.Params.Param.Value.Struct.Member member4 = new MethodCall.Params.Param.Value.Struct.Member();
        
        MethodCall.Params.Param.Value.Struct.Member.Value2 value7 = new MethodCall.Params.Param.Value.Struct.Member.Value2();


        value7.setString(response.getResponseData());

        member4.setName("responseData");

        member4.setValue(value7);

        memberList1.add(member4);
        
        MethodCall.Params.Param.Value.Struct struct1 = new MethodCall.Params.Param.Value.Struct();

        struct1.getMember().addAll(memberList1);

        MethodCall.Params.Param.Value value4 = new MethodCall.Params.Param.Value();

        value4.setStruct(struct1);

        MethodCall.Params.Param param1 = new MethodCall.Params.Param();

        param1.setValue(value4);

        List<MethodCall.Params.Param> paramList1 = new ArrayList<MethodCall.Params.Param>();
        
        paramList1.add(param1);

        MethodCall.Params params1 = new MethodCall.Params(); 

        params1.getParam().addAll(paramList1);

        MethodCall methodCall1 = new MethodCall();

        methodCall1.setMethodName(method);
        
        methodCall1.setParams(params1);

         return methodCall1;
    }

//    @POST 
//    @Path("msisdnValidation")
//    @Consumes("application/x-www-form-urlencoded")
////    @Consumes("text/xml")
//    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
//    public Response msisdnValidate(@FormParam("msisdn") String MSISDN, 
//            @FormParam("mno") String MNO, @FormParam("country_code") String countryCode, 
//            @FormParam("transaction_id") String transactionID) {
//        
//        ValidateMSISDN validateMSISDN = new ValidateMSISDN();
//        
//        Response response = new Response();
//
//        response = validateMSISDN.validateMobileNumber(MSISDN,MNO,countryCode,transactionID);
//        
//        return response;
//    }
}
