package kr.go.spo.workflow;

import camundajar.impl.com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MsgListener {

    @JmsListener(destination = "extrc")
    public void receiveMessage(String msg) {


        Gson gson = new Gson();
        Map<String, Object> jsonMap = gson.fromJson(msg, Map.class);
        log.info("##@# start process {}", msg);

    }

}
