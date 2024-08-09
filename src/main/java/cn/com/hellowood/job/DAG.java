package cn.com.hellowood.job;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DAG {

    static int len_v=10;   //点的个数
    static int len_e=15;   //边的个数
    private static boolean visited2[]=new boolean[len_v];  //DFS中被访问的点
    static Vertex ver[]=new Vertex[len_v];  //存放点
    static int num[]=new int[len_v];  //链表中此时被访问的点
    static int c[]=new int[len_v];  //该节点是否被访问过
    static Stack<Vertex> s=new Stack<Vertex>();  //非递归DFS的栈


    static class Vertex
    {
        int n;//邻接链表的点
        List<Integer> list=new LinkedList<Integer>();

        //点的构造方法
        public Vertex(int n)
        {
            this.n=n;
        }
        //向该点的链表中增加点
        public void add(int value)
        {
            list.add(value);
        }
    }

    static class Edge
    {
        int u;//该点
        int v;//下一个点

        //构造方法
        public Edge(int u,int v)
        {
            this.u=u;
            this.v=v;
        }

        @Override
        //重写hashcode方法
        public int hashCode() {
            return v;
        }

        @Override
        //重写equals方法
        public boolean equals(Object obj) {
            if(!(obj instanceof Edge)){
                return false;
            }
            if(obj == this){
                return true;
            }
            return this.v==((Edge)obj).v&&this.u==((Edge)obj).u;
        }

    }

    //csh即初始化
    public static void csh()
    {
        //构建len_v个点
        for(int i=0;i<len_v;i++)
        {
            ver[i]=create(i+1);
            visited2[i]=false;
        }

        //构建Len_e条边
        Edge e;
        Set<Edge> s=new HashSet<Edge>();//存储所有的边

        while(s.size()<len_e)
        {
            int u=random();
            int v=random();

            while(u==v||s.contains(new Edge(u,v)))
                v=random();

            e=new Edge(u,v);

            if(!s.contains(e))
                s.add(e);
        }

        //将len_e条边加到对应的起始点的链表中
        Iterator<Edge> it=s.iterator();

        while(it.hasNext())
        {
            Edge ee=(Edge) it.next();
            ver[ee.u-1].list.add(ee.v);
            //System.out.println(ee.u+" * "+ee.v);
        }

    }

    //重新初始化BFS和DFS标注点
    public static void csh1()
    {
        for(int i=0;i<len_v;i++)
        {
            visited2[i]=false;
        }
    }

    public static void print(Vertex ver[])
    {
        System.out.println("打印所有点的邻接链表：");
        //打印所有的邻接链表
        for(int i=0;i<len_v;i++)
        {
            int len=ver[i].list.size();
            System.out.print(ver[i].n+"-->");

            for(int j=0;j<len;j++)
                System.out.print(ver[i].list.get(j)+"-->");

            System.out.println("");
        }

    }


    public static int random()
    {
        //生成随机数1-len_v
        int i=(int) (Math.random()*len_v+1);
        return i;
    }

    //构造len_v个点的邻接链表
    public static Vertex create(int n)
    {
        return new Vertex(n);
    }

    //删除边
    public static void delete(Vertex u,Vertex v)
    {
        for(int i=0;i<u.list.size();i++)
            if(u.list.get(i).equals(v.n))
                u.list.remove(i);
    }

    //非递归DFS去除有向图中的环
    public static void Qu_huan(Vertex v)
    {
        visited2[v.n-1]=true;  //将参数中对应点标为已读
        s.add(v);  //将初始点加入栈中

        while(!s.empty())
        {
            while(num[v.n-1]<v.list.size())
            {
                //System.out.println("打印：");
                c[v.n-1]=-1;
                Vertex tem=ver[(int)v.list.get(num[v.n-1])-1]; //定义为该点的下一个点

                //System.out.println(v.list.size());
                if(visited2[tem.n-1]==false)
                {
                    s.add(tem);  //将该点邻接表中第一个（未读）邻接点放入栈中
                    num[v.n-1]++;  //下一次搜索时，避免搜索已遍历过的点
                    visited2[tem.n-1]=true; //把刚才放入的点标记为已读
                }

                else
                {
                    if(c[tem.n-1]==-1)
                    {
                        delete(v,tem);  //删除成环的边
                    }
                    else
                        num[v.n-1]++;
                }

                v=s.peek();  // v=刚才放入的那个点（即继续向下一层进行while寻找）
                //System.out.println(num[v.n-1]+"**"+v.list.size());

            }
            c[v.n-1]=1;

            s.pop();  //这一条深度搜寻链已到底，将最底下的那个点从栈中删除

            if(!s.empty())
                v=s.peek();  //回溯到上一个点
        }

    }

    public static void main(String args[])
    {
        csh();  //初始化
        print(ver);  //打印邻接链表

        csh1();
        System.out.println("");

        for(int i=0;i<len_v;i++)
        {
            num[i]=0;
            c[i]=0;
        }

        for(int i=0;i<len_v;i++)
        {
            if(!s.contains(ver[i]))
                Qu_huan(ver[i]);  //去环
        }

        System.out.println("去环：");
        print(ver);


    }

}

