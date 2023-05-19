package com.fbank.code.service;

import com.fbank.code.entity.Bond;
import ilog.concert.*;
import ilog.cplex.IloCplex;

import java.util.*;

public class BondClpexSolver {

    public static void main(String[] args) throws IloException {
        rzdf();

    }

    public void test() throws IloException {
        // 1. 创建模型
        IloCplex cplex = new IloCplex();


        // 2. 定义优化参数
        // 定义基本参数
        IloNumVar[] x = cplex.numVarArray(3, 0, Double.MAX_VALUE);
        // 定义约束参数
        IloNumVar[] d = cplex.numVarArray(12, 0, Double.MAX_VALUE);
        double[] c = {10, 0, 5, 0, 5, 0, 5, 0, 0, 1, 0, 1};
        IloNumExpr cs1 = cplex.numExpr();
        for(int i=0;i<12;i++){
            cs1 = cplex.sum(cs1,cplex.prod(c[i], d[i]));
        }


        // 3. 设置目标函数
        cplex.addMinimize(cs1);
        // 4. 设置约束
        cplex.addEq(cplex.sum(cplex.prod(2.5,x[0]), cplex.prod(2.5,x[1]), cplex.prod(5,x[2]), d[1], cplex.prod(-1, d[0])), 450);
        cplex.addEq(cplex.sum(x[0], d[3], cplex.prod(-1, d[2])), 30);
        cplex.addEq(cplex.sum(cplex.prod(-1,x[0]), x[1], d[5], cplex.prod(-1, d[4])), 30);
        cplex.addEq(cplex.sum(cplex.prod(-1, x[1]), x[2], d[7], cplex.prod(-1, d[6])), 0);
        cplex.addEq(cplex.sum(x[0], d[9], cplex.prod(-1, d[8])), 24);
        cplex.addEq(cplex.sum(x[1], d[11], cplex.prod(-1, d[10])), 30);



        //            // 5. 模型求解及输出
        if(cplex.solve()){
            cplex.output().println("Solution status = " + cplex.getStatus());
            cplex.output().println("Solution Value = " + cplex.getObjValue());
            double[] val = cplex.getValues(x);
            double[] vald = cplex.getValues(d);
            for(int j=0;j<3;j++){
                System.out.println("x" + (j+1) + " = " + val[j]);
            }

            for(int j=0;j<12;j++){
                System.out.println("d" + (j+1) + " = " + vald[j]);
            }

        }
        cplex.end();
    }


    public static void rzdf() {


        IloCplex cplex = null;
        try {
            // 1. 创建模型

            cplex = new IloCplex();

            // 2. 定义优化参数

            IloNumVar[] x = cplex.numVarArray(2, 1, Double.MAX_VALUE);

            // 3. 设置目标函数
            /*
             * object
             *
             * max y = 40x_1 + 30x_2

             */


            IloLinearNumExpr iloLinearNumExpr = cplex.linearNumExpr();
            iloLinearNumExpr.addTerm(40, x[0]);
            iloLinearNumExpr.addTerm(30, x[1]);
            cplex.addMaximize(iloLinearNumExpr);

            // 4. 设置约束
            /**

             * s.t.
             * x_1 + x_2 <=6
             * x_1 >=1
             * x_2 >=1
             * 240x_1 + 120x_2 <= 1200
             */

            IloLinearNumExpr s1 =  cplex.linearNumExpr();
            s1.addTerm(1, x[0]);
            s1.addTerm(1, x[1]);
            cplex.addLe(s1, 6);

            IloLinearNumExpr s2 =  cplex.linearNumExpr();
            s2.addTerm(240,x[0]);
            s2.addTerm(120,x[1]);
            cplex.addLe(s2, 1200);

            // 5. 模型求解及输出
            if (cplex.solve()) {
                cplex.output().println("Solution status = " + cplex.getStatus());
                cplex.output().println("Solution Value = " + cplex.getObjValue());
                double[] val = cplex.getValues(x);
                for (int j = 0; j < 2; j++) {
                    System.out.println("x" + (j + 1) + " = " + val[j]);
                }
            }

            // 6. 释放资源
            cplex.end();
        } catch (IloException e) {
            e.printStackTrace();
        } finally {
            if (cplex != null) {
                cplex.end();
            }
        }


    }


