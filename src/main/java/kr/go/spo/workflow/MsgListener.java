package kr.go.spo.workflow;

import camundajar.impl.com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import javax.jms.Session;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MsgListener {

    @Value("${workflow.extrc.serverIp}")
    String apiServerIp;

    @JmsListener(destination = "extrc")
    public void receiveMessage(@Payload String strMsg,
                               @Headers MessageHeaders headers,
                               Message message, Session session) {
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######          Message Order           #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("received <" + strMsg + ">");

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######          Message Details           #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: " + headers);
        log.info("message: " + message);
        log.info("session: " + session);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");



        Gson gson = new Gson();
        Map<String, Object> jsonMap = gson.fromJson(strMsg, Map.class);
        log.info("##@# start process {}", strMsg);
        this.startProcess(jsonMap);
    }


    public ProcessInstance startProcess(Map<String, Object> variables) {
        String processId = "extrc";
        ProcessInstanceWithVariables instance = ProcessEngines.getDefaultProcessEngine().getRuntimeService().createProcessInstanceByKey(processId)
                .setVariables(variables)
                .executeWithVariablesInReturn()
                ;
        return instance;
    }

}
