package kr.go.spo.workflow.controller;

import camundajar.impl.com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * REST 컨트롤로 테스트
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TestRestController {

  private final JmsTemplate jmsTemplate;

  @GetMapping("/dummy")
  public Map<String, String> testDummy(HttpServletRequest req) {
    Map<String, String> rtnMap = new HashMap<>();
    rtnMap.put("resultCd","00");
    return rtnMap;
  }

  @GetMapping("/produceMsg/{destinationName}")
  public String produceMsg(
          HttpServletRequest req
          , @PathVariable String destinationName
          , @RequestParam Map<String, Object> paramMap
  ) {
    Gson gson = new Gson();
    String jsonStr = gson.toJson(paramMap);
    jmsTemplate.convertAndSend(destinationName,jsonStr);

    return jsonStr;
  }

}