    public void bondHandler() throws IloException {
        // 构建筛选条件集合
        HashSet<Integer> filterCondition = new HashSet<>();
        // 初筛城投债
        List<Bond> bonds = preProcess(filterCondition);
        List<Bond> optimalBonds = cplexSolver(bonds, filterCondition);



    }

    private List<Bond> cplexSolver(List<Bond> bonds, HashSet<Integer> filterCondition) throws IloException {

        // Ex. 总规模为20亿元
        double totalSize = 2000000000;
        // Ex. 最小交易单位为100万元
        // 最小交易单位可以直接指定
        // 单券最大余额，应该由持仓总额*单券限额比例得出，是一个约束条件
        double minUnit = 1000000;
        // Ex. 最大交易笔数，可以通过总规模与最小交易单位得出
        // 得出上界，债券余额可以转化为 amount =  x_i * minUnit
        // 即各券的交易笔数，转化为整数规划问题
        double n = totalSize / minUnit;
        // 与之类似的，由于单个主体存在授信额度限制，因此单个主体的交易笔数也有上限
        // 设置为线性约束条件即可

        // 采用数组方式初始化变量
        double[] d = new double[bonds.size()];
        // 估值
        double[] ytm = new double[bonds.size()];
        // 发行人
        String[] issuer = new String[bonds.size()];
        // 初始化发行人限额Map
        Map<String, Double> issuerAmount = new HashMap<>();
        // 是否私募债
        boolean[] pr = new boolean[bonds.size()];

        // 1. 创建模型
        IloCplex cplex = new IloCplex();
        // 2. 定义决策变量
        // x 为交易笔数，用于表征债券的持仓余额
        IloIntVar[] x = cplex.intVarArray(bonds.size(), 0, (int) n);
        // 3. 定义约束参数


        // 3. 设置目标函数
        /*
         * object
         *
         * max y = 40x_1 + 30x_2

         */


//        IloLinearNumExpr iloLinearNumExpr = cplex.linearNumExpr();
//        iloLinearNumExpr.addTerm(40, x[0]);
//        iloLinearNumExpr.addTerm(30, x[1]);
//        cplex.addMaximize(iloLinearNumExpr);

        // 4. 设置目标函数

        // 分子表达式
        IloLinearNumExpr numerator  = cplex.linearNumExpr();
        // 分母表达式
        IloLinearNumExpr denominator = cplex.linearNumExpr();

        IloNumVar z  = cplex.numVar(0, Double.MAX_VALUE);
        for (int i = 0; i < bonds.size(); i++) {
            numerator .addTerm(ytm[i], x[i]);
            denominator.addTerm(1, x[i]);
        }
        cplex.addMinimize(cplex.diff(numerator, cplex.prod(z, denominator)));



        // 创建目标表达式



        return new ArrayList<>();

    }


    // 完成城投债数据初筛，根据筛选条件，从总的城投债中筛选出符合条件的城投债
    public List<Bond> preProcess(HashSet<Integer> filterCondition) {
        // 1. 加工过滤条件
        // 过滤条件由前端传输及二次加工，例如：
        //          如果债券余额不满足最小交易单位，那么该债券可以一并过滤
        // 2. 获取城投债及设置过滤条件
        // select * from TQ_BD_BASICINFO where BOND_TYPE = '城投债'
        // 部分筛选条件可以直接写入SQL；
        // 债券状态 => 存续
        // 债券余额 => 至少大于最小交易单位
        // 债券期限 => 期限区间
        // 授信名单 => 债券发行人
        // 是否有担保 => 有担保
        // 特殊定制筛选条件，例如 ：重庆大足国有资产经营管理集团有限公司 限制为公募
        //                    重庆大晟资产经营(集团)有限公司 限制为带AAA担保
        //                    重庆共享工业投资有限公司 限制为带AA+及以上担保
        // ......

        List<Bond> bonds = new ArrayList<>();

        // 3. 输出筛选出符合基本条件的城投债数据
        // 缩小集合范围，减少后续计算量

        // Tips： 可以考虑对债券顺序进行排序，以获得更好的性能
        return new ArrayList<>();
    }

    public static void rzdf2(List<Bond> bonds, double size, double MD) {


        }

        /**
         *  2. 创建模型
         */

    }
