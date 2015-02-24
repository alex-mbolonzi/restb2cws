/*
    Author: Alex Mbolonzi

 */
package ke.co.mmm.b2c.entity;

//import java.util.ArrayList;

//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "response")
@XmlType (propOrder={"transactionID", "responseStatus","responseDescription","responseData"})
public class Response {

    private String response_status;
    
    private String responseDescription;
    
    private String responseData;
    
    private String transactionID;

    @XmlElement(name="response_status")
    public String getResponseStatus() {
        return response_status;
    }

    public void setResponseStatus(String responseStatus) {
        this.response_status = responseStatus;
    }

    @XmlElement(name="responseDescription")
    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    @XmlElement(name="responseData")
    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    @XmlElement(name="transactionID")
    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    
    @Override
    public String toString() {
        return "Response [transactionID=" + transactionID + ", response_status=" + response_status + ", responseDescription="
                + responseDescription + ", responseData=" + responseData  + "]";
    }
    
    public String[] toArray() {
             
        String response[] = new String[4];
        
        response[0] = transactionID;
        response[1] = response_status;
        response[2] = responseDescription;
        response[3] = responseData;
        
        return response;
    }
}
