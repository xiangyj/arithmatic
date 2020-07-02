package common;

public class ClassLazyInitDemo {

    public static void main(String[] args) {
        // 访问类本身，仅仅是类呗JVM加载，而没有初始化
        System.out.println(Collaborator.class.toString());
        // 静态属性加载，执行类的静态代码块static{}
        System.out.println(Collaborator.number);
        System.out.println(Collaborator.flag);
    }
    static class Collaborator {
        static int number = 1;
        static boolean flag = true;
        static {
            System.out.println("Collaborator init...");
        }
    }

}
