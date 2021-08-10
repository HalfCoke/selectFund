## 基金获取工具

工具提供管理一些获取基金信息的方法，具体介绍如下

### 数据格式说明

本工具中会将基金与基金经理的信息序列化为json，通过json在各个方法之间传递

#### Fund

基金数据举例如下：

```json
  {
  "fundCode": "005669",
  "fundName": "前海开源公用事业股票",
  "establishDate": "",
  "assetSize": 0.0,
  "nav": 3.0741,
  "totalNav": 3.0741,
  "dayGrowthRate": -3.02,
  "weekGrowthRate": 0.28,
  "monthGrowthRate": 16.16,
  "threeMonthGrowthRate": 93.66,
  "sixMonthGrowthRate": 78.12,
  "yearGrowthRate": 187.11,
  "twoYearGrowthRate": 197.01,
  "threeYearGrowthRate": 204.94,
  "curYearGrowthRate": 85.03,
  "establishGrowthRate": 207.41,
  "fiveYearGrowthRate": 207.41
}
```

#### FundManager

经理人数据举例如下：

```json
  {
  "name": "艾定飞",
  "id": "30634044",
  "workingDay": 988,
  "totalAssetSize": "5.74亿元",
  "company": "华商基金",
  "companyId": "80053204",
  "currentOperatingFunds": [
    {
      "fundCode": "007685",
      "fundName": "华商电子行业量化股票",
      "establishDate": "",
      "assetSize": 0.0,
      "nav": 0.0,
      "totalNav": 0.0,
      "dayGrowthRate": 0.0,
      "weekGrowthRate": 0.0,
      "monthGrowthRate": 0.0,
      "threeMonthGrowthRate": 0.0,
      "sixMonthGrowthRate": 0.0,
      "yearGrowthRate": 0.0,
      "twoYearGrowthRate": 0.0,
      "threeYearGrowthRate": 0.0,
      "curYearGrowthRate": 0.0,
      "establishGrowthRate": 0.0,
      "fiveYearGrowthRate": 0.0
    },
    {
      "fundCode": "007853",
      "fundName": "华商计算机行业量化股票",
      "establishDate": "",
      "assetSize": 0.0,
      "nav": 0.0,
      "totalNav": 0.0,
      "dayGrowthRate": 0.0,
      "weekGrowthRate": 0.0,
      "monthGrowthRate": 0.0,
      "threeMonthGrowthRate": 0.0,
      "sixMonthGrowthRate": 0.0,
      "yearGrowthRate": 0.0,
      "twoYearGrowthRate": 0.0,
      "threeYearGrowthRate": 0.0,
      "curYearGrowthRate": 0.0,
      "establishGrowthRate": 0.0,
      "fiveYearGrowthRate": 0.0
    }
  ]
}
```

### api说明

`TianTianApi.getFundManagerList()`返回所有基金经理的信息，以json列表形式返回，其中`currentOperatingFunds`字段中的基金信息仅包含名字和代码。

`TianTianApi.getFundList()`返回所有基金的信息列表,以json列表形式返回，其中不包含`单位净值nav`、`累计单位净值totalNav`、`资产大小`、`成立日期`。

`TianTianApi.getTotalFundManagerList()`返回包含所有信息的经理人json列表，其中`currentOperatingFunds`字段信息也完整。

其余工具类api请直接查看`cn.halfcoke.fund.apis.TianTianApi`文件...择日补充

### 其他说明

`cn.halfcoke.fund.Main`中包含了一个demo

用来返回以下规则的基金列表

1. 经理人从业5年以上
2. 基金成立5年以上
3. 经理人近2年操盘的成立5年以上的基金平均收益率在前30%，近5年排20%
4. 获得以上经理人的所有基金，然后选出在"近1月"、"近6月"....等等等收益率排名前30%的基金
