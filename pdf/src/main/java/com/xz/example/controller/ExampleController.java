package com.xz.example.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @RequestMapping("/test")
    public String test(Date date){
        return "hello example";
    }

    public static void main(String[] args) {
        dealBusiness(null,null,null);
    }


    /**
     * 处理业务
     * @param businessType  接口方法
     * @param url 请求地址
     * @param param 请求参数
     * @return
     */
    public static JSONObject dealBusiness(String businessType, String _url, String param){
        JSONObject result=new JSONObject();
        result.put("code", false);
        result.put("message","");
        result.put("xml","");
        try{
            //返回soap xml
            String responseXml="";//HttpRequestUtil.httpPost(_url, null, param);
            responseXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?><SOAP-ENV:Envelope xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:s='http://www.w3.org/2001/XMLSchema'><SOAP-ENV:Body><queryAdmInfoResponse xmlns=\"http://tempuri.org\"><queryAdmInfoResult><![CDATA[<Response><Result_Code>0000</Result_Code><Error_Msg>获取信息成功.</Error_Msg><Result_Data><Record_List><Record_Info><Patient_Id>0000177821</Patient_Id><Patient_Name>测试1217</Patient_Name><Adm_No>OP0000258604</Adm_No><RowId>290710</RowId><Pay_Costs>31.8</Pay_Costs><Visit_Date>2018-12-17</Visit_Date><Doctor_Name>test</Doctor_Name><Performed_By></Performed_By><Ordered_By>NKMZ-内科门诊</Ordered_By><Ordered_Time>2018-12-17</Ordered_Time></Record_Info><Record_Info><Patient_Id>0000177821</Patient_Id><Patient_Name>测试1217</Patient_Name><Adm_No>OP0000258605</Adm_No><RowId>290711</RowId><Pay_Costs>162.39</Pay_Costs><Visit_Date>2018-12-19</Visit_Date><Doctor_Name>test</Doctor_Name><Performed_By></Performed_By><Ordered_By>NKMZ-内科门诊</Ordered_By><Ordered_Time>2018-12-19</Ordered_Time></Record_Info></Record_List></Result_Data></Response>]]></queryAdmInfoResult></queryAdmInfoResponse></SOAP-ENV:Body></SOAP-ENV:Envelope>";
             responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:s=\"http://esb.spdbbiz.com/services/S120030432\" xmlns:d=\"http://esb.spdbbiz.com/metadata\" ><soap:Header><s:ReqHeader><d:Mac>0000000000000000</d:Mac><d:MacOrgId></d:MacOrgId><d:SourceSysId>0825</d:SourceSysId><d:ConsumerId>0825</d:ConsumerId><d:ServiceAdr>http://esb.spdbbiz.com:7701/services/S120030432</d:ServiceAdr><d:ServiceAction>urn:/UnfSocCrdtNoQry</d:ServiceAction><d:ExtendContent>00</d:ExtendContent></s:ReqHeader></soap:Header><soap:Body><s:ReqUnfSocCrdtNoQry><s:ReqSvcHeader><s:TranDate>20170913</s:TranDate><s:TranTime>162116290</s:TranTime><s:TranTellerNo>00000000</s:TranTellerNo><s:TranSeqNo></s:TranSeqNo><s:ConsumerId>0825</s:ConsumerId><s:GlobalSeqNo>15800001016431580643158064</s:GlobalSeqNo><s:SourceSysId>0825</s:SourceSysId><s:BranchId></s:BranchId><s:TerminalCode>1a</s:TerminalCode><s:CityCode>021</s:CityCode><s:AuthrTellerNo></s:AuthrTellerNo><s:AuthrPwd></s:AuthrPwd><s:AuthrCardFlag></s:AuthrCardFlag><s:AuthrCardNo></s:AuthrCardNo><s:TranCode>DM91</s:TranCode><s:PIN>1234567890123</s:PIN><s:SysOffset1>0000</s:SysOffset1><s:SysOffset2>0000</s:SysOffset2><s:MsgEndFlag>1</s:MsgEndFlag><s:MsgSeqNo>3000</s:MsgSeqNo><s:SubTranCode></s:SubTranCode><s:TranMode></s:TranMode><s:TranSerialNo>0</s:TranSerialNo></s:ReqSvcHeader><s:SvcBody><s:OrgInstId>100000024</s:OrgInstId></s:SvcBody></s:ReqUnfSocCrdtNoQry></soap:Body></soap:Envelope> ";


            MessageFactory msgFactory = MessageFactory.newInstance();

            //headers.setHeader(name, value); utf-8
            MimeHeaders head=new MimeHeaders();
//			head.setHeader("Content-Transfer-Encoding", "UTF-8");
//            head.addHeader("Content-Type", "UTF-8");


            SOAPMessage reqMsg = msgFactory.createMessage(head,new ByteArrayInputStream(responseXml.getBytes()));
//            reqMsg.setProperty(SOAPMessage.CHARACTER_SET_ENCODING,"gb2312");
//            reqMsg.saveChanges();
            SOAPBody body = reqMsg.getSOAPBody();
            Iterator<SOAPElement> iterator = body.getChildElements();
            String soapBodyXml="";
            while(iterator.hasNext()){
                soapBodyXml=iterator.next().toString();
            }
            result.put("code", true);
            result.put("xml",soapBodyXml);
        }catch(Exception e){
            e.printStackTrace();
            result.put("message","处理业务异常!");
        }
        return result;
    }

}
