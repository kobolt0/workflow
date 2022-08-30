package kr.go.spo.workflow.worker;

import kr.go.spo.workflow.common.HttpResVo;
import kr.go.spo.workflow.common.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * BPNM에 정의된 REST API URL을 호출하는 worker.
 * REST API 의 결과로 성공/실패 만 받는경우 사용
 * */
@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleWorker  implements JavaDelegate {


    /** 모델러에 정의된 호출할 rest api URI 변수명 */
    final String keyApiUri = "processApiUri";

    @Value("${workflow.extrc.serverIp}")
    String apiServerIp;

    /** 개별로직 구현 */
    @Override
    public void execute(DelegateExecution execution) {
        log.debug("#@## Start excuteMain ################################");
        log.info ("#@## Start task worker : " + execution.getBpmnModelElementInstance().getName());

        // 입력파라메터 Map 설정
        Map<String, String> inParamMap = new HashMap<>();
        Set<String> paramNames = execution.getVariableNames();
        for (String name : paramNames) {
            if (execution.getVariable(name) == null) {
                inParamMap.put(name, null);
            } else {
                TypedValue tval = execution.getVariableTyped(name);
                inParamMap.put(name, tval.getValue().toString());
            }
        }
        log.debug("#@## inParamMap " + inParamMap);
        log.debug("#@## getActivityInstanceId: " + execution.getActivityInstanceId());
        log.debug("#@## ### ########## ################################");

        // rest api 호출
        String callUrl = apiServerIp + inParamMap.get(this.keyApiUri) + HttpUtils.map2GetParam(inParamMap);
        log.info("#@## call REST [{}]", callUrl);
        HttpResVo resVO = HttpUtils.callHttpGet(callUrl);  //호출
        log.debug("#@## HttpUtils.callHttpGet END getResponsCode:[{}]  getResponsCode[{}]  getContent[{}]", resVO.getResponsCode(), resVO.getResponsCode(), resVO.getContent());

        log.info("#@## REST API Response. {}", resVO.toString());
        log.info("#@## REST API Response getContent. {}", resVO.getContent());

    }


}