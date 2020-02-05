/**
 * SalesConstants.java
 */

package com.bizwink.event;

public class SalesConstants{
    public static final int LEAD_TYPE=1;
    public static final int ACCOUNT_TYPE=2;
    public static final int CONTACT_TYPE=3;
    public static final int OPPORTUNITY_TYPE=4;
    public static final int TASK_TYPE=5;
    public static final int EVENT_TYPE=6;
    public static final int NOTE_TYPE=7;
    public static final int USER_TYPE=8;
    public static final int FORECAST_TYPE=9;
    public static final int ASSIGN_TYPE=10;


    //数据操作
    public static final int OP_CREATE=0;
    public static final int OP_EDIT=1;

    //用户行为
    public static final int DO_CREATE=0;
    public static final int DO_READ=1;
    public static final int DO_EDIT=2;
    public static final int DO_DEL=3;
    public static final int DO_ADMIN=4;
    public static final int DO_TRANSFER=5;

    public static final int CANOPERATE=9; //有权限,可以操作

    public static final String[] itemTypes = {
            "线索","客户","联系人","机会","任务","事件","便签", "用户","预测","委派"
    };

    public static final String[] itemTables = {
            "lead","account","contact","opportunity","task","event","note","users","forecast","assign"
    };

    public static final String[] leadStatuses = {
            "开始","联系中","合格","不合格"
    };

    public static final String[] industrys = {
            "财会","广告","服装/纺织制造","银行业","生物工程","建筑业","咨询业",
            "教育","能源","工程服务","娱乐业","环境工程","金融服务","政府","健康",
            "高技术-应用代理","高技术-互联网/电子商务","高技术-制造业",
            "高技术-软件开发","高技术-系统集成/咨询服务","旅馆业","进出口","保险业",
            "法律服务","媒体","非赢利行业","其他","房地产","零售/批发","电讯业",
            "运输业","旅游服务业和公益事业"
    };
    public static final String[] accountTypes = {
            "竞争对手","合作伙伴","顾客","投资商","供货商","货运商",
            "分销商","代理商","批发商"," 零售商","制造商","其它"
    };
    public static final String[] accountOwnerships = {
            "上市公司","私有公司","股份公司","国营公司"
    };
    public static final String[] ratings = {
            "好","一般","差"
    };
    public static final String[] leadSources = {
            "广告","同事推荐","外面推荐","合作伙伴","公关","展览","电话约见"
    };
    public static final String[] taskStatuses = {
            "没开始","正在进行","完成","等待中","推迟"
    };
    public static final String[] taskPrioritys = {
            "高","中","低"
    };

    public static final String[] roles = {
            "系统集成商","咨询","代理商","广告","零售商", "分销商",
            "开发商", "中介", "贷方","供应商","机构","承包商","经销商"
    };
    public static final String[] leadRatings = {
            "好","一般","差"
    };
    public static final String[] opportunityTypes = {
            "已有业务","新业务"
    };
    public static final String[] opportunitySources = {
            "广告","员工提供","外部提供","合作伙伴","公共关系","国际会议","合作交流会","展览","网站","电话约见","其他"
    };

    public static final String[] opportunityStages = {
            "期望","鉴定","需求分析","价格建议","决策者同意","深度分析","建议/报价","谈判/审阅","成功结束","失败",
    };

    public static final String[] contactRoles = {
            "商业用户","决策者","采购员","采购决策者","评估人员", "执行发起者",
            "影响者", "技术买主", "其它"
    };

    public static final String[] passwordQuestion = {
            "你的宠物的名字？","你最想去的地方","你喜欢的活动"
    };
}