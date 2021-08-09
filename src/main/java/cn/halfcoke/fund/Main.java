package cn.halfcoke.fund;

import cn.halfcoke.fund.apis.TianTianAPI;

import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {

        try (FileWriter fileWriter = new FileWriter("./fundManagerList.json")) {
            fileWriter.write(TianTianAPI.getFundManagerList());
        }
    }
}
