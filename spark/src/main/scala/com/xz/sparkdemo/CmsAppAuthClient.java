package com.xz.sparkdemo;

import com.xz.ajiaedu.common.ajia.Param;
import com.xz.ajiaedu.common.appauth.AppAuthClient;
import com.xz.ajiaedu.common.lang.Result;

import java.util.List;

public class CmsAppAuthClient {

    private static String appAuthServerUrl="http://10.10.22.204:18765/";

    private static String appKey="6722785572";

    private static String appSecret="qRT4M4oc7vFdWSopLwpYFv3GDUgdAdpPxjgCSrP57DTmq4ARQS";

    private static AppAuthClient appAuthClient=null;

    public static AppAuthClient getAppAuthClient() {
        if(appAuthClient==null){
            appAuthClient=new AppAuthClient(appAuthServerUrl, appKey, appSecret);
        }
        return appAuthClient;
    }

    /*
    *查询答题卡题目信息
    "cardId":"100031680",
    "cardSubjectId":"006",
    "subjectId":"006",
    "questId":"100031680-18",
    "score":2.0,
    "paperQuestNum":"18",
    "awardScoreTag":false,
    "subObjTag":"o",
    "multiChoice":false,
    "items":[
        "A",
        "B",
        "C",
        "D"
    ],
    "answer":"B",
    "scoreRule":"B",
    "questionNum":"1.18",
    "difficulty":"3",
    "questionTypeName":"选择题",
    "questionTypeId":"f928f3c3-0633-45c4-835c-a08f4c89ec81",
    "questType":"0",
    "points":{
        "1007650":[
            "C"
        ]
    }
     */
    public static List<QuestCardInfo> queryQuestionByProject(String projectId){
        List<QuestCardInfo> resultList=null;

        Param param = new Param();
        param.setParameter("projectId", projectId);
        Result result = getAppAuthClient().callApi("QueryQuestionByProject", param);
        if(result.isSuccess()) {
            resultList = result.getList("quests", QuestCardInfo.class);
        }

        return resultList;
    }
}
