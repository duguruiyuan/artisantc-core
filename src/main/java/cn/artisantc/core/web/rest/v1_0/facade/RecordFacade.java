package cn.artisantc.core.web.rest.v1_0.facade;

import cn.artisantc.core.service.RecordService;
import cn.artisantc.core.web.rest.v1_0.vo.request.ReportMomentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 各种“记录”操作的API。
 * Created by xinjie.li on 2016/9/20.
 *
 * @author xinjie.li
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api")
public class RecordFacade {

    private RecordService recordService;

    @Autowired
    public RecordFacade(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * 举报艺文，生成举报记录。
     *
     * @param reportMomentRequest 举报艺文时的请求参数的封装对象，具体参数请查看对象中的属性
     */
    @RequestMapping(value = "/records/moment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void reportMoment(@RequestBody ReportMomentRequest reportMomentRequest) {
        recordService.reportMoment(reportMomentRequest.getMomentId());
    }
}
