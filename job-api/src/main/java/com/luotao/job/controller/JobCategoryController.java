package com.luotao.job.controller;

import com.luotao.job.domain.CallbackRequest;
import com.luotao.job.domain.JobCategory;
import com.luotao.job.service.CallbackRequestService;
import com.luotao.job.service.JobCategoryService;
import com.luotao.job.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname JobCategoryController
 */
@RestController
@Api(tags = "岗位类别相关接口")
public class JobCategoryController {
    @Resource
    private JobCategoryService jobCategoryService;

    @GetMapping("/all/category")
    @ApiOperation("获取所有岗位类别")
    public ResponseResult getAllCategory() {
        return jobCategoryService.getAllJobCategory();
    }

    // Validated
    @PostMapping("/add/category")
    @ApiOperation("添加岗位类别")
    public ResponseResult addJobCategory(@Validated JobCategory jobCategory) {
        return jobCategoryService.addJobCategory(jobCategory);
    }

    @PostMapping("/delete/category")
    @ApiOperation("删除岗位类别")
    public ResponseResult deleteCategory(@RequestParam(value = "category_id", required = true) Integer id) {
        return jobCategoryService.deleteJobCategory(id);
    }

    @PostMapping("/update/category")
    @ApiOperation("修改岗位类别")
    public ResponseResult updateCategory(JobCategory jobCategory) {
        return jobCategoryService.updateJobCategory(jobCategory);
    }

  /*  //    /notify/receive
    @PostMapping("/notify/receive")
    public ResponseResult receiveCallback(@RequestBody CallbackRequest request) {
        System.out.println("接收到回调通知: " + request);
        String action = request.getAction();
        // 根据action类型进行不同的处理
        switch (action) {
            case "SIGN_FLOW_UPDATE":
                callbackRequestService.handleSignCompleted(request);
                break;
            case "SIGN_FLOW_FINISH":
                callbackRequestService.handleFlowEnded(request);
                break;
//            case "SIGN_DOC_EXPIRE_REMIND":
//                callbackRequestService.handleFlowExpireRemind(request);
//                break;
//            case "SIGN_DOC_EXPIRE":
//                callbackRequestService.handleFlowExpired(request);
//                break;
//            case "BATCH_ADD_WATERMARK_REMIND":
//                callbackRequestService.handleBatchAddWatermarkRemind(request);
//                break;
//            case "FEEDBACK_SIGNERINFO":
//                callbackRequestService.handleSignerInfoFeedback(request);
//                break;
//            case "PROCESS_HANDOVER":
//                callbackRequestService.handleProcessHandover(request);
//                break;
//            case "WILL_FINISH":
//                callbackRequestService.handleWillFinish(request);
//                break;
//            case "PARTICIPANT_MARKREAD":
//                callbackRequestService.handleParticipantMarkRead(request);
//                break;
//            case "FILE_ABNORMAL_REMIND":
//                callbackRequestService.handleFileAbnormalRemind(request);
//                break;
            default:
                System.out.println("未知事件类型: " + action);
                // 忽略未知事件类型
                break;
        }

        return new ResponseResult();
    }*/


}
