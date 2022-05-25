package com.demo;

import java.util.Scanner;

public class KP {
    int[][] thing;
    int[][] end;
    int total;
    int w;
    Scanner s =new Scanner(System.in);
    KP()
    {
        System.out.println("请输入物品总数：");
        total=s.nextInt();
        thing =new int[total+1][2];
        System.out.println("请依序输入物品的重量、价格（空格分割）:");
        for (int i =1;i<total+1;i++)
        {
            thing[i][0]=s.nextInt();
            thing[i][1]=s.nextInt();
        }
        System.out.println("请输入背包重量：");
        w=s.nextInt();
    }

    public void run_top()
    {
        end=new int[total+1][w+1];             //创建动态规划表并进行初始化
        //开始计算

        for (int i = 1; i < total+1; i++)
        {
            for (int j = 1; j < w+1; j++)
            {
                int y;                //x、y为那时的横纵坐标
                if (j<thing[i][0])
                {
                    end[i][j]=end[i-1][j];
                }
                else
                {
                    y=j-thing[i][0];
                    if (y<0)end[i][j]=end[i-1][j];
                    else end[i][j]=Math.max(end[i-1][j],thing[i][1]+end[i-1][y]);
                }
            }
        }
    }
    public void print()
    {
        System.out.print("背包重量\t");
        for (int i = 0; i < w+1; i++) {
            System.out.print(i+"\t");
        }
        System.out.println();

        System.out.println("物品\t");
        for (int i = 0; i <total+1; i++) {
            System.out.print(i+"\t\t");
            for (int j = 0; j < w+1; j++) {
                System.out.print(end[i][j]+"\t");
            }
            System.out.println();
        }
    }

    public void find()
    {
        int[] h =new int[total+1];
        for (int i=w,j=total;j>0;)
        {
            if (end[j][i] != end[j - 1][i]) {
                h[j] = 1;
                i -= thing[j][0];
            }
            j--;
        }
        System.out.println();
        for (int i = 0; i < total+1; i++)
        {
            if (h[i]==1) System.out.print(i+",");
        }
    }

    public void run_below()
    {
        end =new int[total+1][w+1];
        for (int i = 1; i < total+1; i++)
        {
            for (int j = 1; j < w+1; j++)
            {
                end[i][j]=-1;
            }
        }
        run_b_loop(total,w);

    }

    private int run_b_loop(int t,int w)
    {
        int tt=thing[t][0];
        if (w-tt<0&&t>1&&w>1)
        {
            if(end[t-1][w]==-1)run_b_loop(t-1,w);
            else
            {
                end[t][w]=end[t-1][w];
                //return end[t][w];
            }
        }
        else if(t>1&&w>1)
        {
            int y=end[t-1][w-tt]==-1?run_b_loop(t-1,w-tt):end[t-1][w];
            int x=end[t-1][w]==-1?run_b_loop(t-1,w):end[t-1][w];

            end[t][w]=Math.max(x,w-thing[t][0]>=0?y+thing[t][1]:-1);

        }
        else
        {
            end[t][w]=w-tt>=0?Math.max(end[t-1][w],end[t-1][w-tt]+thing[t][1]):(end[t-1][w]==-1)?run_b_loop(t-1,w):end[t-1][w];
        }
        return end[t][w];
    }
}
